package provided;

import errors.SyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JottTokenizer {
    public static void main(String[] args) {
        String inputFilename = args[0];

        tokenize(inputFilename);
    }

    public static ArrayList<Token> tokenize(String filename) {
        ArrayList<Token> tokens = null;
        File file = new File(filename);
        try (Scanner fileInput = new Scanner(file)) {
            tokens = new ArrayList<>();

            int lineNum = 1;
            while(fileInput.hasNextLine()) {
                String line = fileInput.nextLine().strip();
                char[] string = line.toCharArray();

                for (int i = 0; i < string.length; i++) {
                    Token token = null;
                    switch (string[i]) {
                        case ',' -> token = new Token("" + string[i], filename, lineNum, TokenType.COMMA);
                        case ']' -> token = new Token("" + string[i], filename, lineNum, TokenType.R_BRACKET);
                        case '[' -> token = new Token("" + string[i], filename, lineNum, TokenType.L_BRACKET);
                        case '}' -> token = new Token("" + string[i], filename, lineNum, TokenType.R_BRACE);
                        case '{' -> token = new Token("" + string[i], filename, lineNum, TokenType.L_BRACE);
                        case ':' -> token = new Token("" + string[i], filename, lineNum, TokenType.COLON);
                        case ';' -> token = new Token("" + string[i], filename, lineNum, TokenType.SEMICOLON);
                        case '+', '-', '*', '/' -> token = new Token("" + string[i], filename, lineNum, TokenType.MATH_OP);
                        case '<', '>' -> {
                            if (i + 1 == string.length) {
                                throw new SyntaxException("Syntax Error: File ends in invalid character, missing =");
                            }
                            if (string[i + 1] == '=') {
                                token = new Token("" + string[i] + string[i + 1], filename, lineNum, TokenType.REL_OP);
                                i++;
                            } else {
                                token = new Token("" + string[i], filename, lineNum, TokenType.REL_OP);
                            }
                        }
                        case '=' -> {
                            if (i + 1 == string.length) {
                                throw new SyntaxException("Syntax Error: File ends in invalid character, missing =");
                            }
                            if (string[i + 1] == '=') {
                                token = new Token("" + string[i] + string[i + 1], filename, lineNum, TokenType.REL_OP);
                                i++;
                            } else {
                                token = new Token("" + string[i], filename, lineNum, TokenType.ASSIGN);
                            }
                        }
                        case '!' -> {
                            if (i + 1 == string.length) {
                                throw new SyntaxException("Syntax Error: File ends in invalid character, missing =");
                            }
                            new Token("" + string[i] + string[i + 1], filename, lineNum, TokenType.REL_OP); //todo the case of = (notEquals)
                        }
                        case '"' -> new Token("" + string[i], filename, lineNum, TokenType.STRING); //todo loop the string and the case of " (string)
                        case '.' -> {
                            if (i + 1 == string.length) {
                                throw new SyntaxException("Syntax Error: File ends in invalid character, missing digit");
                            }
                            if (Character.isDigit(string[i + 1])){ //passes = accept state
                                String s = "" + string[i] + string[i + 1];
                                i++;
                                token = digitCheck(s, string, i, filename, lineNum);
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
                                    token = digitCheck(s.toString(), string, i, filename, lineNum);
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
            System.err.println(e.getMessage());
        }
        return tokens;
    }

    /**
     * Builds the digit string
     * @param s Current digit string
     * @param string Current token string
     * @param i Index of the char in the file
     * @param filename Name of the file
     * @param lineNum Current line number
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