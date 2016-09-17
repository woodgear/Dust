package com.misakimei.stone;
import com.misakimei.stone.parser.Parser;

import static com.misakimei.stone.parser.Parser.rule;

/**
 * Created by 18754 on 2016/7/29.
 */
public class FuncParse extends BasicParser {
    /**
     * param        :   IDENTIFER
     * params       :   param {",",param}
     * param_list   :   "(" [param] ")"
     * def          :   "def" IDENTIFER param_list block
     * args         :   expr {"," expr}
     * postfix      :   "(" [args] ")"
     * primary      :   ( "(" expr ")" |NUMBER|IDENTIFER|STRING) {postfix}
     * simple       :   expr [args]
     * program      :   [def|statement](";"|EOL)
     */
    protected Parser param=rule().identifier(reserved);
    Parser params=rule(ParamterList.class)
                    .ast(param).repeat(rule().sep(",").ast(param));
    protected Parser paramList =rule().sep("(").maybe(params).sep(")");
    protected Parser def=rule(DefStmnt.class)
                .sep("def").identifier(reserved).ast(paramList).ast(block);
    Parser args=rule(Arguments.class)
            .ast(expr).repeat(rule().sep(",").ast(expr));
    Parser postfix=rule().sep("(").maybe(args).sep(")");


    Parser elements=rule(ArrayLiteral.class).ast(expr).repeat(rule().sep(",").ast(expr));
    public FuncParse(){
        reserved.add(")");
        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);

        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
}
