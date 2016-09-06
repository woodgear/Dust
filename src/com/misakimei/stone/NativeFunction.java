package com.misakimei.stone;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 18754 on 2016/7/30.
 */
public class NativeFunction{

    protected String name;
    protected Method method;
    protected int   numparam;
    public NativeFunction(String name, Method m){
        this.name=name;
        this.method=m;
        numparam=m.getParameterTypes().length;//方法所需的参数的长度
    }
    public int numParams() {
        return numparam;
    }

    //实际上arguments并没有什么卵用
    public Object invoke(Object[] arg, Arguments arguments) {
        try {
         return    method.invoke(null,arg);
        } catch (Exception e){
            throw new StoneExcetion("调用原生JAVA函数"+name+"失败",arguments);
        }
    }

    @Override
    public String toString() {
        return "<NativeFunction "+hashCode()+">";
    }
}
