package com.xtraordinair.tb;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

        if (getArguments() != null) {
            resultSet = getArguments().getParcelable(ARG_PARAM1);
            //preloadNextPage(1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mLinearLayout == null) {
            mLinearLayout = (LinearLayout)
                    inflater.inflate(R.layout.result_list_linear_layout, container, false);
            loadResults();
            return mLinearLayout;
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
        preloadNextPage(1);
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
        //preloadNextPage();
    }

    private class InitialResults extends AsyncTask<Void, Void, Void> {


        private CardViewRecyclerViewAdapter cardViewRecyclerViewAdapter;
        private Context context;

        @Override
        protected void onPreExecute(){
            context = SearchResultFragmentTwo.this.getActivity();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);

            recyclerView = new RecyclerView(context);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Add results to ArrayList
            updateResultList();
            cardViewRecyclerViewAdapter =
                    new CardViewRecyclerViewAdapter(resultSet.getResultList(), context);
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            recyclerView.setAdapter(cardViewRecyclerViewAdapter);
            mLinearLayout.addView(recyclerView);
        }
    }
}
