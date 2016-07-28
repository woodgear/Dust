package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class NegativeExpr extends ASTList {
    public NegativeExpr(List<ASTree> lis) {
        super(lis);
    }
    public  ASTree operand(){
        return child(0);
    }

    @Override
    public String toString() {
        return "-"+operand();
    }

    @Override
    public Object eval(Environment env) {
        Object v=operand().eval(env);
        if (v instanceof Integer){
            return new Integer(-((Integer) v).intValue());
        }
        else
            throw new StoneExcetion("无法对此类型使用-号",this);

    }
}
