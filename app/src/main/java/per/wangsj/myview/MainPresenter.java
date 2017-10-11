package per.wangsj.myview;

import android.content.Intent;

import per.wangsj.myview.titanic.TitanticActivity;

/**
 * Created by shiju.wang on 2017/10/11.
 */

public class MainPresenter implements BasePresenter{

    private MainActivity mContext;

    public MainPresenter(){
    }

    public MainPresenter(MainActivity mainActivity) {
        mContext=mainActivity;
    }

    @Override
    public void jump(int index, int i) {
        Intent intent;
        switch (index){
            case 0:
                intent = new Intent(mContext, ViewActivity.class);
                intent.putExtra("item", i);

                break;
            case 1:
                intent = new Intent(mContext, ViewActivity.class);
                break;
            case 2:
                if(i==0){
                    intent = new Intent(mContext, TitanticActivity.class);
                }else{
                    intent = new Intent(mContext, ViewActivity.class);
                }

                break;
            default:
                intent = new Intent(mContext, ViewActivity.class);
                break;
        }
        mContext.startActivity(intent);
    }
}
