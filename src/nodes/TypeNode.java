package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class TypeNode implements JottTree {

    private final String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public static TypeNode parseTypeNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        String tokenString = token.getToken();
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Type must be KEYWORD Double, Integer, String, or Boolean", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);
        return switch (tokenString) {
            case "Double" -> new TypeNode("Double");
            case "Integer" -> new TypeNode("Integer");
            case "String" -> new TypeNode("String");
            case "Boolean" -> new TypeNode("Boolean");
            default -> throw new SyntaxException("Type must be KEYWORD Double, Integer, String, or Boolean",
                    token.getFilename(), token.getLineNum());
        };
    }

    @Override
    public String convertToJott() {
        return this.type;
    }

    @Override
    public String convertToJava(String className) {
        return switch (this.type) {
            case "Double" -> "double";
            case "Integer" -> "int";
            case "String" -> "String";
            case "Boolean" -> "boolean";
            case "Void" -> "void";
            default -> "";
        };
    }

    @Override
    public String convertToC() {
        return switch (this.type) {
            case "Double" -> "float";
            case "Integer" -> "int";
            case "String" -> "char*";
            case "Boolean" -> "bool"; // Todo add to imports: #import <stdbool.h>
            case "Void" -> "void";
            default -> "";
        };
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public void validateTree() {
    }

    public static boolean validType(String type) {
        if (type == null) return false;
        return switch (type) {
            case "Double", "Integer", "String", "Boolean", "Void" -> true; // NOTE sometimes void is an invalid value, be careful
            default -> false;
        };
    }

    public String getType() {
        return type;
    }
}
