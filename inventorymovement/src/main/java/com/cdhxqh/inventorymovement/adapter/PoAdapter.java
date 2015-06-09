package com.cdhxqh.inventorymovement.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.Po;
import com.cdhxqh.inventorymovement.ui.itemui.ItemDetailsActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 */
public class PoAdapter extends RecyclerView.Adapter<PoAdapter.ViewHolder> {

    private static final String TAG = "PoAdapter";
    Context mContext;
    ArrayList<Po> mPos = new ArrayList<Po>();
//    V2EXDataSource mDataSource = Application.getDataSource();

    public PoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Po po = mPos.get(i);


        viewHolder.itemNum.setText(po.ponum);
        viewHolder.itemDesc.setText(po.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ItemDetailsActivity.class);
//                Bundle bundle = new Bundle();
////                bundle.putString("num",item.itemnum);
////                bundle.putParcelable("item",item);
//                bundle.putParcelable("item", item);
//                Log.i(TAG, "itemid=" + item.itemid + ",itemnum=" + item.itemnum + ",description=" + item.description + ",in20=" + item.in20 + ",in24=" + item.in24 + ",orderunit=" + item.orderunit + ",issueunit=" + item.issueunit + ",enterby=" + item.enterby + ",enterdate=" + item.enterdate);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mPos.size();
    }

    public void update(ArrayList<Po> data, boolean merge) {
        if (merge && mPos.size() > 0) {
            for (int i = 0; i < mPos.size(); i++) {
                Log.i(TAG, "mItems=" + mPos.get(i).poid);
                Po obj = mPos.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).poid == obj.poid) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        mPos = data;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public CardView cardView;

        /**
         * 编号*
         */
        public TextView itemNum;
        /**
         * 描述*
         */
        public TextView itemDesc;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_container);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}
