package com.zhanpengwang.tricobookers;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class SearchResultListAdapter extends BaseAdapter {
    private Context mContext;
    private SearchResultList searchResultList;
    private DBhelper mDBHelper;

    SearchResultListAdapter(Context context, SearchResultList searchResults, DBhelper dBhelper) {
        this.mContext = context;
        this.searchResultList = searchResults;
        this.mDBHelper = dBhelper;
        DataSetObserver observer = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        };
        this.searchResultList.registerDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return searchResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addListItemToSearchResultList(List<SearchResult> srl) {
        searchResultList.addAll(srl);
    }

    public void setSearchResultList(List<SearchResult> srl) {
        searchResultList.setSearchResultList(srl);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchResult searchResult = searchResultList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_result_item, parent, false);
        }
        return convertView;
    }

}