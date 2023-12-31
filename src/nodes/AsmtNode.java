package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.Objects;

public class AsmtNode implements BodyStmtNode {
    private final TypeNode type;
    private final IDNode id;
    private final ExprNode expr;

    public AsmtNode(TypeNode type, IDNode id, ExprNode expr) {
        this.type = type;
        this.id = id;
        this.expr = expr;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token1 = tokens.get(0);
        Token token2 = tokens.get(1);
        if (token1.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Assignment statement must begin with be ID or KEYWORD", token1.getFilename(), token1.getLineNum());
        }
        if (token2.getTokenType() != TokenType.ID_KEYWORD && token2.getTokenType() != TokenType.ASSIGN) {
            throw new SyntaxException("Assignment statement expects to be followed by ID or ASSIGNMENT (=)", token2.getFilename(),
                    token2.getLineNum());
        }
        TypeNode type = null;
        if (token2.getTokenType() == TokenType.ID_KEYWORD) {
            type = TypeNode.parseTypeNode(tokens);
        }
        IDNode id = IDNode.parseIDNode(tokens);
        BasicParsers.parseToken(TokenType.ASSIGN, tokens);
        ExprNode expr = ExprNode.parseExprNode(tokens);
        BasicParsers.parseToken(TokenType.SEMICOLON, tokens);
        return new AsmtNode(type, id, expr);
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (this.type != null) {
            out += this.type.getType();
        }
        return out + ((this.type != null) ? " " : "") + this.id.convertToJott() + " = " + this.expr.convertToJott() +
                ";";
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
    public void validateTree() throws SemanticException {
        id.validateTree();
        expr.validateTree();
        // check against variables named after keywords
        if (this.id.getName().equals("while") || this.id.getName().equals("if") || this.id.getName().equals("else")) {
            throw new SemanticException("Variable cannot be named after a keyword", expr.getToken().getFilename(),
                    expr.getToken().getLineNum());
        }
        // < type > < id >= < expr >;   - just check that type is same type as expr
        if (this.type != null) {
            if (!Objects.equals(this.expr.getType(), this.type.getType())) {
                throw new SemanticException("Variable type is different than expression type",
                        expr.getToken().getFilename(), expr.getToken().getLineNum());
            } else {
                SymbolTable.addVariable(this.type.getType(), this.id.getName(), this.expr);
            }
        } else {
            // check if the variable exists
            if (!SymbolTable.doesVarExistInScope(this.id.getName())) {
                throw new SemanticException("Variable does not exist", expr.getToken().getFilename(), 
                        expr.getToken().getLineNum());
            } else {
                if (!SymbolTable.getVariableType(this.id.getName()).equals(this.expr.getType())) {
                    throw new SemanticException("Variable type is different than expression type",
                            expr.getToken().getFilename(), expr.getToken().getLineNum());
                } else {
                    SymbolTable.setVariable(this.id.getName(), this.expr);
                }
            }
        }
    }
}
