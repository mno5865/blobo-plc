package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class IfStmtNode implements BodyStmtNode {

    private final ExprNode expr;
    private final BodyNode body;
    private final ArrayList<ElseifNode> elseIfList;
    private final ElseNode elseNode;

    public IfStmtNode(ExprNode expr, BodyNode body, ArrayList<ElseifNode> elseIfList, ElseNode elseNode) {
        this.expr = expr;
        this.body = body;
        this.elseIfList = new ArrayList<>(elseIfList);
        this.elseNode = elseNode;
    }

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (!(token.getTokenType() == TokenType.ID_KEYWORD && token.getTokenString().equals("if"))) {
            throw new SyntaxException("if statement must begin with KEYWORD if", token.getFilename(), token.getLineNum());
        }
        tokens.remove(0);

        parseToken(TokenType.L_BRACKET, tokens);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        parseToken(TokenType.R_BRACKET, tokens);
        parseToken(TokenType.L_BRACE, tokens);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        parseToken(TokenType.R_BRACE, tokens);
        ArrayList<ElseifNode> elseIfNodes = new ArrayList<>();
        token = tokens.get(0);
        // else if node list
        ElseifNode elseifNode;
        while (token.getTokenType() == TokenType.ID_KEYWORD && token.getTokenString().equals("elseif")) {
            elseifNode = ElseifNode.parseElseifNode(tokens);
            elseIfNodes.add(elseifNode);
            token = tokens.get(0);
        }
        //else node
        token = tokens.get(0);
        ElseNode elseNode = null;
        if (token.getTokenType() == TokenType.ID_KEYWORD && token.getTokenString().equals("else")) {
            elseNode = ElseNode.parseElseNode(tokens);
        }
        return new IfStmtNode(expr, body, elseIfNodes, elseNode);
    }

    @Override
    public String convertToJott() {
        StringBuilder out = new StringBuilder("if");
        out.append("[");
        out.append(this.expr.convertToJott());
        out.append("]");
        out.append("{\n");
        out.append(this.body.convertToJott());
        out.append("\t}");
        if (!this.elseIfList.isEmpty()) {
            for (ElseifNode elseifNode : this.elseIfList) {
                String out2 = elseifNode.convertToJott();
                out.append(out2);
            }
        }
        if (this.elseNode != null) {
            String out3 = this.elseNode.convertToJott();
            out.append(out3);
        }
        return out.toString();
    }

    @Override
    public String convertToJava() {
        StringBuilder str = new StringBuilder("if");
        str.append("(");
        str.append(this.expr.convertToJava());
        str.append(") ");
        str.append("{\n");
        str.append(this.body.convertToJava());
        str.append("\t}");
        if (!this.elseIfList.isEmpty()) {
            for (ElseifNode elseifNode : this.elseIfList) {
                String out2 = elseifNode.convertToJava();
                str.append(BodyNode.getTabs()).append(out2);
            }
        }
        if (this.elseNode != null) {
            String out3 = this.elseNode.convertToJava();
            str.append(BodyNode.getTabs()).append(out3);
        }
        return str.toString();
    }

    @Override
    public String convertToC() throws SemanticException {
        StringBuilder str = new StringBuilder("if");
        str.append("(");
        str.append(this.expr.convertToC());
        str.append(") ");
        str.append("{\n");
        str.append(this.body.convertToC());
        str.append("\t}");
        if (!this.elseIfList.isEmpty()) {
            for (ElseifNode elseifNode : this.elseIfList) {
                String out2 = elseifNode.convertToC();
                str.append(BodyNode.getTabs()).append(out2);
            }
        }
        if (this.elseNode != null) {
            String out3 = this.elseNode.convertToC();
            str.append(BodyNode.getTabs()).append(out3);
        }
        return str.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder str = new StringBuilder("if ");
        str.append(this.expr.convertToPython());
        str.append(": \n");
        str.append(this.body.convertToPython());
        if (!this.elseIfList.isEmpty()) {
            for (ElseifNode elseifNode : this.elseIfList) {
                String out2 = elseifNode.convertToPython();
                str.append(BodyNode.getTabs()).append(out2);
            }
        }
        if (this.elseNode != null) {
            String out3 = this.elseNode.convertToPython();
            str.append(BodyNode.getTabs()).append(out3);
        }
        return str.toString();
    }

    @Override
    public void validateTree() throws SemanticException {
        if(!expr.getType().equals("Boolean"))
        {
            throw new SemanticException("Expression is not a binary expression",expr.getToken());
        }
        expr.validateTree();
        body.validateTree();
        if (!this.elseIfList.isEmpty()) {
            for (ElseifNode elseifNode : this.elseIfList) {
                elseifNode.validateTree();
            }
        }
        if (this.elseNode != null) {
            elseNode.validateTree();
        }
    }

    public String getReturnType(String neededReturn, boolean mustHaveReturn) throws SemanticException {
        if (elseNode == null) return "Void";

        String returnType = body.getReturnType();
        boolean allReturn = false;

        for (ElseifNode elseIf : elseIfList) {
            String elseIfReturn = elseIf.getReturnType();
            if (!elseIfReturn.equals(returnType)) throw new SemanticException("Return statements inside of if's must match",
                    elseIf.getToken());
        }

        String elseReturn = elseNode.getReturnType();
        if (elseReturn.equals(returnType)) allReturn = true;
        else if (!elseReturn.equals("Void"))
            throw new SemanticException("Else return must match if return", expr.getToken());

        if (!allReturn && mustHaveReturn)
            throw new SemanticException("Else must also have return, or body needs a return", expr.getToken());

        if (mustHaveReturn && !neededReturn.equals(returnType))
            throw new SemanticException("Return type inside of if statement is incorrect", expr.getToken());

        return returnType;
    }

    public Token getToken() {
        return expr.getToken();
    }
}
