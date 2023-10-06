package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class FuncDefParamTailNode implements JottTree {
    private IDNode paramName;
    private TypeNode paramType;

    public FuncDefParamTailNode(IDNode paramName, TypeNode paramType){
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public static FuncDefParamTailNode parseFuncDefParamTailNode(ArrayList<Token> tokens) throws SyntaxException{
        parseToken(TokenType.COMMA, tokens);
        IDNode paramName = IDNode.parseIDNode(tokens);
        parseToken(TokenType.COLON, tokens);
        TypeNode paramType = TypeNode.parseTypeNode(tokens);

        return new FuncDefParamTailNode(paramName, paramType);
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
        String out = ",";
        out += this.paramType.convertToJava(""); //todo className
        out += " ";
        out += this.paramName.convertToJava(""); //todo className
        return out;
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
