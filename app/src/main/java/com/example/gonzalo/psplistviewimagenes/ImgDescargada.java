package com.example.gonzalo.psplistviewimagenes;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by Gonzalo on 30/11/2015.
 */
public class ImgDescargada extends AppCompatActivity {

    private ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagen);
        iv = (ImageView) findViewById(R.id.iv);
        Bitmap img = getIntent().getParcelableExtra("imagen");
        iv.setImageBitmap(img);
    }
}
