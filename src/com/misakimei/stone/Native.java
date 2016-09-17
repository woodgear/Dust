package com.misakimei.stone;

import java.lang.reflect.Method;

/**
 * Created by 18754 on 2016/7/30.
 */
public class Native {
    public Environment environment(Environment env) {
        appendNative(env);
        return env;
    }

    protected void appendNative(Environment env) {
        append(env, "print", Native.class, "print", Object.class);
        append(env, "length", Native.class, "length", String.class);
        append(env, "toInt", Native.class, "toInt", Object.class);
        append(env, "currentTime", Native.class, "currentTime");
    }

    //具体的增加原生函数的方法
    //将clazz类中的名字叫name参数是params的函数加到env中 名字是methodname
    protected void append(Environment env, String name, Class<?> clazz, String methodname, Class<?>... params) {
        Method m = null;
        try {
            m = clazz.getMethod(methodname, params);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        env.put(name, new NativeFunction(methodname, m));//预先将NativeFunction加入环境中
    }

    public static int print(Object obj) {
        System.out.println("==>"+obj.toString());
        return 0;
    }

    public static int length(String s) {
        return s.length();
    }

    public static int toInt(Object val) {
        if (val instanceof String) {
            return Integer.parseInt((String) val);
        } else if (val instanceof Integer) {
            return ((Integer) val).intValue();
        } else {
            throw new NumberFormatException(val.toString());
        }
    }


    public static int currentTime() {
        return (int) (System.currentTimeMillis());
    }

}
