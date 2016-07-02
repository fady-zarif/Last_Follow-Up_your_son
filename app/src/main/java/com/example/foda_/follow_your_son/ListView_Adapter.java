package com.example.foda_.follow_your_son;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListView_Adapter extends BaseAdapter {
    ArrayList<String>item;
    Context myContext;

    ArrayList<Teachers_profiles> profiles;
    public ListView_Adapter(Context mycontext,ArrayList<String>item,ArrayList<Teachers_profiles> profiles)
    {
        this.myContext=mycontext;
        this.item=item;

this.profiles=profiles;
    }
    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        TextView text1,text2;
        LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
        View Row;
        Row= inflater.inflate(R.layout.list_view_layout,parent,false);
        img=(ImageView)Row.findViewById(R.id.image_);
     /* LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(300,300);
        params.setMargins(10,0,10,0);
        img.setLayoutParams(params);*/
        text1=(TextView)Row.findViewById(R.id.text_);
        text2=(TextView)Row.findViewById(R.id.Student_years);
        text1.setText(profiles.get(position).Tname);
        text2.setText("Years :"+profiles.get(position).Years);
        Picasso.with(myContext)
                .load(item.get(position)).placeholder(R.drawable.loading_teacher2)
                .into(img);

        return Row;
    }
}
