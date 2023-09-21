package provided;

import errors.SyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JottTokenizer {
    public static ArrayList<Token> tokenize(String fileName) {
        ArrayList<Token> tokens = null;
        File file = new File(fileName);

        try (Scanner fileInput = new Scanner(file)) {
            tokens = new ArrayList<>();
            int lineNum = 1;

            while (fileInput.hasNextLine()) {
                String line = fileInput.nextLine().strip();
                char[] string = line.toCharArray();

                for (int i = 0; i < string.length; i++) {
                    Token token = null;
                    switch (string[i]) {
                        case ',' -> token = new Token("" + string[i], fileName, lineNum, TokenType.COMMA);
                        case ']' -> token = new Token("" + string[i], fileName, lineNum, TokenType.R_BRACKET);
                        case '[' -> token = new Token("" + string[i], fileName, lineNum, TokenType.L_BRACKET);
                        case '}' -> token = new Token("" + string[i], fileName, lineNum, TokenType.R_BRACE);
                        case '{' -> token = new Token("" + string[i], fileName, lineNum, TokenType.L_BRACE);
                        case ':' -> token = new Token("" + string[i], fileName, lineNum, TokenType.COLON);
                        case ';' -> token = new Token("" + string[i], fileName, lineNum, TokenType.SEMICOLON);
                        case '+', '-', '*', '/' ->
                                token = new Token("" + string[i], fileName, lineNum, TokenType.MATH_OP);
                        case '<', '>' -> {
                            if (string.length - 1 != i && string[i + 1] == '=') {
                                token = new Token("" + string[i] + string[i + 1], fileName, lineNum, TokenType.REL_OP);
                                i++;
                            } else {
                                token = new Token("" + string[i], fileName, lineNum, TokenType.REL_OP);
                            }
                        }
                        case '=' -> {
                            if (string.length - 1 != i && string[i + 1] == '=') {
                                token = new Token("" + string[i] + string[i + 1], fileName, lineNum, TokenType.REL_OP);
                                i++;
                            } else {
                                token = new Token("" + string[i], fileName, lineNum, TokenType.ASSIGN);
                            }
                        }
                        case '!' -> {
                            if (string.length - 1 != i && string[i + 1] == '=') {
                                //Valid token
                                token = new Token("" + string[i] + string[i + 1], fileName, lineNum, TokenType.REL_OP);
                                i++;
                            } else {
                                //Syntax Error
                                throw new SyntaxException("ERROR - expected '=' after '!'", fileName, lineNum);
                            }
                        }
                        case '"' -> {
                            StringBuilder str = new StringBuilder();
                            str.append('"');
                            while (string.length - 1 != i) {
                                str.append(string[i + 1]);
                                if (string[i + 1] == '"') {
                                    i++;
                                    break;
                                }
                                i++;
                                if (string.length - 1 == i) {
                                    //Syntax Errors
                                    throw new SyntaxException("ERROR - missing string end quotes", fileName, lineNum);
                                }
                            }
                            token = new Token(str.toString(), fileName, lineNum, TokenType.STRING);
                        }
                        case '.' -> {
                            if (i + 1 == string.length) {
                                throw new SyntaxException("File ends in invalid character, missing digit", fileName, lineNum);
                            }
                            if (Character.isDigit(string[i + 1])) { //passes = accept state
                                String s = "" + string[i] + string[i + 1];
                                i++;
                                token = digitCheck(s, string, i, fileName, lineNum);
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
                            if (Character.isDigit(string[i])) { //passes = accept state
                                StringBuilder s = new StringBuilder("" + string[i]);
                                i++;
                                while (i < string.length && Character.isDigit(string[i])) {
                                    s.append(string[i]);
                                    i++;
                                }

                                if (i < string.length && string[i] == '.') {
                                    s.append('.');
                                    token = digitCheck(s.toString(), string, i, fileName, lineNum);
                                }
                            } else if (Character.isAlphabetic(string[i])) {
                                //todo case if letter, id or keyword
                            }
                        }
                    }
                    tokens.add(token);
                }
                lineNum++;
            }
        } catch (FileNotFoundException | SyntaxException e) {
            System.err.println(e);
        }
        return tokens;
    }

    /**
     * Builds the digit string
     *
     * @param s        Current digit string
     * @param string   Current token string
     * @param i        Index of the char in the file
     * @param filename Name of the file
     * @param lineNum  Current line number
     * @return The Token
     */
    private static Token digitCheck(String s, char[] string, int i, String filename, int lineNum) {
        StringBuilder sBuilder = new StringBuilder(s);
        while (i + 1 < string.length && Character.isDigit(string[i + 1])) {
            sBuilder.append(string[i + 1]);
            i++;
        }
        s = sBuilder.toString();
        return new Token(s, filename, lineNum, TokenType.NUMBER);
    }
}