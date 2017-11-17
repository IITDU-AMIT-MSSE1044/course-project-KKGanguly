import java.io.IOException;

public interface XSLTTransformation {
	
	public boolean transform(String xsltName,String sourceFileName) throws IOException, InterruptedException;
}	
