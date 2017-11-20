import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import util.FileUtil;

public class XSLTTransformer {
	private String sourceDirectoryPath="src\\secondsample";
	private String xsltResourcePath="src\\xslt";
	private File source;
	private File destination;
	private File xsltResource;
	private String sourceXMLDirectoryName="Transformed Source XMLs";
	private String sourceDirectoryName="Transformed Source";
	private static final String LOG = "logs/callprofile.txt";
	public XSLTTransformer() {
		source=new File(sourceDirectoryPath);
		destination=new File(System.getProperty("user.dir"));
		xsltResource=new File(xsltResourcePath);
	}
	public void transform() throws IOException, InterruptedException {
		clearLog();
		transform(source);
		siblingDelete(destination);
		convertToSource(destination);
		organizeSourceXMLs(destination);
		organizeSource(destination);
	}
	private void clearLog() {
		FileUtil fileUtil=FileUtil.getFileUtil(LOG);
		fileUtil.clearFile();
	}
	private void organizeSourceXMLs(File directory) throws IOException {
		String directoryPath=directory+File.separator+sourceXMLDirectoryName+"_"+System.currentTimeMillis();
		File directoryToCopy = new File(directoryPath);
		if(!directoryToCopy.exists()) {
			directoryToCopy.mkdirs();
		}
		for(File sourceFile:directory.listFiles()) {
			if(sourceFile.getName().endsWith(".java.xml")) {
				FileUtil.move(sourceFile, directoryToCopy);
			}
		}
	}
	private void organizeSource(File directory) throws IOException {
		String directoryPath=directory+File.separator+sourceDirectoryName+"_"+System.currentTimeMillis()+File.separator+"src";
		File directoryToCopy = new File(directoryPath);
		if(!directoryToCopy.exists()) {
			directoryToCopy.mkdirs();
		}
		for(File sourceFile:directory.listFiles()) {
			if(sourceFile.getName().endsWith(".java")) {
				
				FileUtil.move(sourceFile, directoryToCopy);
			}
		}
	}
	private void convertToSource(File directory) throws IOException, InterruptedException {
		for(File sourceFile:directory.listFiles()) {
			String fileName = sourceFile.getName();
			if(sourceFile.isFile() && fileName.endsWith(".xml")) {
				Process process=Runtime.getRuntime().exec("srcml "+fileName+" -o "+fileName.substring(0,fileName.indexOf(".xml")));
				process.waitFor();
			}
		}
	}
	private void transform(File file) throws IOException, InterruptedException {
		for(File sourceFile:source.listFiles()) {
			if(sourceFile.isDirectory()) {
				transform(sourceFile);
			}
			else if(sourceFile.isFile()){
				FileUtil.copy(sourceFile, destination);
				convertToXML(sourceFile.getName());
				transform(sourceFile.getName());
				
			}
		}
	}
	private void siblingDelete(File file) throws IOException, InterruptedException {
		for(File sourceFile:file.listFiles()) {
			if(sourceFile.isFile()&&(sourceFile.getName().endsWith(".xsl")||sourceFile.getName().endsWith("-transformed.xml"))||sourceFile.getName().endsWith(".java")){
				
				sourceFile.delete();
				
			}
		}
	}

	private void convertToXML(String fileName) throws IOException, InterruptedException {
		Process process=Runtime.getRuntime().exec("srcml "+fileName+" -o "+fileName+".xml");
		process.waitFor();
		
	}
	private void transform(String fileName) throws IOException {
		for(File xsltFile:xsltResource.listFiles()) {
			if(xsltFile.isFile()){
				FileUtil.copy(xsltFile, destination);
				try {
					boolean status=new XSLTTransformationSelector().selectTransformationMethod(xsltFile.getName()).transform(xsltFile.getName(), fileName);
					if(status) {
					FileUtil fileUtil=FileUtil.getFileUtil(destination.getAbsolutePath()+File.separator+fileName+".xml");
					FileUtil fileUtilTransformed=FileUtil.getFileUtil(destination.getAbsolutePath()+File.separator+fileName+"-transformed.xml");
					
					String transformedContent=fileUtilTransformed.readFile();
					if (!transformedContent.trim().isEmpty()&&transformedContent.split("\n+").length>2) {
						/*System.err.println(xsltFile.getName() + " " + fileName);
						System.err.println(transformedContent);*/
						fileUtil.write(transformedContent);
					}
					if(new File(destination.getAbsolutePath()+File.separator+fileName+"-transformed.xml").exists()) {
					FileUtils.forceDelete(new File(destination.getAbsolutePath()+File.separator+fileName+"-transformed.xml"));
					}
					}
					//fileUtilTransformed.clearFile();;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
}
