package com.zhanpengwang.tricobookers;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SellingList extends AbstractList<Sellers> {
    private final DataSetObservable dataSetObservable;
    private List<Sellers> sellersList;

    protected SellingList() {
        this.dataSetObservable = new DataSetObservable();
        sellersList = new ArrayList<>();
    }

    public List<Sellers> getSellersList() {
        return sellersList;
    }

    public void setSellersList(List<Sellers> srl) {
        sellersList = srl;
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
    public boolean add(Sellers sellers) {
        boolean isAddSuccessful = sellersList.add(sellers);
        notifyChanged();
        return isAddSuccessful;
    }

    @Override
    public boolean addAll(Collection<? extends Sellers> c) {
        boolean isAddSuccessful = sellersList.addAll(c);
        notifyChanged();
        return isAddSuccessful;
    }

    @Override
    public int indexOf(Object o) {
        return sellersList.indexOf(o);
    }

    @Override
    public void clear() {
        sellersList.clear();
        notifyChanged();
    }

    @Override
    public Sellers get(int index) {
        if (!sellersList.isEmpty()) {
            return sellersList.get(index);
        }
        return null;
    }

    @Override
    public int size() {
        return sellersList.size();
    }
}