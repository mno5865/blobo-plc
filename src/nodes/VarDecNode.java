package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements JottTree {
    private Token type;
    private IDNode id;
    public VarDecNode(Token type, IDNode id){
        this.type = type;
        this.id = id;
    }

    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo syntax exception
        }
        Token type = tokens.remove(0);
        IDNode id = IDNode.parseIDNode(tokens);
        return new VarDecNode(type, id);
    }

    @Override
    public String convertToJott() {
        String out = "";
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
