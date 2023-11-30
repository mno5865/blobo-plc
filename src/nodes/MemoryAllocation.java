package nodes;

import errors.SemanticException;

import java.util.ArrayList;

public class MemoryAllocation {
    public static int variableCounter = 1;

    public static String lastVariable = "";

    public static ArrayList<String> handleConcat(ExprNode expr) throws SemanticException {
        ArrayList<String> linesWithAllocation = handleConcatRecursive(expr);
        return linesWithAllocation;
    }

    private static ArrayList<String> handleConcatRecursive(ExprNode expr) throws SemanticException {
        ArrayList<String> outputLines = new ArrayList<>();
        outputLines.add("");
        if (expr instanceof FuncCallNode && functionContainsConcat((FuncCallNode) expr)) {
            FuncCallNode funcCallExpr = (FuncCallNode) expr;
            for (ExprNode param : funcCallExpr.getParams().getParams()) {
                if (param.checkForConcat()) {
                    ArrayList<String> outputInner = handleConcatRecursive(param);
                    outputLines.set(0, outputLines.get(0) + outputInner.get(0));
                    outputInner.remove(0);
                    outputLines.addAll(outputInner);
                }
            }
        }

        if (expr instanceof FuncCallNode && ((FuncCallNode)expr).getName().equals("concat")) {
            FuncCallNode funcCallExpr = (FuncCallNode) expr;
            while (SymbolTable.doesVarExistInScope("stringToConcatTo" + variableCounter)) variableCounter++;
            String name = "stringToConcatTo" + variableCounter;
            SymbolTable.addVariable("String", name, funcCallExpr);
            outputLines.add(name);
            String endOfLine = ";\n" + BodyNode.getTabs();
            String firstParam = funcCallExpr.getParam(0).convertToC();
            String secondParam =funcCallExpr.getParam(1).convertToC();
            if (funcCallExpr.getParam(0).checkForConcat()) {
                firstParam = outputLines.get(1);
                outputLines.remove(1);
            } if (funcCallExpr.getParam(1).checkForConcat()) {
                firstParam = outputLines.get(1);
                outputLines.remove(1);
            }
            String firstConcat = "strcpy(" + name + ", " + firstParam + ")" + endOfLine;
            String secondConcat = "strcat(" + name + ", " + secondParam + ")" + endOfLine;
            String firstMalloc = "(char*)malloc(sizeof(char) * " +
                    String.format("(strlen(%s) + ", firstParam) +
                    String.format("strlen(%s))", secondParam) + ")";
            outputLines.set(0, outputLines.get(0) + "char* " + name + " = " + firstMalloc + endOfLine);
            outputLines.set(0, outputLines.get(0) + firstConcat);
            outputLines.set(0, outputLines.get(0) + secondConcat);
            return outputLines;
        }

        //expr must be a BinaryOperationNode at this point
        if (expr instanceof BinaryOperationNode) {
            BinaryOperationNode exprOperation = (BinaryOperationNode) expr;
            boolean leftConcat = exprOperation.getLeftExpr().checkForConcat();
            boolean rightConcat = exprOperation.getLeftExpr().checkForConcat();
            String outputLinesWithAllocation = "";
            if (leftConcat) outputLinesWithAllocation += handleConcatRecursive(exprOperation.getLeftExpr());
            if (rightConcat) outputLinesWithAllocation += handleConcatRecursive(exprOperation.getRightExpr());
            outputLines.set(0, outputLines.get(0) + outputLinesWithAllocation);
        }


        return outputLines;
    }

    public static void resetCounter() {
        variableCounter = 1;
    }

    public static boolean functionContainsConcat(FuncCallNode funcCallExpr) {
        for (ExprNode param : funcCallExpr.getParams().getParams()) {
            if (param.checkForConcat()) return true;
        }
        return false;
    }

    public static String getLastVariable() {
        return lastVariable;
    }

    public static void setLastVariable(String variable) {
        lastVariable = variable;
    }

    public static void clearLastVariable() {
        lastVariable = null;
    }
}
