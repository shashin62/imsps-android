package com.proschoolonline.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.proschoolonline.components.AppTextView;
import com.proschoolonline.mob.R;
import com.proschoolonline.model.CategoriesData;

import java.util.HashMap;
import java.util.List;

public class ExpandableDrawerAdapter extends BaseExpandableListAdapter {

    public final Context _context;
    public List<CategoriesData> _alkitab;
    public HashMap<Integer, List<CategoriesData>> _data_alkitab;

    public ExpandableDrawerAdapter(Context context, List<CategoriesData> alkitab, HashMap<Integer, List<CategoriesData>> data_alkitab){
        this._context = context;
        this._alkitab = alkitab;
        this._data_alkitab = data_alkitab;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._data_alkitab.get(this._alkitab.get(groupPosition).getId()).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText;
        //if (getChild(groupPosition, childPosition) instanceof CategoriesData){
            childText = ((CategoriesData)getChild(groupPosition, childPosition)).getName();
      /*  }else {
            childText = (String)getChild(groupPosition, childPosition);
        }*/

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_data, null);
        }
        AppTextView a = (AppTextView)convertView.findViewById(R.id.lblListItem);
        a.setText(Html.fromHtml(childText));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._data_alkitab.get(this._alkitab.get(groupPosition).getId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._alkitab.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._alkitab.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = ((CategoriesData)getGroup(groupPosition)).getName();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_group, null);
        }
       /* if(groupPosition==0){
            ((ImageView)convertView.findViewById(R.id.image2)).setImageResource(R.mipmap.ic_launcher);
            ((TextView)convertView.findViewById(R.id.text2)).setText("Description below group 1");
        }else if(groupPosition==1){
            ((ImageView)convertView.findViewById(R.id.image2)).setImageResource(R.mipmap.ic_launcher);
            ((TextView)convertView.findViewById(R.id.text2)).setText("Description below group 2");
        }else if(groupPosition==2){
            ((ImageView)convertView.findViewById(R.id.image2)).setImageResource(R.mipmap.ic_launcher);
            ((TextView)convertView.findViewById(R.id.text2)).setText("Description below group 3");
        }else if(groupPosition==3){
            ((ImageView)convertView.findViewById(R.id.image2)).setImageResource(R.mipmap.ic_launcher);
            ((TextView)convertView.findViewById(R.id.text2)).setText("Description below group 4");
        }*/
        AppTextView b = (AppTextView)convertView.findViewById(R.id.text1);
        ImageView imvArrow = (ImageView)convertView.findViewById(R.id.imvArrow);
        b.setText(Html.fromHtml(headerTitle));
        if (getChildrenCount(groupPosition) > 0){
            imvArrow.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imvArrow.setImageResource(R.drawable.down_arrow);
            } else {
                imvArrow.setImageResource(R.drawable.right_arrow);
            }
        }else{
            imvArrow.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

