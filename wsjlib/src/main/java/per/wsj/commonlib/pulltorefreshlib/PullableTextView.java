package per.wsj.commonlib.pulltorefreshlib;


import android.content.Context;
import android.util.AttributeSet;

public class PullableTextView extends androidx.appcompat.widget.AppCompatTextView implements Pullable
{

	public PullableTextView(Context context)
	{
		super(context);
	}

	public PullableTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		return true;
	}

	@Override
	public boolean canPullUp()
	{
		return true;
	}

}
