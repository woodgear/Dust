package com.misakimei.stone;

import com.misakimei.stone.type.JavaLoader;
import com.sun.istack.internal.Nullable;
import jdk.nashorn.internal.objects.annotations.Where;

import java.util.HashMap;

/**
 * Created by 18754 on 2016/7/29.
 */
public class NestEnv implements Environment {
    protected HashMap<String, Object> values;
    private Environment outer;

    public NestEnv(Environment out) {
        values=new HashMap<>();
        this.outer = out;
    }

    //可以更换外部作用域
    public void setOuter(Environment out){
        this.outer=out;
    }

    @Override
    public Symbols symbol() {
        return null;
    }

    @Override
    public void put(int nest, int index, Object val) {

    }

    @Override
    public Object get(int nest, int index) {
        return null;
    }

    @Override
    public JavaLoader javaLoader() {
        return null;
    }

    public NestEnv() {
        this(null);
    }


    @Override
    public void put(String name, Object value) {
        Environment e= where(name);
        if (e==null){
            e=this;
        }
        e.putNew(name,value);
    }

    //检测变量在什么环境中
    @Override
    public Environment where(String name) {
        if (values.get(name)!=null){
            return this;
        }else if (outer!=null){
            return outer.where(name);
        }
        return null;
    }

    public void putNew(String name,Object value){
        values.put(name,value);
    }

    @Nullable
    @Override
    public Object get(String name) {
       Object v= values.get(name);
        //内部环境找不到去外部找 外部找不到 就return null
        if (v==null&&outer!=null){
            return outer.get(name);
        }else {
            return v;
        }
    }
}
