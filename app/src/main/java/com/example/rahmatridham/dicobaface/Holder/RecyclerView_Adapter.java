package com.example.rahmatridham.dicobaface.Holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.rahmatridham.dicobaface.CustomView;
import com.example.rahmatridham.dicobaface.MainActivity;
import com.example.rahmatridham.dicobaface.Model.StickerItem;
import com.example.rahmatridham.dicobaface.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rahmatridham on 1/9/2017.
 */

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<StickerItem> arrayList;
    private Context context;

    public RecyclerView_Adapter(Context context, ArrayList<StickerItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.list_row, parent, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final StickerItem model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder

        ((RecyclerViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "item no: " + position, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    MainActivity.stickerCam = model.getImage();
//                    CustomView.drawBox(CustomView.canvas);

                } else {
                    MainActivity.stickerCam = model.getImage();
                }

            }
        });
//        Bitmap image = BitmapFactory.decodeResource(context.getResources(),
//                model.getImage());// This will convert drawbale image into
//         bitmap

        // setting title
//        mainHolder.image.setImageBitmap(image);
        Picasso.with(context).load(model.getImage()).into(mainHolder.image);


    }

}
