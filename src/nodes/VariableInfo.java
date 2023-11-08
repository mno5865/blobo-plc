package nodes;

public class VariableInfo {
    private final String type;
    private ExprNode value;

    public VariableInfo(String type, ExprNode value) {
        this.type = type;
        this.value = value;
    }

    public VariableInfo(String type) {
        this.type = type;
    }

    public boolean hasValueSet() {
        return value != null;
    }

    public String getType() {
        return type;
    }

    public ExprNode getValue() {
        return value;
    }
}
