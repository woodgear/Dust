package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class BinaryExpr extends ASTList {

    public BinaryExpr(List<ASTree> lis) {
        super(lis);
    }
    public ASTree left(){
        return child(0);
    }
    public ASTree right(){
        return child(2);
    }
    public String operator(){
        return ((ASTLeaf)child(1)).token().getText();
    }

}
