package com.rysl.unigradejav.src.userInterface;

public interface RecyclerViewClickListener {
    public void recyclerViewListClicked(int position);

    public void recyclerViewListSwiped(int position);

    public void recyclerViewListLongClicked(int position);

    public boolean menuVisible();
}
