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
        return idName.getTokenString();
    }

    @Override
    public String convertToJott() {
        return this.idName.getTokenString();
    }

    @Override
    public String convertToJava() {
        return this.idName.getTokenString();
    }

    @Override
    public String convertToC() {
        return this.idName.getTokenString();
    }

    @Override
    public String convertToPython() {
        return this.idName.getTokenString();
    }

    @Override
    public void validateTree() {
    }

    public String getType() throws SemanticException {
        if (!SymbolTable.doesVarExistInScope(idName.getTokenString()))
            throw new SemanticException("The variable was never defined", idName);
        return SymbolTable.getVariableType(idName.getTokenString());
    }

    public Token getToken() {
        return idName;
    }

    @Override
    public double evaluate() throws SemanticException { //todo properly fix instanceof error
        ExprNode value = SymbolTable.getVariableValue(idName.getTokenString());
        if (value == null) {
            if (SymbolTable.varIsParamVariable(idName.getTokenString())) return 0;
            else throw new SemanticException("The variable was never defined", idName);
        }
        if (value instanceof BinaryOperationNode) {
            return 0;
        }
        return value.evaluate();
    }

    public boolean hasParamVariable() {
        return SymbolTable.varIsParamVariable(idName.getTokenString());
    }
}
