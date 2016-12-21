package com.xtraordinair.tb.tobedeleted;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.koushikdutta.async.future.Future;
import com.xtraordinair.tb.Search;
import com.xtraordinair.tb.interfaces.SearchResult;
import com.xtraordinair.tb.entities.SearchResultsSet;

import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchResultFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "Result Set";
    private SearchResultsSet resultSet;
    private Future<String> futureResultString;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchResultFragment() {

    }

    /*
     * FRAGMENT LIFECYCLE START
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            resultSet = getArguments().getParcelable(ARG_PARAM1);
            preloadNextPage(1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.fragment_searchresult_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, mColumnCount);
            RecyclerView recyclerView = (RecyclerView) view;

            final ArrayList<SearchResult> shownResults = new ArrayList<>();
            updateResultList();
            shownResults.addAll(resultSet.getResultList());

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(linearLayoutManager);
            } else {
                recyclerView.setLayoutManager(gridLayoutManager);
            }

            final MySearchResultRecyclerViewAdapter recyclerViewAdapter
                    = new MySearchResultRecyclerViewAdapter(shownResults,
                    mListener, getActivity().getApplicationContext());

            recyclerView.setAdapter(recyclerViewAdapter);

            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager, resultSet.getPage()) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    if(resultSet.getTotalPages() > 0  && resultSet.getPage() < resultSet.getTotalPages()) {
                        customLoadMoreDataFromApi(page);

                        // update the adapter, saving the last known size
                        int curSize = recyclerViewAdapter.getItemCount();
                        shownResults.addAll(resultSet.getDeltaResultList());


                        // for efficiency purposes, only notify the adapter of what elements that got changed
                        // curSize will equal to the index of the first element inserted because the list is 0-indexed
                        recyclerViewAdapter.notifyItemRangeInserted(curSize, shownResults.size() - 1);


                        Toast.makeText(getActivity(),
                                "Page " + resultSet.getPage() + " of " + resultSet.getTotalPages(),
                                Toast.LENGTH_SHORT)
                            .show();
                    }
                }
            });
            //Add divider to recyclerView
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));


        }
        return view;
        */
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        setRetainInstance(true);

        if(resultSet.getTotalPages() == 0) {
            Toast.makeText(getActivity(), "No results found.\nPlease try another search.",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * FRAGMENT LIFECYCLE END
     */

    /**
     * >Communicating with Other Fragments
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(SearchResult result);
    }

    /*
     * FRAGMENT SPECIFIC CODE START
     */

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchResultFragment newInstance(SearchResultsSet resultsSet) {
        SearchResultFragment fragment = new SearchResultFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, resultsSet);

        fragment.setArguments(args);
        return fragment;
    }

    private void customLoadMoreDataFromApi(int page) {
        resultSet.setPage(page);
        updateResultList();
    }

    private void updateResultList(){
        JSONObject j = resultSet.retrieveResults(futureResultString);
        resultSet.addResults(j);
        preloadNextPage();
    }

    private void preloadNextPage(){
        futureResultString = Search.accessAPISearchEndpoint(resultSet, this.getActivity(),
                resultSet.getPage()+1);

    }
    //load first page
    private void preloadNextPage(int page){
        futureResultString = Search.accessAPISearchEndpoint(resultSet, this.getActivity(),
                page);
    }

    /*
     * FRAGMENT SPECIFIC CODE END
     */
}