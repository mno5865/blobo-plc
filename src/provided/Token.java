package provided;
/**
 * Class representing a token string for the Jott translator
 *
 * @author Scott C. Johnson
 */
public class Token {

    private final String token;
    private final String filename;
    private final int lineNum;
    private final TokenType type;

    /**
     * Creates an instance of a token
     * @param token the token string
     * @param filename the name of the file the token came from
     * @param lineNum the number of the line in the file that the token appears on
     * @param type the type of this token
     */
    public Token(String token, String filename, int lineNum, TokenType type) {
        this.token = token;
        this.filename = filename;
        this.lineNum = lineNum;
        this.type = type;
    }

    /**
     * Getter for the token string
     * @return the token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Getter for the token file name
     * @return the token file name
     */
    public String getFilename() { return filename; }

    /**
     * Getter for the token line number
     * @return the line number of the token
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * Getter for the token type
     * @return the line number of the token
     */
    public TokenType getTokenType() {
        return type;
    }
}
