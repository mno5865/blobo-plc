package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ParamTailNode implements JottTree {

    public ParamTailNode(){

    }

    public static ParamTailNode parseParamTailNode(ArrayList<Token> tokens) {
        return null;
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
