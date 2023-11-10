import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *  This class is used for handling the tokenization for Jott, Java, Python, and C files
 * */
public class Jott {
    /**
     * Takes in an input file, output file, and a target language to call the corresponding tokenizer
     * @param args The command line arguments (inputFileName, outputFileName, and languageSpec)
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Incorrect number of args. Expected form: inputFile outputFile language");
            return;
        }
        String inputFilename = args[0]; // the name of the user's input file
        String outputFilename = args[1]; // the name of the user's output file
        String languageSpec = args[2]; // the language that we will translate into

        switch (languageSpec) {
            case "Jott":
                if (!Objects.equals(inputFilename.split("\\.")[1], "jott")) {
                    System.err.println("Incorrect File Extension");
                    return;
                }
                ArrayList<Token> tokenList = JottTokenizer.tokenize(inputFilename);
                if (tokenList == null) {
                    System.err.println("Expected a list of tokens, but got null");
                    return;
                }
                JottTree root = JottParser.parse(tokenList);
                if (root == null) return;
                String jottCode = root.convertToJott();
                try {
                    FileWriter writer = new FileWriter(outputFilename);
                    if (jottCode == null) {
                        System.err.println("Expected a program string; got null");
                    }
                    writer.write(jottCode);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case "Java":
            case "Python":
            case "C":
        }
    }
}
