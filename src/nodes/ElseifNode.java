package nodes;

import provided.JottTree;

public class ElseifNode implements JottTree {
    public ElseifNode(){

    }

    public ElseifNode parseElseifNode(){
        return new ElseifNode();
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