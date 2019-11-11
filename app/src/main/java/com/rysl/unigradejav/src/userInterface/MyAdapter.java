package com.rysl.unigradejav.src.userInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rysl.unigradejav.R;
import com.rysl.unigradejav.src.learningTree.Learner;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Learner> dataset;
    private static RecyclerViewClickListener itemListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView nameContent, percentContent, typeContent, addButton, workingPercent;
        public CardView cv;

        public MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            nameContent = view.findViewById(R.id.nameContent);
            percentContent = view.findViewById(R.id.percentContent);
            typeContent = view.findViewById(R.id.typeContent);
            cv = view.findViewById(R.id.card);
            addButton = view.findViewById(R.id.addButton);
            workingPercent = view.findViewById(R.id.currentWorkingPercentage);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v){
            itemListener.recyclerViewListClicked(this.getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v){
            itemListener.recyclerViewListLongClicked(this.getLayoutPosition());
            return false;
        }
    }

    public MyAdapter(ArrayList<Learner> MyDataset, RecyclerViewClickListener itemListener){
        dataset = MyDataset;
        this.itemListener = itemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder hold, final int position){
        Learner learner = dataset.get(position);
        if (learner.getKey() != -1) {
            hold.nameContent.setText(learner.getName());
            hold.addButton.setText("");
        } else {
            hold.addButton.setText("+");
            hold.nameContent.setText("");
        }
        if (learner.getPercentage() != -1) {
            hold.workingPercent.setText("");
            hold.percentContent.setText(learner.getPercentage() + "%");
        } else {
            if(learner.getKey() != -1){
                hold.workingPercent.setText("current percentage: " + learner.getWorkingPercent());
            } else{
                hold.workingPercent.setText("");
            }
            hold.percentContent.setText("");
        }
        if (learner.getType() != null) {
            if (learner.getType()) {
                hold.typeContent.setText("Test");
            } else {
                hold.typeContent.setText("Coursework");
            }
        } else {
            hold.typeContent.setText("");
        }
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }
}
