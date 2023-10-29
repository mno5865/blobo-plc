/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */
package provided;

import errors.SemanticException;
import errors.SyntaxException;
import nodes.ProgramNode;

import java.util.ArrayList;

public class JottParser {

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens) {
        JottTree tree = null;
        int lastLine = 0;
        String lastFile = "";
        if (!tokens.isEmpty()) {
            lastLine = tokens.get(tokens.size() - 1).getLineNum();
            lastFile = tokens.get(tokens.size() - 1).getFilename();
        }
        try {
            tree = ProgramNode.parseProgramNode(tokens);
        } catch (SyntaxException e)  {
            System.err.print(e);
        } catch (IndexOutOfBoundsException e) {
            try {
                throw new SyntaxException("End of file reached before expected", lastFile, lastLine);
            } catch (SyntaxException s) {
                System.err.print(s);
            }
        }
        if (tree != null) {
            try {
                boolean validTree = tree.validateTree();
                System.out.println("\nValidateTree returned: " + validTree + "\n");
            } catch (SemanticException e){
                System.err.print(e);
            }
        }
        return tree;
    }
}
