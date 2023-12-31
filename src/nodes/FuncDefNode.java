package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.net.IDN;
import java.util.ArrayList;

import static nodes.BasicParsers.*;

public class FuncDefNode implements JottTree {
    private final IDNode funcName;
    private final FuncDefParamNode params;
    private final FuncReturnNode returnType;
    private final BodyNode body;

    public FuncDefNode(IDNode funcName, FuncDefParamNode params, FuncReturnNode returnType, BodyNode body) {
        this.funcName = funcName;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FuncDefNode parseFuncDefNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD || !token.getToken().equals("def")) {
            throw new SyntaxException("Function definition must begin with KEYWORD def", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);

        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Function definition expects be followed by ID or KEYWORD", token.getFilename(), token.getLineNum());
        }
        IDNode funcName = IDNode.parseIDNode(tokens);
        parseToken(TokenType.L_BRACKET, tokens);
        FuncDefParamNode params = FuncDefParamNode.parseFuncDefParamNode(tokens);
        parseToken(TokenType.R_BRACKET, tokens);
        parseToken(TokenType.COLON, tokens);
        FuncReturnNode returnType = FuncReturnNode.parseFuncReturnNode(tokens);
        parseToken(TokenType.L_BRACE, tokens);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        parseToken(TokenType.R_BRACE, tokens);

        return new FuncDefNode(funcName, params, returnType, body);
    }

    @Override
    public String convertToJott() {
        return "def " +
                this.funcName.convertToJott() +
                "[" +
                this.params.convertToJott() +
                "]:" +
                this.returnType.convertToJott() +
                "{\n" +
                this.body.convertToJott() +
                "}\n";
    }

    @Override
    public String convertToJava(String className) {
        return "public " +
                this.returnType.convertToJava(className) +
                " " +
                this.funcName.convertToJava(className) +
                "(" +
                this.params.convertToJava(className) +
                "){" +
                this.body.convertToJava(className) +
                "}";
    }

    @Override
    public String convertToC() {
        if (funcName.getName().equals("main")){
            return "int" +
                    funcName.convertToC() +
                    "(" +
                    params.convertToC() +
                    "){" +
                    body.convertToC() +
                    "}";
        }
        return returnType.convertToC() +
                funcName.convertToC() +
                "(" +
                params.convertToC() +
                "){" +
                body.convertToC() +
                "}";
    }

    @Override
    public String convertToPython() {
        return "def " +
                funcName.convertToPython() +
                "(" +
                params.convertToPython() +
                "):\n" +
                body.convertToPython();
    }

    @Override
    public void validateTree() throws SemanticException {
        // if the function is main, check that it returns void
        //this should be making sure the func is unique, this is also where we initialize it in the symbol table
        String returnValue = !returnType.returnTypeExists() ? "Void" : returnType.getReturnType();
        SymbolTable.setFunction(funcName, params.getParamTypes(), params.getParamNames(), returnValue);
        funcName.validateTree();
        if (params.paramsExist()) params.validateTree();
        returnType.validateTree();
        body.validateTree();

        if (funcName.getName().equals("main")) {
            if (returnType.returnTypeExists()) {
                throw new SemanticException("main cannot return anything", funcName.getToken().getFilename(),
                        funcName.getToken().getLineNum());
            }
            if (params.paramsExist()) {
                throw new SemanticException("main cannot have parameters", funcName.getToken().getFilename(),
                        funcName.getToken().getLineNum());
            }
        }

        // check if they are overriding a function name
        if (funcName.getName().equals("concat") || funcName.getName().equals("length") || funcName.getName().equals("print")) {
            throw new SemanticException("cannot override functions concat, length, or print",
                    funcName.getToken().getFilename(), funcName.getToken().getLineNum());
        }

        String bodyReturnType = body.returnPath(returnValue);
        if (!bodyReturnType.equals("Void") && !returnType.returnTypeExists()) {
            throw new SemanticException("Void function is returning something", funcName.getToken().getFilename(),
                    funcName.getToken().getLineNum());
        } else if (bodyReturnType.equals("Void") && returnType.returnTypeExists()) {
            throw new SemanticException("missing return statement", funcName.getToken().getFilename(),
                    funcName.getToken().getLineNum());
        } else if (!bodyReturnType.equals(returnType.getReturnType())) {
            throw new SemanticException("Body return type is incorrect, or missing",
                    funcName.getToken().getFilename(), funcName.getToken().getLineNum());
        }
    }
}
