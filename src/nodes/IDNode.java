package nodes;

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

    public String getType() { //todo this can result in a semantic exception so check for this a level up before you call it
        return SymbolTable.getVariableType(idName.getToken());
    }

    public Token getToken() {
        return idName;
    }
}
