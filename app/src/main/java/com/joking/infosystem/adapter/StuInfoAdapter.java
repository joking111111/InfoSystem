package com.joking.infosystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joking.infosystem.R;
import com.joking.infosystem.activity.DetailActivity;
import com.joking.infosystem.bean.StuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class StuInfoAdapter extends RecyclerView.Adapter<StuInfoAdapter.ViewHolder> {

    private List<StuInfo> mStuInfoList = new ArrayList<>();
    private Context mContext;

//    public StuInfoAdapter() {
//        refresh();
//    }

    public void addItem(StuInfo stuInfo) {
        mStuInfoList.add(stuInfo);
        notifyItemInserted(mStuInfoList.size() - 1);
    }

    public void refresh(List<StuInfo> list) {
        // 性能优化
//        if (!mStuInfoList.containsAll(list)) {
        mStuInfoList.clear();
        // 不要直接引用！！！！！！！！！！！！！！！
        mStuInfoList.addAll(list);
        notifyDataSetChanged();
//        }
    }

    public void clear() {
        mStuInfoList.clear();
        notifyDataSetChanged();
    }

    public void recycle() {
        mContext = null;
        mStuInfoList.clear();
        mStuInfoList = null;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView stu_pic;
        TextView stu_name;
        TextView stu_class;
        TextView stu_NO;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            stu_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            stu_name = (TextView) itemView.findViewById(R.id.tv_name);
            stu_class = (TextView) itemView.findViewById(R.id.tv_class);
            stu_NO = (TextView) itemView.findViewById(R.id.tv_NO);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_stuinfo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final StuInfo stuInfo = mStuInfoList.get(position);

//        holder.item_stu_pic.setImageResource(stuInfo.getPic_id());
        Glide.with(mContext).load(stuInfo.getPic_id()).into(holder.stu_pic);
        holder.stu_name.setText(stuInfo.getName());
        holder.stu_class.setText("" + stuInfo.getClass_id());
        holder.stu_NO.setText("" + stuInfo.getNO_id());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Click", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT < 16) {
                    DetailActivity.actionStart(mContext, "" + stuInfo.getId());
                } else {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) mContext,
                            holder.stu_pic,
                            "fruit");
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(StuInfo.ID, "" + stuInfo.getId());
                    mContext.startActivity(intent, optionsCompat.toBundle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStuInfoList.size();
    }

}
