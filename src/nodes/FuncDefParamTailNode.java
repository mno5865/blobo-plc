package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class FuncDefParamTailNode implements JottTree {
    private final IDNode paramName;
    private final TypeNode paramType;

    public FuncDefParamTailNode(IDNode paramName, TypeNode paramType) {
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public static FuncDefParamTailNode parseFuncDefParamTailNode(ArrayList<Token> tokens) throws SyntaxException {
        parseToken(TokenType.COMMA, tokens);
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Function definition parameter expects Type KEYWORD following comma", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
        IDNode paramName = IDNode.parseIDNode(tokens);
        parseToken(TokenType.COLON, tokens);
        TypeNode paramType = TypeNode.parseTypeNode(tokens);

        return new FuncDefParamTailNode(paramName, paramType);
    }

    @Override
    public String convertToJott() {
        String out = ", ";
        out += this.paramName.convertToJott();
        out += ":";
        out += this.paramType.convertToJott();
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = ", ";
        out += this.paramType.convertToJava("");
        out += " ";
        out += this.paramName.convertToJava("");
        return out;
    }

    @Override
    public String convertToC() {
        String out = ", ";
        out += paramType.convertToC();
        out += " ";
        out += paramName.convertToC();
        return out;
    }

    @Override
    public String convertToPython() {
        String out = ", ";
        out += paramName.convertToPython();
        return out;
    }

    @Override
    public boolean validateTree() { //TODO VALIDATE TREE FOR FUNC DEF PARAM TAIL NODE
        boolean valid = false;
        paramName.validateTree();
        paramType.validateTree();
        return valid;
    }
}
