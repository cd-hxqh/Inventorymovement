package com.cdhxqh.inventorymovement.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Itemreq;
import com.cdhxqh.inventorymovement.ui.detailsUi.ItemreqDetailsActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 * 物资编码申请
 */
public class ItemreqAdapter extends RecyclerView.Adapter<ItemreqAdapter.ViewHolder> {

    private static final String TAG = "ItemreqAdapter";
    Context mContext;
    ArrayList<Itemreq> mItemreqs = new ArrayList<Itemreq>();

    public ItemreqAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Itemreq inv = mItemreqs.get(i);

        viewHolder.itemNum.setText(inv.itemreqnum);
        viewHolder.itemDesc.setText(inv.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemreqDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemreq", inv);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItemreqs.size();
    }

    public void update(ArrayList<Itemreq> data, boolean merge) {
        if (merge && mItemreqs.size() > 0) {
            for (int i = 0; i < mItemreqs.size(); i++) {
                Log.i(TAG, "mItems=" + mItemreqs.get(i).itemreqnum);
                Itemreq obj = mItemreqs.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).itemreqnum == obj.itemreqnum) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        mItemreqs = data;

        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Itemreq> data){
        if(data.size()>0){
            for(int i = 0;i < data.size();i++){
                if(!mItemreqs.contains(data.get(i))){
                    mItemreqs.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public RelativeLayout cardView;
        /**
         * 编号标题*
         */
        public TextView itemNumTitle;
        /**
         * 编号*
         */
        public TextView itemNum;
        /**
         * 描述标题*
         */
        public TextView itemDescTitle;
        /**
         * 描述*
         */
        public TextView itemDesc;

        public ViewHolder(View view) {
            super(view);
            cardView = (RelativeLayout) view.findViewById(R.id.card_container);

            itemNumTitle = (TextView) view.findViewById(R.id.item_num_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDescTitle = (TextView) view.findViewById(R.id.item_desc_title);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}
