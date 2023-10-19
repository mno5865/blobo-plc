package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Objects;

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
