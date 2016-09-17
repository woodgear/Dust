package com.misakimei.stone.type;

import com.misakimei.stone.ArrayEnv;

/**
 * Created by 18754 on 2016/9/14.
 */
public class print {
    public static int m(ArrayEnv env, Object obj){return m(obj);}

    private static int m(Object obj) {
        System.out.println(obj.toString());
        return 0;
    }

}
