package com.xtraordinair.tb;

import com.xtraordinair.tb.entities.Beer;
import com.xtraordinair.tb.entities.Brewery;
import com.xtraordinair.tb.interfaces.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONResultParser {

    private final static String TAG_DATA = "data";

    public static JSONArray parseSearchResultsToJSONArray(JSONObject searchQueryResult) {
        JSONArray searchData;

        if (searchQueryResult.optJSONArray(TAG_DATA) != null) {
            try {
                searchData = searchQueryResult.getJSONArray(TAG_DATA);
            } catch (JSONException e) {
                return null;
            } catch (NullPointerException ex) {
                return null;
            }
        }else{
            return null;
        }
        return searchData;
    }

    public static ArrayList<SearchResult> createArrayListFromJSONArray(JSONArray parsedResults,
                                                                       int searchType) {
        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

        for(int resultCounter = 0; resultCounter < parsedResults.length(); resultCounter++) {
            JSONObject currentObject = null;
            SearchResult newResult = null;

            try {
                currentObject = parsedResults.getJSONObject(resultCounter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (currentObject != null) {
                try {
                    //Check if SearchResult is a Beer or Brewery and pass to class to create
                    //new instance of respective types
                    if (currentObject.get("type").equals("beer"))
                        newResult = Beer.newInstance(currentObject);
                    else if (currentObject.get("type").equals("brewery"))
                        newResult = Brewery.newInstance(currentObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                newResult = null;
            }

            if (newResult != null)
                searchResults.add(newResult);
        }
        return searchResults;
    }

    public static int parseTotalNumberOfPages(JSONObject searchQueryResult){
        final String TAG_TOTAL_PAGES = "numberOfPages";
        String strTotalPages;
        int totalPages = 0;

        strTotalPages = searchQueryResult.optString(TAG_TOTAL_PAGES);

        if(!strTotalPages.equals(""))
            totalPages = Integer.parseInt(strTotalPages);

        return totalPages;
    }
}
