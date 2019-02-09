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
    private Bitmap downloadedImagebitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_item);
        mDBHelper = new DBhelper(getBaseContext());
        Intent intent = getIntent();
        TextView bookName = findViewById(R.id.bookName);
        ImageView imageView = findViewById(R.id.thumbnail_image);
        final String queriedBook = intent.getStringExtra("query");
        final String queriedBookName = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnBookName());
        bookName.setText(queriedBookName);
        String queriedBookImgURL = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnImgUrl());
        Log.e("image url", queriedBookImgURL + " =====url");
        try {
            downloadedImagebitmap = new ImageLoader().execute(queriedBookImgURL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(downloadedImagebitmap);


        // sending over the ISBN
        final String queriedBookISBN = mDBHelper.getInformationByBookCouse(queriedBook, DBhelper.getTableColumnIsbn());
        Button buyButton = findViewById(R.id.Buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), BuyListActivity.class);
                intent1.putExtra("ISBN", queriedBookISBN);
                Log.e("Sending over ISBN", queriedBook+ " =====");
                startActivity(intent1);
            }
        });

        // sending over the book title and bitmap to sellingActivity
        Button sellButtion = findViewById(R.id.Sell);
        sellButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getBaseContext(), SellingActivity.class);
                intent2.putExtra("ImageBitmap", downloadedImagebitmap);
                intent2.putExtra("book title", queriedBookName);
                startActivity(intent2);
            }
        });
    }
}
