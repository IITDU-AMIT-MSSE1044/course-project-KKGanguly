import java.io.IOException;

public class ComplexityModelDataGenerator {
	private CallGraph callGraph;

	public ComplexityModelDataGenerator(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
	}
	
	public void generateData() {
		for (Function function : callGraph.getFunctions()) {
			System.out.println("FUNCTION:"+function.getName());
			for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
				System.out.println(complexityModelData.getCallingContextAsString());
			}
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException, CallGraphException {
		new XSLTTransformer().transform();
		CallGraph callGraph = new StaticCallGraphExtractor().extractCallGraph();
		KProfileGraphBuilder graphBuilder=new KProfileGraphBuilder(callGraph);
		callGraph=graphBuilder.buildKProfileGraph();
		new ComplexityModelDataGenerator(callGraph).generateData();;
	}
}
