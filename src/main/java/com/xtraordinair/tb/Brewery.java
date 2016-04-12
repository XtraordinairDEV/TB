package com.xtraordinair.tb;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Brewery implements Parcelable, SearchResult {

    private String breweryID;
    private String breweryName;
    private String breweryDesc;
    private String breweryURL;
    private String breweryEst;
    private String isOrganic;
    private String iconLoc;
    private String itemType = "brewery";
    private String mediumIconLoc;
    private String largeIconLoc;
    private String secondaryInfo;
    private Bitmap bitmapIcon;
    private Bitmap bitmapMedium;
    private Bitmap bitmapLarge;

    public Brewery() {
    }

    public Brewery(String id) {
        breweryID = id;
    }

    public void setID(String id) {
        breweryID = id;
    }

    public String getID() {
        return breweryID;
    }

    public void setName(String name) {
        breweryName = name;
    }

    public String getName() {
        return breweryName;
    }

    public String getDescription() {
        return breweryDesc;
    }

    public void setDescription(String descrip) {
        breweryDesc = descrip;
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

    public void setBreweryURL(String name) {
        breweryURL = name;
    }

    public String getBreweryURL() {
        return breweryURL;
    }

    public void setBreweryEst(String name) {
        breweryEst = name;
    }

    public String getBreweryEst() {
        return breweryEst;
    }

    public void setIsOrganic(String name) {
        isOrganic = name;
    }

    public String getIsOrganic() {
        return isOrganic;
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

    public static Brewery newInstance(JSONObject currentObject) {
        final String TAG_ID = "id";
        final String TAG_NAME = "name";
        final String TAG_DESCRIPTION = "description";
        final String TAG_WEBSITE = "website";
        final String TAG_YEAR_ESTABLISHED = "established";
        final String TAG_IS_ORGANIC = "isOrganic";
        final String TAG_IMAGES = "images";
        final String TAG_IMAGE_ICON = "icon";
        final String TAG_IMAGE_MEDIUM = "medium";
        final String TAG_IMAGE_LARGE = "large";



        Brewery newBrewery = new Brewery();

        newBrewery.setID(currentObject.optString(TAG_ID, "---"));
        newBrewery.setName(currentObject.optString(TAG_NAME, "---"));
        newBrewery.setDescription(currentObject.optString(TAG_DESCRIPTION, "---"));
        newBrewery.setBreweryURL(currentObject.optString(TAG_WEBSITE, "---"));
        newBrewery.setBreweryEst(currentObject.optString(TAG_YEAR_ESTABLISHED, "---"));
        newBrewery.setIsOrganic(currentObject.optString(TAG_IS_ORGANIC, "---"));

        JSONObject subObject = currentObject.optJSONObject(TAG_IMAGES);
        if(subObject != null){
            newBrewery.setIconLoc(subObject.optString(TAG_IMAGE_ICON, "---"));
            newBrewery.setMediumIconLoc(subObject.optString(TAG_IMAGE_MEDIUM, "---"));
            newBrewery.setLargeIconLoc(subObject.optString(TAG_IMAGE_LARGE, "---"));
        }

        newBrewery.setSecondaryInfo("EST: " + newBrewery.getBreweryEst());

        return newBrewery;
    }

    /*
     * PARCELABLE CODE START
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.breweryID);
        dest.writeString(this.breweryName);
        dest.writeString(this.breweryDesc);
        dest.writeString(this.breweryURL);
        dest.writeString(this.breweryEst);
        dest.writeString(this.isOrganic);
        dest.writeString(this.iconLoc);
        dest.writeString(this.itemType);
        dest.writeString(this.mediumIconLoc);
        dest.writeString(this.largeIconLoc);
        dest.writeString(this.secondaryInfo);
        dest.writeParcelable(this.bitmapIcon, flags);
        dest.writeParcelable(this.bitmapMedium, flags);
        dest.writeParcelable(this.bitmapLarge, flags);
    }

    protected Brewery(Parcel in) {
        this.breweryID = in.readString();
        this.breweryName = in.readString();
        this.breweryDesc = in.readString();
        this.breweryURL = in.readString();
        this.breweryEst = in.readString();
        this.isOrganic = in.readString();
        this.iconLoc = in.readString();
        this.itemType = in.readString();
        this.mediumIconLoc = in.readString();
        this.largeIconLoc = in.readString();
        this.secondaryInfo = in.readString();
        this.bitmapIcon = in.readParcelable(Bitmap.class.getClassLoader());
        this.bitmapMedium = in.readParcelable(Bitmap.class.getClassLoader());
        this.bitmapLarge = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Brewery> CREATOR = new Creator<Brewery>() {
        @Override
        public Brewery createFromParcel(Parcel source) {
            return new Brewery(source);
        }

        @Override
        public Brewery[] newArray(int size) {
            return new Brewery[size];
        }
    };

    /*
     * PARCELABLE CODE END
     */

}
