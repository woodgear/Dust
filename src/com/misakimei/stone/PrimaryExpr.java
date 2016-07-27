package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<ASTree> lis) {
        super(lis);
    }
    public static ASTree create(List<ASTree> c){
        return c.size()==1?c.get(0):new PrimaryExpr(c);
    }
}
