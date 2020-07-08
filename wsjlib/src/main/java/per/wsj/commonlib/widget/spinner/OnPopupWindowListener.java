package per.wsj.commonlib.widget.spinner;

/**
 * Created by Administrator on 2017/4/25.
 */

public interface OnPopupWindowListener<T> {

    String getText(T t);

    void onClick(T t, int pos);

}
