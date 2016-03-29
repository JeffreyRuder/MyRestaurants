package com.epicodus.myrestaurants.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.ui.RestaurantDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 3/29/16.
 */
public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.restaurantImageView)
    ImageView mRestaurantImageView;
    @Bind(R.id.restaurantNameTextView)TextView mNameTextView;
    @Bind(R.id.categoryTextView) TextView mCategoryTextView;
    @Bind(R.id.ratingTextView) TextView mRatingTextView;
    private Context mContext;
    ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    public RestaurantViewHolder(View itemView, ArrayList<Restaurant> restaurants) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mRestaurants = restaurants;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
        intent.putExtra("position", getLayoutPosition() + "");
        intent.putExtra("restaurants", Parcels.wrap(mRestaurants));
        mContext.startActivity(intent);
    }

    public void bindRestaurant(Restaurant restaurant) {
        mNameTextView.setText(restaurant.getName());
        mCategoryTextView.setText(restaurant.getCategories().get(0));
        mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        Picasso.with(mContext).load(restaurant.getImageUrl()).into(mRestaurantImageView);
    }
}