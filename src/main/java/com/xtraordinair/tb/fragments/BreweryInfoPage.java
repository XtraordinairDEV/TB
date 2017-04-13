package com.xtraordinair.tb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xtraordinair.tb.R;
import com.xtraordinair.tb.entities.Brewery;

/**
 * Created by Steph on 12/21/2016.
 */
public class BreweryInfoPage extends  Fragment{
    private static final String ARG_PARAM1 = "item";
    private Brewery mBrewery;
    private ScrollView mItemScrollView;

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
            mBrewery = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mItemScrollView == null){
            //Inflate parent view and then bind all other views with (Parent).findViewById(int resid)

            //ScrollView (Parent)
            mItemScrollView = (ScrollView) inflater.inflate(R.layout.infopage_brewery_layout, container, false);
            mItemScrollView.setSmoothScrollingEnabled(true);

            TextView beerNameTextView = (TextView) mItemScrollView.findViewById(R.id.name_content_textview);
            if(mBrewery.getName() != null){
                beerNameTextView.setText(mBrewery.getName());
            }
            TextView assocBreweryTextView = (TextView) mItemScrollView.findViewById(R.id.assoc_brewery_name_content_textview);
            if(mBrewery.getBreweryEst() != null){
                assocBreweryTextView.setText(mBrewery.getBreweryEst());
            }

            //Label image
            final ImageView labelImageView = (ImageView) mItemScrollView.findViewById(R.id.label_image_imageview);
            labelImageView.setImageBitmap(mBrewery.getBitmapMedium());
            Ion.with(this.getActivity())
                    .load(mBrewery.getLargeIconLoc())
                    .asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {

                            if(e != null){
                                e.printStackTrace();
                                Toast.makeText(BreweryInfoPage.this.getActivity(), "Large Failed", Toast.LENGTH_SHORT).show();
                            } else if(result != null){
                                mBrewery.setBitmapLarge(Bitmap.createScaledBitmap(result, result.getWidth()*2, result.getHeight()*2, false));
                                labelImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                labelImageView.setImageBitmap(mBrewery.getBitmapLarge());
                            }
                        }
                    });

            TextView descriptionTitleTextView = (TextView) mItemScrollView.findViewById(R.id.description_title_textview);
            TextView descriptionContentTextView = (TextView) mItemScrollView.findViewById(R.id.description_content_textview);
            descriptionTitleTextView.setText(R.string.description_title);
            String descriptionContentString = mBrewery.getDescription();
            if(descriptionContentString != null){
                descriptionContentTextView.setText(descriptionContentString);
            }else{
                descriptionContentTextView.setText(R.string.not_available);
            }


            return mItemScrollView;
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
        outState.putParcelable(ARG_PARAM1, mBrewery);
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

    public static Fragment newInstance(Brewery mItem) {
        BreweryInfoPage fragment = new BreweryInfoPage();


        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, mItem);

        fragment.setArguments(args);
        return fragment;
    }
}
