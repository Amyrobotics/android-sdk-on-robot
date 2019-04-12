package com.amy.amynavsdkdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amy.amynavsdkdemo.R;
import com.amy.amynavsdkdemo.activity.RoboNavActivity;
import com.amy.amynavsdkdemo.bean.MapListRespBean;
import com.amy.amynavsdkdemo.listener.OnItemClickListener;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Async_wu
 * @date 2019-3-20 14:45:53
 * 地图adapter
 */
public class MapRecyAdapter extends RecyclerView.Adapter<MapRecyAdapter.MapViewHolder> {
    private Context context;
    private String defaultMapId;
    private List<MapListRespBean.MapFileInfoListBean> mapList;

    public MapRecyAdapter(Context context){
        this.context = context;
    }
    public MapRecyAdapter(Context context,String defaultMapId){
        this.context = context;
        this.defaultMapId = defaultMapId;
    }



    @NonNull
    @Override
    public MapViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_maplist_item,viewGroup,false);
        return new MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapViewHolder mapViewHolder, final int i) {
        mapViewHolder.mapNameTextView.setText(mapList.get(i).getName());
        mapViewHolder.mapIdTextView.setText(mapList.get(i).getId());
        mapViewHolder.createTimeTextView.setText(mapList.get(i).getCreateTime());
        String mapSizeDesc = mapList.get(i).getWidth()+"x"+mapList.get(i).getHeight()+";"+" ratio:"+mapList.get(i).getRatio();
        mapViewHolder.mapSizeTextView.setText(mapSizeDesc);
        if(mapList.get(i).getId().equals(defaultMapId)){
            mapViewHolder.defaultTextView.setVisibility(View.VISIBLE);
        }else {
            mapViewHolder.defaultTextView.setVisibility(View.GONE);
        }
        mapViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoboNavActivity.class);
                intent.putExtra("defaultMapId",mapList.get(i).getId());
                intent.putExtra("bean",new Gson().toJson(mapList.get(i)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(mapList!=null){
            size = mapList.size();
        }
        return size;
    }

    public void getNewList(String defaultMapId,List<MapListRespBean.MapFileInfoListBean> newList){
        if(mapList!=null){
            mapList.clear();
        }
        this.defaultMapId = defaultMapId;
        this.mapList = newList;
    }



    protected class MapViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_mapName)
        TextView mapNameTextView;
        @BindView(R.id.tv_mapId)
        TextView mapIdTextView;
        @BindView(R.id.tv_mapCreateTime)
        TextView createTimeTextView;
        @BindView(R.id.tv_mapSize)
        TextView mapSizeTextView;
        @BindView(R.id.tv_mapIsDefault)
        TextView defaultTextView;

        public MapViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
