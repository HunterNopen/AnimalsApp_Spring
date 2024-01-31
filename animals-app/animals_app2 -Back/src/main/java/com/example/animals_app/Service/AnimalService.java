package com.example.animals_app.Service;

import com.example.animals_app.Entity.AnimalEntity;
import com.example.animals_app.Entity.CapybaraEntity;
import com.example.animals_app.Entity.OtterEntity;
import com.example.animals_app.exceptions.AlreadyExistException;
import com.example.animals_app.exceptions.NotFoundException;
import com.example.animals_app.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService<T extends AnimalEntity> {

    @Autowired
    private AnimalRepository<AnimalEntity> animalRepository;

    public AnimalEntity registration(AnimalEntity animal) throws AlreadyExistException {
            if(animalRepository.findByNameAndType(animal.getName(), animal.getType())!=null || animalRepository.findByNameAndType(animal.getName(), "Other")!=null ){
                throw new AlreadyExistException("Oh, you again?");
            }

        switch (animal.getType()) {
            case "Otter" -> {
                OtterEntity animalResult = new OtterEntity(animal);
               return animalRepository.save(animalResult);
            }
            case "Capybara" -> {
                CapybaraEntity animalResult = new CapybaraEntity(animal);
               return animalRepository.save(animalResult);
            }
            default -> {
                AnimalEntity animalResult=new AnimalEntity(animal);
               return animalRepository.save(animalResult);
            }
        }

    }

    public void registrationAll(List<AnimalEntity> animal) throws AlreadyExistException{
        throw new AlreadyExistException("");
//        try{
//        animal.forEach(this::registration);
//        } catch (AlreadyExistException e){
//            throw new AlreadyExistException("");
//        }
    }

    public AnimalEntity findAnimal(Long id) throws NotFoundException {
            Optional<AnimalEntity> optionalAnimal = animalRepository.findById(id);
            if (optionalAnimal.isPresent()){
                return optionalAnimal.get();
            }
        else throw new NotFoundException("Whoops, no-one is here...");
    }

    public AnimalEntity updateAnimal(Long id, AnimalEntity animal) throws NotFoundException {
        Optional<? extends AnimalEntity> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isPresent()){
            animal.setId(id);
            return animalRepository.save(animal);
        }else throw new NotFoundException("Who is this?");
    }

    public AnimalEntity deleteAnimal(Long id) throws NotFoundException {
        Optional<? extends AnimalEntity> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isPresent()){
            AnimalEntity animalEntity=optionalAnimal.get();
            animalRepository.deleteById(id);
            return animalEntity;
        }else throw new NotFoundException("Who is this?");
    }

    public List<AnimalEntity> findAllByCuteness(int lvl) throws NotFoundException{
        List<AnimalEntity> optionalAnimal = animalRepository.findAllByCutenessLvl(lvl);
        if (!optionalAnimal.isEmpty()){
            return optionalAnimal;
        }
        else throw new NotFoundException("Whoops, no-one is here...");
    }

    public List<AnimalEntity> filterByCuteness() throws NotFoundException{
        List<AnimalEntity> optionalAnimal = (List<AnimalEntity>) animalRepository.findAll();
        if (!optionalAnimal.isEmpty()){
            return optionalAnimal.stream().sorted((a, b)-> Integer.compare(a.getCutenessLvl(), b.getCutenessLvl())).toList();
        }
        else throw new NotFoundException("Whoops, no-one is here...");
    }

    public List<AnimalEntity> findAll(){
        return (List<AnimalEntity>) animalRepository.findAll();
    }
}
