package com.example.tornado.taabirkhab;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tornado on 10/16/2018.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private Context context;
    private List<String> list;
    private List<Integer> list2;

    public RecyclerAdapter(Context context, List<String> list,List<Integer> list2) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycler_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt_title.setText(list.get(position));
        holder.txt_title.setTag(list2.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView txt_title;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_list_item);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_title.setTypeface(MainActivity.font);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ActivityContent.class);
                    intent.putExtra("id",(Integer) txt_title.getTag());
                    intent.putExtra("content",txt_title.getText());
                    context.startActivity(intent);
                }
            });
        }
    }


}
