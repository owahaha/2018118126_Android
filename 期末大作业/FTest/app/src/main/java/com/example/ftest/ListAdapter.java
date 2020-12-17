package com.example.ftest;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class ViewHolder{
    public TextView tip_location;
    public TextView tip_content;
    public TextView tip_time;

    View itemView;

    public ViewHolder(View itemView){
        if (itemView==null){
            throw  new IllegalArgumentException("NULL");
        }
        this.itemView=itemView;
        tip_content=itemView.findViewById(R.id.tip_content);
        tip_time=itemView.findViewById(R.id.tip_time);
    }
}

public class ListAdapter extends BaseAdapter {
    private List<TipInfo> tiplist;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder holder =null;

    public  ListAdapter(Context context,List<TipInfo> tiplist){
        this.tiplist=tiplist;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tiplist.size();
    }

    @Override
    public Object getItem(int position) {
        return tiplist.get(position).getContent();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(tiplist.get(position).getId());
    }

    public void remove(int index){
        tiplist.remove(index);
    }

    public void refreshDataSet(){
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.list_item,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tip_content.setText(tiplist.get(position).getContent());
        holder.tip_time.setText(tiplist.get(position).getTime());
        return convertView;
    }
}
