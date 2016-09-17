package com.misakimei.stone.type;

import com.misakimei.stone.ASTLeaf;
import com.misakimei.stone.ASTList;
import com.misakimei.stone.ASTree;
import com.misakimei.stone.Environment;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 18754 on 2016/9/12.
 */
public class TypeTag extends ASTList {

    public static final String UNDEF="<Undef>";

    public TypeTag(List<ASTree> lis) {
        super(lis);
    }

    public String type(){
        if (numChildren()>0){
            return ((ASTLeaf)child(0)).token().getText();
        }else {
            return UNDEF;
        }
    }

    @Override
    public String toString() {
        return ":"+type();
    }
}
