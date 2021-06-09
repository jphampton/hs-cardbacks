package com.jphampton.hearthstonecardbacks.service;

public interface BlizzardService {
  @GET("https://us.api.blizzard.com/hearthstone/cardbacks?locale=en_US&textFilter={month}%20{year}&sort=dateAdded%3Adesc&access_token={token}")

}
