package com.xtraordinair.tb;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.Future;

import org.json.JSONObject;

/**
 * Created by Steph on 12/8/2016.
 */

public class SearchResultFragmentTwo extends Fragment {
    private static String ARG_PARAM1  = "Result Set";
    private SearchResultsSet resultSet;
    private Future<String> futureResultString;
    private LinearLayout mLinearLayout;
    private RecyclerView recyclerView;

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
            //preloadNextPage(1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        if (mLinearLayout == null) {
            NestedScrollView scrollview = new NestedScrollView(SearchResultFragmentTwo.this.getActivity());
            scrollview.setSmoothScrollingEnabled(true);
            mLinearLayout = (LinearLayout)
                    inflater.inflate(R.layout.result_list_linear_layout, container, false);

            if(recyclerView == null) {
                loadResults();
            }
            scrollview.setSmoothScrollingEnabled(true);
            scrollview.addView(mLinearLayout);
            return scrollview;
        } else{
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

    private void loadResults() {
        //Get first page of results
        if(resultSet.getSize() == 0) {
            preloadNextPage(1);
        }

        new InitialResults().execute();
    }

    private void preloadNextPage(int page){
        futureResultString = Search.accessAPISearchEndpoint(resultSet, this.getActivity(),
                page);
    }

    private void preloadNextPage(){
        futureResultString = Search.accessAPISearchEndpoint(resultSet, this.getActivity(),
                resultSet.getPage()+1);

    }
    private void updateResultList(){
        JSONObject j = resultSet.retrieveResults(futureResultString);
        resultSet.addResults(j);
        preloadNextPage();
    }

    private void customLoadMoreDataFromApi(int page) {
        resultSet.setPage(page);
        updateResultList();
    }

    private class InitialResults extends AsyncTask<Void, Void, Void> {


        private CardViewRecyclerViewAdapter cardViewRecyclerViewAdapter;
        private Context context;
        private GridLayoutManager gridLayoutManager;
        private final int TWO_COLUMNS = 2;
        private TextView endText;

        @Override
        protected void onPreExecute(){
            context = SearchResultFragmentTwo.this.getActivity();

            gridLayoutManager = new GridLayoutManager(context, TWO_COLUMNS);
            recyclerView = new RecyclerView(context);
            recyclerView.setLayoutManager(gridLayoutManager);

            /* untested
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 20);
            recyclerView.setLayoutParams(params);
            */

        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Add results to ArrayList
            if(resultSet.getSize() == 0) {
                updateResultList();
            }
            cardViewRecyclerViewAdapter =
                    new CardViewRecyclerViewAdapter(resultSet.getResultList(), context);

            endText = (TextView)
                    LayoutInflater.from(context).inflate(R.layout.textview_end_of_list, null, false);

            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            recyclerView.setAdapter(cardViewRecyclerViewAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            mLinearLayout.addView(recyclerView);
            endText.setText("End of List");
            mLinearLayout.addView(endText);

            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager, resultSet.getPage()) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    if(resultSet.getTotalPages() > 0  && resultSet.getPage() < resultSet.getTotalPages()) {


                        // update the adapter, saving the last known size
                        int curSize = resultSet.getSize();
                        //shownResults.addAll(resultSet.getDeltaResultList());
                        customLoadMoreDataFromApi(page);

                        // for efficiency purposes, only notify the adapter of what elements that got changed
                        // curSize will equal to the index of the first element inserted because the list is 0-indexed
                        cardViewRecyclerViewAdapter.notifyItemRangeInserted(curSize, resultSet.getSize() - 1);


                        Toast.makeText(getActivity(),
                                "Page " + resultSet.getPage() + " of " + resultSet.getTotalPages()
                                + "\nTotal Items: " + resultSet.getSize(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        }
    }
}
