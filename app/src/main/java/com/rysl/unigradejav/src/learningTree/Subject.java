package com.rysl.unigradejav.src.learningTree;

public class Subject implements Learner{
    private String name;
    private int key;
    private Learner parent;
    private final String[] subID = {"subID", "modID"};
    private final String subTable = "link_sub_mod";
    private final String table = "subject";
    private int workingPercent;

    public Subject(int key, String name){
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
        return -1;
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
    public void setWorkingPercent(int percent){
        this.workingPercent = percent;
    }

    @Override
    public int getWorkingPercent(){
        return this.workingPercent;
    }
}
