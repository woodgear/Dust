package com.misakimei.stone.type;

import com.misakimei.stone.ArrayEnv;

/**
 * Created by 18754 on 2016/9/14.
 */
public class currenttime {
    public static long starttime=System.currentTimeMillis();
    public static int m(ArrayEnv env){
        return m();
    }

    private static int m() {
        return (int) (System.currentTimeMillis()-starttime);
    }

}
