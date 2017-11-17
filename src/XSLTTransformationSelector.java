
public class XSLTTransformationSelector {
	public XSLTTransformation selectTransformationMethod(String xsltName) {
		return new IndividualXSLTTransformation();
	}
}
