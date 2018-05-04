package per.wsj.commonlib.pulltorefreshlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shiju.wang on 2018/3/29.
 *
 * @author shiju.wang
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    
    private BaseViewHolder baseViewHolder;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        baseViewHolder = BaseViewHolder.getViewHolder(itemView);
    }

    public BaseViewHolder getViewHolder() {
        return baseViewHolder;
    }
}
