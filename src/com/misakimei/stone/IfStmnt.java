package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class IfStmnt extends ASTList {

    public IfStmnt(List<ASTree> lis) {
        super(lis);
    }
    public ASTree condition(){return child(0);}
    public ASTree thenBlock(){return child(1);}
    public ASTree elseBlock(){
        return numChildren()>2?child(2):null;
    }

    @Override
    public String toString() {
        return "if "+condition()+" "+thenBlock()+" else "+elseBlock()+")";
    }
}
