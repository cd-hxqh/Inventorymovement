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
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Invreserve;
import com.cdhxqh.inventorymovement.model.Itemreq;
import com.cdhxqh.inventorymovement.model.WorkOrder;
import com.cdhxqh.inventorymovement.ui.detailsUi.InvDetailsActivity;
import com.cdhxqh.inventorymovement.ui.detailsUi.ItemreqDetailsActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 * 出库管理
 */
public class InvreserveAdapter extends RecyclerView.Adapter<InvreserveAdapter.ViewHolder> {

    private static final String TAG = "InvreserveAdapter";
    Context mContext;
    ArrayList<Invreserve> invreserves = new ArrayList<Invreserve>();

    public InvreserveAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Invreserve invreserve = invreserves.get(i);

        viewHolder.itemNumTitle.setText(mContext.getString(R.string.item_num_title));
        viewHolder.itemDescTitle.setText(mContext.getString(R.string.item_desc_title));
        viewHolder.itemNum.setText(invreserve.itemnum);
        viewHolder.itemDesc.setText(invreserve.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemreqDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemreq", invreserve);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return invreserves.size();
    }

    public void update(ArrayList<Invreserve> data, boolean merge) {
        if (merge && invreserves.size() > 0) {
            for (int i = 0; i < invreserves.size(); i++) {
                Invreserve obj = invreserves.get(i);
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
        invreserves = data;

        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Invreserve> data){
        if(data.size()>0){
            for(int i = 0;i < data.size();i++){
                if(!invreserves.contains(data.get(i))){
                    invreserves.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public CardView cardView;
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
            cardView = (CardView) view.findViewById(R.id.card_container);

            itemNumTitle = (TextView) view.findViewById(R.id.item_num_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDescTitle = (TextView) view.findViewById(R.id.item_desc_title);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}
