package com.misakimei.stone.type;

import com.misakimei.stone.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by 18754 on 2016/9/17.
 */
public class JavaFunction extends Function {
    protected String className;
    protected Class<?>clazz;

    public JavaFunction(String name,String method,JavaLoader loader){
        super(null,null,null);
        className=name;
        clazz=loader.load(className,method);
    }

    public static String className(String name){
        return name;
    }

    public Object invoke(Object[]args){
        try {
            return clazz.getDeclaredMethods()[0].invoke(null,args);
        } catch (Exception e) {
            throw new StoneExcetion(e.getMessage());
        }
    }

    public JavaFunction(ParamterList paramters, BlockStment body, Environment env) {
        super(paramters, body, env);
    }
}
