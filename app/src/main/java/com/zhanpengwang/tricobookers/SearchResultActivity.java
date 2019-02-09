package com.zhanpengwang.tricobookers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class SearchResultActivity extends AppCompatActivity {

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
        String queriedBookName = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnBookName());
        bookName.setText(queriedBookName);
        String queriedBookImgURL = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnImgUrl());
        Log.e("image url", queriedBookImgURL + " =====url");
        Bitmap downloadedImagebitmap = null;
        try {
            downloadedImagebitmap = new ImageLoader().execute(queriedBookImgURL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(downloadedImagebitmap);

        Button buyButton = findViewById(R.id.Buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
