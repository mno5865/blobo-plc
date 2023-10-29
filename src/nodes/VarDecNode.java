package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class VarDecNode implements BodyStmtNode {
    //todo refactor, we started using type node to represent type so type should probably be a type node and use its validateTree

    private final Token type;
    private final IDNode id;

    public VarDecNode(Token type, IDNode id) {
        this.type = type;
        this.id = id;
    }

    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Variable declaration must begin Type KEYWORD", token.getFilename(), token.getLineNum());
        }
        Token type = tokens.remove(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Variable declaration Type KEYWORD expects to be followed by ID", token.getFilename(), token.getLineNum());
        }
        IDNode id = IDNode.parseIDNode(tokens);
        parseToken(TokenType.SEMICOLON, tokens);
        return new VarDecNode(type, id);
    }

    @Override
    public String convertToJott() {
        return this.type.getToken() + " " + this.id.convertToJott() + ";";
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
    public boolean validateTree() { //TODO VALIDATE TREE FOR VARIABLE DECLARATION NODE
        boolean valid = true;
        //todo type -> typenode -> validateTree()
        valid = valid && id.validateTree();
        return valid;
    }
}
