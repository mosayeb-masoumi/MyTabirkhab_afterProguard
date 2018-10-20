package com.example.tornado.taabirkhab;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityFavorite extends AppCompatActivity {

   public static List<String> list1;
   public static List<Integer> list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        TextView title_toolbar =  findViewById(R.id.title_toolbar);
        title_toolbar.setTypeface(MainActivity.font);
        ImageView icon_arrow_back =  findViewById(R.id.icon_arrow_back);
        icon_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);
            }
        });
        RecyclerView recyclerView2 =  findViewById(R.id.recyclerview2);


        SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
        Cursor c = sql.rawQuery("SELECT * FROM tabirkhabcontent WHERE fav = 1",null);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        try{
            while (c.moveToNext()){
                list1.add(c.getString(c.getColumnIndex("list")));
                list2.add(c.getInt(c.getColumnIndex("id")));
            }
        }finally {
            c.close();
        }

        recyclerView2.setAdapter(new RecyclerAdapter2(this,list1,list2));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);

    }
}
