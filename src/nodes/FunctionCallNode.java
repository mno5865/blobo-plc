package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionCallNode implements JottTree {
    private IDNode funcName;
    private FuncCallParamNode fcp;

    public FunctionCallNode(IDNode funcName, FuncCallParamNode funcCallParamNode) {
        this.funcName = funcName;
        this.fcp = funcCallParamNode;
    }

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.FC_HEADER) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);
        IDNode funcName = IDNode.parseIDNode(tokens);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);
        FuncCallParamNode funcCallParamNode = FuncCallParamNode.parseFCPN(tokens);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);

        return new FunctionCallNode(funcName, funcCallParamNode);
    }

    @Override
    public String convertToJott() {
        String out = "::";
        out += this.funcName.convertToJott();
        out += "[";
        out += this.fcp.convertToJott();
        out += "]";
        return out;
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
