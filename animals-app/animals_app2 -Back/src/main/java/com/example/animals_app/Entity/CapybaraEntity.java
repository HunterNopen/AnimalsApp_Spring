package com.example.animals_app.Entity;

import jakarta.persistence.Entity;

@Entity
public class CapybaraEntity extends AnimalEntity{

    //private String type;
    public CapybaraEntity(){
        super();
    }

    public CapybaraEntity(AnimalEntity other) {
        this.setName(other.getName());
        this.setType(other.getType());
        this.setCutenessLvl(other.getCutenessLvl());
    }

    public String moan(){
        return "Oink-oink-oink...";
    }

//    @Override
//    public String getType() {
//        return type;
//    }

}
