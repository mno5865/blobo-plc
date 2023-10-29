package nodes;

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

    public static boolean doesFunctionExist(List<String> funcDefinition) {
        return funcDefinitions.containsKey(funcDefinition);
    }

    public static String getFunctionReturnType() {
        return funcReturnTypes.get(scopeFunc);
    }

    public static boolean doesVarExistInScope(String varName) {
        return funcDefinitions.get(scopeFunc).containsKey(varName);
    }

    public static String getVariableReturnType(String varName) {
        //todo do a semantic exception for if the variable doesn't exist
        return funcDefinitions.get(scopeFunc).get(varName);
    }
}
