package nodes;

import provided.JottTree;

public class IfStmtNode implements JottTree {
    public IfStmtNode(){

    }

    public IfStmtNode parseIfStmtNode(){
        return new IfStmtNode();
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
