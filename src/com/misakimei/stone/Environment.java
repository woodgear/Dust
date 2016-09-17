package com.misakimei.stone;

import com.misakimei.stone.type.JavaLoader;

import java.util.Objects;

/**
 * Created by 18754 on 2016/7/28.
 */
public interface Environment {
    void put(String name, Object value);

    Object get(String name);

    void putNew(String name, Object value);//因为在嵌套作用域下有可能重名 所以有一个putNew

    Environment where(String name);//作用域链来检测name在哪里

    void setOuter(Environment env);//设置外部作用域

    Symbols symbol();

    void put(int nest, int index, Object val);

    Object get(int nest, int index);

    JavaLoader javaLoader();

}
