package com.misakimei.stone;

import com.misakimei.stone.type.JavaLoader;

/**
 * Created by 18754 on 2016/8/1.
 * 数组环境与hash环境之间的区别是
 * hash环境通过 hashmap.get(String name) O(1)
 * 数组环境用   Object[]values values[name.index] 无须查询
 */
public class ArrayEnv implements Environment {
    protected Environment outer;
    protected Object[] values;
    protected JavaLoader javaLoader = new JavaLoader();

    public ArrayEnv(int size, Environment out) {
        this.outer = out;
        values = new Object[size];
    }

    public Object get(int nest, int index) {
        if (nest == 0) {
            return values[index];
        } else if (outer == null) {
            return null;
        } else {
            return ((ArrayEnv) outer).get(nest - 1, index);
        }
    }

    @Override
    public JavaLoader javaLoader() {
        return javaLoader;
    }

    public void put(int nest, int index, Object value) {
        if (nest == 0) {
            values[index] = value;
        } else if (outer == null) {
            throw new StoneExcetion("已经没有外部环境了");
        } else {
            ((ArrayEnv) outer).put(nest - 1, index, value);
        }
    }

    @Override
    public void put(String name, Object value) {
        error(name);
    }

    public Symbols symbol() {
        throw new StoneExcetion("没有符号表");
    }


    @Override
    public Object get(String name) {
        error(name);
        return null;
    }

    private void error(String name) {
        throw new StoneExcetion("数组环境无法通过名字来访问");
    }

    @Override
    public void putNew(String name, Object value) {
        error(name);
    }

    @Override
    public Environment where(String name) {
        error(name);
        return null;
    }

    @Override
    public void setOuter(Environment env) {
        outer = env;
    }
}
