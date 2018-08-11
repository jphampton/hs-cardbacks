package com.jphampton.hearthstonecardbacks.models;

public class Card {
    private Card(int id, String name, String description, String source, String sourceDescription, Boolean enabled, String imgURL, String imgAnimatedURL, int sortCategory, int sortOrder, String locale) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.source = source;
        this.sourceDescription = sourceDescription;
        this.enabled = enabled;
        this.imgURL = imgURL;
        this.imgAnimatedURL = imgAnimatedURL;
        this.sortCategory = sortCategory;
        this.sortOrder = sortOrder;
        this.locale = locale;
    }

    public static class Builder {
        int id;
        String name;
        String description;
        String source;
        String sourceDescription;
        Boolean enabled;
        String imgURL;
        String imgAnimatedURL;
        int sortCategory;
        int sortOrder;
        String locale;
        
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

        public Builder setSourceDescription(String sourceDescription) {
            this.sourceDescription = sourceDescription;
            return this;
        }

        public Builder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder setImgURL(String imgURL) {
            this.imgURL = imgURL;
            return this;
        }

        public Builder setImgAnimatedURL(String imgAnimatedURL) {
            this.imgAnimatedURL = imgAnimatedURL;
            return this;
        }

        public Builder setSortCategory(int sortCategory) {
            this.sortCategory = sortCategory;
            return this;
        }

        public Builder setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Builder setLocale(String locale) {
            this.locale = locale;
            return this;
        }
        
        public Card build(){
            return new Card(id, name, description, source, sourceDescription, enabled, imgURL,imgAnimatedURL,sortCategory,sortOrder,locale);
        }
        
        
    }


    public final int id;
    public final String name;
    public final String description;
    public final String source;
    public final String sourceDescription;
    public final Boolean enabled;
    public final String imgURL;
    public final String imgAnimatedURL;
    public final int sortCategory;
    public final int sortOrder;
    public final String locale;
    
    
}
