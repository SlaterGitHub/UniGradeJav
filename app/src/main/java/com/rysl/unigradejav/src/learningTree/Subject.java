package com.rysl.unigradejav.src.learningTree;

import java.util.ArrayList;

public class Subject implements Learner{
    private String name;
    private int key;
    private Learner parent;
    private final String[] subID = {"subID", "modID"};
    private final String subTable = "link_sub_mod";
    private final String table = "subject";
    private double workingPercent;
    private int percent = -2;

    public Subject(int key, String name){
        if(key == -2){
            percent = -1;
        }
        this.key = key;
        this.name = name;
        this.parent = null;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public int getKey(){
        return this.key;
    }

    @Override
    public String getAll(){
        return this.table + ", " +this.key + " " + this.name;
    }

    @Override
    public int getPercentage() {
        return percent;
    }

    @Override
    public Boolean getType() {
        return null;
    }

    @Override
    public Learner getParent(){ return this.parent; }

    @Override
    public String getSubTable(){
        return this.subTable;
    }

    @Override
    public String[] getSubID(){return this.subID;}

    @Override
    public String getTable(){ return this.table; }

    @Override
    public void setWorkingPercent(double percent){
        this.workingPercent = percent;
    }

    @Override
    public double getWorkingPercent(){
        return this.workingPercent;
    }
}
