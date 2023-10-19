package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

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
        try {
            tree = ProgramNode.parseProgramNode(tokens);
        } catch (SyntaxException e)  {
            System.err.print(e);
        } catch (IndexOutOfBoundsException e) {
            try {
                throw new SyntaxException("End of file reached before expected.", "", -1);
            } catch (SyntaxException s) {
                System.err.print(s);
            }
        }
        return tree;
    }
}
