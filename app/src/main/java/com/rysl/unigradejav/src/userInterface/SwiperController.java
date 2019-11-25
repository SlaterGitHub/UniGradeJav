package com.rysl.unigradejav.src.userInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

public class SwiperController extends ItemTouchHelper.Callback {
    RecyclerViewClickListener clickListener;

    public SwiperController(RecyclerViewClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if(viewHolder.getLayoutPosition() != 0 && !clickListener.menuVisible()) {
            return makeMovementFlags(0, LEFT | RIGHT);
        }
        return 0;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(viewHolder.getLayoutPosition() != 0) {
            clickListener.recyclerViewListSwiped(viewHolder.getLayoutPosition());
        }
    }
}
