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
import com.amy.companyinfomation.activity.SortListActivity;
import com.amy.companyinfomation.entity.IniEntity;
import com.amy.companyinfomation.utils.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */

public class MainMenuRecycleAdapter extends RecyclerView.Adapter<MainMenuRecycleAdapter.MainGridHolder> {

    private Context context;
    private List<IniEntity.ClassEntity.ClassBean> dataList;

    public MainMenuRecycleAdapter(Context context) {
        this.context = context;
    }

    public List<IniEntity.ClassEntity.ClassBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<IniEntity.ClassEntity.ClassBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MainGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_grid_adapter, parent, false);
        MainGridHolder holder = new MainGridHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainGridHolder holder, final int position) {
        if (dataList == null) {
            return;
        }
        final IniEntity.ClassEntity.ClassBean bean = dataList.get(position);

        holder.itemTitleTv.setText(bean.getClass_title());
        holder.itemInfoTv.setText(bean.getClass_info());

        RequestOptions options = new RequestOptions()
                .centerCrop();
//                .transform(new GlideRoundTransform(50));

        Glide.with(context).load(new File(BaseConstants.IMG_PATH + bean.getClass_icon())).apply(options).into(holder.itemIconIv);

        holder.itemRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SortListActivity.class);
                intent.putExtra(SortListActivity.CODE_CLASS, position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class MainGridHolder extends RecyclerView.ViewHolder {

        public RelativeLayout itemRLayout;
        public ImageView itemIconIv;
        public TextView itemTitleTv;
        public TextView itemInfoTv;

        public MainGridHolder(View itemView) {
            super(itemView);

            itemRLayout = itemView.findViewById(R.id.rl_main_menu_item);
            itemIconIv = itemView.findViewById(R.id.iv_icon);
            itemTitleTv = itemView.findViewById(R.id.tv_title);
            itemInfoTv = itemView.findViewById(R.id.tv_info);
        }
    }
}
