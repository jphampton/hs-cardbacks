package com.jphampton.hearthstonecardbacks.service;

import com.google.gson.annotations.SerializedName;

//{'id': 202,
//    'sortCategory': 5,
//    'text': 'Hey, who set the Emerald Dream to dark mode?! Acquired by winning 5 games in ranked mode, June 2020.',
//    'name': 'Mystic Forest',
//    'image': 'https://d15f34w2p8l1cc.cloudfront.net/hearthstone/6cffe5ecf372fda38a9651c7fea20cee909f356a86605830215d3cc96de56f82.png',
//    'slug': '202-mystic-forest'}

public class RetroCard {
  @SerializedName("name")
  private String name;

  @SerializedName("id")
  private int id;

  @SerializedName("text")
  private String text;

  @SerializedName("image")
  private String imageURL;

  @SerializedName("slug")
  private String slug;

  @SerializedName("sortCategory")
  private int sortCategory;

  public RetroCard(String name, int id, String text, String imageURL, String slug, int sortCategory) {
    this.name = name;
    this.id = id;
    this.text = text;
    this.imageURL = imageURL;
    this.slug = slug;
    this.sortCategory = sortCategory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public int getSortCategory() {
    return sortCategory;
  }

  public void setSortCategory(int sortCategory) {
    this.sortCategory = sortCategory;
  }
}
