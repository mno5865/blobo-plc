package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IDNode implements JottTree {
    private Token id_name;

    private IDNode(Token ID) {
        this.id_name = ID;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo syntax exception
        }
        return new IDNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.id_name.getToken();
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