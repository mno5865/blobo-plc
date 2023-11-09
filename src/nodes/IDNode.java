package nodes;

import errors.SemanticException;
import provided.Token;

import java.util.ArrayList;

public class IDNode implements ExprNode {
    private final Token idName;

    private IDNode(Token ID) {
        this.idName = ID;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokens) {
        return new IDNode(tokens.remove(0));
    }

    public String getName() {
        return idName.getToken();
    }

    @Override
    public String convertToJott() {
        return this.idName.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.idName.getToken();
    }

    @Override
    public String convertToC() {
        return this.idName.getToken();
    }

    @Override
    public String convertToPython() {
        return this.idName.getToken();
    }

    @Override
    public void validateTree() {
    }

    public String getType() throws SemanticException {
        if (!SymbolTable.doesVarExistInScope(idName.getToken()))
            throw new SemanticException("The variable was never defined", idName);
        return SymbolTable.getVariableType(idName.getToken());
    }

    public Token getToken() {
        return idName;
    }

    @Override
    public double evaluate() throws SemanticException {
        ExprNode value = SymbolTable.getVariableValue(idName.getToken());
        if (value == null) throw new SemanticException("The variable was never defined", idName);
        return value.evaluate();
    }
}
