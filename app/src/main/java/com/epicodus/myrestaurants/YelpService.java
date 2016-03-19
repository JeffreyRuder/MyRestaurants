package com.epicodus.myrestaurants;

import android.content.Context;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by Jeffrey on 3/19/2016.
 */
public class YelpService {
    private Context mContext;

    public YelpService(Context context) {
        this.mContext = context;
    }

    public void findRestaurants(String location, Callback callback) {
        String CONSUMER_KEY = mContext.getString(R.string.consumer_key);
        String CONSUMER_SECRET = mContext.getString(R.string.consumer_secret);
        String TOKEN = mContext.getString(R.string.token);
        String TOKEN_SECRET = mContext.getString(R.string.token_secret);
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.yelp.com/v2/search?term=food").newBuilder();
        urlBuilder.addQueryParameter("location", location);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
