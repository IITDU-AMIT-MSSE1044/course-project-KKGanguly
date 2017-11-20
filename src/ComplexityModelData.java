import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexityModelData {
	private List<Function> callingContext = new ArrayList<Function>();
	private Map<List<Double>, Integer> workLoadToExecutionCountMap = new HashMap<List<Double>, Integer>();
	private Map<List<Double>, Double> workLoadToCostMap = new HashMap<List<Double>, Double>();

	public Map<List<Double>, Integer> getWorkLoadToExecutionCountMap() {
		return workLoadToExecutionCountMap;
	}

	public void setWorkLoadToExecutionCountMap(Map<List<Double>, Integer> workLoadToExecutionCountMap) {
		this.workLoadToExecutionCountMap = workLoadToExecutionCountMap;
	}

	public List<Function> getCallingContext() {
		return callingContext;
	}

	public Map<List<Double>, Double> getWorkLoadToCostMap() {
		return workLoadToCostMap;
	}

	public void setWorkLoadToCostMap(Map<List<Double>, Double> workLoadToCostMap) {
		this.workLoadToCostMap = workLoadToCostMap;
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

	public void addWorkloadAndExecutionCount(List<Double> workloads, int executionCount) {
		workLoadToExecutionCountMap.put(workloads, executionCount);
	}

	public void addWorkloadAndCost(List<Double> workloads, double cost) {
		workLoadToCostMap.put(workloads, cost);
	}

	public void addCallingContextFunction(Function function) {
		callingContext.add(function);
	}

	public void addAllCallingContextFunctions(List<Function> functions) {
		callingContext.addAll(functions);
	}
}
