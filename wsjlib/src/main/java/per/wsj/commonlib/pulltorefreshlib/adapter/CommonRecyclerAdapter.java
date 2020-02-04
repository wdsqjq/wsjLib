package per.wsj.commonlib.pulltorefreshlib.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import java.util.List;

import per.wsj.commonlib.R;

/**
 * Created by shiju.wang on 2018/3/29.
 *
 * @author shiju.wang
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    /**
     * 加载异常
     */
    private int TYPE_EMPTY = 0;
    /**
     * 加载正常
     */
    private int TYPE_NORMAL = 1;
    /**
     * 异常view
     */
    private View emptyView = null;
    /**
     * 异常layout
     */
    private RelativeLayout layout;
    /**
     * 对应的recyclerView
     */
    private RecyclerView parent;

    protected Context mContext;
    protected List<T> mList;

    protected ViewHolderClick<T> holderClick;
    protected AdapterView.OnItemClickListener  onItemClickListener;
    protected AdapterView.OnItemLongClickListener onItemLongClickListener;

    ReLoadListener mReLoadListener;

    public CommonRecyclerAdapter(Context context, List<T> data) {
        this.mContext = context;
//        this.mList = new ArrayList<>();
        this.mList = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (emptyView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }

    /**
     * 清除空数据页
     * 调用了setEmpty()方法后要再显示数据必须调用这个方法
     */
    public void clearEmptyView() {
        emptyView = null;
        if (layout != null) {
            layout.removeAllViews();
        }
    }

    /**
     * 设置空数据页(显示默认的layout)
     */
    public void setEmpty() {
        setEmpty(-1);
    }

    public void setError() {
        setEmpty(R.layout.layout_list_error);
    }

    public void setError(int layoutId) {
        setEmpty(layoutId);
    }

    /**
     * 设置空数据页
     *
     * @param layoutId 要显示的layout资源id
     */
    public void setEmpty(int layoutId) {
        emptyView = null;
        if (layoutId == -1) {
            emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_empty, parent, false);
        } else {
            emptyView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        }
        if (layout == null) {
            layout = new RelativeLayout(mContext);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        layout.removeAllViews();
        layout.addView(emptyView);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (emptyView != null && viewType == TYPE_EMPTY) {
            return new RecyclerViewHolder(layout);
        } else {
            View view = LayoutInflater.from(mContext).inflate(onCreateViewLayoutId(viewType), parent, false);
            return new RecyclerViewHolder(view);
        }
    }

    @Override
    public void onViewRecycled(final RecyclerViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_EMPTY) {
            holder.itemView.setOnClickListener(v -> {
                if (mReLoadListener != null) {
                    mReLoadListener.onReload();
                }
            });
            return;
        }
        onBindViewHolder(holder.getViewHolder(), position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(null, v, holder.getAdapterPosition(), holder.getItemId()));
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                onItemLongClickListener.onItemLongClick(null, v, holder.getAdapterPosition(), holder.getItemId());
                return false;
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_EMPTY ? gridManager.getSpanCount() : 1;
                }
            });
        }

    }

    /**
     * 抽象方法,设置layout
     *
     * @param viewType
     * @return
     */
    public abstract int onCreateViewLayoutId(int viewType);

    public abstract void onBindViewHolder(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return emptyView != null ? mList.size() + 1 : mList.size();
    }

    /**
     * 获取列表
     */
    public List<T> getList() {
        return mList;
    }

    /**
     * 替换某一个元素
     */
    public void replaceBean(int position, T t) {
        if (t != null) {
            this.mList.remove(position);
            this.mList.add(position, t);
            notifyItemChanged(position, t);
        }
    }

    /**
     * 添加数据列表到列表头部
     */
    public void addListAtStart(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.mList.addAll(0, list);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据列表到列表尾部
     */
    public void addListAtEnd(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.mList.addAll(list);
            notifyItemRangeInserted(mList.size() - 1, list.size());
        }

    }


    /**
     * 添加数据列表到列表尾部
     */
    public void addListAtEndAndNotify(List<T> list) {
        if (list != null && !list.isEmpty()) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }


    /**
     * 添加单个元素到列表头
     */
    public void addListBeanAtStart(T t) {
        if (t != null) {
            mList.add(0, t);
            notifyItemInserted(0);
        }

    }

    /**
     * 添加单个元素到列表尾
     */
    public void addListBeanAtEnd(T t) {
        if (t != null) {
            mList.add(t);
            notifyItemInserted(mList.size() - 1);
        }

    }

    /**
     * 替换RecyclerView数据
     */
    public void replaceList(List<T> list) {
        if (list != null) {
            this.mList = list;
        } else {
            mList.clear();
        }

        notifyDataSetChanged();
    }

    /**
     * 替换RecyclerView中的某一个数据
     */
    public void replaceItem(T t, int position) {
        if (position >= 0 && position <= mList.size() && t != null) {
            this.mList.set(position, t);
            notifyItemChanged(position);
        }
    }

    /**
     * 删除RecyclerView所有数据
     */
    public void removeAll() {
        if (mList != null) {
            notifyItemRangeRemoved(0, mList.size());
            this.mList.clear();
        }
    }

    /**
     * 删除RecyclerView指定位置的数据
     */
    public void remove(T t) {
        if (mList != null) {
            this.mList.remove(t);
            notifyDataSetChanged();
        }
    }

    /**
     * 删除RecyclerView指定位置的数据
     */
    public void remove(int position) {
        if (position >= 0 && position <= mList.size() && mList != null) {
            this.mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * item点击事件抽象方法
     */
    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * item长按事件抽象方法
     */
    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 点击事件抽象方法
     */
    public void setOnHolderClick(ViewHolderClick<T> holderClick) {
        this.holderClick = holderClick;
    }

    public interface ViewHolderClick<T> {
        void onViewClick(View view, T t, int position);
    }

    public void setOnReLoadListener(ReLoadListener mReLoadListener) {
        this.mReLoadListener = mReLoadListener;
    }

    public interface ReLoadListener {
        void onReload();
    }
}