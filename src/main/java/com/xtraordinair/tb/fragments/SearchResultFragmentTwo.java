package com.xtraordinair.tb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.Future;
import com.xtraordinair.tb.BreweryDB;
import com.xtraordinair.tb.R;
import com.xtraordinair.tb.adapters.CardViewRecyclerViewAdapter;
import com.xtraordinair.tb.entities.SearchResultsSet;

import org.json.JSONObject;

/**
 * Created by Steph on 12/8/2016.
 */

public class SearchResultFragmentTwo extends Fragment {
    private static String ARG_PARAM1  = "Result Set";
    private SearchResultsSet resultSet;
    private Future<String> futureResultString;
    private RecyclerView recyclerView;
    private ScrollView mScrollView;
    //private Button mLoadMoreButton;
    private TextView mEndOfListTextView;
    private CardView mLoadMoreCardView;
    private TextView mLoadMoreTextView;

    /**********************************************************
     * LIFECYCLE METHODS / OVERRIDDEN METHODS
     **********************************************************/
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            resultSet = savedInstanceState.getParcelable(ARG_PARAM1);
        } else if (getArguments() != null) {
            resultSet = getArguments().getParcelable(ARG_PARAM1);
            //downloadMoreResults(1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mScrollView == null){
            //Inflate parent view and then bind all other views with (Parent).findViewById(int resid)

            //ScrollView (Parent)
            mScrollView = (ScrollView) inflater.inflate(R.layout.search_result_fragment_nested, container, false);
            mScrollView.setSmoothScrollingEnabled(true);

            //RecyclerView for displaying results
            recyclerView = (RecyclerView) mScrollView.findViewById(R.id.search_result_nested_recycler_view);
            recyclerView.setNestedScrollingEnabled(false);

            //LoadMoreCardView to load more results on press
            mLoadMoreCardView = (CardView) mScrollView.findViewById(R.id.load_more_cardview);
            mLoadMoreTextView = (TextView) mScrollView.findViewById(R.id.load_more_textview);

            //LoadMoreButton (Button) to load more results on press
            //mLoadMoreButton = (Button) mScrollView.findViewById(R.id.load_more_button);

            //EOLTextView (TextView) appears when all results are loaded to show user they are at
            //the end of the list
            mEndOfListTextView = (TextView) mScrollView.findViewById(R.id.search_result_eol_textview);

            //Load results from internet in background thread
            new LoadResults().execute();

            return mScrollView;
        }else{
            return null;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //On rotation save:

        //ResultSet for loaded results / current page num / etc
        outState.putParcelable(ARG_PARAM1, resultSet);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    /**********************************************************
     * LIFECYCLE METHODS / OVERRIDDEN METHODS END
     **********************************************************/

    public static SearchResultFragmentTwo newInstance(SearchResultsSet resultsSet) {
        SearchResultFragmentTwo fragment = new SearchResultFragmentTwo();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, resultsSet);

        fragment.setArguments(args);
        return fragment;
    }

    private void downloadMoreResults(int page){
        futureResultString = BreweryDB.searchEndpoint(resultSet, this.getActivity(),
                page);
    }

    private void updateResultList(){
        JSONObject j = resultSet.retrieveResults(futureResultString);
        resultSet.addResults(j);
    }

    private class LoadResults extends AsyncTask<Void, Void, Void> {

        private CardViewRecyclerViewAdapter cardViewRecyclerViewAdapter;
        private Context context = SearchResultFragmentTwo.this.getActivity();
        private GridLayoutManager gridLayoutManager;
        private final int TWO_COLUMNS = 2;

        @Override
        protected void onPreExecute(){

            gridLayoutManager = new GridLayoutManager(context, TWO_COLUMNS);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Add results to ArrayList
            if(resultSet.getSize() == 0) {
                downloadMoreResults(resultSet.getPage());
                updateResultList();
            }
            cardViewRecyclerViewAdapter =
                    new CardViewRecyclerViewAdapter(resultSet.getResultList(), context);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            recyclerView.setAdapter(cardViewRecyclerViewAdapter);

            //If there are more pages to be loaded
            if(resultSet.getTotalPages() > 1 && resultSet.getPage() < resultSet.getTotalPages()) {
                mLoadMoreCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //One click at a time
                        mLoadMoreCardView.setVisibility(View.GONE);
                        //Size before update
                        final int curSize = resultSet.getSize();
                        //Set next page number to be loaded
                        resultSet.setPage(resultSet.getPage() + 1);
                        downloadMoreResults(resultSet.getPage());
                        //Download page and add to resultSet
                        updateResultList();
                        // for efficiency purposes, only notify the adapter of what elements that got changed
                        // curSize will equal to the index of the first element inserted because the list is 0-indexed
                        //cardViewRecyclerViewAdapter.notifyItemRangeInserted(curSize, resultSet.getSize() - 1);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                // Notify adapter with appropriate notify methods
                                cardViewRecyclerViewAdapter.notifyItemRangeInserted(curSize, resultSet.getSize() - 1);
                            }
                        });

                        if(resultSet.getPage() < resultSet.getTotalPages()){
                            mLoadMoreCardView.setVisibility(View.VISIBLE);
                        }else{
                            mEndOfListTextView.setText("All Results Loaded");
                            mEndOfListTextView.setVisibility(View.VISIBLE);
                            mLoadMoreCardView.setVisibility(View.GONE);
                        }
                        Toast.makeText(getActivity(),
                                "Page " + resultSet.getPage() + " of " + resultSet.getTotalPages()
                                        + "\nTotal Items: " + resultSet.getSize(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                mLoadMoreTextView.setText("Load More");
                mLoadMoreCardView.setVisibility(View.VISIBLE);
                mEndOfListTextView.setVisibility(View.GONE);
            }//No Results / Error
            else if(resultSet.getTotalPages() == 0){
                mEndOfListTextView.setText("No Matches Found");
                mEndOfListTextView.setVisibility(View.VISIBLE);
                mLoadMoreCardView.setVisibility(View.GONE);
            }//If all pages have been loaded or there is 1 page and it has been loaded
            else if(resultSet.getPage() == resultSet.getTotalPages()){
                mEndOfListTextView.setText("All Results Loaded");
                mEndOfListTextView.setVisibility(View.VISIBLE);
                mLoadMoreCardView.setVisibility(View.GONE);
            }
        }
    }
}
