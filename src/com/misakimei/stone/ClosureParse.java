package com.misakimei.stone;
import static com.misakimei.stone.parser.Parser.rule;
/**
 * Created by 18754 on 2016/7/29.
 * 支持闭包的语法分析器
 */
public class ClosureParse extends FuncParse {
    public  ClosureParse(){
        primary.insertChoice(rule(Fun.class).sep("fun").ast(paramList).ast(block));
    }

}
