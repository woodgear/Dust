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

    private void append(Environment env, String name, Class<?> clazz, String methodname, Class<?>... params) {
        Method m = null;
        try {
            m = clazz.getMethod(methodname, params);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        env.put(name, new NativeFunction(methodname, m));
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

    private static long startTime = System.currentTimeMillis();

    public static int currentTime() {
        return (int) (System.currentTimeMillis() - startTime);
    }


}
