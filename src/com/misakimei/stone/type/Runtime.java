package com.misakimei.stone.type;

import com.misakimei.stone.ArrayEnv;

/**
 * Created by 18754 on 2016/9/17.
 */
public class Runtime {
    public static int eq(Object a,Object b){
        if (a==null){
            return b==null?1:0;
        }else {
            return a.equals(b)?1:0;
        }
    }
    public static Object plus(Object a,Object b){
        if (a instanceof Integer&&b instanceof Integer){
            return ((Integer) a).intValue()+((Integer) b).intValue();
        }else {
            return a.toString()+b.toString();
        }
    }
    public static int writeInt(ArrayEnv env,int index,int val){
        env.put(0,index,val);
        return val;
    }
    public static String writeString(ArrayEnv env,int index,String val){
        env.put(0,index,val);
        return val;
    }
    public static Object writeAny(ArrayEnv env,int index,Object  val){
        env.put(0,index,val);
        return val;
    }


}
