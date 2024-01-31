package com.example.animals_app.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AnimalEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String name;
    private int cutenessLvl;

    public AnimalEntity(){}

    public AnimalEntity(AnimalEntity animal) {
        this.setId(animal.getId());
        this.setName(animal.getName());
        this.setType(animal.getType());
        this.setCutenessLvl(animal.getCutenessLvl());
    }

    public AnimalEntity(Long id, String type, String name, int cutenessLvl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cutenessLvl = cutenessLvl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCutenessLvl() {
        return cutenessLvl;
    }

    public void setCutenessLvl(int cutenessLvl) {
        this.cutenessLvl = cutenessLvl;
    }
}
