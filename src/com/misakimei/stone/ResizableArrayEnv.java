package com.misakimei.stone;

import java.util.Arrays;

/**
 * Created by 18754 on 2016/8/1.
 */
public class ResizableArrayEnv extends ArrayEnv {
    private Symbols names;
    public ResizableArrayEnv() {
        super(10, null);
        names=new Symbols();
    }

    @Override
    public Symbols symbol() {
        return names;
    }

    @Override
    public Object get(String name) {
        Integer i=names.find(name);
        if (i==null){
            if (outer==null){
                return null;
            }else {
                return outer.get(name);
            }
        }else {
            return values[i];
        }
    }

    @Override
    public void put(String name, Object value) {
        Environment e=where(name);
        if (e==null){
            e=this;//如果在找不到外部 自身有此变量的定义 那么将他放入自身中
        }
        e.putNew(name,value);
    }

    @Override
    public void putNew(String name, Object value) {
        int index=names.putNew(name);
        assign(index,value);
    }


    @Override
    public Environment where(String name) {
        if (names.find(name)!=null){
            return this;
        }else if (outer==null){
            return null;
        }else {
            return outer.where(name);
        }
    }

    @Override
    public void put(int nest, int index, Object value) {
        if (nest==0){
            assign(index,value);
        }else {
            super.put(nest,index,value);
        }
    }

    private void assign(int index, Object value) {
        if (index>=values.length){
            int newlen=values.length*2;
            if (index>=newlen){
                newlen=index+1;
            }
            values= Arrays.copyOf(values,newlen);
        }
        values[index]=value;
    }
}
