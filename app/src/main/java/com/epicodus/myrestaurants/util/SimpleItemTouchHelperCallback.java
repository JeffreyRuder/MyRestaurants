package com.epicodus.myrestaurants.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    //Constructor that takes an ItemTouchHelperAdapter. When implemented in our
    //FirebaseRestaurantListAdapter, this adapter passes the event back to the
    //Firebase adapter where we can then deal with what happens when an
    //item is moved or dismissed
    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    //Tells the ItemTouchHelper that drag gesture is enabled.
    //If set to 'false', we can turn drag detection off.
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //Tells the ItemTouchHelper that swipe is enabled.
    //If set to 'false', we can turn swipe detection off.
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    //getMovementFlags tells the ItemTouchHelper which movement directions will be supported.
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
                          RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        //Notifies the adapter that an item moved. This triggers the onItemMove override in our
        //Firebase adapter where we will eventually update the restaurants ArrayList to save the new position.
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        //Notifies the adapter that an item was dismissed.
        //This triggers the onItemDismiss override in our Firebase adapter
        //where we will eventually delete the item from the database.
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    //Triggers the callback in the ItemTouchHelperViewHolder which
    //will be sent to our RestaurantListViewHolder.
    //In the onItemSelected override in our RestaurantListViewHolder,
    //we can animate/change the appearance of the selected item.
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //Changes only the active item
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                //Tells the viewHolder that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    //Triggers the callback in the ItemTouchHelperViewHolder which
    //will be sent to our RestaurantListViewHolder.
    //In the clearView override in our RestaurantListViewHolder,
    //the item that was selected will still have the animations/appearance
    //changes assigned in the onItemSelected method so we will need to
    //change it back to normal.
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            //Tells the view holder to return the item back to it's normal appearance.
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}