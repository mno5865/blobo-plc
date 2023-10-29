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
    public boolean validateTree() { //todo default id should also just return true, asmt and func def should handle respective checks
        return true;
    }

    @Override
    public boolean isInteger() { //todo this function
        return false;
    }
}
