package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class FuncDefParamNode implements JottTree {
    private IDNode paramName;
    private TypeNode paramType;
    private ArrayList<FuncDefParamTailNode> paramTail;

    public FuncDefParamNode(IDNode paramName, TypeNode paramType, ArrayList<FuncDefParamTailNode> paramTail) {
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramTail = paramTail;
    }

    public static FuncDefParamNode parseFuncDefParamNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.R_BRACKET) {
            if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
                throw new SyntaxException("FuncDefParamNode first element must be id", token.getFilename(),
                        token.getLineNum());
            }
            IDNode paramName = IDNode.parseIDNode(tokens);
            parseToken(TokenType.COLON, tokens);
            TypeNode paramType = TypeNode.parseTypeNode(tokens);
            ArrayList<FuncDefParamTailNode> paramTail = new ArrayList<>();

            token = tokens.get(0);
            while (token.getTokenType() != TokenType.R_BRACKET) {
                paramTail.add(FuncDefParamTailNode.parseFuncDefParamTailNode(tokens));
                token = tokens.get(0);
            }
            return new FuncDefParamNode(paramName, paramType, paramTail);
        }
        return new FuncDefParamNode(null, null, null);
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (paramName != null) {
            out += this.paramName.convertToJott();
            out += ":";
            out += this.paramType.convertToJott();
            for (FuncDefParamTailNode param : paramTail) {
                out += param.convertToJott();
            }
        }
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        if (paramName != null) {
            out += this.paramType.convertToJava("");
            out += " ";
            out += this.paramName.convertToJava("");
            for (FuncDefParamTailNode param : paramTail) {
                out += param.convertToJava("");
            }
        }
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        if (paramName != null) {
            out += paramType.convertToC();
            out += " ";
            out += paramName.convertToC();
            for (FuncDefParamTailNode param : paramTail) {
                out += param.convertToC();
            }
        }

        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        if (paramName != null) {
            out += paramName.convertToPython();
            for (FuncDefParamTailNode param : paramTail) {
                out += param.convertToPython();
            }
        }
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
