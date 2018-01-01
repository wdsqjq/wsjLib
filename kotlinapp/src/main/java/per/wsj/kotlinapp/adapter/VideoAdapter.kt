package per.wsj.kotlinapp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import per.wsj.kotlinapp.Constants
import per.wsj.kotlinapp.R
import per.wsj.kotlinapp.VideoActivity
import per.wsj.kotlinapp.net.BaseVideoResponse

/**
 * Created by shiju.wang on 2017/11/24.
 */
class VideoAdapter(var mContext: Context, var mData:List<BaseVideoResponse.BaseVideo>): RecyclerView.Adapter<VideoAdapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.title?.setText(mData.get(position).title)

        holder?.itemLayout?.setOnClickListener {
            var intent: Intent =Intent(mContext, VideoActivity::class.java)
            intent.putExtra("url",mData.get(position).url)
            intent.putExtra("title",mData.get(position).title)
            mContext.startActivity(intent)
        }
        val url=Constants.VIDEO_URL+mData.get(position).thumb
        Log.d("VideoAdpater",url)
        Glide.with(mContext)
                .load(url)//加载资源
                .placeholder(R.mipmap.ic_loading) // can also be a drawable
//                .error(error) // will be displayed if the image cannot be loaded
                .dontAnimate()//don't use animate
                .skipMemoryCache(false)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//跳过磁盘缓存
                .into(holder?.ivThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view:View=LayoutInflater.from(mContext).inflate(R.layout.layout_item_video,parent,false)
        var myViewHolder: MyViewHolder = MyViewHolder(view)
        return myViewHolder
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView=itemView.findViewById(R.id.tvTitle)
        var itemLayout:View=itemView
        var ivThumb:ImageView=itemView.findViewById(R.id.ivThumb)
    }
}