package com.example.temperatursensor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityListView extends AppCompatActivity {

    Cursor cursor;

    SQLiteDatabase sqllitedatabase;
    SQLiteHelper sqlitehelper;
    SQLiteListAdapter ListAdapter;

    ArrayList<String> ID_ArrayList = new ArrayList<>();
    ArrayList<String> TITLE_ArrayList = new ArrayList<>();
    ArrayList<String> SUHU_ArrayList = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_data);

        listView = findViewById(R.id.list);

        sqlitehelper = new SQLiteHelper(this);
    }

    @Override
    protected void onResume() {
        ViewSQLDatabase();
        super.onResume();
    }

    private void ViewSQLDatabase(){
        sqllitedatabase = sqlitehelper.getWritableDatabase();
        cursor = sqllitedatabase.rawQuery("SELECT * FROM Nama_Tabel", null);
        ID_ArrayList.clear();
        TITLE_ArrayList.clear();
        SUHU_ArrayList.clear();

        if (cursor.moveToFirst()){
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));
                TITLE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TITLE)));
                SUHU_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_SUHU)));
            }while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(ActivityListView.this, ID_ArrayList,TITLE_ArrayList,SUHU_ArrayList);

        listView.setAdapter(ListAdapter);
        cursor.close();
    }
}
