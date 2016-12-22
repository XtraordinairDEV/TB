package com.xtraordinair.tb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xtraordinair.tb.R;
import com.xtraordinair.tb.entities.Beer;

/**
 * Created by Steph on 12/21/2016.
 */
public class BeerInfoPage extends Fragment{
    private static final String ARG_PARAM1 = "item";
    private Beer mBeer;
    private ScrollView itemScrollView;

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
            mBeer = savedInstanceState.getParcelable(ARG_PARAM1);
        } else if (getArguments() != null) {
            mBeer = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (itemScrollView == null){
            //Inflate parent view and then bind all other views with (Parent).findViewById(int resid)

            //ScrollView (Parent)
            itemScrollView = (ScrollView) inflater.inflate(R.layout.infopage_beer_layout, container, false);
            itemScrollView.setSmoothScrollingEnabled(true);

            TextView beerNameTextView = (TextView) itemScrollView.findViewById(R.id.name_content_textview);
            if(mBeer.getName() != null){
                beerNameTextView.setText(mBeer.getName());
            }
            TextView assocBreweryTextView = (TextView) itemScrollView.findViewById(R.id.assoc_brewery_name_content_textview);
            if(mBeer.getBreweryName() != null){
                assocBreweryTextView.setText(mBeer.getBreweryName());
            }

            //Label image
            ImageView labelImageView = (ImageView) itemScrollView.findViewById(R.id.label_image_imageview);
            labelImageView.setImageBitmap(mBeer.getBitmapLarge());

            //ABV content
            TextView abvTitleTextView = (TextView) itemScrollView.findViewById(R.id.abv_title_textview);
            TextView abvContentTextView = (TextView) itemScrollView.findViewById(R.id.abv_content_textview);
            abvTitleTextView.setText(R.string.abv_title);
            String abvContentString = mBeer.getABV();
            if(abvContentString != null) {
                abvContentString = abvContentString + "%";
                abvContentTextView.setText(abvContentString);
            }else{
                abvContentTextView.setText(R.string.not_available);
            }

            //IBU content
            TextView ibuTitleTextView = (TextView) itemScrollView.findViewById(R.id.ibu_title_textview);
            TextView ibuContentTextView = (TextView) itemScrollView.findViewById(R.id.ibu_content_textview);
            ibuTitleTextView.setText(R.string.ibu_title);
            String ibuContentString = mBeer.getIBU();
            if(ibuContentString != null){
                ibuContentTextView.setText(ibuContentString);
            }else{
                ibuContentTextView.setText(R.string.not_available);
            }

            //SRM content
            TextView srmTitleTextView = (TextView) itemScrollView.findViewById(R.id.srm_title_textview);
            TextView srmContentTextView = (TextView) itemScrollView.findViewById(R.id.srm_content_textview);
            srmTitleTextView.setText(R.string.srm_title);
            String srmContentString = mBeer.getStandardReferenceMethod();
            if(srmContentString != null){
                srmContentTextView.setText(srmContentString);
            }else{
                srmContentTextView.setText(R.string.not_available);
            }

            //OG content
            TextView ogTitleTextView = (TextView) itemScrollView.findViewById(R.id.og_title_textview);
            TextView ogContentTextView = (TextView) itemScrollView.findViewById(R.id.og_content_textview);
            ogTitleTextView.setText(R.string.og_title);
            String ogContentString = mBeer.getOriginalGravity();
            if(ogContentString != null){
                ogContentTextView.setText(ogContentString);
            }else{
                ogContentTextView.setText(R.string.not_available);
            }

            TextView descriptionTitleTextView = (TextView) itemScrollView.findViewById(R.id.description_title_textview);
            TextView descriptionContentTextView = (TextView) itemScrollView.findViewById(R.id.description_content_textview);
            descriptionTitleTextView.setText(R.string.description_title);
            String descriptionContentString = mBeer.getDescription();
            if(descriptionContentString != null){
                descriptionContentTextView.setText(descriptionContentString);
            }else{
                descriptionContentTextView.setText(R.string.not_available);
            }


            return itemScrollView;
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
        outState.putParcelable(ARG_PARAM1, mBeer);
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

    public static Fragment newInstance(Beer mItem) {
        SearchResultFragment fragment = new SearchResultFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, mItem);

        fragment.setArguments(args);
        return fragment;
    }
}
