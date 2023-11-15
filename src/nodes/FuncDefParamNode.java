package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.List;

import static nodes.BasicParsers.parseToken;

public class FuncDefParamNode implements JottTree {
    private final IDNode paramName;
    private final TypeNode paramType;
    private final ArrayList<FuncDefParamTailNode> paramTail;

    public FuncDefParamNode(IDNode paramName, TypeNode paramType, ArrayList<FuncDefParamTailNode> paramTail) {
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramTail = paramTail;
    }

    public static FuncDefParamNode parseFuncDefParamNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.R_BRACKET) {
            if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
                throw new SyntaxException("Function definition parameter requires Type KEYWORD before parameter ID", token.getFilename(),
                        token.getLineNum());
            }
            IDNode paramName = IDNode.parseIDNode(tokens);
            parseToken(TokenType.COLON, tokens);
            TypeNode paramType = TypeNode.parseTypeNode(tokens);
            ArrayList<FuncDefParamTailNode> paramTail = new ArrayList<>();

            token = tokens.get(0);
            while (token.getTokenType() != TokenType.R_BRACKET) {
                paramTail.add(FuncDefParamTailNode.parseFuncDefParamTailNode(tokens));
                token = tokens.get(0);
            }
            return new FuncDefParamNode(paramName, paramType, paramTail);
        }
        return new FuncDefParamNode(null, null, null);
    }

    @Override
    public String convertToJott() {
        StringBuilder out = new StringBuilder();
        if (paramName != null) {
            out.append(this.paramName.convertToJott());
            out.append(":");
            out.append(this.paramType.convertToJott());
            for (FuncDefParamTailNode param : paramTail) {
                out.append(param.convertToJott());
            }
        }
        return out.toString();
    }

    @Override
    public String convertToJava(String className) {
        StringBuilder out = new StringBuilder();
        if (paramName != null) {
            out.append(this.paramType.convertToJava(className));
            out.append(" ");
            out.append(this.paramName.convertToJava(className));
            for (FuncDefParamTailNode param : paramTail) {
                out.append(param.convertToJava(""));
            }
        }
        return out.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder out = new StringBuilder();
        if (paramName != null) {
            out.append(paramType.convertToC());
            out.append(" ");
            out.append(paramName.convertToC());
            for (FuncDefParamTailNode param : paramTail) {
                out.append(param.convertToC());
            }
        }

        return out.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder out = new StringBuilder();
        if (paramName != null) {
            out.append(paramName.convertToPython());
            for (FuncDefParamTailNode param : paramTail) {
                out.append(param.convertToPython());
            }
        }
        return out.toString();
    }

    @Override
    public void validateTree() throws SemanticException {
        if (this.paramName == null) return;
        paramName.validateTree();
        paramType.validateTree();
        for (FuncDefParamTailNode param : paramTail) {
            param.validateTree();
        }
    }

    public boolean paramsExist() {
        return paramName != null;
    }

    public List<String> getParamTypes() {
        List<String> params = new ArrayList<>();
        if (this.paramName == null) return params;
        params.add(paramType.getType());
        for (FuncDefParamTailNode param : paramTail) {
            params.add(param.getType());
        }
        return params;
    }

    public List<String> getParamNames() {
        List<String> params = new ArrayList<>();
        if (this.paramName == null) return params;
        params.add(paramName.getName());
        for (FuncDefParamTailNode param : paramTail) {
            params.add(param.getName());
        }
        return params;
    }
}
