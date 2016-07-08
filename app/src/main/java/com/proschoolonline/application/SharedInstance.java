package com.proschoolonline.application;

import com.proschoolonline.model.CategoriesData;
import com.proschoolonline.model.NewsData;

import java.util.ArrayList;
import java.util.List;

public class SharedInstance {
    private static SharedInstance instance = null;
    private SharedInstance() {
        // Exists only to defeat instantiation.
    }
    public static SharedInstance getInstance() {
        if(instance == null) {
            instance = new SharedInstance();
        }
        return instance;
    }

    private List<NewsData> newsDataList;
    private List<CategoriesData> categoriesDataList;
    private List<NewsData> bookmarkedList;

    public List<NewsData> getNewsDataList() {
        return newsDataList;
    }

    public void setNewsDataList(List<NewsData> newsDataList) {
        if (this.newsDataList == null){
            this.newsDataList = new ArrayList<>();
        }

        this.newsDataList = newsDataList;
    }

    public List<CategoriesData> getCategoriesDataList() {
        return categoriesDataList;
    }

    public void setCategoriesDataList(List<CategoriesData> categoriesDataList) {
        if (this.categoriesDataList == null){
            this.categoriesDataList = new ArrayList<>();
        }
        this.categoriesDataList = categoriesDataList;
    }

    public List<NewsData> getBookmarkedList() {
        return bookmarkedList;
    }

    public void setBookmarkedList(List<NewsData> bookmarkedList) {
        if (this.bookmarkedList == null){
            this.bookmarkedList = new ArrayList<>();
        }
        this.bookmarkedList = bookmarkedList;
    }
}
