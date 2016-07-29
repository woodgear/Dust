package com.misakimei.stone;
import static com.misakimei.stone.Parser.rule;

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
    Parser param=rule().identifier(reserved);
    Parser params=rule(ParamterList.class)
                    .ast(param).repeat(rule().sep(",").ast(param));
    Parser paramsList=rule().sep("(").maybe(params).sep(")");
    Parser def=rule(DefStmnt.class)
                .sep("def").identifier(reserved).ast(paramsList).ast(block);
    Parser args=rule(Arguments.class)
            .ast(expr).repeat(rule().sep(",").ast(expr));
    Parser postfix=rule().sep("(").maybe(args).sep(")");


    public FuncParse(){
        reserved.add(")");
        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);
    }
}
