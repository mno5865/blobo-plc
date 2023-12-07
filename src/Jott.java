import nodes.BodyNode;
import provided.JottParser;
import provided.JottTokenizer;
import provided.JottTree;
import provided.Token;

import java.io.File;
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
        String[] input = new File(inputFilename).getName().split("\\.");

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
        if (root == null) {
            System.err.println("Expected a JottTree, but got null");
            return;
        }

        String code = "";
        switch (languageSpec) {
            case "Jott" -> code = root.convertToJott();
            case "Java" -> {
                BodyNode.setIndentationLevel(1);
                String[] outputPath = outputFilename.split(System.getProperty("file.separator"));
                String className = outputPath[outputPath.length - 1];
                code = "public class " + className.split("\\.")[0] + " {\n";
                code += root.convertToJava();
                code += "}";
            }
            case "Python" -> code = root.convertToPython();
            case "C" -> {
                code = """
                        #include <stdlib.h>
                        #include <stdio.h>
                        #include <stdbool.h>
                        #include <string.h>
                        
                        """;
                code += root.convertToC();
            }
        }

        // for debugging purposes be sure to remove before phase 4 submission
//        System.out.println(code);
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
