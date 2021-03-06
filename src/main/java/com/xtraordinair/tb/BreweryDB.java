package com.xtraordinair.tb;

import android.content.Context;

import com.koushikdutta.async.future.Future;
import com.xtraordinair.tb.entities.SearchResultsSet;

/**
 * Created by Steph on 4/7/2016.
 */
public class BreweryDB {

    /* DEPRECATED
    public static SearchResultsSet accessAPISearchEndpoint(SearchResultsSet resultsSet, Context applicationContext, int page) {
        resultsSet.retrieveResults(HTTPInfoRetrieve.getSearchQueryResult(resultsSet.getUserSearchQuery(),
                resultsSet.getSearchType(), page, applicationContext));

        return resultsSet;
    }
    */

    public static Future<String> searchEndpoint(SearchResultsSet resultsSet, Context context){
        return HTTPInfoRetrieve.getSearchQueryResult(resultsSet.getUserSearchQuery(),
                resultsSet.getSearchType(), resultsSet.getPage(), context);
    }

    public static Future<String> searchEndpoint(SearchResultsSet resultsSet, Context context, int page) {
        return HTTPInfoRetrieve.getSearchQueryResult(resultsSet.getUserSearchQuery(),
                resultsSet.getSearchType(), page, context);
    }
}
