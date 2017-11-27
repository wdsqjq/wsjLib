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

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class BasisAdapter extends RecyclerView.Adapter<BasisAdapter.ViewHolder> {

    private final List<String> mValues;
    private Context mContext;

    public BasisAdapter(Context context, List<String> items) {
        mValues = items;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_basis, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mContentView.setText(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ArticleActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("title", mValues.get(position));
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
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
