package com.example.animals_app.model;

import com.example.animals_app.Entity.AnimalEntity;

public class Animal {
    private Long id;
    private String type;
    private String name;
    private int cutenessLvl;

    public Animal(Long id, String type, String name, int cutenessLvl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.cutenessLvl = cutenessLvl;
    }
    public Animal() {}

    public static Animal toModel(AnimalEntity animalEntity){
    Animal animal=new Animal();
    animal.setName(animalEntity.getName());
    animal.setType(animalEntity.getType());
    animal.setCutenessLvl(animalEntity.getCutenessLvl());
    return animal;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCutenessLvl() {
        return cutenessLvl;
    }

    public void setCutenessLvl(int cutenessLvl) {
        this.cutenessLvl = cutenessLvl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
