package com.epicodus.myrestaurants;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Jeffrey on 3/26/2016.
 */
public class MyRestaurantsApplication extends Application {
    private static MyRestaurantsApplication app;
    private Firebase mFirebaseRef;

    public static MyRestaurantsApplication getAppInstance() {
        return app;
    }

    public Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(this.getString(R.string.firebase_url));
    }
}