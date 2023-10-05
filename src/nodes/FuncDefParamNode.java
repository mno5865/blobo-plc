package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncDefParamNode implements JottTree {
    private IDNode paramName;
    private TypeNode paramType;
    private ArrayList<FuncDefParamTailNode> paramTail;

    @Override
    public String convertToJott() {
        String out = "";
        if (paramName == null){
            out += this.paramName.convertToJott();
            out += ":";
            out += this.paramType.convertToJott();
            for (FuncDefParamTailNode param : paramTail){
                out += param.convertToJott();
            }
        }
        return out;
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
