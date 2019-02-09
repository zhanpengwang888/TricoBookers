package com.zhanpengwang.tricobookers;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class parseCSVIntoDB extends AsyncTask<Void, Void, Void> {

    private MainActivity activity;
    private DBhelper mDBHelper;

    public parseCSVIntoDB(MainActivity activity, DBhelper mDBhelper) {
        this.activity = activity;
        this.mDBHelper = mDBhelper;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String csvFile = "trico_books_info.tsv";
        InputStream inputStream = null;
        try {
            AssetManager assetManager = activity.getAssets();
            inputStream = assetManager.open(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] columns = line.split("\t");
                    // columns[0] is the book name, columns[1] is authors, columns[2] is the department
                    // columns[3] is ISBN, columns[4] is course number, columns[5] is required,
                    // columns[6] is used_price, columns[7] is the new price, columns[8] is semester
                    // columns[9] is image url
                    mDBHelper.insertNewRecord(columns[0], columns[1], columns[2], columns[3], columns[4],
                            columns[5], columns[6], columns[7], columns[8], columns[9]);
                    //Log.e("error in parsing data", columns[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private DBhelper mDBhelper;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private boolean isDefault = true;
    private ArrayAdapter<String> arrayAdapter;
    private String searchOption = DBhelper.getTableColumnBookName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a DBhelper
        mDBhelper = new DBhelper(getBaseContext());

        // locate the search view
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);

        // Get SearchView autocomplete object
        searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);
        arrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, new String[]{});
        searchAutoComplete.setAdapter(arrayAdapter);

        // parse data from csv to the database
        if (mDBhelper.getCount()<=0) {
            new parseCSVIntoDB(this, mDBhelper).execute();
            Log.e("Parsing data into db", "=====");
        }

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
                            searchOption = DBhelper.getTableColumnBookName();
                            isDefault = false;
                            break;
                    }
                }
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.e("onQueryTextSubmit", s + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e("OnQueryTextChange", "======== "+s);
        if (s.length() >= 5) {
            ArrayList<String> queryTexts = new ArrayList<>();
            ArrayList<Integer> queryResultsID = mDBhelper.getIdsForQueryMatchingString(s, searchOption, isDefault);
            // get information from database according to search option and id.
            for (Integer id : queryResultsID) {
                queryTexts.add(mDBhelper.getInformationFromOID(id, DBhelper.getTableColumnBookName()).toString());
            }
            String[] queryResult = new String[queryTexts.size()];
            queryResult = queryTexts.toArray(queryResult);
            arrayAdapter.clear();
            arrayAdapter.addAll(queryResult);
            arrayAdapter.notifyDataSetChanged();
        }
        return false;
    }
}
