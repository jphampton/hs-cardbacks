package com.jphampton.hearthstonecardbacks.service;

import android.util.Log;

import androidx.annotation.Nullable;
import com.endpoints.cardback.cardback.Cardback;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Optional;
import com.jphampton.hearthstonecardbacks.models.Card;

import java.time.Month;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HearthstoneServiceImpl implements HearthstoneService {

  @Override
  public Disposable getCardByDate(Month month, int year, Consumer<Optional<Card>> onCompletion) {
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
  private Card getCardbackFromServer(Month month, int year) {
    Cardback.Builder builder = new Cardback.Builder(
        AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
    Cardback service = builder.build();
    com.endpoints.cardback.cardback.model.Cardback curr = null;
    try {
       curr = service.currentCardback(month.toString(), year).setKey(PrivateConstants.API_KEY).execute();
    } catch (Exception e) {
      Log.e("card fetch", e.getMessage());
    }
    return curr == null ? null : cardbackToCard(curr);
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
}