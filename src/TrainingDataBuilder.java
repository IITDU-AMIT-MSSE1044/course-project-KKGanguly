import java.io.File;
import java.util.List;
import java.util.Map;

import util.FileUtil;

public class TrainingDataBuilder {
	private CallGraph callGraph;
	private static final String TRAINING_DIRECTORY="training";
	private static final String TRAINING_DIRECTORY_MULTIATTRIBUTE="trainingextendedmulti";
	public TrainingDataBuilder(CallGraph callGraph) {
		super();
		this.callGraph = callGraph;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		File directory=new File(TRAINING_DIRECTORY);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		File directoryMultiAttribute=new File(TRAINING_DIRECTORY_MULTIATTRIBUTE);
		if(!directoryMultiAttribute.exists()) {
			directoryMultiAttribute.mkdirs();
		}
	}

	public void buildTrainingData() {
		for (Function function : callGraph.getFunctions()) {
			for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
				
				String trainingFilePath=TRAINING_DIRECTORY+File.separator+complexityModelData.getCallingContextAsString().trim()+" "+function+".csv";
				FileUtil fileUtil=FileUtil.getFileUtil(trainingFilePath);
				String content ="";
				Map<List<Double>,Integer> workloadExecutionCountMap=complexityModelData.getWorkLoadToExecutionCountMap();
				for (List<Double> workloadAttributes : workloadExecutionCountMap.keySet()) {
					for (Double workloadAttribute : workloadAttributes) {
						content+=workloadAttribute+",";
					}

					content+=workloadExecutionCountMap.get(workloadAttributes)+"\n";
					
				}
				if(!content.isEmpty()) {
				fileUtil.clearFile();
				fileUtil.appendFile("workload,Count");
				
				fileUtil.appendFile(content);
			}
			}
		}
	}
	public void buildTrainingDataMultiAttribute() {
		for (Function function : callGraph.getFunctions()) {
			for (ComplexityModelData complexityModelData : function.getComplexityModelDatas()) {
				
				String trainingFilePath=TRAINING_DIRECTORY_MULTIATTRIBUTE+File.separator+complexityModelData.getCallingContextAsString().trim()+" "+function+".csv";
				FileUtil fileUtil=FileUtil.getFileUtil(trainingFilePath);
				String content ="";
				int columnNo=0;
				Map<List<Double>,Integer> workloadExecutionCountMap=complexityModelData.getWorkLoadToExecutionCountMap();
				for (List<Double> workloadAttributes : workloadExecutionCountMap.keySet()) {
					columnNo=workloadAttributes.size();
					for (Double workloadAttribute : workloadAttributes) {
						content+=workloadAttribute+",";
					}

					content+=workloadExecutionCountMap.get(workloadAttributes)+"\n";
					
				}
				if(!content.isEmpty()) {
				String header="";
				for (int i=1;i<=columnNo;i++) {
					header+="workload"+i+",";
				}
				header+="Count";
				fileUtil.clearFile();
				fileUtil.appendFile(header);
				fileUtil.appendFile(content);
			}
			}
		}
	}
	
}

