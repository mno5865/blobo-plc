package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BodyStmtNode implements JottTree {

    private final IfStmtNode ifStmt;
    private final WhileLoopNode whileLoop;
    private final AsmtNode assignment;
    private final VarDecNode varDec;
    private final FuncCallNode funcCall;

    public BodyStmtNode(IfStmtNode ifStmt, WhileLoopNode whileLoop, AsmtNode assignment, VarDecNode varDec, FuncCallNode funcCall) {
        this.ifStmt = ifStmt;
        this.whileLoop = whileLoop;
        this.assignment = assignment;
        this.varDec = varDec;
        this.funcCall = funcCall;
    }

    public static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token =  tokens.get(0);
        if (token.getTokenType() != TokenType.ID_KEYWORD || token.getTokenType() != TokenType.FC_HEADER) {
            throw new SyntaxException(); // elaborate later
        }
        IfStmtNode ifStmtNode = null;
        WhileLoopNode whileLoopNode = null;
        AsmtNode assignmentNode = null;
        VarDecNode varDecNode = null;
        FuncCallNode funcCallNode = null;

        token = tokens.get(0);
        if (token.getToken().equals("if")) {
            ifStmtNode = IfStmtNode.parseIfStmtNode(tokens);
        } else if (token.getToken().equals("while")) {
            whileLoopNode = WhileLoopNode.parseWhileLoopNode(tokens);
        } else if (token.getTokenType() == TokenType.ID_KEYWORD) {
            ArrayList<Token> tempTokens = new ArrayList<>(tokens);
            TypeNode.parseTypeNode(tempTokens);
            IDNode.parseIDNode(tempTokens);
            token = tempTokens.get(0);
            if (token.getTokenType() == TokenType.ASSIGN){
                TypeNode.parseTypeNode(tokens);
                IDNode.parseIDNode(tokens);
                assignmentNode = AsmtNode.parseAsmtNode(tokens);
            } else if (token.getTokenType() == TokenType.SEMICOLON) {
                TypeNode.parseTypeNode(tokens);
                IDNode.parseIDNode(tokens);
                varDecNode = VarDecNode.parseVarDecNode(tokens);
            }
        } else if (token.getTokenType() == TokenType.FC_HEADER) {
            funcCallNode = FuncCallNode.parseFunctionCallNode(tokens);
        }

        if (token.getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException(); // elaborate later
        }
        return new BodyStmtNode(ifStmtNode, whileLoopNode, assignmentNode, varDecNode, funcCallNode);
    }

    @Override
    public String convertToJott() {
        if (ifStmt != null)
            return ifStmt.convertToJott();
        else if (whileLoop != null)
            return whileLoop.convertToJott();
        else if (assignment != null)
            return assignment.convertToJott();
        else if (varDec != null)
            return varDec.convertToJott();
        else if (funcCall != null)
            return funcCall.convertToJott();
        return "";
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
