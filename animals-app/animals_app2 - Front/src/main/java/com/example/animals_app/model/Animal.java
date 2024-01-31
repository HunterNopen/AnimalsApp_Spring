package com.example.animals_app.model;

import com.example.animals_app.Entity.AnimalEntity;

public class Animal {
    private String type;
    private String name;
    private Integer cutenessLvl;

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
}
