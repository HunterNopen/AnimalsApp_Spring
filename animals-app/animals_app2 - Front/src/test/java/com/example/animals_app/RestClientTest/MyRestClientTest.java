package com.example.animals_app.RestClientTest;

import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.Service.AnimalService;
import com.example.animals_app.exceptions.AlreadyExistException;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest
public class MyRestClientTest {

    MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    RestClient.Builder builder = RestClient.builder();
    AnimalService service;

    private final static String URL="http://localhost:8080/animals/";

    @BeforeEach
    public void setUp() {
        customizer.customize(builder);
        service = new AnimalService(builder.build());
    }

    @Test
    public void testFindById() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"get?id=1"))
                .andRespond(withSuccess("""
                    {"name":"Neuvi", "type": "Dragon", "cutenessLvl": 13}
                    """, MediaType.APPLICATION_JSON));

        AnimalEntity dto = service.findAnimal(1L);

        assertAll(
                ()->assertEquals("Neuvi", dto.getName()),
                ()->assertEquals("Dragon", dto.getType()),
                ()->assertEquals(13, dto.getCutenessLvl())
                );
    }

    @Test
    public void testNotFoundExceptionForId(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"get?id=1"))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, ()->service.findAnimal(1L));
    }

    @Test
    public void testGetAll(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"getAll"))
                .andRespond(withSuccess("""
                        [{"name":"Grogg", "type": "Capybara", "cutenessLvl": 13},
                        {"name":"Neuvi", "type": "Dragon", "cutenessLvl": 13}]
                    """, MediaType.APPLICATION_JSON));

        List<AnimalEntity> dto = service.findAll();

        assertAll(
                ()->assertEquals("Grogg", dto.get(0).getName()),
                ()->assertEquals("Capybara", dto.get(0).getType()),
                ()->assertEquals(13, dto.get(0).getCutenessLvl()),
                ()->assertEquals("Neuvi", dto.get(1).getName()),
                ()->assertEquals("Dragon", dto.get(1).getType()),
                ()->assertEquals(13, dto.get(1).getCutenessLvl())
        );
    }

    @Test
    public void testAddAnimal(){
        AnimalEntity animal=new AnimalEntity(1L,"Capybara","Grogg",10);

        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"registration"))
                .andRespond(withSuccess());

        service.registration(animal);
    }

    @Test
    public void testAddAllAnimal(){
        List<AnimalEntity> list=List.of(new AnimalEntity(1L,"Capybara","Grogg",10),new AnimalEntity(2L,"Otter","Neuvi",10));

        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"all"))
                .andRespond(withSuccess());

        service.registrationAll(list);
    }

    @Test
    public void testAlreadyExistThrownWhenAddAllAnimal(){
        List<AnimalEntity> list=List.of(new AnimalEntity(1L,"Capybara","Grogg",10),new AnimalEntity(1L,"Capybara","Grogg",10),new AnimalEntity(1L,"Capybara","Grogg",143));

        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"all"))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.BAD_REQUEST));

        assertThrows(AlreadyExistException.class, ()->service.registrationAll(list));
    }

    @Test
    public void testDeleteAnimal(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"deleteBack/1"))
                .andRespond(withSuccess());

        service.deleteAnimal(1L);
    }

    @Test
    public void testUpdateAnimal(){
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(URL+"editBack/1"))
                .andRespond(withSuccess());

        service.updateAnimal(1L,new AnimalEntity());
    }
}
