package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BasicParsers {
    public static void parseToken(TokenType type, ArrayList<Token> tokens) throws SyntaxException{
        Token token = tokens.get(0);
        if (token.getTokenType() != type) {
            String out = "Next token must be '";
            switch (type){//todo error descriptions
                case COLON:
                    out += ":";
                    break;
                case STRING:
                    out += "string";
                    break;
                case L_BRACKET:
                    out += "[";
                    break;
                case R_BRACKET:
                    out += "]";
                    break;
                case L_BRACE:
                    out += "{";
                    break;
                case R_BRACE:
                    out += "}";
                    break;
                case FC_HEADER:
                    out += "::";
                    break;

            }
            out += "'";
            throw new SyntaxException(out, token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);
    }

}
