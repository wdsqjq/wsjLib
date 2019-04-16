package per.wsj.commonlib.pulltorefreshlib;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;

/**
 * Created by shiju.wang on 2017/11/17.
 */

public class PullableViewPager extends ViewPager implements Pullable {


    public PullableViewPager(Context context) {
        super(context);
    }

    public PullableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
