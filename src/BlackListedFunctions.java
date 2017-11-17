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
	}
	public boolean contains(String name) {
		return blackListedFunctionNames.contains(name);
	}
}
