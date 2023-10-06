package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ProgramNode implements JottTree {
    private ArrayList<FuncDefNode> funcDefs;

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
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
