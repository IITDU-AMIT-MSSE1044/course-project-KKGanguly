import java.util.ArrayList;
import java.util.List;

public class ComplexityTransition {
	private String parent;
	private List<ComplexityTransition> childList=new ArrayList<ComplexityTransition>();
	private double parentComplexity;
	private double childrenComplexity = 0.0;

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<ComplexityTransition> getChildList() {
		return childList;
	}

	public void setChildList(List<ComplexityTransition> childList) {
		this.childList = childList;
	}

	public double getParentComplexity() {
		return parentComplexity;
	}

	public void setParentComplexity(double parentComplexity) {
		this.parentComplexity = parentComplexity;
	}

	public double getChildrenComplexity() {
		return childrenComplexity;
	}

	public void setChildrenComplexity(double childrenComplexity) {
		this.childrenComplexity = childrenComplexity;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return parent;
	}
}
