package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity 
@Table(name = "heroes") 
public class Hero {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private long id;

    @NotBlank(message = "Name is mandatory") 
    private String name;

    private String imageUrl;
    
    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Superpoder> powers = new ArrayList<>();

    public Hero() {
    }

    public Hero(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Superpoder> getPowers() {
        return powers;
    }
    
    public void setPowers(List<Superpoder> powers) {
        this.powers = powers;
    }
    
    // Métodos helper para mantener sincronizada la relación bidireccional
    public void addPower(Superpoder power) {
        powers.add(power);
        power.setHero(this);
    }
    
    public void removePower(Superpoder power) {
        powers.remove(power);
        power.setHero(null);
    }

    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Hero [id=" + id + ", name=" + name + "]";
    }
}