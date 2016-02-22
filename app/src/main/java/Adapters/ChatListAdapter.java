package Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mori.sepid.chatapp.R;

import java.util.ArrayList;

import DataModels.ChatListsDataModel;
import Utility.MLRoundedImageView;


public class ChatListAdapter extends BaseAdapter {

    private ArrayList<ChatListsDataModel>arrayCLDM=new ArrayList<ChatListsDataModel>();
    private ViewHolder holder;
    private Activity activity;

    public ChatListAdapter(Activity activity,ArrayList<ChatListsDataModel>arrayCLDM)
    {
        this.arrayCLDM=arrayCLDM;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return arrayCLDM.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayCLDM.get(i);
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
            view=activity.getLayoutInflater().inflate(R.layout.lv_chat_list,null);
            holder.tv_name= (TextView) view.findViewById(R.id.tv_ch_name);
            holder.tv_count= (TextView) view.findViewById(R.id.tv_ch_num);
            holder.tv_time= (TextView) view.findViewById(R.id.tv_ch_time);
            holder.tv_msg= (TextView) view.findViewById(R.id.tv_ch_msg);
            holder.iv_img= (MLRoundedImageView) view.findViewById(R.id.iv_ch_img);
            view.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(arrayCLDM.get(i).group_name);
        holder.tv_time.setText(arrayCLDM.get(i).last_time+"");
        holder.tv_msg.setText(arrayCLDM.get(i).last_message);
        if (arrayCLDM.get(i).new_count!=0)
        {
            holder.tv_count.setVisibility(View.VISIBLE);
            holder.tv_count.setText(arrayCLDM.get(i).new_count+"");

        }else
        {
            holder.tv_count.setVisibility(View.GONE);
        }

        if (i==0)
        {
            holder.iv_img.setImageResource(R.drawable.projectmanagement);
        }else
        {
            holder.iv_img.setImageResource(R.drawable.test);
        }

        return view;
    }

    public int getId(int pos) {
        return arrayCLDM.get(pos).id;
    }

    private class ViewHolder
    {
        TextView tv_name,tv_time,tv_count,tv_msg;
        MLRoundedImageView iv_img;
    }

    public void addAll(ArrayList<ChatListsDataModel>arrayList) {
        arrayCLDM.clear();
        arrayCLDM=arrayList;
        notifyDataSetChanged();
    }

    public String getTitle(int pos)
    {
        return arrayCLDM.get(pos).group_name;
    }

    public int getNewNum(int pos)
    {
        return arrayCLDM.get(pos).new_count;
    }
}
