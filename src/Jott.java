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
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Incorrect number of args. Expected form: inputFile outputFile language");
            return;
        }
        String inputFilename = args[0]; // the name of the user's input file
        String outputFilename = args[1]; // the name of the user's output file
        String languageSpec = args[2]; // the language that we will translate into
        String[] input = inputFilename.split("\\.");

        if (input.length != 2) throw new Exception("Input file was entered in incorrectly");
        if (!Objects.equals(input[1], "jott")) {
            System.err.println("Incorrect File Extension");
            return;
        }

        ArrayList<Token> tokenList = JottTokenizer.tokenize(inputFilename);
        if (tokenList == null) {
            System.err.println("Expected a list of tokens, but got null");
            return;
        }
        JottTree root = JottParser.parse(tokenList);
        assert root != null;
        String code = "";
        switch (languageSpec) {
            case "Jott" -> code = root.convertToJott();
            case "Java" -> {
                code = "public class " + input[0] + " {\n\t";

                code += root.convertToJava(input[0]);
                code += "\n}";
            }
            case "Python" -> code = root.convertToPython();
            case "C" -> {
                code = "#include <stdlib.h>\n" +
                        "#include <stdio.h>\n" +
                        "#include <string.h>\n" +
                        "#include <stdbool.h>";
                code += root.convertToC();
            }
        };

        System.out.println(code);
        try {
            FileWriter writer = new FileWriter(outputFilename);
            if (code == null) {
                System.err.println("Expected a program string; got null");
            } else {
                writer.write(code);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
