package per.wsj.kotlinapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import per.wsj.kotlinapp.R
import per.wsj.kotlinapp.Video0Activity

/**
 * Created by shiju.wang on 2017/11/24.
 */
class Article2Adapter(var mContext: Context, var mData:List<String>): RecyclerView.Adapter<Article2Adapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.title?.setText(mData.get(position))

        holder?.itemLayout?.setOnClickListener {
            var intent: Intent =Intent(mContext, Video0Activity::class.java)
            intent.putExtra("position",position)
            intent.putExtra("title",mData.get(position))
            mContext.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view:View=LayoutInflater.from(mContext).inflate(R.layout.layout_item_video,null)
        var myViewHolder: MyViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView=itemView.findViewById(R.id.tvTitle)
        var preview:ImageView=itemView.findViewById(R.id.ivPreview)
        var itemLayout:LinearLayout=itemView.findViewById(R.id.llItem)
    }

}