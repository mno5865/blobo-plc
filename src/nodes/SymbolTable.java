package nodes;

import errors.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTable { //todo add built-ins to table on startup
    //takes in funcName and (param types and return) and returns the variables for the function
    private static HashMap<List<String>, HashMap<String, String>> funcDefinitions = new HashMap<>();

    private static HashMap<List<String>, String> funcReturnTypes = new HashMap<>();

    private static List<String> scopeFunc; //this should be set to the current function definition we're in

    public static void setFunction(String funcName, List<String> funcParamTypes, List<String> funcParamNames, String returnType) {
        HashMap<String, String> variables = new HashMap<>();

        for (int i = 0; i < funcParamNames.size(); i++) {
            variables.put(funcParamNames.get(i), funcParamTypes.get(i));
        }

        List<String> functionDefinition = new ArrayList<>();
        functionDefinition.add(funcName);
        functionDefinition.addAll(funcParamTypes);

        scopeFunc = functionDefinition;
        funcReturnTypes.put(functionDefinition, returnType);
        funcDefinitions.put(functionDefinition, variables);
    }

    public static void addVariable(String varType, String varName) throws SemanticException {
        if (doesVarExistInScope(varName)){
            throw new SemanticException("", "", -1); //todo yeah
        }
        HashMap<String, String> existingVariables = funcDefinitions.get(scopeFunc);
        existingVariables.put(varName, varType);
    }

    public static boolean doesFunctionExist(List<String> funcDefinition) {
        boolean printCheck = funcDefinition.get(0).equals("print") && TypeNode.validType(funcDefinition.get(1)) &&
                !funcDefinition.get(0).equals("Void") && funcDefinition.size() == 2; //todo ask scott if print can only take in 1 param

        boolean concatCheck = funcDefinition.get(0).equals("concat") && funcDefinition.get(1).equals("String")
                && funcDefinition.get(2).equals("String") && funcDefinition.size() == 3;

        boolean lengthCheck = funcDefinition.get(0).equals("length") && funcDefinition.get(1).equals("String") &&
                funcDefinition.size() == 2;

        return printCheck || concatCheck || lengthCheck || funcDefinitions.containsKey(funcDefinition);
    }

    public static String getFunctionReturnType() {
        return funcReturnTypes.get(scopeFunc);
    }

    public static boolean doesVarExistInScope(String varName) {
        return funcDefinitions.get(scopeFunc).containsKey(varName);
    }

    //todo add a semantic exception in the right class for the above/below functions when a variable that doesn't exist is called

    public static String getVariableType(String varName) {
        return funcDefinitions.get(scopeFunc).get(varName);
    }
}
