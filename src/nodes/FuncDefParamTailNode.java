package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class FuncDefParamTailNode implements JottTree {
    private final IDNode paramName;
    private final TypeNode paramType;

    public FuncDefParamTailNode(IDNode paramName, TypeNode paramType) {
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public static FuncDefParamTailNode parseFuncDefParamTailNode(ArrayList<Token> tokens) throws SyntaxException {
        parseToken(TokenType.COMMA, tokens);
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Function definition parameter expects Type KEYWORD following comma", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
        IDNode paramName = IDNode.parseIDNode(tokens);
        parseToken(TokenType.COLON, tokens);
        TypeNode paramType = TypeNode.parseTypeNode(tokens);

        return new FuncDefParamTailNode(paramName, paramType);
    }

    @Override
    public String convertToJott() {
        return ", " +
               this.paramName.convertToJott() +
               ":" +
               this.paramType.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return ", " +
               this.paramType.convertToJava(className) +
               " " +
               this.paramName.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return ", " +
               paramType.convertToC() +
               " " +
               paramName.convertToC();
    }

    @Override
    public String convertToPython() {
        return ", " +
               paramName.convertToPython();
    }

    @Override
    public void validateTree() throws SemanticException {
        paramName.validateTree();
        paramType.validateTree();
    }

    public String getType() {
        return paramType.getType();
    }

    public String getName() {
        return paramName.getName();
    }
}
