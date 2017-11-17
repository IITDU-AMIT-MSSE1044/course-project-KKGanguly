import java.util.ArrayList;
import java.util.List;

public class CallGraph {
	private Function root;
	private List<Function> functions = new ArrayList<Function>();

	public Function getRoot() {
		return root;
	}

	public void setRoot(Function root) {
		this.root = root;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public void addFunction(Function function) {
		if (function != null) {
			functions.add(function);
		}
	}

	public void removeFunction(Function function) {
		if (function != null) {
			functions.remove(function);
		}
	}

	public Function search(String functionName) {
		for (Function function : functions) {
			if (function.getName().equals(functionName)) {
				return function;
			}
		}
		return null;
	}

	public Function searchByClassName(String functionName, String className) {
		for (Function function : functions) {
			if (function.getName().equals(functionName) && function.getName().equals(className)) {
				return function;
			}
		}
		return null;
	}
}
