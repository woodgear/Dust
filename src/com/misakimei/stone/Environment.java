package com.misakimei.stone;

import java.util.Objects;

/**
 * Created by 18754 on 2016/7/28.
 */
public interface Environment {
    void put(String name,Object value);
    Object get(String name);
}
