package com.jphampton.hearthstonecardbacks.service;

import com.squareup.okhttp.Credentials;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jphampton.hearthstonecardbacks.service.PrivateConstants.CLIENT_ID;
import static com.jphampton.hearthstonecardbacks.service.PrivateConstants.CLIENT_SECRET;

class RetrofitClientInstance {
  private static final String CREDENTIAL = Credentials.basic(CLIENT_ID, CLIENT_SECRET);

  private static final Map<QueryType, Retrofit> retrofits = new HashMap<>();

  private enum QueryType {
    CARD("https://us.api.blizzard.com/"),
    AUTH("https://us.battle.net/");
    final String url;

    QueryType(String url) {
      this.url = url;
    }
  }


  private static Retrofit getRetrofitInstance(QueryType querytype) {
    if (retrofits.containsKey(querytype)) {
      return retrofits.get(querytype);
    } else {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(querytype.url)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
      retrofits.put(querytype, retrofit);
      return retrofit;
    }
  }

  static Retrofit getAuthRetrofit() {
    return getRetrofitInstance(QueryType.AUTH);
  }

  static Retrofit getCardRetrofit() {
    return getRetrofitInstance(QueryType.CARD);
  }

}

