package com.food2school.loginregister;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Vin on 08/12/2016.
 */

public class RightAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private RightAdapterCallback mCallback;
    private List<MenuCategory.Food> mData;

    private LayoutInflater mInflater;

    public RightAdapter(@NonNull Context mContext, @NonNull List<MenuCategory.Food> mData,@NonNull RightAdapterCallback callback) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCallback = callback;
        mInflater = LayoutInflater.from(mContext);
    }

    public void update(int position,MenuCategory.Food model){
        //System.out.println(mData.get(position).getName());
        //System.out.println(mData.get(position).getNum());
        mData.set(position,model);
        //System.out.println(mData.get(position).getNum());
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.right_header,null);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.title.setText(mData.get(position).getcName());

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).getcId();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (null == view) {
            view = mInflater.inflate(R.layout.right_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final MenuCategory.Food sm = mData.get(i);
        /*
        System.out.println(i);
        System.out.println(sm.getcName());
        System.out.println(sm.getcId());
        System.out.println(sm.getName());
        System.out.println(holder.name.getText().toString());
        System.out.println(" ");
        */
        holder.name.setText(sm.getName());
        holder.price.setText(String.valueOf(sm.getPrice()));

        //holder.name.setText(String.format(holder.name.getText().toString(),sm.getName()));
        //holder.price.setText(String.format(holder.price.getText().toString(),sm.getPrice()));
        holder.num.setText(String.valueOf(sm.getNum()));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClickNumButton(i,true);
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sm.getNum() > 0) {
                    mCallback.onClickNumButton(i, false);
                }
            }
        });

        return view;
    }


    class ViewHolder {
        TextView name;
        TextView price;
        Button add;
        Button remove;
        TextView num;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            add = (Button) view.findViewById(R.id.add);
            remove = (Button) view.findViewById(R.id.remove);
            num = (TextView) view.findViewById(R.id.num);
        }
    }

    class HeaderViewHolder {
        TextView title;
        public HeaderViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.header_title);
        }
    }
}