package com.misakimei.stone;
import com.misakimei.stone.parser.Parser;

import static com.misakimei.stone.parser.Parser.rule;
/**
 * Created by 18754 on 2016/7/31.
 */
public class ClassParse extends ClosureParse {

    /**
     * member       :   def|simple
     * class_body   :   "{" [member] {(";"|EOL) [member]} "}"
     * def_class    :   "class" IDENTIFIER ["extends" IDENTIFIER] class_body
     * postfix      :   "." IDENTIFIER | "(" [args] ")"
     * program      :   [defclass | def |statement] (";"|EOL)
     *
     */

    Parser member=rule().or(def,simple);
    Parser classbody=rule(ClassBody.class)
            .sep("{")
            .option(member)
            .repeat(rule().sep(";",Token.EOL).option(member))
            .sep("}");
    Parser defclass=rule(ClassStmnt.class)
            .sep("class")
            .identifier(reserved)
            .option(rule().sep("extends").identifier(reserved))
            .ast(classbody);

    public ClassParse(){
        postfix.insertChoice(rule(Dot.class).sep(".").identifier(reserved));
        program.insertChoice(defclass);
    }
}
