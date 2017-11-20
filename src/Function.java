import java.util.ArrayList;
import java.util.List;

public class Function {
	private String name;
	private String className;
	private List<Function> calledFunctions = new ArrayList<Function>();
	private List<Function> calledBy = new ArrayList<Function>();
	private List<ComplexityModelData> complexityModelDatas = new ArrayList<ComplexityModelData>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void addCalledFunction(Function function) {
		calledFunctions.add(function);
	}

	public void removeCalledFunction(Function function) {
		calledFunctions.remove(function);
	}

	public void addCalledByFunction(Function function) {
		calledBy.add(function);
	}

	public void removeCalledByFunction(Function function) {
		calledBy.remove(function);
	}

	public List<Function> getCalledFunctions() {
		return calledFunctions;
	}

	public List<Function> getCalledBy() {
		return calledBy;
	}

	public List<ComplexityModelData> getComplexityModelDatas() {
		return complexityModelDatas;
	}
	public boolean hasComplexityModelData(ComplexityModelData complexityModelDataSearched) {
		for (ComplexityModelData complexityModelData : complexityModelDatas) {
			if(complexityModelData.getCallingContext().equals(complexityModelDataSearched.getCallingContext())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return className+","+name;
	}
	public void setComplexityModelDatas(List<ComplexityModelData> complexityModelDatas) {
		this.complexityModelDatas = complexityModelDatas;
	}
	public void addComplexityModelData(ComplexityModelData complexityModelData) {
		complexityModelDatas.add(complexityModelData);
	}
}
