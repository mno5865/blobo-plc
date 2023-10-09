package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FuncReturnNode implements JottTree {
    private TypeNode type;
    public FuncReturnNode(){

    }

    public static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens){
        return new FuncReturnNode();
    }

    @Override
    public String convertToJott() {
        String out = "";
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
