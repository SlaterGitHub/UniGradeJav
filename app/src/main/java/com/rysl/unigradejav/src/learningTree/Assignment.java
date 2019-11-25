package com.rysl.unigradejav.src.learningTree;

public class Assignment implements Learner {
    private String name;
    private int percentage;
    private Boolean type;
    private int key;
    private Learner parent;
    private final String[] subID = {"asgID"};
    private final String table = "assignment";

    public Assignment(int key, String name, int percentage, boolean type, Learner parent){
        this.key = key;
        this.name = name;
        this.percentage = percentage;
        this.type = type;
        this.parent = parent;
    }
    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public int getPercentage(){
        return this.percentage;
    }

    @Override
    public Boolean getType(){
        return this.type;
    }

    @Override
    public int getKey(){
        return this.key;
    }

    @Override
    public String getAll(){
        return this.table + ", " + this.key + " " + this.name + " " + this.percentage + " " + this.type;
    }

    @Override
    public Learner getParent(){ return this.parent; }

    @Override
    public String getSubTable(){ return null; }

    @Override
    public String[] getSubID(){return this.subID;}

    @Override
    public String getTable(){ return this.table; }

    @Override
    public double getWorkingPercent(){
        return -1;
    }

    @Override
    public void setWorkingPercent(double percent){
        return;
    }
}
