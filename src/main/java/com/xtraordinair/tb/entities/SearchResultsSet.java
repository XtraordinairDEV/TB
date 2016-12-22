package com.xtraordinair.tb.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.koushikdutta.async.future.Future;
import com.xtraordinair.tb.JSONResultParser;
import com.xtraordinair.tb.interfaces.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchResultsSet implements Parcelable {

    private boolean isPaginated;
    private int searchType;
    private String userSearchQuery;
    private int page;
    private ArrayList<SearchResult> resultList = new ArrayList<>();
    private int totalPages = 0;

    public SearchResultsSet(boolean isPag, int sType, String query) {
        isPaginated = isPag;
        searchType = sType;
        userSearchQuery = query;
        page = 1;
    }

    public int getPage(){
        return page;
    }

    public String getUserSearchQuery(){
        return userSearchQuery;
    }

    public int getSearchType(){
        return searchType;
    }

    public boolean getIsPaginated(){
        return isPaginated;
    }

    public ArrayList<SearchResult> getResultList() {
        return resultList;
    }

    public SearchResult get(int pos) {
        return resultList.get(pos);
    }


    private void setTotalPages(int numPages){
        totalPages = numPages;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public int getSize() {
            return resultList.size();
    }

    public JSONObject retrieveResults(Future<String> futureResultString) {
        String resultString;
        JSONObject jsonResults = null;

        try {
            resultString = futureResultString.get();
        } catch (InterruptedException e) {
            resultString = null;
        } catch (ExecutionException e) {
            resultString = null;
        }

        if(resultString != null){
            try {
                jsonResults = new JSONObject(resultString);
            } catch (JSONException e) {
                jsonResults = null;
            }
        }

        return jsonResults;
    }

    public void addResults(JSONObject searchQueryResult) {
        JSONArray parsedResults = JSONResultParser.parseSearchResultsToJSONArray(searchQueryResult);

        setTotalPages(JSONResultParser.parseTotalNumberOfPages(searchQueryResult));

        if(parsedResults != null){
            resultList.addAll(JSONResultParser.createArrayListFromJSONArray(parsedResults, searchType));
        }

    }

    public void setPage(int page) {
        this.page = page;
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
        dest.writeByte(this.isPaginated ? (byte) 1 : (byte) 0);
        dest.writeInt(this.searchType);
        dest.writeString(this.userSearchQuery);
        dest.writeInt(this.page);
        dest.writeList(this.resultList);
        dest.writeInt(this.totalPages);
    }

    protected SearchResultsSet(Parcel in) {
        this.isPaginated = in.readByte() != 0;
        this.searchType = in.readInt();
        this.userSearchQuery = in.readString();
        this.page = in.readInt();
        this.resultList = new ArrayList<SearchResult>();
        in.readList(this.resultList, SearchResult.class.getClassLoader());
        this.totalPages = in.readInt();
    }

    public static final Creator<SearchResultsSet> CREATOR = new Creator<SearchResultsSet>() {
        @Override
        public SearchResultsSet createFromParcel(Parcel source) {
            return new SearchResultsSet(source);
        }

        @Override
        public SearchResultsSet[] newArray(int size) {
            return new SearchResultsSet[size];
        }
    };
}
