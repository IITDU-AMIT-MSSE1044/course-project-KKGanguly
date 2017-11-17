public class KProfileGraphBuilder {
	private CallGraph callGraph;

	public KProfileGraphBuilder(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
	}
	
	public CallGraph buildKProfileGraph() {
		buildRecursively(callGraph.getRoot());
		return callGraph;
	}

	private void buildRecursively(Function callingContextNode) {
		// TODO Auto-generated method stub
		for (Function function : callingContextNode.getCalledFunctions()) {
			/*System.out.println(currentNode.getName());
			System.out.println("CALLED:"+function.getName());*/
			if(callingContextNode.getComplexityModelDatas().isEmpty()) {
				ComplexityModelData complexityModelData=new ComplexityModelData();
				complexityModelData.addCallingContextFunction(callingContextNode);
				function.addComplexityModelData(complexityModelData);
			}
			else {
			for (ComplexityModelData parentComplexityModel : callingContextNode.getComplexityModelDatas()) {
				ComplexityModelData complexityModelData = new ComplexityModelData();
				complexityModelData.addAllCallingContextFunctions(parentComplexityModel.getCallingContext());
				complexityModelData.addCallingContextFunction(callingContextNode);
					if (!function.hasComplexityModelData(complexityModelData)) {
						function.addComplexityModelData(complexityModelData);
					}
			}
			}
			buildRecursively(function);
		}
	}
}
