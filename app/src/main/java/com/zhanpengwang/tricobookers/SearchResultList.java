package com.zhanpengwang.tricobookers;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchResultList extends AbstractList<SearchResult> {
    private final DataSetObservable dataSetObservable;
    private List<SearchResult> searchResultList;

    protected SearchResultList() {
        this.dataSetObservable = new DataSetObservable();
        searchResultList = new ArrayList<>();
    }

    public List<SearchResult> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(List<SearchResult> srl) {
        searchResultList = srl;
    }

    protected void notifyChanged() {
        this.dataSetObservable.notifyChanged();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.dataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.dataSetObservable.unregisterObserver(observer);
    }

    @Override
    public boolean add(SearchResult searchResult) {
        boolean isAddSuccessful = searchResultList.add(searchResult);
        notifyChanged();
        return isAddSuccessful;
    }

    @Override
    public boolean addAll(Collection<? extends SearchResult> c) {
        boolean isAddSuccessful = searchResultList.addAll(c);
        notifyChanged();
        return isAddSuccessful;
    }

    @Override
    public int indexOf(Object o) {
        return searchResultList.indexOf(o);
    }

    @Override
    public void clear() {
        searchResultList.clear();
        notifyChanged();
    }

    @Override
    public SearchResult get(int index) {
        if (!searchResultList.isEmpty()) {
            return searchResultList.get(index);
        }
        return null;
    }

    @Override
    public int size() {
        return searchResultList.size();
    }
}