package com.example.animals_app;

import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.Service.AnimalService;
import com.example.animals_app.controller.RestAnimalController;
import com.example.animals_app.exceptions.AlreadyExistException;
import com.example.animals_app.exceptions.CustomResponseEntityExceptionHandler;
import com.example.animals_app.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Import(value = CustomResponseEntityExceptionHandler.class)
public class AnimalControllerTest {
    private MockMvc mockMvc;
    @Mock
    private AnimalService animalService;
    @InjectMocks
    private RestAnimalController controller;

    @BeforeEach
    public void setup(){
        this.mockMvc= MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void registrationReturns200() throws Exception{
        AnimalEntity animal = new AnimalEntity(2L, "krowa", "moo", 3);
        when(animalService.registration(any(AnimalEntity.class))).thenReturn(animal);

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/registration").contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"2\",\"name\": \"moo\", \"type\": \"Other\", \"cutenessLvl\":  \"3\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void registrationAllReturns200() throws Exception{
        List<AnimalEntity> animalEntities=List.of(new AnimalEntity(), new AnimalEntity());
        when(animalService.registration(any(AnimalEntity.class))).thenReturn(animalEntities.get(1));

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/all").contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": \"2\",\"name\": \"moo\", \"type\": \"Other\", \"cutenessLvl\":  \"3\"}, " +
                                "{\"id\": \"3\",\"name\": \"bark\", \"type\": \"Other\", \"cutenessLvl\":  \"3\"}]")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdReturns200WhenAnimalIsPresent() throws Exception{
        AnimalEntity animal = new AnimalEntity(2L, "krowa", "moo", 3);
        when(animalService.findAnimal(2L)).thenReturn(animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/get?id=2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("moo"))
                .andExpect(jsonPath("$.type").value("krowa"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAnimalReturns200() throws Exception{
        List<AnimalEntity> animalEntities=List.of(new AnimalEntity(1L,"krowa", "moo",10), new AnimalEntity());
        when(animalService.findAll()).thenReturn(animalEntities);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/getAll").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].name").value("moo"))
                .andExpect(jsonPath("$.[0].type").value("krowa"))
                .andExpect(jsonPath("$.[0].cutenessLvl").value("10"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByCutenessLvlReturns200WhenAnimalIsPresent() throws Exception{
        AnimalEntity animal = new AnimalEntity(2L, "krowa", "moo", 3);
        when(animalService.findAllByCuteness(3)).thenReturn(List.of(animal));

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/findByCutenessLvl/3").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].name").value("moo"))
                .andExpect(jsonPath("$.[0].type").value("krowa"))
                .andExpect(jsonPath("$.[0].cutenessLvl").value("3"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAnimalReturns200() throws Exception{
        AnimalEntity animal = new AnimalEntity(2L, "krowa", "moo", 3);
        when(animalService.updateAnimal(any(Long.class),any(AnimalEntity.class))).thenReturn(animal);

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/editBack/2").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"2\",\"name\": \"moo\", \"type\": \"Other\", \"cutenessLvl\":  \"3\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("moo"))
                .andExpect(jsonPath("$.type").value("krowa"))
                .andExpect(jsonPath("$.cutenessLvl").value("3"))
                .andExpect(status().isOk());
    }

    @Test
    public void check400WhenNoneToDelete() throws Exception{
       given(animalService.deleteAnimal(any(Long.class))).willThrow(new NotFoundException("aAAA"));

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/deleteBack/3").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void check400WhenNoneToUpdate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/animals/editBack/3").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void check400IsReturnedWhenAnimalIsAlreadyThere() throws Exception{
        Mockito.doThrow(new AlreadyExistException("Animal already exists")).when(animalService).registration(any(AnimalEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"2\",\"name\": \"moo\", \"type\": \"Other\", \"cutenessLvl\":  \"3\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



}
