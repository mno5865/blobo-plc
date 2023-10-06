package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class FuncCallNode implements JottTree {
    private IDNode funcName;
    private FuncCallParamNode params;

    public FuncCallNode(IDNode funcName, FuncCallParamNode funcCallParamNode) {
        this.funcName = funcName;
        this.params = funcCallParamNode;
    }

    public static FuncCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws SyntaxException {
        parseToken(TokenType.FC_HEADER, tokens);
        IDNode funcName = IDNode.parseIDNode(tokens);
        parseToken(TokenType.L_BRACKET, tokens);
        FuncCallParamNode funcCallParamNode = FuncCallParamNode.parseFuncCallParamNode(tokens);
        parseToken(TokenType.R_BRACKET, tokens);

        return new FuncCallNode(funcName, funcCallParamNode);
    }

    @Override
    public String convertToJott() {
        String out = "::";
        out += this.funcName.convertToJott();
        out += "[";
        out += this.params.convertToJott();
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
