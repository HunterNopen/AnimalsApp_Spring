package com.example.animals_app.controller;

import com.example.animals_app.Service.AnimalService;
import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.exceptions.AlreadyExistException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class RestAnimalController {

    private AnimalService<AnimalEntity> animalService;

    @Autowired
    public RestAnimalController(AnimalService<AnimalEntity> animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody AnimalEntity animal){
            animalService.registration(animal);
            return ResponseEntity.ok().body("Welcome newbie!");
    }

    @PostMapping("/all")
    public ResponseEntity<String> registrationAll(@RequestBody List<AnimalEntity> animal){
        String endMessage = "Welcome .... HOW MANY OF YOU!\n";
        animalService.registrationAll(animal);
        return ResponseEntity.ok().body(endMessage.trim());
    }

    @GetMapping(value = "/get")
    public ResponseEntity<AnimalEntity> getAnimal(@PathParam("id") Long id){
        return ResponseEntity.ok().body(animalService.findAnimal(id));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<AnimalEntity>> getAllAnimal(){
        return ResponseEntity.ok().body(animalService.findAll());
    }

    @GetMapping(value = "/cutenessLvl")
    public ResponseEntity<List<AnimalEntity>> getCuteAnimalsChart(){
        return ResponseEntity.ok().body(animalService.filterByCuteness());
    }

    @GetMapping(value = "/findByCutenessLvl/{lvl}")
    public ResponseEntity<List<AnimalEntity>> findByCutenessLvl(@PathVariable int lvl){
        return ResponseEntity.ok().body(animalService.findAllByCuteness(lvl));
    }

    @PostMapping("/editBack/{id}")
    public ResponseEntity<AnimalEntity> updateAnimal(@PathVariable Long id, @RequestBody AnimalEntity animal){
            return ResponseEntity.ok().body(animalService.updateAnimal(id, animal));
    }

    @GetMapping("/deleteBack/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Long id){
            return ResponseEntity.ok().body("The "+animalService.deleteAnimal(id).getName()+" is gone...");
    }
}
