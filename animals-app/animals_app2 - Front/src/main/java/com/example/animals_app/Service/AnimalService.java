package com.example.animals_app.Service;

import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.exceptions.AlreadyExistException;
import com.example.animals_app.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;

@Service
public class AnimalService<T extends AnimalEntity> {

    @Autowired
    private RestClient restClient;

    public static final String URI="http://localhost:8080/animals";

    public AnimalService(RestClient restClient){
        this.restClient=restClient;
    }


    public List<AnimalEntity> findAll(){
        return restClient.get()
                .uri(URI+"/getAll")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public void registration(AnimalEntity animal) throws AlreadyExistException {
                    restClient
                    .post()
                    .uri(URI+"/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(animal)
                    .retrieve();
    }

    public AnimalEntity findAnimal(Long id) throws NotFoundException {
        return restClient.get()
                .uri(URI+"/get?id="+id)
                .retrieve()
                .body(AnimalEntity.class);
    }

    public void updateAnimal(Long id, AnimalEntity animal) throws NotFoundException{
        restClient.post()
                .uri(URI+"/editBack/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(animal)
                .retrieve();
    }

    public void registrationAll(List<AnimalEntity> animal) throws AlreadyExistException {
        throw new AlreadyExistException("");
//        restClient
//                .post()
//                .uri(URI+"/all")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(animal)
//                .retrieve();
    }

    public void deleteAnimal(Long id) throws NotFoundException{
          restClient.get()
                .uri(URI+"/deleteBack/"+id)
                 .retrieve();
    }
}
