package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class BodyNode implements JottTree {
    ArrayList<BodyStmtNode> bodyStmts;
    ReturnStmtNode returnStmt;

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws SyntaxException {
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
        for (BodyStmtNode bodyStmt: bodyStmts) {
            out += "\t" + bodyStmt.convertToC();
        }
        out += "\t" + returnStmt.convertToC();
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        for (BodyStmtNode bodyStmt: bodyStmts) {
            out += "\t" + bodyStmt.convertToPython();
        }
        out += "\t" + returnStmt.convertToPython();
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
