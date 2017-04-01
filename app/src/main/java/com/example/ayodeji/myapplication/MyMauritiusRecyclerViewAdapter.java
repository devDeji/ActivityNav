package com.example.ayodeji.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayodeji.myapplication.MauritiusFragment.OnListFragmentInteractionListener;
import com.example.ayodeji.myapplication.mauritius.MauritiusContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMauritiusRecyclerViewAdapter extends RecyclerView.Adapter<MyMauritiusRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMauritiusRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mauritius, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        holder.mContentView.setText(mValues.get(position).details);
        Context ctx = holder.mImageView.getContext();
        Log.e("mValues position", String.valueOf(mValues.get(position)));
        Log.e("mValues getImageUrl", mValues.get(position).imageUrl);
        int id = ctx.getResources().getIdentifier("com.example.ayodeji.myapplication:drawable/" + mValues.get(position).imageUrl, null, null);
        Log.e("id++++++", String.valueOf(id));
        //holder.mImageView.setImageResource(id);
        Resources res = ctx.getResources();
        String mDrawableName = mValues.get(position).imageUrl;
        int resID = res.getIdentifier(mDrawableName, "drawable", ctx.getPackageName());
        Log.e("resID ", String.valueOf(resID));

        Drawable drawable = res.getDrawable(resID);
        holder.mImageView.setImageDrawable(drawable);

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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
