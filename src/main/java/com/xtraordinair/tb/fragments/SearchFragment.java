package com.xtraordinair.tb.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.xtraordinair.tb.activities.MainActivity;
import com.xtraordinair.tb.R;
import com.xtraordinair.tb.entities.SearchResultsSet;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RelativeLayout mSearchFragmentLayout;
    private EditText searchInputEditText;
    private Button searchButton;
    private Spinner searchChoicesSpinner;


    /*
     * INITIALIZATION METHODS START
     */

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {

        //CREATE BUNDLE WITH ARGS IF NEEDED
        return new SearchFragment();
    }
    /*
     * INITIALIZATION METHODS END
     */


    /*
    * FRAGMENT LIFECYCLE START
    */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //Do something with arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the cardview_result_layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mSearchFragmentLayout = (RelativeLayout)
                getActivity().findViewById(R.id.search_layout);

        setTitle();
        bindXMLLayout();
        onSearchButtonPress();
        onEnterKeyPressInSearchEditText();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * FRAGMENT LIFECYCLE END
     */

    /*
     * See: Communicating with Other Fragments
     * http://developer.android.com/training/basics/fragments/communicating.html
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*
    * FRAGMENT SPECIFIC CODE START
    */

    private void setTitle() {
        getActivity().setTitle("TB Search");
    }

    private void bindXMLLayout() {
        searchInputEditText = (EditText) getActivity().findViewById(R.id.search_query_edittext);
        searchButton = (Button) getActivity().findViewById(R.id.buttonStart);
        searchChoicesSpinner = (Spinner) getActivity().findViewById(R.id.spinChoices);
    }

    private void onSearchButtonPress() {

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkSearchParam();
            }
        });
    }

    private void onEnterKeyPressInSearchEditText() {

        searchInputEditText.setOnKeyListener(new EditText.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //If ENTER key is DOWN
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    checkSearchParam();
                    return true;
                } else
                    return false;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(MainActivity.FRAGMENT_TAG, "searchFrag");
        super.onSaveInstanceState(outState);
    }

    private void checkSearchParam(){
        String userSearchQuery = searchInputEditText.getText().toString();

        if(userSearchQuery.matches("")){
            Snackbar
                    .make(mSearchFragmentLayout,
                            "Please enter a beer or brewery", Snackbar.LENGTH_LONG)
                    .show();
        }
        else{
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            searchInputEditText.setText("");

            SearchResultsSet resultsSet = new SearchResultsSet(true, getSearchType(), userSearchQuery);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.main_fragment,
                            SearchResultFragment.newInstance(resultsSet), "SearchResults")
                    .addToBackStack("Search -> Results")
                    .commit();
        }

    }

    private int getSearchType() {
        final int SEARCH_BEER_ONLY = 1;
        final int SEARCH_BREWERIES_ONLY=2;
        final int SEARCH_ALL = 3;

        String searchTypeOption = searchChoicesSpinner.getSelectedItem().toString();

        if (searchTypeOption.equals("All"))
            return SEARCH_ALL;
        else if (searchTypeOption.equals("Beers Only"))
            return SEARCH_BEER_ONLY;
        else if (searchTypeOption.equals("Breweries Only"))
            return SEARCH_BREWERIES_ONLY;
        else
            return SEARCH_ALL;
    }

    /*
    *FRAGMENT SPECIFIC CODE END
    */
}