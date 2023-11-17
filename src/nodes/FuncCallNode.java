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
    public String convertToJava(String className) { //todo ask scott about all the default functions
        if (this.funcName.getToken().getTokenString().equals("print")) {
            return "System.out.print" + "(" + this.params.convertToJava(className) + ")";
        } else if (this.funcName.getToken().getTokenString().equals("length")) {
            return this.params.convertToJava(className) + ".length";
        } else if (this.funcName.getToken().getTokenString().equals("concat")) {
            return this.params.convertToJavaConcat(className);
        }
        return this.funcName.convertToJava(className) + "(" + this.params.convertToJava(className) + ")";
    }

    // todo removes the semanticException error
    @Override
    public String convertToC() throws SemanticException { // todo check if we're making sure you can only call length on a string
        if (this.funcName.getToken().getTokenString().equals("print")) {
            return "printf(" + params.getFormatSpecifierAndQuotes() + ", " + this.params.convertToC() + ")";
        } else if (this.funcName.getToken().getTokenString().equals("length")) {
            return "strlen(" + this.params.convertToC() + ")";
        } else if (this.funcName.getToken().getTokenString().equals("concat")) {
            return this.params.convertToCConcat();
        }
        return this.funcName.convertToC() + "(" + this.params.convertToC() + ")";
    }

    @Override
    public String convertToPython() {
        if (this.funcName.getToken().getTokenString().equals("print")) {
            return "print" + "(" + this.params.convertToPython() + ")";
        } else if (this.funcName.getToken().getTokenString().equals("length")) {
            return "len" + "(" + this.params.convertToPython() + ")";
        } else if (this.funcName.getToken().getTokenString().equals("concat")) {
            return this.params.convertToPythonConcat();
        }
        return this.funcName.convertToPython() + "(" + this.params.convertToPython() + ")";
    }

    @Override
    public void validateTree() throws SemanticException { //just checks to make sure the function exists right now
        List<String> funcDefinition = new ArrayList<>();
        funcDefinition.add(funcName.getName());
        funcDefinition.addAll(params.getParamTypes());
        boolean valid = SymbolTable.doesFunctionExist(funcDefinition);
        if (!valid) throw new SemanticException("The function " + funcName.getName() + " does not exist with given params", funcName.getToken());
        funcName.validateTree();
        params.validateTree();
    }

    @Override
    public String getType() throws SemanticException {
        return SymbolTable.getFunctionReturnType(funcName, params);
    }

    @Override
    public Token getToken() {
        return funcName.getToken();
    }
}
