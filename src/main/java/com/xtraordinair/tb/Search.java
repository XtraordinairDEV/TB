package com.xtraordinair.tb;

import android.content.Context;

/**
 * Created by Steph on 4/7/2016.
 */
public class Search {

    public static SearchResultsSet accessAPISearchEndpoint(SearchResultsSet resultsSet, Context applicationContext, int page) {
        resultsSet.addResults(HTTPInfoRetrieve.getSearchQueryResult(resultsSet.getUserSearchQuery(),
                resultsSet.getSearchType(), page, applicationContext));

        return resultsSet;
    }
}
