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
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.STRING) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        IDNode funcName = IDNode.parseIDNode(tokens);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        FuncDefParamNode params = FuncDefParamNode.parseFuncDefParamNode();

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.COLON) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        TypeNode returnType = TypeNode.parseTypeNode(tokens);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

        BodyNode body = BodyNode.parseBodyNode(tokens);

        token = tokens.get(0);
        if (token.getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
        }
        tokens.remove(0);

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
