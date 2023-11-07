package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class VarDecNode implements BodyStmtNode {
    //todo refactor, we started using type node to represent type so type should probably be a type node and use its validateTree

    private final TypeNode type;
    private final IDNode id;

    public VarDecNode(TypeNode type, IDNode id) {
        this.type = type;
        this.id = id;
    }

    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Variable declaration must begin Type KEYWORD", token.getFilename(), token.getLineNum());
        }
        TypeNode type = TypeNode.parseTypeNode(tokens);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Variable declaration Type KEYWORD expects to be followed by ID", token.getFilename(), token.getLineNum());
        }
        IDNode id = IDNode.parseIDNode(tokens);
        parseToken(TokenType.SEMICOLON, tokens);
        return new VarDecNode(type, id);
    }

    @Override
    public String convertToJott() {
        return this.type.getType() + " " + this.id.convertToJott() + ";";
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
    public void validateTree() throws SemanticException {
        type.validateTree();
        id.validateTree();
    }
}
