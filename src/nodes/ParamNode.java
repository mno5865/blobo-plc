package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ParamNode implements JottTree {
    ExprNode expr;
    ArrayList<ParamTailNode> paramTail;

    public ParamNode(ExprNode expr, ArrayList<ParamTailNode> paramTail) {
        this.expr = expr;
        this.paramTail = paramTail;
    }

    public static ParamNode parseParamNode(ArrayList<Token> tokens) {
        return null;
    }

    @Override
    public String convertToJott() {
        return null;
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
