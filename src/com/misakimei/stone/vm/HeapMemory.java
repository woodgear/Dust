package com.misakimei.stone.vm;

/**
 * Created by 18754 on 2016/8/31.
 */
public interface HeapMemory {
    Object read(int index);
    void  write(int index,Object v);
}
