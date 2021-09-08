package per.wsj.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import per.wsj.commonlib.utils.DisplayUtil
import per.wsj.commonlib.utils.LogUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtil.e("width: " + DisplayUtil.getScreenWidth())
    }
}