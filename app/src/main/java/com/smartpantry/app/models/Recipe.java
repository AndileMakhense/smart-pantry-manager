package com.smartpantry.app.models;

public class Recipe {

    private long id;
    private String name;
    private String steps;

    // Constructor for a new recipe, not yet saved to the database (no id yet)
    public Recipe(String name, String steps) {
        this.name = name;
        this.steps = steps;
    }

    // Constructor for a recipe loaded from the database (already has an id)
    public Recipe(long id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
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

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}