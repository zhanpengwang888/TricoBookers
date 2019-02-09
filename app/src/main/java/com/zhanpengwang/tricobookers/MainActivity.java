package com.zhanpengwang.tricobookers;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class parseCSVIntoDB extends AsyncTask<Void, Void, Void> {

    private MainActivity activity;
    private DBhelper mDBHelper;

    public parseCSVIntoDB(MainActivity activity, DBhelper mDBhelper) {
        this.activity = activity;
        this.mDBHelper = mDBhelper;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String csvFile = "trico_books_info.csv";
        InputStream inputStream = null;
        try {
            AssetManager assetManager = activity.getAssets();
            inputStream = assetManager.open(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                // TODO: Finish inserting all the data into the database.
                // columns[1] is the dictionary name, columns[2] is the dictionary download url
                //mDBHelper.insertNewRecord(columns[1], columns[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private DBhelper mDBhelper;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private boolean isDefault = true;
    private String searchOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a DBhelper
        mDBhelper = new DBhelper(getBaseContext());

        // locate the search view
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        // set the search options
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton searchOptionRadioButton = group.findViewById(checkedId);
                if (searchOptionRadioButton != null) {
                    switch (searchOptionRadioButton.getText().toString()) {
                        case "Book Title":
                            searchOption = DBhelper.getTableColumnBookName();
                            isDefault = true;
                            break;
                        case "Authors":
                            searchOption = DBhelper.getTableColumnAuthors();
                            isDefault = true;
                            break;
                        case "Department":
                            searchOption = DBhelper.getTableColumnDepartment();
                            isDefault = true;
                            break;
                        case "Course Number":
                            searchOption = DBhelper.getTableColumnCourse();
                            isDefault = true;
                            break;
                        case "ISBN":
                            searchOption = DBhelper.getTableColumnIsbn();
                            isDefault = true;
                            break;
                        case "Default":
                            searchOption = "";
                            isDefault = false;
                            break;
                    }
                }
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e("OnQueryTextChange", "======== "+s);
        return false;
    }
}
