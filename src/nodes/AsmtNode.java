package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class AsmtNode implements BodyStmtNode {
    private final Token type; //todo see notes in varDecNode on type as a node class
    private final IDNode id;
    private final ExprNode expr;

    public AsmtNode(Token type, IDNode id, ExprNode expr) {
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
        Token type = null;
        if (token2.getTokenType() == TokenType.ID_KEYWORD) {
            type = tokens.remove(0);
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
            out += this.type.getToken();
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
    public boolean validateTree() throws SemanticException {
        // todo this should make sure the assigned value is of the right type if the variable is already assigned
        // should also prob make sure the variable exists and shi and some other stuff idk good
        // luck figuring it out i have other code i gotta write 🙏🏾

        // here is the symbol table code that adds a newly initialized variable to the symbol table
        if (this.type != null) {
            SymbolTable.addVariable(this.type.getToken(), this.id.getName()); //todo once you rework type replace this.type with whatever the actual function to get the type is
        }
        // you can remove these comments (comments are ugly so once u read pls do) but don't remove the code this is to just explain what it does

        boolean valid = true;
        valid = valid && id.validateTree();
        valid = valid && expr.validateTree();
        return valid;
    }
}
