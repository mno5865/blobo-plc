package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ElseNode implements JottTree {

    private final BodyNode body;

    public ElseNode(BodyNode body) {
        this.body = body;
    }

    public static ElseNode parseElseNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD || !token.getToken().equals("else")) {
            throw new SyntaxException("else statement must begin with KEYWORD else", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);

        parseToken(TokenType.L_BRACE, tokens);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        parseToken(TokenType.R_BRACE, tokens);

        return new ElseNode(body);
    }

    @Override
    public String convertToJott() {
        String out = "\telse";
        out += "{\n\t";
        out += "\t" + this.body.convertToJott();
        out += "\t}";
        return out;
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
