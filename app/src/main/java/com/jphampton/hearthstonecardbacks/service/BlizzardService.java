package com.jphampton.hearthstonecardbacks.service;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

class BlizzardService {
  interface BlizzardCardService {
    @GET("hearthstone/cardbacks?locale=en_US&textFilter={month}%20{year}&sort=dateAdded%3Adesc&access_token={token}")
    Call<RetroCardResponse> GetCardByDate(@Path("month") String month, @Path("year") String year, @Path("token") String token);
  }

  interface BlizzardAuthService {

    @POST("oauth/authorize?grant_type=client_credentials")
    Call<BlizzardAuthResponse> GetAuthToken(@Header("Authorization") String credential);

  }
}