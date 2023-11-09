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
        String out = "def ";
        out += this.funcName.convertToJott();
        out += "[";
        out += this.params.convertToJott();
        out += "]:";
        out += this.returnType.convertToJott();
        out += "{\n\t";
        out += this.body.convertToJott();
        out += "}\n";
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "public ";
        out += this.returnType.convertToJava("");
        out += " ";
        out += this.funcName.convertToJava("");
        out += "(";
        out += this.params.convertToJava("");
        out += "){";
        out += this.body.convertToJava("");
        out += "}";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        out += returnType.convertToC();
        out += funcName.convertToC();
        out += "(";
        out += params.convertToC();
        out += "){";
        out += body.convertToC();
        out += "}";
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "def ";
        out += funcName.convertToPython();
        out += "(";
        out += params.convertToPython();
        out += "):\n";
        out += body.convertToPython();
        return out;
    }

    @Override
    public void validateTree() throws SemanticException {
        // if the function is main, check that it returns void
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

        //this should be making sure the func is unique, this is also where we initialize it in the symbol table
        String returnValue = !returnType.returnTypeExists() ? "" : returnType.getReturnType();

        if (!body.getReturnType().equals(returnType.getReturnType())) {
            throw new SemanticException("body return type is not the same as the function return type",
                    funcName.getToken().getFilename(), funcName.getToken().getLineNum());
        } else if (body.getReturnType() == null && returnType.returnTypeExists()) {
            throw new SemanticException("Void function is returning something", funcName.getToken().getFilename(),
                    funcName.getToken().getLineNum());
        } else if (!body.getReturnType().equals("Void") && !returnType.returnTypeExists()) {
            throw new SemanticException("missing return statement", funcName.getToken().getFilename(),
                    funcName.getToken().getLineNum());
        }

        SymbolTable.setFunction(funcName, params.getParamTypes(), params.getParamNames(), returnValue);
        funcName.validateTree();
        if (params.paramsExist()) params.validateTree();
        returnType.validateTree();
        body.validateTree();
    }
}
