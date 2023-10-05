package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncDefParamTailNode implements JottTree {
    private IDNode paramName;
    private TypeNode paramType;

    public FuncDefParamTailNode(IDNode paramName, TypeNode paramType){
        this.paramName = paramName;
        this.paramType = paramType;
    }

    @Override
    public String convertToJott() {
        String out = ",";
        out += this.paramName.convertToJott();
        out += ":";
        out += this.paramType.convertToJott();
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
