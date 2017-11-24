package per.wsj.commonlib.pulltorefreshlib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class PullToRefreshRecyclerView extends BaseRecyclerView implements Pullable {
    private LinearLayoutManager mLinearLayoutManager;

    private DatasChangedListener mListener;


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
    protected void toggleEmptyView() {
        if (mListener != null)
            mListener.toggleEmptyView();
    }

    @Override
    protected void toggleOriginView() {
        if (mListener != null)
            mListener.toggleOriginView();
    }

    @Override
    protected int getEmptyCount() {
        if (mListener != null)
            return mListener.getEmptyCount();
        return 0;
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
        } else
            return false;
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
                    .getBottom() <= getMeasuredHeight())
                return true;
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

    /**
     * 设置数据监听
     *
     * @param listener
     */
    public void setDatasChangedListener(DatasChangedListener listener) {
        this.mListener = listener;
    }

    public interface DatasChangedListener {
        void toggleEmptyView();

        void toggleOriginView();

        int getEmptyCount();
    }

}
