package com.example.animals_app.Entity;

import jakarta.persistence.Entity;

@Entity
public class OtterEntity extends AnimalEntity{

    //private final String type=this.getClass().getSimpleName();
    public OtterEntity(){
        super();
    }

    public OtterEntity(AnimalEntity other) {
        this.setName(other.getName());
        this.setType(other.getType());
        this.setCutenessLvl(other.getCutenessLvl());
    }

    public String moan(){
        return "Kr-kr-kr...";
    }

//    @Override
//    public String getType() {
//        return type;
//    }
}
