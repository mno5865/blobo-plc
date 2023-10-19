package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

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
            throw new SyntaxException("Function definition must be followed by ID or KEYWORD", token.getFilename(), token.getLineNum());
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
    public boolean validateTree() {
        return false;
    }
}
