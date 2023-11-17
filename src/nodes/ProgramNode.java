package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;
import java.util.List;

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
        StringBuilder out = new StringBuilder();
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out.append(funcDef.convertToJott());
            }
        }
        return out.toString();
    }

    @Override
    public String convertToJava() {
        StringBuilder out = new StringBuilder();
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out.append(funcDef.convertToJava());
            }
        }
        return out.toString();
    }

    @Override
    public String convertToC() throws SemanticException {
        StringBuilder out = new StringBuilder();
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out.append(funcDef.convertToC());
            }
        }
        return out.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder out = new StringBuilder();
        if (funcDefs != null) {
            for (FuncDefNode funcDef : funcDefs) {
                out.append(funcDef.convertToPython());
            }
        }
        return out.toString();
    }

    @Override
    public void validateTree() throws SemanticException {
        for (FuncDefNode funcDef : funcDefs) {
            funcDef.validateTree();
        }
        List<String> main = new ArrayList<>();
        main.add("main");
        if (!SymbolTable.doesFunctionExist(main)) throw new SemanticException("Main must be defined",
                JottParser.lastFile, JottParser.lastLine);
    }
}
