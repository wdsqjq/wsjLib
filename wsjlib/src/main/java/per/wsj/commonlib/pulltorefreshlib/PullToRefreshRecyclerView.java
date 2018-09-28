package per.wsj.commonlib.pulltorefreshlib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PullToRefreshRecyclerView extends RecyclerView implements Pullable {

    private double scale = 1;   //抛掷速度,滚动阻力

    private LinearLayoutManager mLinearLayoutManager;


    public PullToRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getChildCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getManager().findFirstVisibleItemPosition() == 0
                && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean canPullUp() {
        if (getChildCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getManager().findLastVisibleItemPosition() == (getManager().getItemCount() -
                1)) {
            // 滑到底部了
            if (getChildAt(getManager().findLastVisibleItemPosition() - getManager()
                    .findFirstVisibleItemPosition()) != null && getChildAt(getManager()
                    .findLastVisibleItemPosition() - getManager().findFirstVisibleItemPosition())
                    .getBottom() <= getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }

    private LinearLayoutManager getManager() {
        if (mLinearLayoutManager == null) {
            synchronized (PullToRefreshRecyclerView.class) {
                if (mLinearLayoutManager == null) {
                    mLinearLayoutManager = (LinearLayoutManager) getLayoutManager();
                }
            }
        }
        return mLinearLayoutManager;
    }

    public void setFlingScale(double scale) {
        this.scale = scale;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityY *= scale;
        return super.fling(velocityX, velocityY);
    }
}
