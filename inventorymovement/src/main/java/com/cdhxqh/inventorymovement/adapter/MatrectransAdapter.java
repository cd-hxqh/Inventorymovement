package com.cdhxqh.inventorymovement.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Matrectrans;
import com.cdhxqh.inventorymovement.ui.MatrectransActivity;
import com.cdhxqh.inventorymovement.ui.detailsUi.ItemDetailsActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/12/12.
 */
public class MatrectransAdapter extends RecyclerView.Adapter<MatrectransAdapter.ViewHolder> {

    private static final String TAG = "MatrectransAdapter";
    Context mContext;
    ArrayList<Matrectrans> mItems = new ArrayList<Matrectrans>();

    public MatrectransAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Matrectrans item = mItems.get(i);

        Log.i(TAG, "item.itemnum=" + item.itemnum);
        viewHolder.itemNum_title.setText(R.string.item_num_text);
        viewHolder.itemNum.setText(item.itemnum);
        viewHolder.itemDesc.setText(item.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MatrectransActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("matrectrans", item);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void update(ArrayList<Matrectrans> data, boolean merge) {
        if (merge && mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                Log.i(TAG, "mItems=" + mItems.get(i).itemnum);
                Matrectrans obj = mItems.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).itemnum == obj.itemnum) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        mItems = data;

        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Matrectrans> data){
        if(data.size()>0){
            for(int i = 0;i < data.size();i++){
                if(!mItems.contains(data.get(i))){
                    mItems.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void removeAllData() {
        if (mItems.size() > 0) {
            mItems.removeAll(mItems);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public CardView cardView;

        /**
         * 编号*
         */
        public TextView itemNum_title;

        /**
         * 项目*
         */
        public TextView itemNum;
        /**
         * 描述*
         */
        public TextView itemDesc;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_container);
            itemNum_title = (TextView) view.findViewById(R.id.item_num_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}