package com.amy.amynavsdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.amy.amynavsdkdemo.R;
import com.amy.amynavsdkdemo.bean.MarkPointRespBean;
import com.amy.amynavsdkdemo.listener.OnItemClickListener;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Async_wu
 * @date 2019-3-22 16:16:48
 * 标记点适配器
 */
public class MarkPointRecyAdapter extends RecyclerView.Adapter<MarkPointRecyAdapter.MarkPointViewHolder> {
    private Context context;
    private LinkedList<MarkPointRespBean> markList;
    public MarkPointRecyAdapter (Context context){
        this.context = context;
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public MarkPointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_markpoint_item,parent,false);
        return new MarkPointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarkPointViewHolder holder, final int position) {
        final MarkPointRespBean markPoint = markList.get(position);
        holder.markNameTextView.setText(markPoint.getText());
        String coor = markPoint.getX()+","+markPoint.getY();
        holder.mapCoorTextView.setText(coor);
        String realCoor = markPoint.getRealX()+","+markPoint.getRealY();
        holder.mapRealCoorTextView.setText(realCoor);
        holder.angleTextView.setText(markPoint.getRealAngle()+"");
        holder.aliasTextView.setText(markPoint.getName2());
        holder.descTextview.setText(markPoint.getDesc());
        String isStart = "";
        if(markPoint.isIsStartPoint()){
            isStart = context.getResources().getString(R.string.title_sure);
        }else {
            isStart = context.getResources().getString(R.string.title_false);
        }
        holder.isStartTextView.setText(isStart);
        holder.navToPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(markList!=null){
            size = markList.size();
        }
        return size;
    }

    public void getMarkList( LinkedList<MarkPointRespBean> newList){
        if(markList!=null){
            markList.clear();
        }
        markList = newList;
    }

    class MarkPointViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_markPoint)
        TextView markNameTextView;
        @BindView(R.id.tv_mapCoordinate)
        TextView mapCoorTextView;
        @BindView(R.id.tv_mapRealCoordinate)
        TextView mapRealCoorTextView;
        @BindView(R.id.tv_markAngle)
        TextView angleTextView;
        @BindView(R.id.tv_markAlias)
        TextView aliasTextView;
        @BindView(R.id.tv_markDesc)
        TextView descTextview;
        @BindView(R.id.tv_isStartPoint)
        TextView isStartTextView;
        @BindView(R.id.btn_navToPoint)
        Button navToPoint;
        public MarkPointViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
