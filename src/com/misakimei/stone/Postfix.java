package com.misakimei.stone;

import java.util.List;

/**
 * Created by 18754 on 2016/7/29.
 */
public abstract class Postfix extends ASTList {
    public Postfix(List<ASTree> lis) {
        super(lis);
    }

    public abstract Object eval(Environment env,Object value);
}
