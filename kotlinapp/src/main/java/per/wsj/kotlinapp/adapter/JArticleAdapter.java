package per.wsj.kotlinapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import per.wsj.kotlinapp.ArticleActivity;
import per.wsj.kotlinapp.R;
import per.wsj.kotlinapp.net.ArticleResponse.DetailBean;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class JArticleAdapter extends RecyclerView.Adapter<JArticleAdapter.ViewHolder> {

    private final List<DetailBean> mValues;
    private Context mContext;

    public JArticleAdapter(Context context, List<DetailBean> items) {
        mValues = items;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ArticleActivity.class);
                intent.putExtra("url",mValues.get(position).getName());
                intent.putExtra("title", mValues.get(position).getName());
                mContext.startActivity(intent);
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.tvTitle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
