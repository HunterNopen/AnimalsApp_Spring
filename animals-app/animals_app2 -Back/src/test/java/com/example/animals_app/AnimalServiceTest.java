package com.example.animals_app;

import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.Service.AnimalService;
import com.example.animals_app.exceptions.AlreadyExistException;
import com.example.animals_app.exceptions.NotFoundException;
import com.example.animals_app.repository.AnimalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnimalServiceTest {
    @Mock
    private AnimalRepository<AnimalEntity> animalRepository;
    private AutoCloseable openMocks;
    @InjectMocks
    private AnimalService animalService;

    @BeforeEach
    public void init(){
        openMocks= MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    public void findFinds(){
        AnimalEntity animalEntity=new AnimalEntity(1L,"fafafa","Groff",10);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));

        AnimalEntity result=animalService.findAnimal(1L);
        assertEquals(result, animalEntity);
    }

    @Test
    public void saveSaves(){
    AnimalEntity animalEntity=new AnimalEntity(1L,"fafafa","Groff",10);
    animalRepository.save(animalEntity);
    ArgumentCaptor<AnimalEntity> captor=ArgumentCaptor.forClass(AnimalEntity.class);
    when(animalRepository.save(captor.capture())).thenReturn(animalEntity);

    Mockito.verify(animalRepository, Mockito.times(1))
            .save(Mockito.any());
    AnimalEntity animalSaved=animalService.registration(animalEntity);
    assertEquals(animalEntity, animalSaved);
    }

    @Test
    public void testDeleteAnimalService(){
        AnimalEntity animalEntity=new AnimalEntity(1L,"fafafa","Groff",10);
        animalRepository.save(animalEntity);
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->animalService.deleteAnimal(1L));
    }

    @Test
    public void findAllByCuteness(){
        ArgumentCaptor<AnimalEntity> captor=ArgumentCaptor.forClass(AnimalEntity.class);
        List<AnimalEntity> animalEntities=List.of(new AnimalEntity(),new AnimalEntity());
        when(animalRepository.findAllByCutenessLvl(10)).thenReturn(animalEntities);
        assertEquals(animalEntities,animalService.findAllByCuteness(10));
    }

    @Test
    public void testUpdateService(){
        AnimalEntity animalEntity=new AnimalEntity(1L,"fafafa","Groff",10);
        animalRepository.save(animalEntity);
        AnimalEntity animalUpdate=new AnimalEntity(1L,"AHHAHA","Joker",10);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));
        when(animalRepository.save(animalUpdate)).thenReturn(animalUpdate);

        Assertions.assertEquals(animalUpdate, animalService.updateAnimal(1L,animalUpdate));
    }

    @Test
    public void alreadyExistsThrows(){
        AnimalEntity animal = new AnimalEntity(2L, "krowa", "moo", 3);
        when(animalRepository.findByNameAndType("moo", "krowa")).thenReturn(new AnimalEntity());

        assertThrows(AlreadyExistException.class, () -> animalService.registration(animal));
    }

    @Test
    public void notFoundThrows(){
        AnimalEntity animal = new AnimalEntity(2L, "krowa", "moo", 3);
        when(animalRepository.findByNameAndType("moo", "krowa")).thenReturn(animal);

        assertThrows(NotFoundException.class, () -> animalService.findAnimal(1L));
    }
}
