package Adapters;

import android.app.Activity;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mori.sepid.chatapp.R;

import java.util.ArrayList;


public class TagAdapter extends BaseAdapter {

    private ArrayList<String>tags;
    private Activity activity;
    private ViewHolder holder;
    public void TagAdapter(Activity activity,ArrayList<String>tags)
    {
        this.tags=tags;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int i) {
        return tags.get(i);
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
            view=activity.getLayoutInflater().inflate(R.layout.tag_cells,null);
            holder.tv_tag= (TextView) view.findViewById(R.id.tv_tags);
            holder.ll_tag= (LinearLayout) view.findViewById(R.id.ll_tags);
            view.setTag(holder);
        }else
        {
            holder= (ViewHolder) view.getTag();
        }
        // TODO: 2/21/2016 ll_tag color should be changing insted of clicking
        holder.tv_tag.setText(tags.get(i));
        return view;
    }

    private class ViewHolder
    {
        TextView tv_tag;
        LinearLayout ll_tag;
    }

    public void setDataChange(ArrayList<String>tags)
    {
        this.tags=tags;
        notifyDataSetChanged();
    }
}
