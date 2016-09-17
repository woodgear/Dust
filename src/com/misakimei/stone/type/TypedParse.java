package com.misakimei.stone.type;

import com.misakimei.stone.FuncParse;
import com.misakimei.stone.parser.Parser;

import static com.misakimei.stone.parser.Parser.rule;

/**
 * Created by 18754 on 2016/9/12.
 */
public class TypedParse extends FuncParse{
    Parser typetag=rule(TypeTag.class).sep(":").identifier(reserved);
    Parser variable=rule(VarStmnt.class).sep("var").identifier(reserved).maybe(typetag).sep("=").ast(expr);

    public TypedParse(){
        reserved.add(":");
        param.maybe(typetag);
        def.reset().sep("def").identifier(reserved).ast(paramList)
                .maybe(typetag).ast(block);

        statement.insertChoice(variable);
    }

}
