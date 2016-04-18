package com.xtraordinair.tb;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsSet implements Parcelable {

    private boolean isPaginated;
    private int searchType;
    private String userSearchQuery;
    private int page;
    private ArrayList<SearchResult> resultList = new ArrayList<>();
    private ArrayList<SearchResult> deltaResultArrayList = new ArrayList<>();
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

    public ArrayList<SearchResult> getDeltaResultList(){
        return deltaResultArrayList;
    }

    private void setTotalPages(int numPages){
        totalPages = numPages;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public void addResults(JSONObject searchQueryResult) {
        JSONArray parsedResults = JSONResultParser.parseSearchResultsToJSONArray(searchQueryResult);
        deltaResultArrayList = new ArrayList<>();

        setTotalPages(JSONResultParser.parseTotalNumberOfPages(searchQueryResult));

        if(parsedResults != null){
            deltaResultArrayList.addAll(JSONResultParser.createArrayListFromJSONArray(parsedResults,
                    searchType));
            resultList.addAll(deltaResultArrayList);
        }

    }

    /*
     * PARCELABLE CODE START
     */

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(isPaginated ? (byte) 1 : (byte) 0);
        dest.writeInt(this.searchType);
        dest.writeString(this.userSearchQuery);
        dest.writeInt(this.page);
        dest.writeList(this.resultList);
        dest.writeList(this.deltaResultArrayList);
        dest.writeInt(this.totalPages);
    }

    protected SearchResultsSet(Parcel in) {
        this.isPaginated = in.readByte() != 0;
        this.searchType = in.readInt();
        this.userSearchQuery = in.readString();
        this.page = in.readInt();
        this.resultList = new ArrayList<SearchResult>();
        in.readList(this.resultList, SearchResult.class.getClassLoader());
        this.deltaResultArrayList = new ArrayList<SearchResult>();
        in.readList(this.deltaResultArrayList, SearchResult.class.getClassLoader());
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

    /*
     * PARCELABLE CODE END
     */
}
