package com.xtraordinair.tb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.xtraordinair.tb.SearchResultFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class CardViewRecyclerViewAdapter
        extends RecyclerView.Adapter<CardViewRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SearchResult> mValues;
    private final Context mContext;
    private final String LOLLIPOP_SDK = "5.";

    public CardViewRecyclerViewAdapter(ArrayList<SearchResult> results,
                                       Context c) {
        mValues = results;
        mContext = c;
    }

    //Get cardview_layout from XML
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_layout, parent, false);
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

        if(android.os.Build.VERSION.RELEASE.startsWith(LOLLIPOP_SDK)){
            if(imgURL != null){
                imgURL = imgURL.replaceFirst("https://", "http://");
            }

        }

        //Load image into ImageView
        //If error or fail use Drawable
        if(itemType.equals("beer")) {
            Ion.with(mContext)
                    .load(imgURL)
                    .withBitmap()
                    .placeholder(R.drawable.ic_local_bar_black_24dp)
                    .error(R.drawable.ic_local_bar_black_24dp)
                    .intoImageView(holder.mResultIcon);
        }else if(itemType.equals("brewery")){
            Ion.with(mContext)
                    .load(imgURL)
                    .withBitmap()
                    .placeholder(R.drawable.ic_store_mall_directory_black_24dp)
                    .error(R.drawable.ic_store_mall_directory_black_24dp)
                    .intoImageView(holder.mResultIcon);
        }

        /*
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mSecondaryInfoView;
        public final ImageView mResultIcon;
        public SearchResult mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.cardview_result_name_textview);
            mSecondaryInfoView = (TextView) view.findViewById(R.id.cardview_result_date_textview);
            mResultIcon = (ImageView) view.findViewById(R.id.cardview_result_imageview);
        }
    }
}
