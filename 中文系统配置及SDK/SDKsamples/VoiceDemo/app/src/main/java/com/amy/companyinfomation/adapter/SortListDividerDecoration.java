package com.amy.companyinfomation.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/9/21.
 */

public class SortListDividerDecoration extends RecyclerView.ItemDecoration {

    private int type;

    public SortListDividerDecoration(int type) {
        this.type = type;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildLayoutPosition(view);
        if (type == 1) {
            outRect.left = 40;
            outRect.right = 40;
            outRect.top = 40;
            outRect.bottom = 40;
        } else if (type == 2) {
//            if (position)
            outRect.left = 40;
            outRect.right = 0;
            outRect.top = 40;
            outRect.bottom = 0;
        } else if (type == 3) {
            if (position % 2 == 0) {
                outRect.top = 10;
            } else {
                outRect.left = 17;
                outRect.top = 10;
            }

        } else {
            return;
        }
    }
}
