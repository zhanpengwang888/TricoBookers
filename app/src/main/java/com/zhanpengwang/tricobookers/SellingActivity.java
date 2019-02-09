package com.zhanpengwang.tricobookers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SellingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_activity);
        Intent intent = getIntent();
        Bitmap imageBitmap = (Bitmap) intent.getParcelableExtra("ImageBitmap");
        String bookTitle = intent.getStringExtra("book title");
        TextView bookTitleTextView = findViewById(R.id.bookTitle);
        ImageView imageView = findViewById(R.id.bookImg);
        bookTitleTextView.setText(bookTitle);
        imageView.setImageBitmap(imageBitmap);

    }
}
