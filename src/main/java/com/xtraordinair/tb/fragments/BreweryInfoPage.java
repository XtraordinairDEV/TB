package com.xtraordinair.tb.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.xtraordinair.tb.entities.Brewery;

/**
 * Created by Steph on 12/21/2016.
 */
public class BreweryInfoPage extends Fragment {
    private static final String ARG_PARAM1 = "item";

    public static Fragment newInstance(Brewery mItem) {
        BreweryInfoPage fragment = new BreweryInfoPage();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, mItem);

        fragment.setArguments(args);
        return fragment;
    }
}
