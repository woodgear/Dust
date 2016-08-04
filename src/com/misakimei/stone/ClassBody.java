package com.misakimei.stone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18754 on 2016/7/31.
 */
public class ClassBody extends ASTList {
    public ClassBody(List<ASTree> lis) {
        super(lis);
    }

    @Override
    public Object eval(Environment env) {
         for (ASTree t:this){
             if (!(t instanceof DefStmnt)){
                 t.eval(env);
             }
        }
        return null;
    }


    public void lookup(Symbols newsyms, Symbols methodNames, Symbols fieldName, ArrayList<DefStmnt> methods) {
        for (ASTree t:this){
            if (t instanceof DefStmnt){
                DefStmnt def= (DefStmnt) t;
                int oldsize=methodNames.size();
                int i=methodNames.putNew(def.name());
                if (i>=oldsize){
                    methods.add(def);
                }else {
                    methods.set(i,def);
                }
                def.lookupAsMethod(fieldName);
            }else {
                t.lookup(newsyms);
            }
        }
    }
}
