package nodes;

import errors.SemanticException;
import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public interface ExprNode extends JottTree {
    static ExprNode parseExprNode(ArrayList<Token> tokens) throws SyntaxException {
        TokenType type = tokens.get(0).getTokenType();
        boolean tokenIsBoolean = tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False");

        if (type == TokenType.ID_KEYWORD && tokenIsBoolean) {
            return BoolNode.parseBoolNode(tokens);
        }
        if (type == TokenType.STRING) {
            return StringNode.parseStringNode(tokens);
        }

        ExprNode left = null;

        if (type == TokenType.ID_KEYWORD) {
            left = IDNode.parseIDNode(tokens);
        } else if (type == TokenType.NUMBER) {
            left = NumberNode.parseNumberNode(tokens);
        } else if (type == TokenType.FC_HEADER) {
            left = FuncCallNode.parseFunctionCallNode(tokens);
        }

        if (left == null) {
            throw new SyntaxException("Operator must be followed by ID, NUMBER, or FUNCTION_HEADER",
                    tokens.get(0).getFilename(), tokens.get(0).getLineNum());
        }

        OpNode operator;
        type = tokens.get(0).getTokenType();

        if (type == TokenType.MATH_OP || type == TokenType.REL_OP) {
            operator = OpNode.parseOpNode(tokens);
            ExprNode right = ExprNode.parseExprNode(tokens);
            return new BinaryOperationNode(operator, left, right);
        } else {
            return left;
        }
    }

    String getType() throws SemanticException;

    Token getToken();

    default double evaluate() throws SemanticException {
        return 0;
    };
}