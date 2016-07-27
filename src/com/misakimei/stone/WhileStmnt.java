package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/27.
 */
public class WhileStmnt extends ASTList {

    public WhileStmnt(List<ASTree> lis) {
        super(lis);
    }
    public ASTree condition(){return child(0);}
    public ASTree body(){return child(1);}
    public String toString(){
        return "(while "+condition()+" "+body()+")";
    }
}
