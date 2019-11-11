package com.rysl.unigradejav.src.AdvanceDataTypes;

import com.rysl.unigradejav.src.learningTree.Learner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Stack_L {
    Stack<Learner> learnerStack = new Stack<>();
    ArrayList<Integer> sizes = new ArrayList<>();
    public Stack_L(){}

    public void push(ArrayList<Learner> learners){
        for(Learner learner : learners){
            learnerStack.push(learner);
        }
        sizes.add(learners.size());
    }

    public ArrayList<Learner> pop(){
        ArrayList<Learner> tempArray = new ArrayList<>();
        for(int x = 0; x<sizes.get(sizes.size()-1); x++){
            tempArray.add(learnerStack.pop());
        }
        sizes.remove(sizes.size()-1);
        Collections.reverse(tempArray);
        return tempArray;
    }
}
