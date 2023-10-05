package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FuncDefParamNode implements JottTree {
    private IDNode paramName;
    private TypeNode paramType;
    private ArrayList<FuncDefParamTailNode> paramTail;

    public FuncDefParamNode(IDNode paramName, TypeNode paramType, ArrayList<FuncDefParamTailNode> paramTail){
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramTail = paramTail;
    }

    public static FuncDefParamNode parseFuncDefParamNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.L_BRACKET) {
            IDNode paramName = IDNode.parseIDNode(tokens);

            token = tokens.get(0);
            if (token.getTokenType() != TokenType.COLON) {
                throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo error description
            }
            tokens.remove(0);

            TypeNode paramType = TypeNode.parseTypeNode(tokens);
            FuncDefParamTailNode paramTail = FuncDefParamTailNode.parseFuncDefParamTailNode(tokens);
        }
        return new FuncDefParamNode(null, null, null);
    }

        @Override
    public String convertToJott() {
        String out = "";
        if (paramName != null){
            out += this.paramName.convertToJott();
            out += ":";
            out += this.paramType.convertToJott();
            for (FuncDefParamTailNode param : paramTail){
                out += param.convertToJott();
            }
        }
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        if (paramName != null){
            out += this.paramType.convertToJava(""); //todo className
            out += " ";
            out += this.paramName.convertToJava(""); //todo className
            for (FuncDefParamTailNode param : paramTail){
                out += param.convertToJava(""); //todo className
            }
        }
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
