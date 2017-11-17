import java.util.ArrayList;
import java.util.List;

public class ComplexityModelData {
	private List<Double> workloadParameters = new ArrayList<Double>();
	private List<Function> callingContext = new ArrayList<Function>();
	private int executionCount;

	public List<Double> getWorkloadParameters() {
		return workloadParameters;
	}

	public void setWorkloadParameters(List<Double> workloadParameters) {
		this.workloadParameters = workloadParameters;
	}

	public List<Function> getCallingContext() {
		return callingContext;
	}

	public String getCallingContextAsString() {
		String callingContextString = "";
		for (Function function : callingContext) {
			callingContextString += function + " ";
		}
		return callingContextString;
	}

	public void setCallingContext(List<Function> callingContext) {
		this.callingContext = callingContext;
	}

	public int getExecutionCount() {
		return executionCount;
	}

	public void setExecutionCount(int executionCount) {
		this.executionCount = executionCount;
	}

	public void addWorkload(Double parameterValue) {
		workloadParameters.add(parameterValue);
	}

	public void addCallingContextFunction(Function function) {
		callingContext.add(function);
	}

	public void addAllCallingContextFunctions(List<Function> functions) {
		callingContext.addAll(functions);
	}
}
