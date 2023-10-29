package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.List;

import static nodes.BasicParsers.parseToken;

public class FuncCallNode implements ExprNode, BodyStmtNode {

    private final IDNode funcName;
    private final ParamNode params;

    public FuncCallNode(IDNode funcName, ParamNode ParamNode) {
        this.funcName = funcName;
        this.params = ParamNode;
    }

    public static FuncCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws SyntaxException {
        parseToken(TokenType.FC_HEADER, tokens);
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Function call expects to be followed by ID or KEYWORD", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
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
    public boolean validateTree() throws SemanticException { //just checks to make sure the function exists right now
        // todo it should be asmt that checks whether the assignment is valid
        List<String> funcDefinition = new ArrayList<>();
        funcDefinition.add(funcName.getName());
        funcDefinition.addAll(params.getParamTypes());
        boolean valid = SymbolTable.doesFunctionExist(funcDefinition);
        if (!valid) throw new SemanticException("The function " + funcName.getName() + " does not exist", funcName.getToken());
        valid = valid && funcName.validateTree();
        valid = valid && params.validateTree();
        return valid;
    }

    // todo do I need an evaluate for a func call node? I suppose if it for BinaryOperation's evaluate

    @Override
    public String getType() {
        return SymbolTable.getFunctionReturnType();
    }
}
