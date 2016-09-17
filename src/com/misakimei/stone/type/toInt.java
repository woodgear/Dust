package com.misakimei.stone.type;

import com.misakimei.stone.ArrayEnv;
import com.misakimei.stone.StringLiteral;

/**
 * Created by 18754 on 2016/9/14.
 */
public class toInt {
    public static int m(ArrayEnv env, Object obj){return m(obj);}

    private static int m(Object obj) {
       if (obj instanceof String){
           return Integer.parseInt((String) obj);
       }else if (obj instanceof Integer){
           return ((Integer) obj).intValue();
       }else {
           throw new NumberFormatException(obj.toString());
       }
    }
}
