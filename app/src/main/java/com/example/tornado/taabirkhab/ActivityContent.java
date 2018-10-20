package com.example.tornado.taabirkhab;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        final TextView title_toolbar = (TextView) findViewById(R.id.title_toolbar);
        final TextView txt_content = (TextView) findViewById(R.id.txt_content);

        title_toolbar.setTypeface(MainActivity.font);
        txt_content.setTypeface(MainActivity.font);

        ImageView icon_arrow_back = (ImageView) findViewById(R.id.icon_arrow_back);
        ImageView icon_share = (ImageView) findViewById(R.id.icon_share);
        final ImageView icon_favorite = (ImageView) findViewById(R.id.icon_favorite);

        icon_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);

            }
        });

        Bundle bundle=getIntent().getExtras();
        final int id=bundle.getInt("id");
        final String words=bundle.getString("content");

        title_toolbar.setText(words);

         // jahat gereftan matn taabir az database
        SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
        final Cursor c = sql.rawQuery("SELECT * FROM tabirkhabcontent WHERE id="+id,null);
        c.moveToFirst();
        txt_content.setText(Html.fromHtml(c.getString(c.getColumnIndex("content"))));



        if (c.getInt(c.getColumnIndex("fav")) == 1) {
            icon_favorite.setImageResource(R.mipmap.ic_fullheart);
            icon_favorite.setTag("1");
        }else {
            icon_favorite.setImageResource(R.mipmap.ic_emptyheart);
            icon_favorite.setTag("0");
        }




        icon_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(icon_favorite.getTag()=="0")
              {
                icon_favorite.setImageResource(R.mipmap.ic_fullheart);
                icon_favorite.setTag("1");

                //taghirat dade shode dar database zakhire gardad
                  SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
                  sql.execSQL("UPDATE tabirkhabcontent SET fav = 1 WHERE id="+id);
                  Toast.makeText(ActivityContent.this, "به علاقه مندی اضافه شد", Toast.LENGTH_SHORT).show();
              }else{
                  icon_favorite.setImageResource(R.mipmap.ic_emptyheart);
                  icon_favorite.setTag("0");
                  
                  SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
                  sql.execSQL("UPDATE tabirkhabcontent SET fav = 0 WHERE id="+id);
                  Toast.makeText(ActivityContent.this, "از علاقه مندی حذف شد", Toast.LENGTH_SHORT).show();

              }
            }
        });


       icon_arrow_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

       icon_share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent share = new Intent(Intent.ACTION_SEND);
               share.setType("text/plain");
               share.putExtra(Intent.EXTRA_TEXT,txt_content.getText());
               share.putExtra(Intent.EXTRA_SUBJECT,title_toolbar.getText());

                  startActivity(Intent.createChooser(share,"اشتراک با ..."));

           }
       });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_in_back, R.anim.activity_out_back);

    }
}
