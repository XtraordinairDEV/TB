package com.xtraordinair.tb.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xtraordinair.tb.R;
import com.xtraordinair.tb.activities.MainActivity;
import com.xtraordinair.tb.entities.Beer;
import com.xtraordinair.tb.entities.Brewery;
import com.xtraordinair.tb.fragments.BeerInfoPage;
import com.xtraordinair.tb.fragments.BreweryInfoPage;
import com.xtraordinair.tb.interfaces.SearchResult;

import java.util.ArrayList;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class CardViewRecyclerViewAdapter
        extends RecyclerView.Adapter<CardViewRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SearchResult> mValues;
    private final Context mContext;

    public CardViewRecyclerViewAdapter(ArrayList<SearchResult> results,
                                       Context context) {
        super();
        mValues = results;
        mContext = context;
    }

    //Get cardview_result_layout from XML
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_result_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String imgURL = mValues.get(holder.getAdapterPosition()).getMediumIconLoc();
        String topText = mValues.get(holder.getAdapterPosition()).getName();
        String bottomText = mValues.get(holder.getAdapterPosition()).getSecondaryInfo();
        String itemType = mValues.get(holder.getAdapterPosition()).getType();

        holder.mItem = mValues.get(holder.getAdapterPosition());
        holder.mNameView.setText(topText);
        holder.mSecondaryInfoView.setText(bottomText);

        if(imgURL == null){
            holder.mResultIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);

            if(itemType.equals("beer")) {
                holder.mResultIcon.setImageResource(R.drawable.ic_local_bar_black_24dp);
            }else if(itemType.equals("brewery")){
                holder.mResultIcon.setImageResource(R.drawable.ic_store_mall_directory_black_24dp);
            }
        }else{
            holder.mResultIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //Quick fix for Koush Ion https:// not working on Lollipop
            //Change https:// to http://
            String LOLLIPOP_SDK = "5.";
            if(android.os.Build.VERSION.RELEASE.startsWith(LOLLIPOP_SDK)){
                imgURL = imgURL.replaceFirst("https://", "http://");
                holder.mResultIcon.setImageResource(R.drawable.ic_insert_photo_black_24dp);
            }

            Ion.with(mContext)
                .load(imgURL)
                .asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        if(result != null) {
                            holder.mItem.setBitmapMedium(result);
                            holder.mResultIcon.setImageBitmap(holder.mItem.getBitmapMedium());
                        }
                    }
                });
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) mContext;
                FragmentManager fragmentManager = activity.getFragmentManager();
                Log.i("XO mItem type", holder.mItem.getType());
                if(holder.mItem.getType().equals("beer")) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.main_fragment,
                                    BeerInfoPage.newInstance((Beer) holder.mItem))
                            .addToBackStack("A")
                            .commit();
                }else if(holder.mItem.getType().equals("brewery")){
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.main_fragment,
                                    BreweryInfoPage.newInstance((Brewery) holder.mItem))
                            .addToBackStack("A")
                            .commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final TextView mSecondaryInfoView;
        final ImageView mResultIcon;
        SearchResult mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.cardview_result_name_textview);
            mSecondaryInfoView = (TextView) view.findViewById(R.id.cardview_result_date_textview);
            mResultIcon = (ImageView) view.findViewById(R.id.cardview_result_imageview);
        }
    }
}
