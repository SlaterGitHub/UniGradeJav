package com.rysl.unigradejav.src.learningTree;

public interface Learner {

    Learner getParent();

    String getName();

    int getPercentage();

    Boolean getType();

    int getKey();

    String getAll();

    String getSubTable();

    String[] getSubID();

    String getTable();

    int getWorkingPercent();

    void setWorkingPercent(int percent);
}
