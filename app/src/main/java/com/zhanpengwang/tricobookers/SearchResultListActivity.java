package com.zhanpengwang.tricobookers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class SearchResultListActivity extends AppCompatActivity {

    private DBhelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_item);
        mDBHelper = new DBhelper(getBaseContext());
        Intent intent = getIntent();
        TextView bookName = findViewById(R.id.bookName);
        ImageView imageView = findViewById(R.id.thumbnail_image);
        String queriedBook = intent.getStringExtra("query");
//        String queriedBookName = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnBookName()).toString();
//        bookName.setText(queriedBookName);
//        String queriedBookImgURL = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnImgUrl()).toString();
//        Bitmap downloadedImagebitmap = null;
//        try {
//            downloadedImagebitmap = new ImageLoader().execute(queriedBookImgURL).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        imageView.setImageBitmap(downloadedImagebitmap);
    }
}
