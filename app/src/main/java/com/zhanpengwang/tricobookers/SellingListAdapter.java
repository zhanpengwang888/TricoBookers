package com.zhanpengwang.tricobookers;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class SellingListAdapter extends BaseAdapter {
    private Context mContext;
    private SellingList sellingList;
    private DBhelper mDBHelper;

    SellingListAdapter(Context context, SellingList searchResults, DBhelper dBhelper) {
        this.mContext = context;
        this.sellingList = searchResults;
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
        this.sellingList.registerDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return sellingList.size();
    }

    @Override
    public Object getItem(int position) {
        return sellingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addListItemToSearchResultList(List<SearchResult> srl) {
        sellingList.addAll(srl);
    }

    public void setSellingList(List<SearchResult> srl) {
        sellingList.setSearchResultList(srl);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchResult searchResult = sellingList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_result_item, parent, false);
        }
        return convertView;
    }

}