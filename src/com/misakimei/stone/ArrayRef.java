package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;

import java.util.List;
import java.util.Objects;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ArrayRef extends Postfix {
    public ArrayRef(List<ASTree>c){
        super(c);
    }
    public ASTree index(){
        return child(0);
    }

    @Override
    public String toString() {
        return "["+index()+"]";
    }

    @Override
    public Object eval(Environment env, Object value) {
        if (value instanceof Object[]){
            Object index=index().eval(env);
            if (index instanceof Integer){
                return ((Object[])value)[(Integer) index];
            }
        }
        throw new StoneExcetion("无法访问此数组");
    }

}
