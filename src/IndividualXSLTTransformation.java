import java.io.IOException;

import util.ProcessLogger;

public class IndividualXSLTTransformation implements XSLTTransformation {
	private static final String FILEPATH="logs/callprofile.txt";
	@Override
	public boolean transform(String xsltName, String sourceFileName) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Process proc = Runtime.getRuntime().exec("srcml --xslt " + xsltName + " " + sourceFileName + ".xml " + " -o "
				+ sourceFileName + "-transformed.xml");
		proc.waitFor();
		ProcessLogger.printProcessOutputToFile(FILEPATH, proc);
		return true;
	}

}
