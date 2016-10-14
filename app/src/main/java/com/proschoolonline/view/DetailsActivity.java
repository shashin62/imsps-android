package com.proschoolonline.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.proschoolonline.application.DataFetchApplication;
import com.proschoolonline.application.SharedInstance;
import com.proschoolonline.application.SharedPreference;
import com.proschoolonline.components.AppTextView;
import com.proschoolonline.mob.R;
import com.proschoolonline.model.CategoriesData;
import com.proschoolonline.model.NewsData;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.DateFormatSymbols;
import java.util.List;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity /*implements Html.ImageGetter*/ {
    private final static String TAG = "DetailsActivity";
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Extra("detail_data")
    NewsData newsData;

    @ViewById(R.id.imvNewsDetail)
    ImageView imvNewsDetail;

    @ViewById(R.id.tvTitleDetail)
    AppTextView tvTitleDetail;

    /*@ViewById(R.id.tvDescDetail)
    AppTextView tvDescDetail;*/

    @ViewById(R.id.webview)
    WebView webview;

    @ViewById(R.id.tvNewsDate)
    AppTextView tvNewsDate;

    @ViewById(R.id.tvNewsCategory)
    AppTextView tvNewsCategory;

    @ViewById(R.id.tvPeopleRead)
    AppTextView tvPeopleRead;

    /*@ViewById(R.id.tvNewsAdmin)
    AppTextView tvNewsAdmin;

    @ViewById(R.id.tvNewsComment)
    AppTextView tvNewsComment;

    @ViewById(R.id.tvNewsSticky)
    AppTextView tvNewsSticky;*/

    @App
    DataFetchApplication application;

    private Context context = this;
    private boolean flag;

    @AfterViews
    void initViews(){
        Log.v("ItemDetail",newsData.getTitle().getRendered()+"---");

        toolbar.setTitleTextColor(Color.WHITE);
       // toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (SharedInstance.getInstance().getCategoriesDataList() == null){
            Log.v("Inside_category","Details");
            SharedInstance.getInstance().setCategoriesDataList(SharedPreference.getInstance().readFromFileCategory(context));
        }
        setDetailsData();

    }

    private void setDetailsData() {

        //tvDescDetail.setMovementMethod(LinkMovementMethod.getInstance());
        tvTitleDetail.setText(Html.fromHtml(newsData.getTitle().getRendered()));
        if (newsData.getCounter() != null){
            if (!newsData.getCounter().isEmpty()){
                tvPeopleRead.setVisibility(View.VISIBLE);
                tvPeopleRead.setText(newsData.getCounter()+" people read this article");
            }
        }else {
            tvPeopleRead.setVisibility(View.GONE);
        }
        //tvDescDetail.setText(Html.fromHtml(newsData.getContent().getRendered().replaceAll("<img.+?>", "")));

        //tvDescDetail.setText(Html.fromHtml(newsData.getContent().getRendered().replaceFirst("<img.+?>", ""), this, new HtmlTagHandler()));
        webview.setInitialScale(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        //webview.getSettings().setLoadWithOverviewMode(true);
       // webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        webview.loadDataWithBaseURL(null,newsData.getContent().getRendered().replaceFirst("<img.+?>", ""), "text/html", "UTF-8", null);

      //  webview.loadData(newsData.getContent().getRendered().replaceFirst("<img.+?>", ""), "text/html", "UTF-8");
        String[] tempDate = newsData.getDate().split("T");
        String[] format = tempDate[0].split("-");
        tvNewsDate.setText(getMonth(Integer.parseInt(format[1]) - 1) + " " + format[2].replaceFirst("^0+(?!$)", "") + ", " + format[0]);

        String categoryData = "";
        if (SharedInstance.getInstance().getCategoriesDataList() != null && SharedInstance.getInstance().getCategoriesDataList().size() > 0){
            for (Integer catInteger : newsData.getCategories()){
                CategoriesData tempData = null;
                for (CategoriesData data : SharedInstance.getInstance().getCategoriesDataList()){
                    if (catInteger.intValue() == data.getId().intValue()){
                        tempData = data;
                        break;
                    }else {
                        tempData = null;
                    }
                }
                if (tempData != null){
                    if (categoryData.isEmpty()){
                        categoryData = tempData.getName();
                    }else{
                        categoryData = categoryData+", "+tempData.getName();
                    }
                }else{
                    if (categoryData.isEmpty()){
                        categoryData = "Uncategorized";
                    }else{
                        categoryData = categoryData+", Uncategorized";
                    }
                }
            }
        }

        Log.v("CategoryList",categoryData+"---");
        tvNewsCategory.setText(categoryData);
        Document doc = Jsoup.parse(newsData.getContent().getRendered());
        String imageUrl = "";
        if (doc != null && doc.select("img") != null){
            Element image = doc.select("img").first();
            if (image != null &&  image.absUrl("src") != null){
                imageUrl = image.absUrl("src");
            }
        }
        if (!imageUrl.isEmpty()) {
            Picasso.with(context)
                    .load(imageUrl)
                    .tag(context)
                    .error(R.drawable.menuimgontainer)
                    //.resize(800,325)
                    .into(imvNewsDetail);
        }
    }

    public static String getMonth(int month) {

        return new DateFormatSymbols().getMonths()[month];

    }


    private void setBookMarkIcon(MenuItem item) {

        for(NewsData data : SharedInstance.getInstance().getNewsDataList()){
            if (data.getId().intValue() == newsData.getId().intValue()){
                Log.v("ItemDetail","-item_bookmarkInside--"+data.isBookmarked());

                if (data.isBookmarked()){
                    /*data.setBookmarked(false);
                    item.setIcon(getResources().getDrawable(R.drawable.star_icon));

                    if (SharedInstance.getInstance().getBookmarkedList() != null){
                        Iterator<NewsData> it = SharedInstance.getInstance().getBookmarkedList().iterator();
                        while (it.hasNext()) {
                            if (it.next().getId().intValue() == data.getId().intValue()) {
                                it.remove();
                                break;
                                // it's unique, you could `break;` here
                            }
                        }
                    }*/
                    Toast.makeText(getApplicationContext(), "Already in Bookmark list", Toast.LENGTH_SHORT).show();
                }else{
                    data.setBookmarked(true);
                    item.setIcon(getResources().getDrawable(R.drawable.star_filled));
                    Toast.makeText(getApplicationContext(), "Successfully Bookmarked", Toast.LENGTH_SHORT).show();
                }

                Log.v("ItemDetail","-item_bookmarkAfter--"+data.isBookmarked());
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        Log.v("ItemDetail","-item_bookmarkOptions--"+newsData.isBookmarked());
        if (newsData.isBookmarked()){
            menu.findItem(R.id.item_bookmark).setIcon(getResources().getDrawable(R.drawable.star_filled));
        }else{
            menu.findItem(R.id.item_bookmark).setIcon(getResources().getDrawable(R.drawable.star_icon));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.item_bookmark:
                setBookMarkIcon(item);
                break;

            case R.id.item_share:

                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,newsData.getTitle().getRendered());
                i.putExtra(android.content.Intent.EXTRA_TEXT, newsData.getLink()+" \n\nvia "+getString(R.string.app_name)+" App");
                startActivity(Intent.createChooser(i,"Insert share chooser title here"));

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("Inside","onStopDetail");
        //SharedPreference.getInstance().storeFavorites(context,SharedInstance.getInstance().getNewsDataList());
        SharedPreference.getInstance().writeToFile(context,SharedInstance.getInstance().getNewsDataList());
    }

    @Click(R.id.imvFacebook)
    void fbClick(){
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, newsData.getTitle().getRendered());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, newsData.getLink()+" \n\nvia "+getString(R.string.app_name)+" App");

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList)
        {
            if ((app.activityInfo.name).startsWith("com.facebook"))
            {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                context.startActivity(shareIntent);
                break;
            }
        }
    }

    @Click(R.id.imvTwitter)
    void twitterClick(){
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, newsData.getTitle().getRendered());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, newsData.getLink()+" \n\nvia "+getString(R.string.app_name)+" App");

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList)
        {
            if ((app.activityInfo.name).startsWith("com.twitter.android"))
            {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                context.startActivity(shareIntent);
                break;
            }
        }
    }

    @Click(R.id.imvWhatsapp)
    void whatsAppClick(){
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, newsData.getTitle().getRendered());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, newsData.getLink()+" \n\nvia "+getString(R.string.app_name)+" App");

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList) {
            if ((app.activityInfo.name).startsWith("com.whatsapp")) {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(
                        activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                context.startActivity(shareIntent);
                break;
            }
        }
    }


   /* @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.mipmap.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tvDescDetail.getText();
                tvDescDetail.setText(t);
            }
        }
    }*/
}
