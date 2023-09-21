import provided.JottTokenizer;
import provided.Token;
import java.util.ArrayList;

/**
 *  This class is used for handling the tokenization for Jott, Java, Python, and C files
 * */
public class Jott {
    /**
     * Takes in an input file, output file, and a target language to call the corresponding tokenizer
     * @param args The command line arguments (inputFileName, outputFileName, and languageSpec)
     */
    public static void main(String[] args) {
        String inputFilename = args[0]; // the name of the user's input file
        String outputFilename = args[1]; // the name of the user's output file
        String languageSpec = args[2]; // the language that we will translate into

        switch (languageSpec) {
            case "Jott":
                if (inputFilename.split("\\.")[1] != "Jott") {
                    System.err.println("Incorrect File Extension");
                    return;
                }
                ArrayList<Token> tokenList = JottTokenizer.tokenize(inputFilename);
            case "Java": // TODO add case for Java tokenizer
            case "Python": // TODO add case for Python tokenizer
            case "C": // TODO add case for C tokenizer
        }
    }
}
