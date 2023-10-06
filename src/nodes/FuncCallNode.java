package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class FuncCallNode implements JottTree {
    private IDNode funcName;
    private ParamNode params;

    public FuncCallNode(IDNode funcName, ParamNode ParamNode) {
        this.funcName = funcName;
        this.params = ParamNode;
    }

    public static FuncCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws SyntaxException {
        parseToken(TokenType.FC_HEADER, tokens);
        IDNode funcName = IDNode.parseIDNode(tokens);
        parseToken(TokenType.L_BRACKET, tokens);
        ParamNode ParamNode = nodes.ParamNode.parseParamNode(tokens);
        parseToken(TokenType.R_BRACKET, tokens);

        return new FuncCallNode(funcName, ParamNode);
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
