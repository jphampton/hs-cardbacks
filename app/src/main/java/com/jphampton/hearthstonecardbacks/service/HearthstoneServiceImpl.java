package com.jphampton.hearthstonecardbacks.service;

import android.util.Log;

import androidx.annotation.Nullable;
import com.endpoints.cardback.cardback.Cardback;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Optional;
import com.jphampton.hearthstonecardbacks.models.Card;
import com.squareup.okhttp.Credentials;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jphampton.hearthstonecardbacks.service.PrivateConstants.CLIENT_ID;
import static com.jphampton.hearthstonecardbacks.service.PrivateConstants.CLIENT_SECRET;

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
    BlizzardService.BlizzardAuthService authService = RetrofitClientInstance.getAuthRetrofit().create(BlizzardService.BlizzardAuthService.class);
    Call<BlizzardAuthResponse> authCall = authService.GetAuthToken(Credentials.basic(CLIENT_ID, CLIENT_SECRET));
    authCall.enqueue(new Callback<BlizzardAuthResponse>() {
      @Override
      public void onResponse(Call<BlizzardAuthResponse> call, Response<BlizzardAuthResponse> response) {
        BlizzardAuthResponse authResponse = response.body();
        BlizzardService.BlizzardCardService cardService = RetrofitClientInstance.getCardRetrofit().create(BlizzardService.BlizzardCardService.class);
        Call<RetroCardResponse> cardResponse = cardService.GetCardByDate(month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.US), Integer.toString(year), authResponse.getAccessToken());
      }

      @Override
      public void onFailure(Call<BlizzardAuthResponse> call, Throwable t) {

      }
    });
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