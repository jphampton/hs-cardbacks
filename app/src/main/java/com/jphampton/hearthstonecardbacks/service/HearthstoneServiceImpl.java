package com.jphampton.hearthstonecardbacks.service;

import android.util.Log;

import com.jphampton.hearthstonecardbacks.models.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HearthstoneServiceImpl implements HearthstoneService {

    @Override
    public Disposable getRankedCards(Consumer<List<Card>> onCompletion) {
        Disposable subscribe = Single.<List<Card>>create(e -> {
            List<Card> cardbacks = getCardList();
            e.onSuccess(cardbacks);
        })
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onCompletion);
        return subscribe;
    }

    private String getCardString() {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://omgvamp-hearthstone-v1.p.mashape.com/cardbacks");
            URLConnection urlConnection = setToken(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();

            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Card> getCardList() {
        String cardString = getCardString();
        JSONArray cardJSON = null;
        List<Card> cardList = new ArrayList<Card>();
        try {
            cardJSON = new JSONArray(cardString);
            for(int i=0; i < cardJSON.length();i++) {
                JSONObject currJSON = cardJSON.getJSONObject(i);
                Card.Builder builder = new Card.Builder();
                builder.setId(currJSON.getInt("id"))
                .setName(currJSON.getString("name"))
                .setDescription(currJSON.getString("description"))
                .setSource(currJSON.getString("source"))
                .setSourceDescription(currJSON.getString("sourceDescription"))
                .setEnabled(currJSON.getBoolean("enabled"))
                .setImgURL(currJSON.getString("imgURL"))
                .setImgAnimatedURL(currJSON.getString("imgAnimatedURL"))
                .setSortCategory(currJSON.getInt("sortCategory"))
                .setSortOrder(currJSON.getInt("sortOrder"))
                .setLocale(currJSON.getString("locale"));
                Card newCard = builder.build();
                cardList.add(newCard);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cardList;
    }

    private URLConnection setToken(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = "samplekey";
        urlConnection.setRequestProperty("X-Mashape-Key", authString);
        return urlConnection;
    }
}