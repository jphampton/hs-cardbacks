package com.jphampton.hearthstonecardbacks.service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetroCardResponse {
  @SerializedName("cardBacks")
  private List<RetroCard> CardList;

  @SerializedName("cardCount")
  private int cardCount;

  public RetroCardResponse(List<RetroCard> cardList, int cardCount) {
    CardList = cardList;
    this.cardCount = cardCount;
  }

  public List<RetroCard> getCardList() {
    return CardList;
  }

  public void setCardList(List<RetroCard> cardList) {
    CardList = cardList;
  }

  public int getCardCount() {
    return cardCount;
  }

  public void setCardCount(int cardCount) {
    this.cardCount = cardCount;
  }
}
