package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.*;

public class FuncDefNode implements JottTree {
    private IDNode funcName;
    private FuncDefParamNode params;
    private FuncReturnNode returnType;
    private BodyNode body;

    public FuncDefNode(IDNode funcName, FuncDefParamNode params, FuncReturnNode returnType, BodyNode body) {
        this.funcName = funcName;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FuncDefNode parseFuncDefNode(ArrayList<Token> tokens) throws SyntaxException {
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
        String out = "def";
        out += this.funcName.convertToJott();
        out += "[";
        out += this.params.convertToJott();
        out += "]:";
        out += this.returnType.convertToJott();
        out += "{";
        out += this.body.convertToJott();
        out += "}";
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "public ";
        out += this.returnType.convertToJava(""); //todo className
        out += " ";
        out += this.funcName.convertToJava(""); //todo className
        out += "(";
        out += this.params.convertToJava(""); //todo className
        out += "){";
        out += this.body.convertToJava(""); //todo className
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
