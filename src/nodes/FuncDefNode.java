package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncDefNode implements JottTree {
    private IDNode funcName;
    private FuncDefParamNode params;
    private TypeNode returnType;
    private BodyNode body;

    public FuncDefNode(IDNode funcName, FuncDefParamNode params, TypeNode returnType, BodyNode body){
        this.funcName = funcName;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FuncDefNode parseFuncDefNode(ArrayList<Token> tokens) throws SyntaxException{
        return null;
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
