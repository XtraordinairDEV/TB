package com.xtraordinair.tb;

import android.graphics.Bitmap;

public interface SearchResult {

    void setID(String id);

    String getID();

    void setName(String name);

    String getName();

    String getDescription();

    void setDescription(String descrip);

    String getIconLoc();

    void setIconLoc(String img);

    String getMediumIconLoc();

    void setMediumIconLoc(String img);

    String getLargeIconLoc();

    void setLargeIconLoc(String img);

    Bitmap getBitmapMedium();

    void setBitmapMedium(Bitmap bitmap);

    Bitmap getBitmapLarge();

    void setBitmapLarge(Bitmap bitmap);

    Bitmap getBitmapIcon();

    void setBitmapIcon(Bitmap bitmap);

    String getType();

    void setSecondaryInfo(String info);

    String getSecondaryInfo();


}
