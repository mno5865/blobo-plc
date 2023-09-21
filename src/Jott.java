import provided.JottTokenizer;
import provided.Token;

import java.sql.Array;
import java.util.ArrayList;

public class Jott {
    public static void main(String[] args) {
        String inputFilename = args[0]; // the name of the user's input file
        String outputFilename = args[1]; // the name of the user's output file
        String languageSpec = args[2]; // the language that we will translate into

        switch (languageSpec){
            case "Jott":
                ArrayList<Token> tokenList = JottTokenizer.tokenize(inputFilename);
        }
    }
}
