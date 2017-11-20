import java.util.ArrayList;
import java.util.List;

public class BlackListedFunctions {
	private List<String> blackListedFunctionNames=new ArrayList<String>();

	public BlackListedFunctions(List<String> blackListedFunctionNames) {
		super();
		this.blackListedFunctionNames = blackListedFunctionNames;
	}
	public BlackListedFunctions() {
		blackListedFunctionNames.add("getBytes");
		blackListedFunctionNames.add("toString");
		blackListedFunctionNames.add("write");
		blackListedFunctionNames.add("get");
		blackListedFunctionNames.add("add");
		blackListedFunctionNames.add("printStackTrace");
		blackListedFunctionNames.add("append");
		blackListedFunctionNames.add("nanoTime");
		blackListedFunctionNames.add("split");
		blackListedFunctionNames.add("equals");
		blackListedFunctionNames.add("size");
		blackListedFunctionNames.add("sleep");
	}
	public boolean contains(String name) {
		return blackListedFunctionNames.contains(name);
	}
}
