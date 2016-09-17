package com.misakimei.stone;

import com.misakimei.stone.type.TypeEnv;
import com.misakimei.stone.type.TypeException;
import com.misakimei.stone.type.TypeInfo;
import com.misakimei.stone.vm.Code;

import java.util.ArrayList;
import java.util.List;

import static com.misakimei.stone.type.ToJava.*;
import static com.misakimei.stone.vm.Opcode.BCONST;
import static com.misakimei.stone.vm.Opcode.encodeRegister;

/**
 * Created by 18754 on 2016/7/27.
 */
public class BlockStment extends ASTList {
    public BlockStment(List<ASTree> lis) {
        super(lis);
    }

    TypeInfo type;

    @Override
    public Object eval(Environment env) {
        Object result = 0;
        for (ASTree ast : this) {
            if (!(ast instanceof NULLStmnt)) {
                result = ast.eval(env);
            }
        }
        return result;
    }

    @Override
    public TypeInfo typecheck(TypeEnv tenv) throws TypeException {
        type = TypeInfo.INT;
        for (ASTree a : this) {
            if (!(a instanceof NULLStmnt)) {
                type = a.typecheck(tenv);
            }
        }
        return type;
    }

    @Override
    public String translate(TypeInfo res) {
        ArrayList<ASTree> body = new ArrayList<>();
        for (ASTree t : this) {
            if (!(t instanceof NULLStmnt)) {
                body.add(t);
            }
        }
        StringBuilder code = new StringBuilder();
        if (res != null && body.size() < 1) {
            code.append(returnZero(res));
        } else {
            for (int i = 0; i < body.size(); i++) {
                translateStmnt(code, body.get(i), res, i == body.size() - 1);
            }
        }
        return code.toString();
    }

    protected void translateStmnt(StringBuilder code, ASTree tree, TypeInfo res, boolean last) {
        if (isControlStmnt(tree)) {
            code.append(tree.translate(last ? res : null));
        } else {
            if (last && res != null) {
                code.append(RESULT).append("=").append(transloateExpr(tree, type, res)).append(";\n");
            } else if (isExprStmnt(tree)) {
                code.append(tree.translate(null)).append(";\n");
            } else {
                throw new StoneExcetion("非法的表达式", this);
            }
        }
    }

    protected static boolean isExprStmnt(ASTree tree) {
        if (tree instanceof BinaryExpr) {
            return "=".equals(((BinaryExpr) tree).operator());
        }
        return tree instanceof PrimaryExpr||tree instanceof IfStmnt;
    }

    protected static boolean isControlStmnt(ASTree tree) {
        return tree instanceof BlockStment||tree instanceof IfStmnt||tree instanceof WhileStmnt;
    }

}
