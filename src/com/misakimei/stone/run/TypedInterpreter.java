package com.misakimei.stone.run;

import com.misakimei.stone.*;
import com.misakimei.stone.tool.Log;
import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.type.TypedNatives;
import com.misakimei.stone.type.TypedParse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by 18754 on 2016/9/14.
 */
public class TypedInterpreter {

    public static void main(String[] args) {
        TypeEnv te=new TypeEnv();
        run(new TypedParse(),new TypedNatives(te).environment(new ResizableArrayEnv()),te,"./data/typecheck.stone");
    }
    private static void run(BasicParser bp, Environment environment, TypeEnv te,String path) {
        try {
            Lexer lex = new Lexer(new FileReader(new File(path)));
            while (lex.peek(0)!= Token.EOF){
                ASTree asTree=bp.parse(lex);
                asTree.lookup(environment.symbol());
                TypeInfo type=asTree.typecheck(te);
                Object r=asTree.eval(environment);
                Log.d("==> "+r+" : "+type);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
