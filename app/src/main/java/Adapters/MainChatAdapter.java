package Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mori.sepid.chatapp.PictureActivity;
import com.mori.sepid.chatapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import DataModels.MainChatDataModel;
import Utility.MLRoundedImageView;

public class MainChatAdapter extends BaseAdapter {


    private ViewHolder holder;
    private Activity activity;
    private ArrayList<MainChatDataModel>arrayMCDM;

    public MainChatAdapter(Activity activity,ArrayList<MainChatDataModel>arrayMCDM)
    {
        this.arrayMCDM=arrayMCDM;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return arrayMCDM.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayMCDM.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null)
        {
            holder=new ViewHolder();
            view=activity.getLayoutInflater().inflate(R.layout.lv_main_chat_cells,null);
            holder.tv_s_msg= (TextView) view.findViewById(R.id.tv_cell_send_msg);
            holder.tv_r_msg= (TextView) view.findViewById(R.id.tv_cell_recive_msg);
            holder.tv_s_time= (TextView) view.findViewById(R.id.tv_cell_send_time);
            holder.tv_r_time= (TextView) view.findViewById(R.id.tv_cell_recive_time);
            holder.tv_state= (TextView) view.findViewById(R.id.tv_mch_state);
            holder.iv_snd= (MLRoundedImageView) view.findViewById(R.id.iv_send_img);
            holder.iv_rsnd= (MLRoundedImageView) view.findViewById(R.id.iv_recive_img);
            holder.rl_recive= (RelativeLayout) view.findViewById(R.id.rl_cell_recive);
            holder.rl_send= (RelativeLayout) view.findViewById(R.id.rl_cell_send);
            holder.tv_s_imgname= (TextView) view.findViewById(R.id.tv_s_img_name);
            holder.tv_s_imgsize= (TextView) view.findViewById(R.id.tv_s_img_size);
            holder.tv_r_imgname= (TextView) view.findViewById(R.id.tv_r_img_name);
            holder.tv_r_imgsize= (TextView) view.findViewById(R.id.tv_r_img_size);
            holder.rl_ssms= (RelativeLayout) view.findViewById(R.id.rl_s_sms);
            holder.rl_smms= (RelativeLayout) view.findViewById(R.id.rl_s_mms);
            holder.rl_rsms= (RelativeLayout) view.findViewById(R.id.rl_r_sms);
            holder.rl_rmms= (RelativeLayout) view.findViewById(R.id.rl_r_mms);
            holder.iv_s_img= (ImageView) view.findViewById(R.id.iv_s_img);
            holder.iv_r_img= (ImageView) view.findViewById(R.id.iv_r_img);
            view.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) view.getTag();
        }

        if (arrayMCDM.get(i).msg_type)
        {
            holder.rl_send.setVisibility(View.VISIBLE);
            holder.rl_recive.setVisibility(View.GONE);
            if (!arrayMCDM.get(i).image_url.equals(""))
            {
                holder.rl_smms.setVisibility(View.VISIBLE);
                holder.rl_ssms.setVisibility(View.GONE);
                holder.tv_s_imgname.setText(arrayMCDM.get(i).image_name);
                holder.tv_s_imgsize.setText(arrayMCDM.get(i).image_size);
                File img=getCacheImage(arrayMCDM.get(i).image_url);
                Picasso.with(activity).load(img).into(holder.iv_s_img);

                holder.iv_s_img.setTag(arrayMCDM.get(i).image_url);
                holder.iv_s_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, PictureActivity.class);
                        intent.putExtra("data", view.getTag().toString());
                        activity.startActivity(intent);
                    }
                });

            }else
            {
                holder.rl_smms.setVisibility(View.GONE);
                holder.rl_ssms.setVisibility(View.VISIBLE);
                holder.tv_s_msg.setText(arrayMCDM.get(i).message);
                DateFormat df = new SimpleDateFormat("HH:mm");
                String formatted = df.format(arrayMCDM.get(i).received_time);
                holder.tv_s_time.setText(formatted+"");
            }
            switch (arrayMCDM.get(i).msg_state)
            {
                case 0:
                    holder.tv_state.setTextColor(activity.getResources().getColor(R.color.txt_yello));
                    holder.tv_state.setText("Sent");
                    break;
                case 1:
                    holder.tv_state.setTextColor(activity.getResources().getColor(R.color.txt_green));
                    holder.tv_state.setText("Delivered");
                    break;
                case 2:
                    holder.tv_state.setTextColor(activity.getResources().getColor(R.color.txt_red));
                    holder.tv_state.setText("Failed");
                    break;
            }
        }
        else
        {
            holder.rl_send.setVisibility(View.GONE);
            holder.rl_recive.setVisibility(View.VISIBLE);
            if (!arrayMCDM.get(i).image_url.equals("null"))
            {
                holder.rl_rmms.setVisibility(View.VISIBLE);
                holder.rl_rsms.setVisibility(View.GONE);
                holder.tv_r_imgname.setText(arrayMCDM.get(i).image_name);
                holder.tv_r_imgsize.setText(arrayMCDM.get(i).image_size);
                holder.iv_r_img.setImageResource(R.drawable.tstimg);
                holder.iv_r_img.setTag("tstimg");
                holder.iv_r_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(activity, PictureActivity.class);
                        intent.putExtra("data",view.getTag().toString());
                        activity.startActivity(intent);
                    }
                });

            }else
            {
                holder.rl_rmms.setVisibility(View.GONE);
                holder.rl_rsms.setVisibility(View.VISIBLE);
                holder.tv_r_msg.setText(arrayMCDM.get(i).message);
                DateFormat df = new SimpleDateFormat("HH:mm");
                String formatted = df.format(arrayMCDM.get(i).received_time);
                holder.tv_r_time.setText(formatted+"");
            }
        }
        return view;
    }

    public void setMSg(MainChatDataModel mainChatDataModel)
    {
        arrayMCDM.add(mainChatDataModel);
        notifyDataSetChanged();
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

    private class ViewHolder
    {
        TextView tv_s_time,tv_r_time,tv_state,tv_s_msg,tv_r_msg,tv_s_imgname,tv_s_imgsize,tv_r_imgname,tv_r_imgsize;
        RelativeLayout rl_send,rl_recive,rl_ssms,rl_smms,rl_rsms,rl_rmms;
        MLRoundedImageView iv_snd,iv_rsnd;
        ImageView iv_s_img,iv_r_img;
    }
}
