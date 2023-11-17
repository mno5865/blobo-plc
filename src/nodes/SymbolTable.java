package nodes;

import errors.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTable {
    //takes in funcName and (param types and return) and returns the variables for the function
    private static HashMap<List<String>, HashMap<String, VariableInfo>> funcDefinitions = new HashMap<>();
    private static HashMap<List<String>, HashMap<String, VariableInfo>> funcParamVariables = new HashMap<>();

    private static HashMap<List<String>, String> funcReturnTypes = new HashMap<>();

    private static List<String> scopeFunc; //this should be set to the current function definition we're in

    public static void setFunction(IDNode funcName, List<String> funcParamTypes, List<String> funcParamNames,
                                   String returnType) throws SemanticException {
        if (Character.isUpperCase(funcName.getName().toCharArray()[0]))
            throw new SemanticException("Function header must start with a lowercase letter",
                    funcName.getToken());

        HashMap<String, VariableInfo> variables = new HashMap<>();

        for (int i = 0; i < funcParamNames.size(); i++) {
            if (Character.isUpperCase(funcParamNames.get(i).toCharArray()[0]))
                throw new SemanticException("All variables in function header must start with a lowercase letter",
                        funcName.getToken());
            variables.put(funcParamNames.get(i), new VariableInfo(funcParamTypes.get(i)));
        }

        List<String> functionDefinition = new ArrayList<>();
        functionDefinition.add(funcName.getName());
        functionDefinition.addAll(funcParamTypes);

        scopeFunc = functionDefinition;
        funcReturnTypes.put(functionDefinition, returnType);
        funcDefinitions.put(functionDefinition, variables);
        funcParamVariables.put(functionDefinition, variables);
    }

    public static void setScope(IDNode funcName, List<String> funcParamTypes) {
        List<String> functionDefinition = new ArrayList<>();
        functionDefinition.add(funcName.getName());
        functionDefinition.addAll(funcParamTypes);
        scopeFunc = functionDefinition;
    }

    public static void addVariable(String varType, String varName, ExprNode expr) throws SemanticException {
        if (doesVarExistInScope(varName)){
            throw new SemanticException("variable is already defined", expr.getToken().getFilename(),
                    expr.getToken().getLineNum());
        }
        if (Character.isUpperCase(varName.toCharArray()[0]))
            throw new SemanticException("All variables defined must start with a lowercase letter",
                    expr.getToken());
        HashMap<String, VariableInfo> existingVariables = funcDefinitions.get(scopeFunc);
        existingVariables.put(varName, new VariableInfo(varType, expr));
    }

    public static boolean doesFunctionExist(List<String> funcDefinition) {
        boolean printCheck = funcDefinition.get(0).equals("print") && TypeNode.validType(funcDefinition.get(1)) &&
                !funcDefinition.get(0).equals("Void") && funcDefinition.size() == 2;

        boolean concatCheck = funcDefinition.get(0).equals("concat") && funcDefinition.get(1).equals("String")
                && funcDefinition.get(2).equals("String") && funcDefinition.size() == 3;

        boolean lengthCheck = funcDefinition.get(0).equals("length") && funcDefinition.get(1).equals("String") &&
                funcDefinition.size() == 2;

        return printCheck || concatCheck || lengthCheck || funcDefinitions.containsKey(funcDefinition);
    }

    public static String getFunctionReturnType(IDNode funcName, ParamNode params) throws SemanticException {
        List<String> functionDefinition = new ArrayList<>();
        functionDefinition.add(funcName.getName());
        functionDefinition.addAll(params.getParamTypes());
        if (!doesFunctionExist(functionDefinition))
            throw new SemanticException("The function " + funcName.getName() +
                    " does not exist with the given parameters", funcName.getToken());
        if (funcName.getToken().getTokenString().equals("print")) return "Void";
        if (funcName.getToken().getTokenString().equals("length")) return "Integer";
        if (funcName.getToken().getTokenString().equals("concat")) return "String";
        return funcReturnTypes.get(functionDefinition);
    }

    public static boolean doesVarExistInScope(String varName) {
        return funcDefinitions.get(scopeFunc).containsKey(varName);
    }

    public static boolean varIsParamVariable(String varName) {
        return funcParamVariables.get(scopeFunc).containsKey(varName);
    }

    public static String getVariableType(String varName) {
        return funcDefinitions.get(scopeFunc).get(varName).getType();
    }

    public static ExprNode getVariableValue(String varName) {
        return funcDefinitions.get(scopeFunc).get(varName).getValue();
    }

    public static void setVariable(String varName, ExprNode expr) {
        funcDefinitions.get(scopeFunc).replace(varName, new VariableInfo(getVariableType(varName), expr));
    }
}
