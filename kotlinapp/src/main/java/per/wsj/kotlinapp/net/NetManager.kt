package per.wsj.kotlinapp.net

import android.content.Context
import per.wsj.commonlib.net.NetLib
import per.wsj.kotlinapp.Constants

/**
 * Created by shiju.wang on 2018/1/2.
 */
class NetManager(context: Context, url: String = Constants.BASE_URL) : NetLib(context, url)
