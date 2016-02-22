package Fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mori.sepid.chatapp.MainActivity;
import com.mori.sepid.chatapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Adapters.MainChatAdapter;
import DataModels.ContactsDataModel;
import DataModels.MainChatDataModel;

public class MainChatFragment extends Fragment implements View.OnClickListener{
    private static EditText et_msg;
    private EditText et_pname;
    private Button btn_pname;
    private ListView lv_msg;
    private ImageView iv_camera,iv_send;
    private static ImageView iv_img_msg;
    private static TextView tv_img_title,tv_img_size;
    private static Fragment _this;
    private static FragmentManager fragmentManager;
    private static ArrayList<MainChatDataModel>arrayRecive;
    private static ArrayList<MainChatDataModel>arrayMCDM;
    private static ArrayList<MainChatDataModel>arraySend;
    private static ArrayList<ContactsDataModel>arrayCDM;
    private static Context myContext;
    private static RelativeLayout rl_img;
    private MainChatAdapter mainChatAdapter;
    private ImageView iv_tags;
    private static TextView tv_tag_to,tv_tags_name;
    private int msgid=1;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_chat, container, false);
        Casting(view);
        fakeData();
        _this=this;
        arraySend=new ArrayList<MainChatDataModel>();
        myContext=getActivity().getApplicationContext();
        fragmentManager=getFragmentManager();
        mainChatAdapter=new MainChatAdapter(getActivity(),arrayMCDM);
        lv_msg.setAdapter(mainChatAdapter);
        MainActivity.ll_back.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        et_pname.setText(getArguments().getString("title"));
        MainActivity.tv_title.setText(getArguments().getString("title"));
        btn_pname.setOnClickListener(this);
        iv_tags.setOnClickListener(this);
        iv_send.setOnClickListener(this);
        et_pname.setFocusableInTouchMode(false);
        MainActivity.iv_search.setOnClickListener(this);
        et_pname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                {
                    et_pname.selectAll();
                }
            }
        });
        return view;
    }
    private void Casting(View view)
    {
        et_pname= (EditText) view.findViewById(R.id.et_pro_name);
        btn_pname= (Button) view.findViewById(R.id.btn_pro_name);
        et_msg= (EditText) view.findViewById(R.id.et_snd_message);
        tv_img_size= (TextView) view.findViewById(R.id.tv_img_size);
        tv_img_title= (TextView) view.findViewById(R.id.tv_img_name);
        iv_camera= (ImageView) view.findViewById(R.id.iv_camera);
        iv_send= (ImageView) view.findViewById(R.id.iv_send);
        lv_msg= (ListView) view.findViewById(R.id.lv_main_chat);
        iv_img_msg= (ImageView) view.findViewById(R.id.iv_img_msg);
        rl_img= (RelativeLayout) view.findViewById(R.id.rl_send_image);
        iv_tags= (ImageView) view.findViewById(R.id.iv_sh_tags);
        tv_tag_to= (TextView) view.findViewById(R.id.tv_sh_to);
        tv_tags_name= (TextView) view.findViewById(R.id.tv_sh_tags);
    }

    private void fakeData()
    {
        AssetManager assetManager = getActivity().getAssets();
        InputStream input;
        try {
            input = assetManager.open("recive_data.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            String text = new String(buffer);
            Gson gson = new Gson();
            arrayRecive = gson.fromJson(text, new TypeToken<List<MainChatDataModel>>() {
            }.getType());
            arrayMCDM=new ArrayList<MainChatDataModel>();
            arrayRecive.get(0).received_time=System.currentTimeMillis();
            arrayMCDM.add(arrayRecive.get(0));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InputStream input2;
        try {
            input2 = assetManager.open("contact_lists.txt");
            int size2 = input2.available();
            byte[] buffer2 = new byte[size2];
            input2.read(buffer2);
            input2.close();

            // byte buffer into a string
            String text2 = new String(buffer2);
            Gson gson2 = new Gson();
            arrayCDM = gson2.fromJson(text2, new TypeToken<List<ContactsDataModel>>() {
            }.getType());

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim=null;
        try{
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            anim.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    ChatListFragment.hideFragment();
                }
            });
            return anim;
        }catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_send:
                if (et_msg.getText().toString().equals(""))
                {
                    if (rl_img.getVisibility()==View.GONE)
                        return;
                    else
                    {
                        MainChatDataModel mainChatDataModel=new MainChatDataModel();
                        mainChatDataModel.msg_type=true;
                        mainChatDataModel.image_url=tv_img_title.getText().toString();
                        mainChatDataModel.image_name=tv_img_title.getText().toString();
                        mainChatDataModel.image_size=tv_img_size.getText().toString();
                        mainChatDataModel.msg_state=new Random().nextInt(3);
                        mainChatDataModel.received_time=System.currentTimeMillis();
                        arraySend.add(mainChatDataModel);
                        mainChatAdapter.setMSg(mainChatDataModel);
                        getMessage();
                        rl_img.setVisibility(View.GONE);
                        et_msg.setVisibility(View.VISIBLE);
                        et_msg.setText("");
                    }
                }
                else
                {
                    MainChatDataModel mainChatDataModel=new MainChatDataModel();
                    mainChatDataModel.msg_type=true;
                    mainChatDataModel.image_url="";
                    mainChatDataModel.message=et_msg.getText().toString();
                    mainChatDataModel.msg_state=new Random().nextInt(3);
                    mainChatDataModel.received_time=System.currentTimeMillis();
                    arraySend.add(mainChatDataModel);
                    mainChatAdapter.setMSg(mainChatDataModel);
                    getMessage();
                    et_msg.setText("");
                }
                break;
            case R.id.ll_back:
                myOnKeyDown(0);
                break;
            case R.id.iv_camera:
                DialogImportFiles dialog;
                dialog=new DialogImportFiles(getActivity(), R.style.MaterialDialogSheet);
                dialog.show();
                break;
            case R.id.btn_pro_name:
                if (btn_pname.getText().toString().equals("Edit")){
                    et_pname.setFocusable(true);
                    et_pname.setFocusableInTouchMode(true);
                    et_pname.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(et_pname, 0);
                    btn_pname.setText("Save");
                } else
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    et_pname.setFocusableInTouchMode(false);
                    btn_pname.setText("Edit");
                    et_pname.setFocusable(false);
                    MainActivity.tv_title.setText(et_pname.getText().toString());
                }
                break;
            case R.id.iv_sh_tags:
                DialogSelectTags dialogSelectTags=new DialogSelectTags(getActivity(),R.style.MaterialDialogSheet,arrayCDM);
                dialogSelectTags.show();
                break;
            case R.id.iv_ac_search:
                Toast.makeText(getActivity(), "There is no Data for Searching", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getMessage() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (msgid<8) {
                            arrayRecive.get(msgid).received_time = System.currentTimeMillis();
                            mainChatAdapter.setMSg(arrayRecive.get(msgid));
                            msgid++;
                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"Joey is Offline Now",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
;
            }
        },new Random().nextInt(5)*1000);
    }

    public static void myOnKeyDown(int key_code){
            ChatListFragment.showFragment();
        MainActivity.iv_back.setVisibility(View.INVISIBLE);
        MainActivity.tv_title.setText("Chat List");
        fragmentManager.beginTransaction().setCustomAnimations(0, R.anim.anim_back_2).remove(_this).commit();
    }

    public static void getImage(String path)
    {
        rl_img.setVisibility(View.VISIBLE);
        et_msg.setVisibility(View.GONE);
        String[]name=path.split("/");
        File img=getCacheImage(name[name.length-1]);
        Picasso.with(myContext).load(img).into(iv_img_msg);
        tv_img_size.setText((img.length()/1024)+" KB");
        tv_img_title.setText(name[name.length-1]);
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
    public static void setTags(String tags)
    {
        if (!tags.equals("")) {
            tv_tag_to.setText("To : ");
            tv_tags_name.setText(tags);
        }else
        {
            tv_tags_name.setText("");
            tv_tag_to.setText("No Tags");
        }
    }
}
