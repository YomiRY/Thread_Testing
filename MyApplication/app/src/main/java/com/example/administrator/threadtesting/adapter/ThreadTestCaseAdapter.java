package com.example.administrator.threadtesting.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.threadtesting.IOnItemClickListener;
import com.example.administrator.threadtesting.R;

public class ThreadTestCaseAdapter extends RecyclerView.Adapter<ThreadTestCaseAdapter.ViewHolder> {

    private Context mCtx;
    private IOnItemClickListener mListener;
    private String[] mTestCaseList;

    public ThreadTestCaseAdapter(Context ctx, String[] testCaseList) {
        this.mCtx = ctx;
        this.mTestCaseList = testCaseList;
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.test_case_item_view, null);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.testCaseItem.setText(mTestCaseList[position]);
        holder.setPos(position);
    }

    @Override
    public int getItemCount() {
        return mTestCaseList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView testCaseItem;

        private View mItemView;
        private int mPos;

        public ViewHolder(View itemView) {
            super(itemView);

            this.mItemView = itemView;
            this.testCaseItem = (TextView) itemView.findViewById(R.id.tv_test_case_item);

            mItemView.setOnClickListener(this);
        }

        public void setPos(int pos) {
            mPos = pos;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null && mPos >= 0) {
                mListener.onItemClick(mItemView, mPos);
            }
        }
    }
}
