package com.amy.companyinfomation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amy.companyinfomation.BaseConstants;
import com.amy.companyinfomation.R;
import com.amy.companyinfomation.activity.DetailActivity;
import com.amy.companyinfomation.entity.IniEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/9/21.
 */

public class SortListRecycleAdapter extends RecyclerView.Adapter<SortListRecycleAdapter.SortListHolder> {

    private Context context;
    private int type;
    private int classPosition;
    private List<IniEntity.ClassEntity.ClassBean.DataBean.ItemBean> dataList;

    public SortListRecycleAdapter(Context context, int type, int classPosition) {
        this.context = context;
        this.type = type;
        this.classPosition = classPosition;
    }

    public List<IniEntity.ClassEntity.ClassBean.DataBean.ItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<IniEntity.ClassEntity.ClassBean.DataBean.ItemBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public SortListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_sort_list_adapter_type_1, parent, false);
        } else if (type == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.item_sort_list_adapter_type_2, parent, false);
        } else {
            return null;
        }
        SortListHolder holder = new SortListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SortListHolder holder, final int position) {
        if (dataList == null) {
            return;
        }
        final IniEntity.ClassEntity.ClassBean.DataBean.ItemBean bean = dataList.get(position);

        if (type == 1) {
            holder.itemTitleTv.setText(bean.getItem_title());
            holder.itemNameTv.setText(bean.getItem_name());
            holder.itemInfoTv.setText(bean.getItem_info());
            holder.itemNumTv.setText(bean.getItem_num());

            Glide.with(context).load(new File(BaseConstants.WEB_PATH + bean.getItem_icon())).into(holder.itemProfileIv);

        } else {
            int loop = position / 4;
            if (loop % 2 == 0) {
                if (position % 2 == 0) {
                    holder.itemRLayout.setBackgroundResource(R.drawable.shape_circular_bead_blue);
                    holder.itemNameTv.setTextColor(context.getResources().getColor(R.color.font_title_white));
                } else {
                    holder.itemRLayout.setBackgroundResource(R.drawable.shape_circular_bead_white);
                    holder.itemNameTv.setTextColor(context.getResources().getColor(R.color.font_title_blue));
                }
            } else {
                if (position % 2 == 0) {
                    holder.itemRLayout.setBackgroundResource(R.drawable.shape_circular_bead_white);
                    holder.itemNameTv.setTextColor(context.getResources().getColor(R.color.font_title_blue));
                } else {
                    holder.itemRLayout.setBackgroundResource(R.drawable.shape_circular_bead_blue);
                    holder.itemNameTv.setTextColor(context.getResources().getColor(R.color.font_title_white));
                }
            }

            holder.itemNameTv.setText(bean.getItem_name());
        }

        holder.itemRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_WEB_POSITION, position);
                intent.putExtra(DetailActivity.CODE_CLASS_POSITION, classPosition);
                context.startActivity(intent);
            }
        });
    }

    public class SortListHolder extends RecyclerView.ViewHolder {

        public RelativeLayout itemRLayout;
        public ImageView itemProfileIv;
        public TextView itemTitleTv;
        public TextView itemNameTv;
        public TextView itemInfoTv;
        public TextView itemNumTv;

        public SortListHolder(View itemView) {
            super(itemView);

            if (type == 1) {
                itemRLayout = itemView.findViewById(R.id.rl_sort_list_item);
                itemProfileIv = itemView.findViewById(R.id.iv_profile);
                itemNameTv = itemView.findViewById(R.id.tv_name);
                itemInfoTv = itemView.findViewById(R.id.tv_info);
                itemNumTv = itemView.findViewById(R.id.tv_num);
                itemTitleTv = itemView.findViewById(R.id.tv_title);
            } else if (type == 2) {
                itemRLayout = itemView.findViewById(R.id.rl_sort_list_item);
                itemNameTv = itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
