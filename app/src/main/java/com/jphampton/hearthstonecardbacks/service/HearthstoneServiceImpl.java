package com.jphampton.hearthstonecardbacks.service;

import android.support.annotation.Nullable;
import android.util.Log;

import com.endpoints.cardback.cardback.Cardback;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Optional;
import com.jphampton.hearthstonecardbacks.models.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HearthstoneServiceImpl implements HearthstoneService {

    @Override
    public Disposable getRankedCards(Consumer<List<Card>> onCompletion) {
        return Single.<List<Card>>create(e -> {
            List<Card> cardbacks = getCardList();
            e.onSuccess(cardbacks);
        })
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onCompletion);
    }

    @Override
    public Disposable getCardByDate(Month month, int year, Consumer<Optional<Card>> onCompletion) {
        Log.e("waterlily", "getting card by date");
        return Single.<Optional<Card>>create(e -> {
            @Nullable Card cardback = getCardbackFromServer(month, year);
            e.onSuccess(Optional.fromNullable(cardback));
        })
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onCompletion);
    }

    @Nullable
    private Card getCardbackFromServer(Month month, int year) throws IOException {
        // TODO: call our server endpoint
        Cardback.Builder builder = new Cardback.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        Cardback service = builder.build();
        com.endpoints.cardback.cardback.model.Cardback curr = service.currentCardback(month.toString(), year).setKey(PrivateConstants.API_KEY).execute();
        return cardbackToCard(curr);
    }

    private String getCardString() {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://omgvamp-hearthstone-v1.p.mashape.com/cardbacks");
            URLConnection urlConnection = setMashapeToken(url);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
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

    private List<Card> getCardList() {
        String cardString = getCardString();
        JSONArray cardJSON;
        List<Card> cardList = new ArrayList<>();
        try {
            cardJSON = new JSONArray(cardString);
            for (int i = 0; i < cardJSON.length(); i++) {
                JSONObject currJSON = cardJSON.getJSONObject(i);
                cardList.add(jsonToCard(currJSON));
            }
        } catch (JSONException e) {
            Log.w("JSON", "Failed to parse JSON.\n" + e);
        }
        return cardList;
    }

    private Card jsonToCard(JSONObject json) throws JSONException {
        return new Card.Builder()
                .setId(json.getInt("id"))
                .setName(json.getString("name"))
                .setDescription(json.getString("description"))
                .setSource(json.getString("source"))
                .setSourceDescription(json.getString("sourceDescription"))
                .setEnabled(json.getBoolean("enabled"))
                .setImgURL(json.getString("imgURL"))
                .setImgAnimatedURL(json.getString("imgAnimatedURL"))
                .setSortCategory(json.getInt("sortCategory"))
                .setSortOrder(json.getInt("sortOrder"))
                .setLocale(json.getString("locale"))
                .build();
    }

    private Card cardbackToCard(com.endpoints.cardback.cardback.model.Cardback cardback) {
        return new Card.Builder()
                .setImgURL(cardback.getImgURL())
                .setId(cardback.getId())
                .setName(cardback.getMashapeName())
                .setDescription("")
                .setEnabled(true)
                .setImgAnimatedURL("")
                .setLocale("")
                .setSortCategory(0)
                .setSortOrder(0)
                .setSource("")
                .setSourceDescription("").build();
    }

    private URLConnection setMashapeToken(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = "samplekey";
        urlConnection.setRequestProperty("X-Mashape-Key", authString);
        return urlConnection;
    }

    private URLConnection setGcsToken(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = "samplekey";
        urlConnection.setRequestProperty("Authorization", authString);
        return urlConnection;
    }
}