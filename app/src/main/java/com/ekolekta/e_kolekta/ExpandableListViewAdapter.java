package com.ekolekta.e_kolekta;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.e_kalat.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String>howList;
    private HashMap<String, List<String>> subList;

    public ExpandableListViewAdapter(Context context, List<String> howList, HashMap<String, List<String>> subList){
        this.context = context;
        this.howList = howList;
        this.subList = subList;
    }

    @Override
    public int getGroupCount() {
        return this.howList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.subList.get(this.howList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.howList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.subList.get(this.howList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String howTitle = (String) getGroup(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.how_list1, null);
        }
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/Bungee-Regular.ttf");
        TextView howtext = view.findViewById(R.id.how_1);
        howtext.setText(howTitle);
        howtext.setTypeface(type);
        howtext.setTextSize(20);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String subTitle = (String) getChild(i, i1);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sub_list1, null);
        }
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/Antic-Regular.ttf");
        TextView subtext = view.findViewById(R.id.sub_list_text);
        subtext.setText(subTitle);
        subtext.setTypeface(tf);
        subtext.setTextSize(17);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
