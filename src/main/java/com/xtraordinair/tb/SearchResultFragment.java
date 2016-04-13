package com.xtraordinair.tb;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
    private static final String ARG_PARAM1 = "Search Results";
    private SearchResultsSet resultSet;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, mColumnCount);
            RecyclerView recyclerView = (RecyclerView) view;
            final ArrayList<SearchResult> shownResults = new ArrayList<>();
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
                    customLoadMoreDataFromApi(page);

                    // update the adapter, saving the last known size
                    int curSize = recyclerViewAdapter.getItemCount();
                    shownResults.addAll(resultSet.getDeltaResultList());


                    // for efficiency purposes, only notify the adapter of what elements that got changed
                    // curSize will equal to the index of the first element inserted because the list is 0-indexed
                    recyclerViewAdapter.notifyItemRangeInserted(curSize, shownResults.size() - 1);
                }
            });
            //Add divider to recyclerView
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getActivity()));


        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setRetainInstance(true);

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
        resultSet = Search.accessAPISearchEndpoint(resultSet, getActivity().getApplicationContext(),
                                                    resultSet.getPage());

    }


    /*
     * FRAGMENT SPECIFIC CODE END
     */
}
