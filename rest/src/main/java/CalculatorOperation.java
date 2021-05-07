import javax.ws.rs.ext.Provider;

@Provider
public class CalculatorOperation {

    public int operandA;
    public int operandB;
    public int result;
    public String operation;

    public CalculatorOperation(){}

    public CalculatorOperation(int operandA, int operandB){
        this.operandA = operandA;
        this.operandB = operandB;
    }

    void add() {
        operation = "addition";
        result = operandA + operandB;
    }
}
