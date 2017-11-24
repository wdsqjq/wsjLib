package per.wsj.commonlib.pulltorefreshlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by jason.huang on 2016/8/4 0004.
 */
public abstract class BaseRecyclerView extends RecyclerView {

    private static final String TAG = "RecyclerView";

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            Log.i(TAG, "onItemRangeInserted" + itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {

        if (getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == getEmptyCount();
            if (emptyViewVisible) {
                toggleEmptyView();
            } else {
                toggleOriginView();
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    /**
     * 显示空页面
     */
    protected abstract void toggleEmptyView();

    /**
     * 显示原视图
     */
    protected abstract void toggleOriginView();

    /**
     * 定义空数据数量
     */
    protected abstract int getEmptyCount();


}
