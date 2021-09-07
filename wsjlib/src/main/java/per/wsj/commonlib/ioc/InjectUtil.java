package per.wsj.commonlib.ioc;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class InjectUtil {

    /**
     * 绑定
     * @param activity
     */
    public static void bind(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        //处理类
        processType(activity, clazz);
        //遍历所有的字段
        for (Field field : clazz.getDeclaredFields()) {
            //处理字段
            processField(activity, field);
        }
        //遍历所有的方法
        for (Method method : clazz.getDeclaredMethods()) {
            //处理方法
            processMethod(activity, method);
        }
    }

    /**
     * 解绑
     *
     * @param activity
     */
    public static void unbind(Activity activity) {
        try {
            Class<? extends Activity> clazz = activity.getClass();
            //遍历所有的字段
            for (Field field : clazz.getDeclaredFields()) {
                Log.e("InjectUtil","field=" + field.getName() + "\t" + field.getType());
                //将所有字段置空
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(activity, null);
                field.setAccessible(accessible);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理类注解
     */
    private static void processType(Activity activity, Class<? extends Activity> clazz) {
        List<Class<? extends Annotation>> annoList = new ArrayList<>();
        annoList.add(IContentView.class);

        for (Class<? extends Annotation> annotationType : annoList) {
            //判断是否存在IContentView注解
            if (clazz.isAnnotationPresent(annotationType)) {
                dispatchType(activity, clazz, annotationType);
            }
        }
    }

    /**
     * 分发类注解
     */
    private static void dispatchType(Activity activity, Class<? extends Activity> clazz, Class<? extends Annotation> annotationType) {
        if (annotationType == IContentView.class) {
            IContentView annotation = clazz.getAnnotation(IContentView.class);
            int value = annotation.value();
            activity.setContentView(value);
        }
    }

    /**
     * 处理字段注解
     */
    private static void processField(Activity activity, Field field) {
        List<Class<? extends Annotation>> annoList = new ArrayList<>();
        annoList.add(IView.class);
//        annoList.add(IColor.class);
        annoList.add(IString.class);

        for (Class<? extends Annotation> annotationType : annoList) {
            if (field.isAnnotationPresent(annotationType)) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                dispatchFiled(activity, field, annotationType);
                field.setAccessible(accessible);
            }
        }
    }

    /**
     * 分发字段注解
     */
    private static void dispatchFiled(Activity activity, Field field, Class<?> annotationType) {
        try {
            if (annotationType == IView.class) {
                IView anno = field.getAnnotation(IView.class);
                int value = anno.value();
                field.set(activity, activity.findViewById(value));
            }
            if (annotationType == IString.class) {
                IString anno = field.getAnnotation(IString.class);
                int value = anno.value();
                field.set(activity, activity.getString(value));
            }
//            if (annotationType == IColor.class) {
//                IColor anno = field.getAnnotation(IColor.class);
//                int value = anno.value();
//                field.set(activity, activity.getResources().getColor(value));
//            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理方法注解
     */
    private static void processMethod(Activity activity, Method method) {
        List<Class<? extends Annotation>> annoList = new ArrayList<>();
        annoList.add(IClick.class);

        for (Class<? extends Annotation> annotationType : annoList) {
            //判断是否存在IContentView注解
            if (method.isAnnotationPresent(annotationType)) {
                dispatchMethod(activity, method, annotationType);
            }
        }
    }

    /**
     * 分发方法注解
     */
    private static void dispatchMethod(Activity activity, Method method, Class<? extends Annotation> annotationType) {
        try {
            if (annotationType == IClick.class) {
                IClick annotation = method.getAnnotation(IClick.class);
                // 获取注解里面的id
                int[] ids = annotation.value();
                // 当有注解的时候生成动态代理
                ClassLoader classLoader = View.OnClickListener.class.getClassLoader();
                Class<?>[] interfaces = {View.OnClickListener.class};
                Object proxy = Proxy.newProxyInstance(classLoader, interfaces, new DynaHandler(activity, method));
                for (int id : ids) {
                    View view = activity.findViewById(id);
                    Method onClickMethod = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                    // 调用setOnClickListener方法回调在动态类里面
                    onClickMethod.invoke(view, proxy);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
