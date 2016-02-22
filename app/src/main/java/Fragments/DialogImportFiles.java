package Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mori.sepid.chatapp.R;


public class DialogImportFiles extends android.app.Dialog implements View.OnClickListener{

    private Activity context;
    private Button btn_in,btn_up;
    private ImageView iv_close;

    public DialogImportFiles(Activity context, int theme) {
        super(context, theme);
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        getWindow().setGravity(Gravity.CENTER);
        listener=(setOnPassResultNetwork)context;

        setContentView(R.layout.dialog_import);
        Casting();
        btn_in.setOnClickListener(this);
        btn_up.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    public void Casting()
    {
        btn_in= (Button) findViewById(R.id.btn_camera);
        btn_up= (Button) findViewById(R.id.btn_gallery);
        iv_close= (ImageView) findViewById(R.id.iv_dlg_net_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_camera:
                listener.onPassResultImage("camera");
                dismiss();
                break;
            case  R.id.btn_gallery:
                listener.onPassResultImage("gallery");
                dismiss();
                break;
            case R.id.iv_dlg_net_close:
                cancel();
                break;
        }
    }

    public static interface setOnPassResultNetwork
    {
        public void onPassResultImage(String result);
    }

    setOnPassResultNetwork listener;

}
