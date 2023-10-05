package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncDefParamTailNode implements JottTree {
    private IDNode paramName;
    private TypeNode paramType;

    public FuncDefParamTailNode(IDNode paramName, TypeNode paramType){
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public static FuncDefParamTailNode parseFuncDefParamTailNode(ArrayList<Token> tokens) throws SyntaxException{
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.COMMA){
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD{
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        IDNode paramName = IDNode.parseIDNode(tokens);

        tokens.get(0);
        if (token.getTokenType() != TokenType.COLON{
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.STRING{
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
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
