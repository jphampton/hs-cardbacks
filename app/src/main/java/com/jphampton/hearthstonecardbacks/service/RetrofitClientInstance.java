package com.jphampton.hearthstonecardbacks.service;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClientInstance {
  private static final Map<QueryType, Retrofit> retrofits = new HashMap<>();

  private enum QueryType {
    CARD("https://us.api.blizzard.com/hearthstone/cardbacks"),
    AUTH("https://us.battle.net/oauth/authorize");
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

