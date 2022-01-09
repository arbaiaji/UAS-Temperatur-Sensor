package com.example.temperatursensor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SQLiteListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> user_id;
    private ArrayList<String> user_title;
    private ArrayList<String> user_suhu;

    SQLiteListAdapter(Context context1, ArrayList<String> ID, ArrayList<String> TITLE,ArrayList<String> SUHU){
        this.context = context1;
        this.user_id = ID;
        this.user_title = TITLE;
        this.user_suhu = SUHU;
    }

    public class Holder{
        TextView textViewid;
        TextView textViewtitle;
        TextView textViewsuhu;
    }

    @Override
    public int getCount() {
        return user_id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LayoutInflater inflater;

        if (convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_data_layout, null);

            holder = new Holder();

            holder.textViewid = convertView.findViewById(R.id.textID);
            holder.textViewtitle = convertView.findViewById(R.id.textTitle);
            holder.textViewsuhu = convertView.findViewById(R.id.textSuhu);

            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.textViewid.setText(user_id.get(position));
        holder.textViewtitle.setText(user_title.get(position));
        holder.textViewsuhu.setText(user_suhu.get(position));
        return convertView;
    }
}
