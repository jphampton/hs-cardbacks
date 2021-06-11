package com.jphampton.hearthstonecardbacks.service;

import com.google.gson.annotations.SerializedName;

public class BlizzardAuthResponse {
  @SerializedName("access_token")
  String accessToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public BlizzardAuthResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
