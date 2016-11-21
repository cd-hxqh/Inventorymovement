package com.cdhxqh.inventorymovement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Itemreqline;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 * 物资编码申请
 */
public class ItemreqLineAdapter extends RecyclerView.Adapter<ItemreqLineAdapter.ViewHolder> {

    private static final String TAG = "ItemreqAdapter";
    Context mContext;
    ArrayList<Itemreqline> mItemreqlines = new ArrayList<Itemreqline>();

    public ItemreqLineAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_itemreq_line, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Itemreqline itemreqline = mItemreqlines.get(i);

        viewHolder.itemNum.setText(itemreqline.itemnum);
        viewHolder.maternameTitle.setText(itemreqline.matername);
        viewHolder.xhText.setText(itemreqline.xh);


    }

    @Override
    public int getItemCount() {
        return mItemreqlines.size();
    }

    public void update(ArrayList<Itemreqline> data, boolean merge) {
        if (merge && mItemreqlines.size() > 0) {
            for (int i = 0; i < mItemreqlines.size(); i++) {
                Log.i(TAG, "mItems=" + mItemreqlines.get(i).itemnum);
                Itemreqline obj = mItemreqlines.get(i);
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
        mItemreqlines = data;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public LinearLayout cardView;
        /**
         * 物资编号*
         */
        public TextView itemNum;
        /**
         * 物资名称
         */
        public TextView maternameTitle;
        /**
         * 物资型号
         */
        public TextView xhText;

        public ViewHolder(View view) {
            super(view);
            cardView = (LinearLayout) view.findViewById(R.id.card_container);

            itemNum = (TextView) view.findViewById(R.id.itemreq_num_text);
            maternameTitle = (TextView) view.findViewById(R.id.itemreq_matername_text);
            xhText = (TextView) view.findViewById(R.id.itemreq_xh_text);
        }
    }


    public void adddate(ArrayList<Itemreqline> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (!mItemreqlines.contains(data.get(i))) {
                    mItemreqlines.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


}
