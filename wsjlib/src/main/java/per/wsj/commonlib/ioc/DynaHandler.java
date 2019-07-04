package per.wsj.commonlib.ioc;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynaHandler implements InvocationHandler {

    private Activity activity;
    private Method method;

    public DynaHandler(Activity activity, Method method) {
        this.activity = activity;
        this.method = method;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return this.method.invoke(activity, objects);
    }
}
