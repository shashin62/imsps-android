package com.proschoolonline.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.proschoolonline.application.SharedInstance;
import com.proschoolonline.components.AppTextView;
import com.proschoolonline.components.ExpandableTextView;
import com.proschoolonline.mob.R;
import com.proschoolonline.model.NewsData;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsListAdapter extends BaseAdapter implements Filterable {

    public List<NewsData> myList;
    LayoutInflater inflater;
    Context context;
    public List<NewsData> orig;
    String fromPage;

    public NewsListAdapter(Context context, List<NewsData> myList, String fromPage) {
        super();
        this.myList = myList;
        this.context = context;
        this.fromPage = fromPage;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<NewsData> results = new ArrayList<>();
                if (orig == null)
                    orig = myList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final NewsData g : orig) {
                            if (g.getTitle().getRendered().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()) || g.getExcerpt().getRendered().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                myList = (ArrayList<NewsData>) results.values;
                notifyDataSetChanged();
            }

        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public NewsData getItem(int position) {
        return (NewsData)myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_child, parent, false);

            holder = new MyViewHolder();
            holder.tvTitle = (AppTextView) convertView.findViewById(R.id.tvNewsTitle);
            holder.tvDesc = (ExpandableTextView) convertView.findViewById(R.id.tvNewsDesc);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.imvNews);
            holder.imvDelete = (ImageView) convertView.findViewById(R.id.imvDelete);
            holder.tvPeopleRead = (AppTextView) convertView.findViewById(R.id.tvPeopleRead);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder)convertView.getTag();
        }

        final NewsData currentListData = getItem(position);

        holder.tvTitle.setText(Html.fromHtml(currentListData.getTitle().getRendered()));
        holder.tvDesc.setText(Html.fromHtml(currentListData.getExcerpt().getRendered()));
        if (currentListData.getCounter() != null){
            holder.tvPeopleRead.setVisibility(View.VISIBLE);
            if (currentListData.getCounter().contains(",")){
                holder.tvPeopleRead.setText(currentListData.getCounter().substring(0,currentListData.getCounter().indexOf(","))+" people read this article");
            }else
                holder.tvPeopleRead.setText(currentListData.getCounter()+" people read this article");
        }else {
            holder.tvPeopleRead.setVisibility(View.GONE);
        }
        Document doc = Jsoup.parse(currentListData.getContent().getRendered());
        String imageUrl = "";
        if (doc != null && doc.select("img") != null){
            Element image = doc.select("img").first();
            if (image != null &&  image.absUrl("src") != null){
                imageUrl = image.absUrl("src");
            }
        }

        Log.v("Image Url After",imageUrl+"---");
        if (!imageUrl.isEmpty()){
            Picasso.with(context)
                    .load(imageUrl)
                    .tag(context)
                    .error(R.drawable.menuimgontainer)
                    //.resize(500,325)
                    .into(holder.ivIcon);
        }


        if ("main".equalsIgnoreCase(fromPage)){
            holder.imvDelete.setVisibility(View.GONE);
        }
        else if ("bookmark".equalsIgnoreCase(fromPage)){
            holder.imvDelete.setVisibility(View.VISIBLE);
            holder.imvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //currentListData.setBookmarked(false);
                    openAlertDialog(currentListData);
                }
            });
        }

        return convertView;
    }

    public void openAlertDialog(final NewsData currentListData) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        //alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(R.string.delete_text);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteNewsItem(currentListData);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        if (alertDialog != null && !alertDialog.isShowing()){
            alertDialog.show();
        }

    }

    public void deleteNewsItem(NewsData currentListData) {
        if (SharedInstance.getInstance().getBookmarkedList() != null){
            Iterator<NewsData> it = SharedInstance.getInstance().getBookmarkedList().iterator();
            while (it.hasNext()) {
                if (it.next().getId().intValue() == currentListData.getId().intValue()) {
                    it.remove();
                    notifyDataSetChanged();
                    break;
                    // it's unique, you could `break;` here
                }
            }
        }
        for (NewsData newsData : SharedInstance.getInstance().getNewsDataList()){
            if (newsData.getId().intValue() == currentListData.getId().intValue()){
                newsData.setBookmarked(false);
                break;
            }
        }
    }

    private class MyViewHolder {
        AppTextView tvTitle,tvPeopleRead;
        ExpandableTextView tvDesc;
        ImageView ivIcon,imvDelete;
    }


}
