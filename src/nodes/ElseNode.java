package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ElseNode implements JottTree {

    private BodyNode body;

    public ElseNode(BodyNode body) {
        this.body = body;
    }

    public static ElseNode parseElseNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Next token must be 'id_keyword'", token.getFilename(), token.getLineNum());
        } else if (!token.getToken().equals("else")) {
            throw new SyntaxException("Next token must be an id_keyword of else", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);

        parseToken(TokenType.L_BRACE, tokens);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        parseToken(TokenType.R_BRACE, tokens);

        return new ElseNode(body);
    }

    @Override
    public String convertToJott() {
        String out = "else";
        out += "{";
        out += this.body.convertToJott();
        out += "}";
        return out;
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
