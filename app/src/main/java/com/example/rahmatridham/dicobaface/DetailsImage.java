package com.example.rahmatridham.dicobaface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailsImage extends AppCompatActivity {
    ImageView gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_image);
        gambar = (ImageView) findViewById(R.id.getImage);

    }
}
