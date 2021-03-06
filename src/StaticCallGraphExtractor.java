import util.FileUtil;

public class StaticCallGraphExtractor implements CallGraphExtractor {
	private static final String LOG = "logs/callprofile.txt";
	private String rootName = "run";
	private String rootClassName = "NotePadExecutor";
	private CallGraph callGraph = new CallGraph();
	private BlackListedFunctions BlackListedFunctions = new BlackListedFunctions();
	
	public StaticCallGraphExtractor(String rootName, String rootClassName) {
		super();
		this.rootName = rootName;
		this.rootClassName = rootClassName;
	}

	@Override
	public CallGraph extractCallGraph() throws CallGraphException {
		// TODO Auto-generated method stub
		FileUtil fileUtil = FileUtil.getFileUtil(LOG);
		String content = fileUtil.readFile();
		String[] lines = content.split("\n");
		Function function = new Function();
		for (String line : lines) {
			if (line.startsWith("Function ")) {
				String[] functionSpec = line.split("\\s+");
				function = callGraph.search(functionSpec[1]);
				callGraph.removeFunction(function);
				if (function == null || !function.getClassName().equals(functionSpec[3])) {
					function = new Function();
					function.setName(functionSpec[1]);
					function.setClassName(functionSpec[3]);
				}
				callGraph.addFunction(function);
			} else {
				String[] functionSpec = line.split(",");
				if (!BlackListedFunctions.contains(functionSpec[0])) {
					Function calledFunction = callGraph.search(functionSpec[0]);
					callGraph.removeFunction(calledFunction);
					if (calledFunction == null || !calledFunction.getClassName().equals(functionSpec[1])) {
						calledFunction = new Function();
						calledFunction.setName(functionSpec[0]);
						calledFunction.setClassName(functionSpec[1]);
						calledFunction.addCalledByFunction(function);
					} else if (calledFunction.getClassName().equals(functionSpec[1])) {
						calledFunction.addCalledByFunction(function);
					}
					function.addCalledFunction(calledFunction);
					callGraph.addFunction(calledFunction);
				}
			}
		}
		callGraph.setRoot(findCallGraphRoot());
		if (callGraph.getRoot() == null) {
			throw new CallGraphException("Call Graph root not specified properly");
		}
		return callGraph;
	}

	private Function findCallGraphRoot() {
		for (Function function : callGraph.getFunctions()) {
			if (function.getName().equals(rootName) && function.getClassName().equals(rootClassName)) {
				return function;
			}
		}
		return null;
	}

}
