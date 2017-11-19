package com.proschoolonline.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.ListView;

import com.proschoolonline.adapter.ExpandableDrawerAdapter;
import com.proschoolonline.adapter.LoginAdapter;
import com.proschoolonline.adapter.NewsListAdapter;
import com.proschoolonline.application.DataFetchApplication;
import com.proschoolonline.application.SharedInstance;
import com.proschoolonline.application.SharedPreference;
import com.proschoolonline.components.AppTextView;
import com.proschoolonline.components.NestedExpandableListView;
import com.proschoolonline.mob.R;
import com.proschoolonline.model.CategoriesData;
import com.proschoolonline.model.NewsData;
import com.proschoolonline.utilities.AlarmReceiver_;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.proschoolonline.services.Constants.CHAT_KEY;

@EActivity(R.layout.activity_main)
public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener, ExpandableListView.OnChildClickListener{

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.left_drawer)
    View left_drawer;

    @ViewById(R.id.expandableListView)
    NestedExpandableListView expandableListView;

    @ViewById(R.id.textHome)
    AppTextView tvHome;

    @ViewById(R.id.textBookmark)
    AppTextView tvBookmark;

    @ViewById(R.id.tvSorry)
    AppTextView tvSorry;

    @ViewById(R.id.lvNews)
    ListView lvNews;

    @App
    DataFetchApplication application;

    @Bean
    LoginAdapter loginAdapter;

    public ActionBarDrawerToggle actionBarDrawerToggle;

    private int lastExpandPosition = -1;

    ExpandableDrawerAdapter expandableDrawerAdapter;

    private Context context = this;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    NewsListAdapter newsListAdapterAll;
    private ProgressDialog progressDialog;
    public static final String FromMainPage = "main";
    public static final String FromBookmarkPage = "bookmark";
    private boolean doubleBackToExitPressedOnce;

    @UiThread
    void showProgressDialog() {
        // TODO Auto-generated method stub
        progressDialog = ProgressDialog.show(context,"", "Please Wait...");
        progressDialog.show();
    }

    @AfterViews
    void afterViews() {
        toolbar.setTitleTextColor(Color.WHITE);
        // comment this line for menu navigation drawer enable
       // toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.menuicon));

        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.latest_posts, R.string.latest_posts){
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle(title);

                Log.v("Drawer","Closed");
                //supportInvalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
               // getSupportActionBar().setTitle("Open Drawer");
                Log.v("Drawer","Open");
                onQueryTextChange("");
                supportInvalidateOptionsMenu();
            }
        };

        // comment this line for menu navigation drawer enable
       // actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();

        /*if (SharedPreference.getInstance().loadFavorites(this.context) != null){
            SharedInstance.getInstance().setNewsDataList(SharedPreference.getInstance().loadFavorites(this.context));
        }*/

        if (SharedPreference.getInstance().readFromFile(this.context) != null){
            SharedInstance.getInstance().setNewsDataList(SharedPreference.getInstance().readFromFile(this.context));
            newsListAdapterAll = new NewsListAdapter(context,SharedInstance.getInstance().getNewsDataList(),FromMainPage);
            lvNews.setAdapter(newsListAdapterAll);

            if (SharedInstance.getInstance().getCategoriesDataList() == null){
                SharedInstance.getInstance().setCategoriesDataList(SharedPreference.getInstance().readFromFileCategory(context));
            }

            loadData();
        }else{
            showProgressDialog();
        }

        ZopimChat.init(CHAT_KEY);
        VisitorInfo visitorData = new VisitorInfo.Builder()
                /*.name("Visitor name")
                .email("visitor@example.com")
                .phoneNumber("0123456789")*/
                .build();

        ZopimChat.setVisitorInfo(visitorData);
    }

    public void loadData(){
        List<CategoriesData> listParent = new ArrayList<>();
        List<CategoriesData> listChild = new ArrayList<>();
        HashMap<Integer, List<CategoriesData>>  mapParentToChild = new HashMap<>();

        for (CategoriesData categoriesData : SharedInstance.getInstance().getCategoriesDataList()) {
            if (categoriesData.getParent().intValue() == 0)
                listParent.add(categoriesData);
            else
                listChild.add(categoriesData);
        }

      /*  for (CategoriesData categoriesData : SharedInstance.getInstance().getCategoriesDataList()){
            catList.add(categoriesData.getName());
        }*/

       /* Set<Object> hs = new TreeSet<>();
        hs.addAll(catList);
        catList.clear();
        catList.addAll(hs);*/

        for (int i = 0; i < listParent.size(); i++) {
            List<CategoriesData> tempChild = new ArrayList<>();
            for (int j = 0; j < listChild.size(); j++) {

                if (listChild.get(j).getParent().intValue() == listParent.get(i).getId().intValue()) {
                    tempChild.add(listChild.get(j));
                }
            }
            mapParentToChild.put(listParent.get(i).getId(), tempChild);

        }

        expandableDrawerAdapter = new ExpandableDrawerAdapter(this, listParent, mapParentToChild);
        expandableListView.setAdapter(expandableDrawerAdapter);
        expandableListView.setTextFilterEnabled(true);

        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnGroupExpandListener(this);
        expandableListView.setOnGroupCollapseListener(this);
        expandableListView.setOnChildClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Inside","onResume");
        if (!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this);
        Intent alarm = new Intent(this.context, AlarmReceiver_.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        Log.v("Inside","Hello activity"+alarmRunning);

        if(!alarmRunning) {
            pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
        }else{
            pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_NO_CREATE);
        }

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,1200,1200, pendingIntent);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (newsListAdapterAll != null){
            newsListAdapterAll.notifyDataSetChanged();
        }
        Log.v("Inside","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("Inside","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("Inside","onStop");
        //SharedPreference.getInstance().storeFavorites(context,SharedInstance.getInstance().getNewsDataList());
        SharedPreference.getInstance().writeToFile(context,SharedInstance.getInstance().getNewsDataList());

        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Inside","onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(List<NewsData> newsData){
        Log.v("Inside_Activity","onEvent--"+newsData);
        if (newsData != null && newsData.size() > 0){
            SharedInstance.getInstance().setNewsDataList(newsData);
        }

        if (isNetworkAvailable() && SharedInstance.getInstance().getCategoriesDataList() == null){
            Log.v("Inside_category","onEvent");
            processCategoryData(newsData);
        }else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            updateNewsDataList(newsData);
        }
    }

    public void updateNewsDataList(List<NewsData> newsData){
        if (SharedInstance.getInstance().getNewsDataList() != null && SharedInstance.getInstance().getNewsDataList().size() > 0){
            for (NewsData newsDataOld : SharedInstance.getInstance().getNewsDataList()){
                for (NewsData newsDataNew : newsData){
                    if (newsDataNew.getId().intValue() == newsDataOld.getId().intValue()){
                        newsDataOld.setBookmarked(newsDataNew.isBookmarked());
                        Log.v("DataCame","onEvent--"+newsDataOld.getTitle().getRendered()+"--"+newsDataOld.isBookmarked());
                    }
                }
            }

            if (newsListAdapterAll == null){
                Log.v("InsideCASE","onEvent--IF");
                newsListAdapterAll = new NewsListAdapter(context,SharedInstance.getInstance().getNewsDataList(),FromMainPage);
                lvNews.setAdapter(newsListAdapterAll);
                //lvNews.setTextFilterEnabled(true);
            }else {
                Log.v("InsideCASE","onEvent--ELSE");
                newsListAdapterAll.notifyDataSetChanged();
            }
        }
    }

    @Background
    void processCategoryData(List<NewsData> newsData){
        Log.v("InsideCategory","processCategoryData--");
        List<CategoriesData> weatherData = loginAdapter.getCategoriesData();
        publishResultCategory(weatherData,newsData);
    }

    @UiThread
    void publishResultCategory(List<CategoriesData> newsDatas, List<NewsData> newsData){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            checkFirstRun();
        }
        if (newsDatas != null){
            SharedInstance.getInstance().setCategoriesDataList(newsDatas);
            SharedPreference.getInstance().writeToFileCategory(context,newsDatas);
            updateNewsDataList(newsData);
            loadData();
        }

    }

    void checkFirstRun() {
        SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        if (settings.getBoolean("isFirstRun", true)) {
            displayDialog();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }
    }

    @UiThread
    void displayDialog() {
        Intent intent = new Intent(this, FormAlertActivity_.class);
        startActivity(intent);
    }

    @UiThread
    void showProgressDialogFilter(String catId) {
        lvNews.setAdapter(null);
        progressDialog = ProgressDialog.show(context,"", "Please Wait...");
        progressDialog.show();
        processNewsDataFilter(catId);
    }

    @Background
    void processNewsDataFilter(String catId){
        Log.v("InsideCategory","processCategoryData--");
        List<NewsData> newsDatas = loginAdapter.getNewsDataFilter(catId);
        Iterator<NewsData> itr = newsDatas.iterator();
        while (itr.hasNext()) {
            NewsData newsData = itr.next();
            if (!newsData.getAcf().toString().contains("visible_in_app=Yes")){
                itr.remove();
            }else{
                if (newsData.getAcf().toString().contains("counter=")){
                    Matcher m = Pattern.compile(
                            Pattern.quote("counter=")
                                    + "(.*?)"
                                    + Pattern.quote("}")
                    ).matcher(newsData.getAcf().toString());
                    while(m.find()){
                        String match = m.group(1);
                        newsData.setCounter(match);
                    }
                }
            }

        }
        publishResultCategoryFilter(newsDatas);
    }

    @UiThread
    void publishResultCategoryFilter(List<NewsData> newsData){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (newsData != null){
            if (newsData.size() > 0) {
                tvSorry.setVisibility(View.GONE);
                newsListAdapterAll = new NewsListAdapter(context, newsData, FromMainPage);
                lvNews.setAdapter(newsListAdapterAll);
            }else {
                tvSorry.setVisibility(View.VISIBLE);
            }
        }else{
            tvSorry.setVisibility(View.VISIBLE);
        }

    }

    @ItemClick(R.id.lvNews)
    void handlesListSelection(int position) {

        Intent intent = new Intent(context,DetailsActivity_.class);
        if (newsListAdapterAll != null && newsListAdapterAll.getItem(position) != null){
            if (SharedInstance.getInstance().getNewsDataList() != null && SharedInstance.getInstance().getNewsDataList().size() > 0){
                for (NewsData newsDataOld : SharedInstance.getInstance().getNewsDataList()){
                    if (newsListAdapterAll.getItem(position).getId().intValue() == newsDataOld.getId().intValue()){
                        newsListAdapterAll.getItem(position).setBookmarked(newsDataOld.isBookmarked());
                    }
                }
            }
            Log.v("ItemDetail",position+"---"+newsListAdapterAll.getItem(position).getTitle().getRendered()+"---"+newsListAdapterAll.getItem(position).isBookmarked());
            intent.putExtra("detail_data",newsListAdapterAll.getItem(position));
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchable_activity, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(HomeActivity.this);

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(left_drawer));
                drawerLayout.closeDrawer(left_drawer);
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v("Query","Submit");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v("Query","Change");
        if (newsListAdapterAll != null){
            Filter filter = newsListAdapterAll.getFilter();
            filter.filter(newText);
        }

       /* if (TextUtils.isEmpty(newText)) {
            lvNews.clearTextFilter();
        } else {
            lvNews.setFilterText(newText);
        }*/
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        int childrenCount = expandableDrawerAdapter.getChildrenCount(groupPosition);
        if (childrenCount < 1){
            Object childItem = expandableDrawerAdapter.getGroup(groupPosition);

            setTitle(getString(R.string.latest_posts));
            if (isNetworkAvailable()) {
                showProgressDialogFilter(((CategoriesData) childItem).getId().toString());
            } else {
                List<NewsData> newsFilterList = new ArrayList<>();
                for (NewsData newsData : SharedInstance.getInstance().getNewsDataList()) {
                    for (Integer catInteger : newsData.getCategories()) {
                        if (((CategoriesData) childItem).getId() == catInteger.intValue()) {
                            newsFilterList.add(newsData);
                        }
                    }
                }
                if (newsFilterList.size() > 0) {
                    tvSorry.setVisibility(View.GONE);
                    newsListAdapterAll = new NewsListAdapter(context, newsFilterList, FromMainPage);
                    lvNews.setAdapter(newsListAdapterAll);
                }else {
                    tvSorry.setVisibility(View.VISIBLE);
                }
            }

            drawerLayout.closeDrawer(left_drawer);
            return true;
        }
        return false;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastExpandPosition != -1 && groupPosition != lastExpandPosition) {
            expandableListView.collapseGroup(lastExpandPosition);
        }
        lastExpandPosition = groupPosition;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {

    }

    @Click(R.id.textHome)
    void onClickHome(View v){
        setTitle(getString(R.string.latest_posts));
        if (SharedInstance.getInstance().getNewsDataList() != null)
        if (SharedInstance.getInstance().getNewsDataList().size() > 0) {
            tvSorry.setVisibility(View.GONE);
        }else {
            tvSorry.setVisibility(View.VISIBLE);
        }
        newsListAdapterAll = new NewsListAdapter(context,SharedInstance.getInstance().getNewsDataList(),FromMainPage);
        lvNews.setAdapter(newsListAdapterAll);
        drawerLayout.closeDrawer(left_drawer);
    }

    @Click(R.id.textBookmark)
    void onClickBook(View v){
        List<NewsData> newsDataListBook = new ArrayList<>();
        if (isNetworkAvailable()){

            for (NewsData newsData : SharedInstance.getInstance().getNewsDataList()){
                if (newsData.isBookmarked()){
                    newsDataListBook.add(newsData);
                }
            }

        }else {
            for (NewsData newsData : SharedPreference.getInstance().readFromFile(this.context)){
                if (newsData.isBookmarked()){
                    newsDataListBook.add(newsData);
                }
            }

        }

        SharedInstance.getInstance().setBookmarkedList(newsDataListBook);
        setTitle(getString(R.string.bookmark));
        if (SharedInstance.getInstance().getBookmarkedList().size() > 0) {
            tvSorry.setVisibility(View.GONE);
        }else {
            tvSorry.setVisibility(View.VISIBLE);
        }
        newsListAdapterAll = new NewsListAdapter(context,SharedInstance.getInstance().getBookmarkedList(),FromBookmarkPage);
        lvNews.setAdapter(newsListAdapterAll);
        drawerLayout.closeDrawer(left_drawer);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            //int grup_pos = (int) expandableDrawerAdapter.getGroupId(groupPosition);
            //int child_pos = (int) expandableDrawerAdapter.getChildId(groupPosition, childPosition);
            Object childItem = expandableDrawerAdapter.getChild(groupPosition, childPosition);

            setTitle(getString(R.string.latest_posts));

            if (isNetworkAvailable()) {
                showProgressDialogFilter(((CategoriesData) childItem).getId().toString());
            } else {
                List<NewsData> newsFilterList = new ArrayList<>();
                for (NewsData newsData : SharedInstance.getInstance().getNewsDataList()) {
                    for (Integer catInteger : newsData.getCategories()) {
                        if (((CategoriesData) childItem).getId() == catInteger.intValue()) {
                            newsFilterList.add(newsData);
                        }
                    }
                }
                if (newsFilterList.size() > 0) {
                    tvSorry.setVisibility(View.GONE);
                    newsListAdapterAll = new NewsListAdapter(context, newsFilterList, FromMainPage);
                    lvNews.setAdapter(newsListAdapterAll);
                }else {
                    tvSorry.setVisibility(View.VISIBLE);
                }
            }

            expandableListView.setItemChecked(childPosition, true);
            drawerLayout.closeDrawer(left_drawer);

            return false;
        }


    @SuppressWarnings("unused")
    private void displayViewExpandableListview(int position){
        android.app.Fragment frag = null;
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            default:
                break;
        }

        if(frag != null){
            android.app.FragmentManager frag_mgr = getFragmentManager();
            frag_mgr.beginTransaction().replace(R.id.content_frame, frag).addToBackStack(null).commit();
            expandableListView.setItemChecked(position, true);
            drawerLayout.closeDrawer(left_drawer);
            lvNews.setVisibility(View.GONE);
        } else {
            Log.d("Error 1", "Error creating fragment");
        }
    }

    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /*@Subscribe
    public void onEvent(List<CategoriesData> newsData){
        Log.v("Inside ACTIVITY","onEvent--"+newsData.get(0).getName());
        ArrayList<String> arrayList  = new ArrayList<>();
        for (CategoriesData list : newsData){
            arrayList.add(list.getName());
        }
        lvNews.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arrayList));
    }*/

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Click(R.id.fab)
    void onClickFab(){
        startActivity(new Intent(HomeActivity.this, ZopimChatActivity.class));
    }
}
