package com.example.exerciseapp;

public class Exercise {

    private String name;
    private int reps;
    private Boolean outdoor;

    public Exercise(){

    }

    public Exercise(String name, int reps, Boolean outdoor){
        this.name = name;
        this.reps = reps;
        this.outdoor = outdoor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setOutdoor(Boolean outdoor) {
        this.outdoor = outdoor;
    }

    public Boolean getOutdoor() {
        return outdoor;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }
}
