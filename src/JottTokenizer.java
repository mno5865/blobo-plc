package src;

public class JottTokenizer {
    public static void main(String[] args) {
        char[] string = new char[]{'/', '<', ':', ',', '1', '.', 'f'}; //todo get input from file
        // todo handle comments
        for (int i = 0; i < string.length; i++) {
            String printString = "";
            switch (string[i]) {
                case ',' -> printString = "comma";
                case ']' -> printString = "rBracket";
                case '[' -> printString = "lBracket";
                case '}' -> printString = "rbrace";
                case '{' -> printString = "lbrace";
                case ':' -> printString = "colon";
                case ';' -> printString = "semicolon";
                case '+', '-', '*', '/' -> printString = "mathOp";
                case '<', '>' -> {
                    if (string[i + 1] == '=') {
                        i++;
                        printString = "relOp";
                    } else {
                        printString = "relOp";
                    }
                }
                case '=' -> {
                    if (string[i + 1] == '=') {
                        i++;
                        printString = "relop";
                    } else {
                        printString = "assign";
                    }
                }
                case '!' -> printString = "notEquals"; //todo the case of = (notEquals)
                case '"' -> printString = "string"; //todo loop the string and the case of " (string)
                case '.' -> {
                    if (Character.isDigit(string[i + 1])) { //passes = accept state
                        i++;
                        printString = digitCheck(string, i);
                    } else {
                        printString = "error"; //todo change print to be more specific
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
                        i++;
                        while (i < string.length && Character.isDigit(string[i])) {
                            i++;
                        }

                        if (i < string.length && string[i] == '.') {
                            printString = digitCheck(string, i);
                        }
                    } else if (Character.isAlphabetic(string[i])) {
                        //todo case if letter, id or keyword
                    }
                }
            }
            System.out.println(printString);
        }
    }

    private static String digitCheck(char[] string, int i) {
        while (i + 1 < string.length && Character.isDigit(string[i + 1])) {
            i++;
        }
        return "number";
    }
}