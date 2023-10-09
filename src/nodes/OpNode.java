package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class OpNode implements JottTree {
    public OpNode(){

    }

    public static OpNode parseOpNode(ArrayList<Token> tokens){
        return new OpNode();
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
