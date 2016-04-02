package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.epicodus.myrestaurants.MyRestaurantsApplication;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.adapters.FirebaseRestaurantListAdapter;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.util.OnStartDragListener;
import com.epicodus.myrestaurants.util.SimpleItemTouchHelperCallback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends AppCompatActivity implements OnStartDragListener {
    private Query mQuery;
    private Firebase mFirebaseRef;
    private String mCurrentUserUid;
    private FirebaseRestaurantListAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        ButterKnife.bind(this);

        Firebase.setAndroidContext(this);
        mFirebaseRef = MyRestaurantsApplication.getAppInstance().getFirebaseRef();
        mCurrentUserUid = mFirebaseRef.getAuth().getUid();

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Restaurant restaurant : mAdapter.getItems()) {
            restaurant.setIndex(Integer.toString(mAdapter.getItems().indexOf(restaurant)));
            mFirebaseRef.child("restaurants/" + mCurrentUserUid + "/"
                    + restaurant.getName())
                    .setValue(restaurant);
        }
    }

    private void setUpFirebaseQuery() {
        mQuery = mFirebaseRef.child("restaurants/" + mFirebaseRef.getAuth().getUid()).orderByChild("index");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseRestaurantListAdapter(mQuery, Restaurant.class, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                this.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void logout() {
        mFirebaseRef.unauth();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}