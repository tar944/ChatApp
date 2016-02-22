package com.mori.sepid.chatapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import static com.mori.sepid.chatapp.R.id.iv_image;

public class PictureActivity extends Activity {

    private ImageView iv_main,iv_back;
    private TextView tv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Casting();
        if (getIntent().getExtras().getString("data").equals("tstimg"))
        {
            iv_main.setImageResource(R.drawable.tstimg);
        }else
        {
            Picasso.with(this).load(getCacheImage(getIntent().getExtras().getString("data"))).into(iv_main);
        }
        tv_name.setText(getIntent().getExtras().getString("data"));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Casting()
    {
        iv_main= (ImageView) findViewById(iv_image);
        iv_back= (ImageView) findViewById(R.id.iv_img_back);
        tv_name= (TextView) findViewById(R.id.tv_iv_name);

    }
    private static File getCacheImage(String name)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        Log.v("Images Read ", root + "/Avesty/" + name);
        File myDir = new File(root + "/Avesty/"+"/"+name);

        if (myDir.exists())
        {
            return myDir;
        }
        else
            return null;
    }
}
