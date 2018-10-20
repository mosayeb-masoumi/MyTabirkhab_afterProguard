package com.example.tornado.taabirkhab;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by tornado on 10/16/2018.
 */


public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder>{

    private Context context;
    private List<String> list;
    private List<Integer> list2;

    public RecyclerAdapter2(Context context, List<String> list, List<Integer> list2) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public RecyclerAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycler_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter2.ViewHolder holder, final int position) {

        holder.txt_title.setText(list.get(position));
        holder.txt_title.setTag(list.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert= new AlertDialog.Builder(context);
                alert.setTitle("حذف");
                alert.setMessage("آیا مایل به حذف از علاقه مندی هستید؟");
                alert.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        SQLiteDatabase sql = MainActivity.database.getWritableDatabase();
                        sql.execSQL("UPDATE tabirkhabcontent SET fav = 0 WHERE id="+list2.get(position));
                        Toast.makeText(context,"از علاقه مندی حذف شد",Toast.LENGTH_SHORT).show();
                        ActivityFavorite.list1.remove(position);
                        ActivityFavorite.list2.remove(position);
                        RecyclerAdapter2.this.notifyItemRemoved(position);

                    }
                });
                alert.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();


            }

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView txt_title;
        public RecyclerView recyclerView2;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_list_item);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_title.setTypeface(MainActivity.font);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(context,ActivityContent.class);
                    intent.putExtra("content",txt_title.getText());
                    intent.putExtra("id", (Integer) txt_title.getTag());

                    context.startActivity(intent);
                }
            });


        }
    }
}
