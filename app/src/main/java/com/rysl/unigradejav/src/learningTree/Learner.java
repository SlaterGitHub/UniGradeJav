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

    double getWorkingPercent();

    void setWorkingPercent(double percent);
}
