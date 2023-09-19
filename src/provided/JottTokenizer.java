package src;

public class JottTokenizer {
    public static void main(String[] args) {
        char[] string = new char[]{'/', '<', ':', ',', '1', '.', 'f'}; //todo get input from file
        ArrayList<Token> tokens = new ArrayList<Token>();
        // todo handle comments
        for (int i = 0; i < string.length; i++) {
            String token;
            switch (string.get(i)) {
                case ',' -> token = new Token("" + string.get(i), filename, linenum, TokenType.COMMA);
                case ']' -> token = new Token("" + string.get(i), filename, linenum, TokenType.R_BRACKET);
                case '[' -> token = new Token("" + string.get(i), filename, linenum, TokenType.L_BRACKET);
                case '}' -> token = new Token("" + string.get(i), filename, linenum, TokenType.R_BRACE);
                case '{' -> token = new Token("" + string.get(i), filename, linenum, TokenType.L_BRACE);
                case ':' -> token = new Token("" + string.get(i), filename, linenum, TokenType.COLON);
                case ';' -> token = new Token("" + string.get(i), filename, linenum, TokenType.SEMICOLON);
                case '+', '-', '*', '/' -> token = new Token("" + string.get(i), filename, linenum, TokenType.MATH_OP);
                case '<', '>' -> {
                    if (string.get(i + 1) == '=') {
                        token = new Token("" + string.get(i) + string.get(i + 1), filename, linenum, TokenType.REL_OP);
                        i++;
                    } else {
                        token = new Token("" + string.get(i), filename, linenum, TokenType.REL_OP);
                    }
                }
                case '=' -> {
                    if (string.get(i + 1) == '=') {
                        token = new Token("" + string.get(i) + string.get(i + 1), filename, linenum, TokenType.REL_OP);
                        i++;
                    } else {
                        token = new Token("" + string.get(i), filename, linenum, TokenType.ASSIGN);
                    }
                }
                case '!' -> token = "notEquals"; //todo the case of = (notEquals)
                case '"' -> token = "string"; //todo loop the string and the case of " (string)
                case '.' -> {
                    if (Character.isDigit(string.get(i + 1))) { //passes = accept state
                        String s = "" + string.get(i) + string.get(i + 1)
                        i++;
                        token = digitCheck(s, string, i);
                    } else {
                        System.err.println("error"); //todo change print to be more specific
                    }
                }
                case '#' -> {
                    char currentChar = string[i];
                    int skip = 0; // the number of chars that are part of the comment and need to be skipped/left out
                    while (currentChar != '\n' && currentChar != '\u001a') {
                        currentChar = string[skip + 1];
                        skip++;
                    }
                    i += skip;
                }
                default -> {
                    if (Character.isDigit(string.get(i))) { //passes = accept state
                        string s = "" + string.get(i);
                        i++;
                        while (i < string.length && Character.isDigit(string.get(i))) {
                            s += string.get(i);
                            i++;
                        }

                        if (i < string.length && string.get(i) == '.') {
                            token = digitCheck(string, i);
                        }
                    } else if (Character.isAlphabetic(string.get(i))) {
                        //todo case if letter, id or keyword
                    }
                }
            }
            tokens.append(token);
        }
    }

    /**
     * Builds the digit string
     * @param string the current token string
     * @param i the index of the char in the file
     * @return the Token
     */
    private static Token digitCheck(s, char[] string, int i) {
        while (i + 1 < string.length && Character.isDigit(string.get(i + 1))) {
            s += string.get(i + 1);
            i++;
        }
        return token = new Token(s, filename, linenum, TokenType.NUMBER);
    }
}