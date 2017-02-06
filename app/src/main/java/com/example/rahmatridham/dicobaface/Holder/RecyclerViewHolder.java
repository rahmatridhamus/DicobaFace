package com.example.rahmatridham.dicobaface.Holder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.rahmatridham.dicobaface.R;

/**
 * Created by rahmatridham on 1/9/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        this.image = (ImageView) itemView.findViewById(R.id.iconSticker);
    }
}
