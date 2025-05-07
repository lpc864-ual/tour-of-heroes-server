package com.example.demo.dto;

import java.util.List;

public class HeroDTO {
    private long id;
    private String name;
    private List<String> powers;
    private String imageUrl;

    public HeroDTO() {
    }

    public HeroDTO(long id, String name, List<String> powers) {
        this.id = id;
        this.name = name;
        this.powers = powers;
    }

    public HeroDTO(long id, String name, List<String> powers, String imageUrl) {
        this.id = id;
        this.name = name;
        this.powers = powers;
        this.imageUrl = imageUrl;
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

    public List<String> getPowers() {
        return powers;
    }

    public void setPowers(List<String> powers) {
        this.powers = powers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}