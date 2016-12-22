package com.xtraordinair.tb.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.xtraordinair.tb.interfaces.SearchResult;

import org.json.JSONArray;
import org.json.JSONObject;

public class Beer implements Parcelable, SearchResult {

    private String beerName;
    private String beerID;
    private String iconLoc;
    private String mediumIconLoc;
    private String largeIconLoc;
    private String alcoholByVolume;
    private String bitternessUnits;
    private String standardReferenceMethod;
    private String originalGravity;
    private String servingTemperature;
    private String glassName;
    private String beerDescription;
    private String breweryID;
    private String breweryName;
    private String styleID;
    private String styleName;
    private String itemType = "beer";
    private String categoryID;
    private String categoryName;
    private String availability;
    private String secondaryInfo;
    private Bitmap bitmapIcon;
    private Bitmap bitmapMedium;
    private Bitmap bitmapLarge;

    public Beer() {
    }

    public Beer(String id) {beerID = id;}

    public void setName(String name) {
        beerName = name;
    }

    public String getName() {
        return beerName;
    }

    public void setID(String id) {
        beerID = id;
    }

    public String getID() {
        return beerID;
    }

    public String getIconLoc() {
        return iconLoc;
    }

    public void setIconLoc(String img) {
        iconLoc = img;
    }

    public String getMediumIconLoc() {
        return mediumIconLoc;
    }

    public void setMediumIconLoc(String img) {
        mediumIconLoc = img;
    }

    public String getLargeIconLoc() {
        return largeIconLoc;
    }

    public void setLargeIconLoc(String img) {
        largeIconLoc = img;
    }

    public String getABV() {
        return alcoholByVolume;
    }

    public void setABV(String abv) {
        alcoholByVolume = abv;
    }

    public String getIBU() {
        return bitternessUnits;
    }

    public void setIBU(String ibu) {
        bitternessUnits = ibu;
    }

    public String getDescription() {
        return beerDescription;
    }

    public void setDescription(String descrip) {
        beerDescription = descrip;
    }

    public String getBreweryID() {
        return breweryID;
    }

    public void setBreweryID(String brewerID) {
        breweryID = brewerID;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String brewerName) {
        breweryName = brewerName;
    }

    public Bitmap getBitmapMedium() {
        return bitmapMedium;
    }

    public void setBitmapMedium(Bitmap bitmap) {
        bitmapMedium = bitmap;
    }

    public Bitmap getBitmapLarge() {
        return bitmapLarge;
    }

    public void setBitmapLarge(Bitmap bitmap) {
        bitmapLarge = bitmap;
    }

    public Bitmap getBitmapIcon() {
        return bitmapIcon;
    }

    public void setBitmapIcon(Bitmap bitmap) {
        bitmapIcon = bitmap;
    }

    public String getStyleID() {
        return styleID;
    }

    public void setStyleID(String id) {
        styleID = id;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String name) {
        styleName = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String id) {
        categoryID = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        categoryName = name;
    }

    public String getType() {
        return itemType;
    }

    @Override
    public void setSecondaryInfo(String info) {
        secondaryInfo = info;
    }

    @Override
    public String getSecondaryInfo() {
        return secondaryInfo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availableName) {
        availability = availableName;
    }

    public String getOriginalGravity() {
        return originalGravity;
    }

    public void setOriginalGravity(String og) {
        originalGravity = og;
    }

    public String getServingTemperature() {
        return servingTemperature;
    }

    public void setServingTemperature(String servingTemp) {
        servingTemperature = servingTemp;
    }

    public String getGlass() {
        return glassName;
    }

    public void setGlass(String glass) {
        glassName = glass;
    }

    public String getStandardReferenceMethod() {
        return standardReferenceMethod;
    }

    public void setStandardReferenceMethod(String standardReferenceMethod) {
        this.standardReferenceMethod = standardReferenceMethod;
    }

    public static Beer newInstance(JSONObject currentObject) {
        final String TAG_ID = "id";
        final String TAG_Name = "name";
        final String TAG_DESCRIPTION = "description";
        final String TAG_ABV = "abv";
        final String TAG_IBU = "ibu";
        final String TAG_SRM = "srm";
        final String TAG_ORIGINAL_GRAVITY = "originalGravity";
        final String TAG_SERVING_TEMPERATURE = "servingTemperatureDisplay";
        final String TAG_LABEL = "labels";
        final String TAG_IMAGE_ICON = "icon";
        final String TAG_IMAGE_MEDIUM = "medium";
        final String TAG_IMAGE_LARGE = "large";
        final String TAG_ASSOCIATED_BREWERY = "breweries";
        final String TAG_ASSOCIATED_BREWERY_ID = "id";
        final String TAG_ASSOCIATED_BREWERY_NAME = "name";
        final String TAG_STYLE = "style";
        final String TAG_STYLE_ID = "id";
        final String TAG_STYLE_NAME= "name";
        final String TAG_CATEGORY = "category";
        final String TAG_CATEGORY_ID = "id";
        final String TAG_CATEGORY_NAME = "name";
        final String TAG_AVAILABLE = "available";
        final String TAG_AVAILABLE_NAME = "name";

        Beer newBeer = new Beer();

        newBeer.setID(currentObject.optString(TAG_ID, "---"));
        newBeer.setName(currentObject.optString(TAG_Name, "---"));
        newBeer.setDescription(currentObject.optString(TAG_DESCRIPTION,"---"));
        newBeer.setABV(currentObject.optString(TAG_ABV,"---"));
        newBeer.setIBU(currentObject.optString(TAG_IBU,"---"));
        newBeer.setStandardReferenceMethod(currentObject.optString(TAG_SRM, "---"));
        newBeer.setOriginalGravity(currentObject.optString(TAG_ORIGINAL_GRAVITY,"---"));
        newBeer.setServingTemperature(currentObject.optString(TAG_SERVING_TEMPERATURE,"---"));

        JSONObject subObject = currentObject.optJSONObject(TAG_LABEL);
        if(subObject != null){
            newBeer.setIconLoc(subObject.optString(TAG_IMAGE_ICON, "---"));
            newBeer.setMediumIconLoc(subObject.optString(TAG_IMAGE_MEDIUM, "---"));
            newBeer.setLargeIconLoc(subObject.optString(TAG_IMAGE_LARGE, "---"));
        }

        JSONArray subArray = currentObject.optJSONArray(TAG_ASSOCIATED_BREWERY);
        if(subArray != null){
            subObject = subArray.optJSONObject(0);
            if(subObject != null) {
                newBeer.setBreweryID(subObject.optString(TAG_ASSOCIATED_BREWERY_ID, "---"));
                newBeer.setBreweryName(subObject.optString(TAG_ASSOCIATED_BREWERY_NAME, "---"));
            }
        }

        subObject = currentObject.optJSONObject(TAG_STYLE);
        if(subObject != null){
            newBeer.setStyleID(subObject.optString(TAG_STYLE_ID, "---"));
            newBeer.setStyleName(subObject.optString(TAG_STYLE_NAME, "---"));
        }

        subObject = currentObject.optJSONObject(TAG_CATEGORY);
        if(subObject != null){
            newBeer.setCategoryID(subObject.optString(TAG_CATEGORY_ID, "---"));
            newBeer.setCategoryName(subObject.optString(TAG_CATEGORY_NAME, "---"));
        }

        subObject = currentObject.optJSONObject(TAG_AVAILABLE);
        if(subObject != null){
            newBeer.setAvailability(subObject.optString(TAG_AVAILABLE_NAME, "---"));
        }

        newBeer.setSecondaryInfo(newBeer.getBreweryName());

        return newBeer;
    }

    /*
     * PARCELABLE CODE START
     */

    /*
     * PARCELABLE CODE END
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.beerName);
        dest.writeString(this.beerID);
        dest.writeString(this.iconLoc);
        dest.writeString(this.mediumIconLoc);
        dest.writeString(this.largeIconLoc);
        dest.writeString(this.alcoholByVolume);
        dest.writeString(this.bitternessUnits);
        dest.writeString(this.standardReferenceMethod);
        dest.writeString(this.originalGravity);
        dest.writeString(this.servingTemperature);
        dest.writeString(this.glassName);
        dest.writeString(this.beerDescription);
        dest.writeString(this.breweryID);
        dest.writeString(this.breweryName);
        dest.writeString(this.styleID);
        dest.writeString(this.styleName);
        dest.writeString(this.itemType);
        dest.writeString(this.categoryID);
        dest.writeString(this.categoryName);
        dest.writeString(this.availability);
        dest.writeString(this.secondaryInfo);
        dest.writeParcelable(this.bitmapIcon, flags);
        dest.writeParcelable(this.bitmapMedium, flags);
        dest.writeParcelable(this.bitmapLarge, flags);
    }

    protected Beer(Parcel in) {
        this.beerName = in.readString();
        this.beerID = in.readString();
        this.iconLoc = in.readString();
        this.mediumIconLoc = in.readString();
        this.largeIconLoc = in.readString();
        this.alcoholByVolume = in.readString();
        this.bitternessUnits = in.readString();
        this.standardReferenceMethod = in.readString();
        this.originalGravity = in.readString();
        this.servingTemperature = in.readString();
        this.glassName = in.readString();
        this.beerDescription = in.readString();
        this.breweryID = in.readString();
        this.breweryName = in.readString();
        this.styleID = in.readString();
        this.styleName = in.readString();
        this.itemType = in.readString();
        this.categoryID = in.readString();
        this.categoryName = in.readString();
        this.availability = in.readString();
        this.secondaryInfo = in.readString();
        this.bitmapIcon = in.readParcelable(Bitmap.class.getClassLoader());
        this.bitmapMedium = in.readParcelable(Bitmap.class.getClassLoader());
        this.bitmapLarge = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel source) {
            return new Beer(source);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };
}
