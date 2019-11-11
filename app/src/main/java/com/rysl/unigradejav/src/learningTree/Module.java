package com.rysl.unigradejav.src.learningTree;

public class Module implements Learner{
    private String name;
    private int key;
    private Learner parent;
    private final String[] subID = {"modID", "asgID"};
    private final String subTable = "link_mod_asg";
    private final String table = "module";
    private int workingPercent;

     public Module(int key, String name, Learner parent){
        this.key = key;
        this.name = name;
        this.parent = parent;
     }

    @Override
     public String getName(){
         return this.name;
     }

    @Override
     public int getPercentage(){
         return -1;
     }

    @Override
    public int getKey(){
        return this.key;
    }

    @Override
    public String getAll(){
         return this.table + ", " + this.key + " " + this.name;
    }

    @Override
    public Boolean getType(){
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
