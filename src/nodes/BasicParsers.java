package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BasicParsers {
    public static void parseToken(TokenType type, ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.isEmpty()){
            throw new SyntaxException("Expected token, got EOF", "", -1);
        }
        Token token = tokens.get(0);
        if (token.getTokenType() != type) {
            String out = "Next token must be '";
            //todo add more error descriptions
            switch (type) {
                case COLON -> out += ":";
                case COMMA -> out += ",";
                case STRING -> out += "string";
                case L_BRACKET -> out += "[";
                case R_BRACKET -> out += "]";
                case L_BRACE -> out += "{";
                case R_BRACE -> out += "}";
                case FC_HEADER -> out += "::";
                case ASSIGN -> out += "=";
                case SEMICOLON -> out += ";";
            }
            out += "'";
            throw new SyntaxException(out, token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);
    }
}
