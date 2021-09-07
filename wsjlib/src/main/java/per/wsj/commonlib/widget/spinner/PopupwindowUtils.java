package per.wsj.commonlib.widget.spinner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import per.wsj.commonlib.R;


public class PopupwindowUtils {


    /**
     * 显示菜单
     */
    public static <T> void showMenu(Context context, View target, List<T> datas, final OnPopupWindowListener<T> listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_menu, null);
        int width = target.getWidth();
//        popupWindow = new PopupWindow(view, FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT, true);
        PopupWindow popupWindow = new PopupWindow(view, width, FrameLayout.LayoutParams.WRAP_CONTENT, true);

        ViewGroup rlParent = view.findViewById(R.id.llParent);
        rlParent.setOnClickListener(view1 -> popupWindow.dismiss());

        RecyclerView recyclerView = view.findViewById(R.id.rvMenu);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new CommonAdapter<T>(context, R.layout.popupwindow_menu_item, datas) {
            @Override
            public void convert(RecyclerHolder holder, T item, int position) {
                if (listener != null) {
                    holder.setText(R.id.tvItem, listener.getText(item));
                } else {
                    holder.setText(R.id.tvItem, "");
                }

                holder.getView(R.id.llItem).setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(item, position);
                    }
                    popupWindow.dismiss();
                });
            }
        });
        popupWindow.setAnimationStyle(R.style.popupwindow_anim);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor((v, event) -> {
            // 这里如果返回true的话，touch事件将被拦截
            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            return false;
        });
//        ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
//        popupWindow.setBackgroundDrawable(drawable);
        showPopupWindowLocation(target, popupWindow, Gravity.NO_GRAVITY);
    }

    /**
     * 显示PopupWindow位置
     *
     * @param target
     * @param popupWindow
     */
    public static void showPopupWindowLocation(View target, PopupWindow popupWindow, int gravity) {
//        if (Build.VERSION.SDK_INT >= 24) {
//            Rect visibleFrame = new Rect();
//            target.getGlobalVisibleRect(visibleFrame);
//            int height = target.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
//            popupWindow.setHeight(height);
//        }
        popupWindow.showAsDropDown(target);
    }
}
