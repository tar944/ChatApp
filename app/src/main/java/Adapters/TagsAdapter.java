package Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mori.sepid.chatapp.R;

import java.util.ArrayList;

import DataModels.ContactsDataModel;

public class TagsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ContactsDataModel>arrayCDM;
    private ViewHolder holder;
    public TagsAdapter(Activity activity,ArrayList<ContactsDataModel>arrayCDM)
    {
        this.arrayCDM=arrayCDM;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return arrayCDM.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayCDM.get(i);
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
            view=activity.getLayoutInflater().inflate(R.layout.tags_cell,null);
            holder.tv_name= (TextView) view.findViewById(R.id.tv_tag_name);
            holder.tv_url= (TextView) view.findViewById(R.id.tv_tag_link);
            holder.rl_tag= (RelativeLayout) view.findViewById(R.id.rl_tags);
            view.setTag(holder);
        }else
        {
            holder= (ViewHolder) view.getTag();
        }

        holder.tv_name.setText(arrayCDM.get(i).contact_name);
        holder.tv_url.setText(arrayCDM.get(i).contact_url);

        if (arrayCDM.get(i).state)
        {
            holder.rl_tag.setBackgroundColor(activity.getResources().getColor(R.color.light_green));
        }else
        {
            holder.rl_tag.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        return view;
    }

    private class ViewHolder
    {
        TextView tv_name,tv_url;
        RelativeLayout rl_tag;
    }

    public String getTags()
    {
        String result="";
        for (int i=0;i<arrayCDM.size();i++)
        {
            if (arrayCDM.get(i).state)
            {
                result +=arrayCDM.get(i).contact_url+",";
            }
        }
        return result;
    }

    public void setState(int pos)
    {
        if (arrayCDM.get(pos).state)
        {
            arrayCDM.get(pos).state=false;
        }else
        {
            arrayCDM.get(pos).state=true;
        }
        notifyDataSetChanged();
    }
}
