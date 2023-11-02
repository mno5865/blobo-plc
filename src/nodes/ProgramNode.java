package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ProgramNode implements JottTree {
    private final ArrayList<FuncDefNode> funcDefs;

    public ProgramNode(ArrayList<FuncDefNode> funcDefs) {
        this.funcDefs = funcDefs;
    }

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws SyntaxException {
        if (!tokens.isEmpty()) {
            ArrayList<FuncDefNode> funcDefs = new ArrayList<>();
            while (!tokens.isEmpty()) {
                funcDefs.add(FuncDefNode.parseFuncDefNode(tokens));
            }

            return new ProgramNode(funcDefs);
        }

        return new ProgramNode(null);
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out += funcDef.convertToJott();
            }
        }
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out += funcDef.convertToJava("");
            }
        }
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out += funcDef.convertToC();
            }
        }
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out += funcDef.convertToPython();
            }
        }
        return out;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        for (FuncDefNode funcDef : funcDefs) {
            funcDef.validateTree();
        }
        return true;
    }
}
