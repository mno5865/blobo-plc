package provided;

import errors.SyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JottTokenizer {
    public static ArrayList<Token> tokenize(String fileName) {
        ArrayList<Token> tokens;
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
                        case ':' -> {
                            if (string.length - 1 != i && string[i + 1] == ':') {
                                token = new Token("" + string[i] + string[i + 1], fileName, lineNum, TokenType.FC_HEADER);
                                i++;
                            } else {
                                token = new Token("" + string[i], fileName, lineNum, TokenType.COLON);
                            }
                        }
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
                                token = new Token("" + string[i] + string[i + 1], fileName, lineNum, TokenType.REL_OP);
                                i++;
                            } else {
                                throw new SyntaxException("Expected '=' after '!'", fileName, lineNum);
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
                                    throw new SyntaxException("Missing string end quotes", fileName, lineNum);
                                }
                            }
                            token = new Token(str.toString(), fileName, lineNum, TokenType.STRING);
                        }
                        case '.' -> {
                            if (i + 1 != string.length && Character.isDigit(string[i + 1])) {
                                StringBuilder sBuilder = new StringBuilder("" + string[i] + string[i + 1]);
                                i++;

                                while (i < string.length && Character.isDigit(string[i + 1])) {
                                    sBuilder.append(string[i + 1]);
                                    i++;
                                }
                                token = new Token(sBuilder.toString(), fileName, lineNum, TokenType.NUMBER);
                            } else {
                                throw new SyntaxException("Digit required after . token", fileName, lineNum);
                            }
                        }
                        case '#' -> {
                            char currentChar = string[i];
                            int skip = 0;
                            while (currentChar != '\n' && skip + 1 < string.length) {
                                currentChar = string[skip + 1];
                                skip++;
                            }
                            i += skip;
                            continue;
                        }
                        case ' ' -> {
                            continue;
                        }
                        default -> {
                            if (Character.isDigit(string[i])) {
                                StringBuilder sBuilder = new StringBuilder("" + string[i]);

                                while (i + 1 < string.length && Character.isDigit(string[i + 1])) {
                                    sBuilder.append(string[i + 1]);
                                    i++;
                                }

                                if (i + 1 < string.length && string[i + 1] == '.') {
                                    sBuilder.append('.');
                                    i++;
                                    while (i + 1 < string.length && Character.isDigit(string[i + 1])) {
                                        sBuilder.append(string[i + 1]);
                                        i++;
                                    }
                                }
                                token = new Token(sBuilder.toString(), fileName, lineNum, TokenType.NUMBER);
                            } else if (Character.isAlphabetic(string[i])) {
                                StringBuilder str = new StringBuilder("" + string[i]);
                                while (i + 1 < string.length && (Character.isDigit(string[i + 1]) || Character.isAlphabetic(string[i + 1]))) {
                                    str.append(string[i + 1]);
                                    i++;
                                }
                                token = new Token(str.toString(), fileName, lineNum, TokenType.ID_KEYWORD);
                            }
                        }
                    }
                    tokens.add(token);
                }
                lineNum++;
            }
        } catch (FileNotFoundException | SyntaxException e) {
            System.err.println(e);
            return null;
        }
        return tokens;
    }
}