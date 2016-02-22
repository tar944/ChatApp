package com.mori.sepid.chatapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import Fragments.ChatListFragment;
import Fragments.DialogImportFiles;
import Fragments.DialogSelectTags;
import Fragments.MainChatFragment;
import Utility.UserPicture;

public class MainActivity extends FragmentActivity implements DialogImportFiles.setOnPassResultNetwork, DialogSelectTags.setOnPassResultTags{

    public static ImageView iv_search,iv_back;
    public static TextView tv_title;
    public static LinearLayout ll_back;
    public static String fragment_name="";
    public static String imgPath="";
    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_SINGLE_PICTURE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Casting();
        Bundle bundle=new Bundle();
        if (imgPath.equals("")) {
            iv_back.setVisibility(View.INVISIBLE);
            MainActivity.tv_title.setText("Chat List");
            ChatListFragment fragment;
            fragment = new ChatListFragment();
            fragment_name = "list";
            bundle.putString("from", "main");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rl_container, fragment).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!imgPath.equals(""))
        {
                MainChatFragment.getImage(imgPath);
        }

    }

    private void Casting()
    {
        iv_back= (ImageView) findViewById(R.id.iv_ac_back);
        iv_search= (ImageView) findViewById(R.id.iv_ac_search);
        tv_title= (TextView) findViewById(R.id.tv_acn_title);
        ll_back= (LinearLayout) findViewById(R.id.ll_back);
    }

    @Override
    public void onPassResultImage(String result) {
        if (result.equals("camera"))
        {
            startActivity(new Intent(MainActivity.this,CameraActivity.class));

        }else
        {
            Intent intent = new Intent();
            intent.setType(IMAGE_TYPE);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    getString(R.string.select_picture)), SELECT_SINGLE_PICTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                String []name=selectedImageUri.toString().split("/");
                try {
                    SaveproIamge(new UserPicture(selectedImageUri, getContentResolver()).getBitmap(),name[name.length-1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, imgPath, Toast.LENGTH_SHORT).show();
                MainChatFragment.getImage("/"+name[name.length-1]+".jpg");
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.msg_failed_to_get_intent_data, Toast.LENGTH_LONG).show();
            Log.d(MainActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }

    public static void SaveproIamge(Bitmap finalBitmap,String name) {

        String root = Environment.getExternalStorageDirectory().toString();
        Log.v("Images", root + "/Avesty/" + name);
        File myDir = new File(root + "/Avesty/");
        myDir.mkdirs();
        String fname = name +".jpg";
        File file = new File(myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPassResultTags(String result) {
        MainChatFragment.setTags(result);
    }
}
