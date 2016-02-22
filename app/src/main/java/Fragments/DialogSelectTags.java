package Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mori.sepid.chatapp.R;

import java.util.ArrayList;

import Adapters.TagsAdapter;
import DataModels.ContactsDataModel;

public class DialogSelectTags extends android.app.Dialog implements View.OnClickListener {

    private Activity context;
    private Button  btn_ok;
    private ImageView iv_close;
    private TagsAdapter tagsAdapter;
    private ArrayList<ContactsDataModel>arrayCDM;
    private ListView lv_contacts;

    public DialogSelectTags(Activity context, int theme,ArrayList<ContactsDataModel>arrayCDM) {
        super(context, theme);
        this.context = context;
        this.arrayCDM=arrayCDM;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        listener = (setOnPassResultTags) context;
        setContentView(R.layout.dialog_contact);
        Casting();
        tagsAdapter=new TagsAdapter(context,arrayCDM);
        lv_contacts.setAdapter(tagsAdapter);

        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tagsAdapter.setState(i);
            }
        });

        btn_ok.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    public void Casting() {
        btn_ok = (Button) findViewById(R.id.btn_ok_tags);
        iv_close = (ImageView) findViewById(R.id.iv_dlg_net_close);
        lv_contacts= (ListView) findViewById(R.id.lv_contacts);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok_tags:
                listener.onPassResultTags(tagsAdapter.getTags());
                dismiss();
                break;
            case R.id.iv_dlg_net_close:
                cancel();
                break;
        }
    }

    public static interface setOnPassResultTags {
        public void onPassResultTags(String result);
    }

    setOnPassResultTags listener;
}
