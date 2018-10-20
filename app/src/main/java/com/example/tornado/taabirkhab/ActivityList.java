package com.example.tornado.taabirkhab;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView title_toolbar = (TextView) findViewById(R.id.title_toolbar);
        title_toolbar.setTypeface(MainActivity.font);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        ImageView icon_arrow_back = (ImageView) findViewById(R.id.icon_arrow_back);
        EditText edt_search = (EditText) findViewById(R.id.edt_search);
        icon_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
            }
        });

        SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
        Cursor c = sql.rawQuery("SELECT * FROM tabirkhabcontent",null);

        final List<String> list = new ArrayList<String>();
        final List<Integer> list2 = new ArrayList<Integer>();
        try {
            while(c.moveToNext()) {
                list.add(c.getString(c.getColumnIndex("list")));
                list2.add(c.getInt(c.getColumnIndex("id")));
            }
        } finally {
            c.close();
        }

        final RecyclerAdapter adapter = new RecyclerAdapter(this,list,list2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        edt_search.setTypeface(MainActivity.font);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                list2.clear();
                SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM tabirkhabcontent WHERE list LIKE '%"+charSequence+"%'",null);
                try {
                    while(c.moveToNext()) {
                        list.add(c.getString(c.getColumnIndex("list")));
                        list2.add(c.getInt(c.getColumnIndex("id")));
                    }
                } finally {
                    c.close();
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityList.this,LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);

    }
}
