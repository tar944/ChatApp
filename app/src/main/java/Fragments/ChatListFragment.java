package Fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mori.sepid.chatapp.MainActivity;
import com.mori.sepid.chatapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Adapters.ChatListAdapter;
import DataModels.ChatListsDataModel;

public class ChatListFragment extends Fragment {

    private ListView lv_chats;
    private ChatListAdapter chatListAdapter;
    private ArrayList<ChatListsDataModel>arrayCLDM;
    private static FragmentManager fragmentManager;
    private static Fragment _this;
    Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat_list, container, false);
        Casting(view);
        FakeData();
        fragmentManager=getFragmentManager();
        _this=this;
        bundle=new Bundle();
        MainActivity.iv_back.setVisibility(View.INVISIBLE);
        MainActivity.tv_title.setText("Chats List");
        chatListAdapter=new ChatListAdapter(getActivity(),arrayCLDM);
        lv_chats.setAdapter(chatListAdapter);
        MainActivity.iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"There is no Data for Searching",Toast.LENGTH_SHORT).show();
            }
        });
        lv_chats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (chatListAdapter.getNewNum(i)!=0) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.anim_back_1, 0);
                    bundle.putString("title", chatListAdapter.getTitle(i));
                    bundle.putInt("chatid", chatListAdapter.getId(i));
                    MainChatFragment fragment;
                    fragment = new MainChatFragment();
                    MainActivity.iv_back.setVisibility(View.VISIBLE);
                    bundle.putInt("to", 0);
                    fragment.setArguments(bundle);
                    ft.add(R.id.rl_container, fragment).commit();
                }else
                {
                    Toast.makeText(getActivity(),"There is not any new Message in This Group",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void FakeData()
    {
        AssetManager assetManager = getActivity().getAssets();
        InputStream input;
        try {
            input = assetManager.open("chat_lists.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            String text = new String(buffer);
            Gson gson = new Gson();
            arrayCLDM = gson.fromJson(text, new TypeToken<List<ChatListsDataModel>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Casting(View view)
    {
        lv_chats= (ListView) view.findViewById(R.id.lv_chats);
    }

    public static void showFragment()
    {
        fragmentManager.beginTransaction().show(_this).commit();
    }

    public static void hideFragment()
    {
        fragmentManager.beginTransaction().hide(_this).commit();
    }

}
