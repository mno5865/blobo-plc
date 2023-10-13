package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class TypeNode implements JottTree {

    private String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public static TypeNode parseTypeNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        String tokenString = token.getToken();
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo syntax exception
        }
        tokens.remove(0);
        return switch (tokenString) {
            case "Double" -> new TypeNode("Double");
            case "Integer" -> new TypeNode("Integer");
            case "String" -> new TypeNode("String");
            case "Boolean" -> new TypeNode("Boolean");
            default -> new TypeNode("");
        };
    }

    @Override
    public String convertToJott() {
        return this.type;
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
