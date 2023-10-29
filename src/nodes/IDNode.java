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

    public String getFuncName() {
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
    public boolean validateTree() {
        //todo someone should check to make sure this is always true
        // (i'm pretty sure base nodes don't have any errors)
        // asmt and func def should handle their respective checks in their own classes and not in idnode
        return true;
    }

    @Override
    public boolean isInteger() { //todo this function
        return false;
    }
}
