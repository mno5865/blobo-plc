package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class VarDecNode implements BodyStmtNode {
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
        return this.type.convertToJott() + " " + this.id.convertToJott() + ";";
    }

    @Override
    public String convertToJava() {
        return this.type.convertToJava() + " " + this.id.convertToJava() + ";";
    }

    @Override
    public String convertToC() {
        return this.type.convertToC() + " " + this.id.convertToC() + ";";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public void validateTree() throws SemanticException {
        type.validateTree();
        id.validateTree();
        SymbolTable.addUndefinedVariable(this.type.getTypeString(), this.id.getName(), id.getToken());
    }
}
