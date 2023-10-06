package nodes;

import provided.JottTree;

public class AsmtNode implements JottTree {
    public AsmtNode(){

    }

    public AsmtNode parseAsmtNode(){
        return new AsmtNode();
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
