package com.rysl.unigradejav.src.userInterface;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rysl.unigradejav.src.AdvanceDataTypes.Stack_L;
import com.rysl.unigradejav.src.learningTree.Learner;
import com.rysl.unigradejav.src.learningTree.Subject;

import java.util.ArrayList;


public class RecyclerViewInterface extends View {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private Context context;
    public ArrayList<Learner> learners = new ArrayList<>();
    private Stack_L stack = new Stack_L();
    Subject addButton = new Subject(-1, "+");

    public RecyclerViewInterface(Context context, RecyclerView view, ArrayList<Learner> learners, RecyclerViewClickListener clickListener){
        super(context);
        this.learners.add(addButton);
        this.learners.addAll(learners);
        this.context = context;
        this.recyclerView = view;
        this.manager = new LinearLayoutManager(this.context);
        this.adapter = new MyAdapter(this.learners, clickListener);
        this.recyclerView.setLayoutManager(this.manager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.adapter);
        SwiperController swipeController = new SwiperController(clickListener);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeController);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public void refreshView(ArrayList<Learner> learners){
        stack.push(this.learners);
        this.learners.clear();
        this.learners.add(addButton);
        this.learners.addAll(learners);
        this.adapter.notifyDataSetChanged();
    }

    public void addLearner(ArrayList<Learner> learners){
        int position = learners.size();
        this.learners.add(learners.get(position-1));
        this.adapter.notifyItemInserted(position);
    }

    public void updateLearner(Learner learner, int position){
        this.learners.set(position, learner);
        this.adapter.notifyItemChanged(position);
    }

    public void deleteLearner(int position){
        this.learners.remove(position);
        this.adapter.notifyItemRemoved(position);
    }
}
