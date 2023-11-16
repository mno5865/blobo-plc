package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncReturnNode implements JottTree {
    private final TypeNode type;

    public FuncReturnNode(TypeNode type) {
        this.type = type;
    }

    public static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Function definition requires a return type", token.getFilename(), token.getLineNum());
        }
        if (token.getToken().equals("Void")) {
            tokens.remove(0);
            return new FuncReturnNode(null);
        }
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        return new FuncReturnNode(typeNode);
    }

    @Override
    public String convertToJott() {
        if (this.type == null) {
            return "Void";
        }
        return type.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        if (this.type == null) {
            return "void";
        }
        return this.type.convertToJava(className);
    }

    @Override
    public String convertToC() {
        if (this.type == null) {
            return "void";
        }
        return type.convertToC();
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public void validateTree() throws SemanticException {
        if (!returnTypeExists()) return;
        type.validateTree();
    }

    public boolean returnTypeExists() {
        return this.type != null;
    }

    public String getReturnType() {
        if (type == null) return "Void";
        return type.getType();
    }
}
