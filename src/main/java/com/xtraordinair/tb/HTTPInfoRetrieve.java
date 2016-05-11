package com.xtraordinair.tb;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by Steph on 4/7/2016.
 */
public class HTTPInfoRetrieve {

    private static final String DOMAIN = "http://api.brewerydb.com/v2/";
    private static final String KEY = "key=";
    private static final String FORMAT = "format=json";
    private static final String AND = "&";
    private static final String SEARCH = "search?";
    private static final String BREWERIES = "withBreweries=Y";

    public static Future<String> getSearchQueryResult(String queryString, int queryInt, int pageNum,
                                                  Context context){

        String query = "q=" + prepareSearchQuery(queryString);
        String queryType = getQueryType(queryInt);
        String resultPage = "p=" + pageNum;
        Resources res = context.getResources();

        String url = DOMAIN + SEARCH + query +
                AND + queryType +
                AND + resultPage +
                AND + BREWERIES +
                AND + KEY + res.getString(R.string.API_KEY) +
                AND + FORMAT;

        Future futureResultString = performDownload(url, context);
        return futureResultString;
    }

    private static Future<String> performDownload(String url, Context context) {

        Future<String> resultString= Ion.with(context)
                        .load(url)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                // download done...
                                // do stuff with the File or error//

                                if(e != null){
                                    e.printStackTrace();
                                }
                            }
                        });

        return resultString;
    }

    private static String prepareSearchQuery(String searchQuery) {

        try{
            searchQuery = java.net.URLEncoder.encode(searchQuery, "ISO-8859-1");
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
        return searchQuery;
    }

    private static String getQueryType(int queryType) {
        String type = null;

        if (queryType == 1)
            type = "type=beer";
        else if (queryType == 2)
            type = "type=brewery";
        else if (queryType == 3)
            type = "";
        return type;
    }
}
