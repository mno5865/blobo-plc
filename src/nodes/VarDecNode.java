package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements BodyStmtNode {

    private final Token type;
    private final IDNode id;

    public VarDecNode(Token type, IDNode id) {
        this.type = type;
        this.id = id;
    }

    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("var_dec first element must be type", token.getFilename(), token.getLineNum());
        }
        Token type = tokens.remove(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("var_dec second element must be an id", token.getFilename(), token.getLineNum());
        }
        IDNode id = IDNode.parseIDNode(tokens);
        return new VarDecNode(type, id);
    }

    @Override
    public String convertToJott() {
        return this.type.getToken() + this.id.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
