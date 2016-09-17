package com.misakimei.stone.type;

import com.misakimei.stone.ASTree;
import com.misakimei.stone.StoneExcetion;

/**
 * Created by 18754 on 2016/9/12.
 */
public class TypeException extends StoneExcetion {
    public TypeException(String s) {
        super(s);
    }

    public TypeException(String msg, ASTree t) {
        super(msg, t);
    }
}
