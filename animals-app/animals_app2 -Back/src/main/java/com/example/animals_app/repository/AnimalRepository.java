package com.example.animals_app.repository;


import com.example.animals_app.Entity.AnimalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository<T extends AnimalEntity> extends CrudRepository<T, Long> {
    T findByNameAndType(String name, String type);

    List<T> findAllByCutenessLvl(int cutenessLvl);
}
