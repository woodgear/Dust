package com.misakimei.stone.tool;


/**
 * Created by 18754 on 2016/7/27.
 */
public class Log {

    public static void d(String msg){
        System.out.println(msg);
    }
    public static void e(String msg){
        System.out.println(msg);
    }

    public static void d(Object o) {
        if (o==null){
            d("null");
        }else {
            d(o.toString());
        }
    }
}
