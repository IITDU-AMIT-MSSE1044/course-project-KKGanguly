package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Executor {
	private WDPBTest wdpbTest;
	private int numberOfMetadatas;
	public Executor(int numberOfMetadatas) {
		this.numberOfMetadatas=numberOfMetadatas;
	}
	private void init() {
		// TODO Auto-generated method stub
		try {
			Files.write(Paths.get("log.txt"), "Executor,init ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<MetaData> metaDatas=new ArrayList<MetaData>();
		for(int i=0;i<numberOfMetadatas;i++) {
			metaDatas.add(getGeneratedMetadata());
		}
		wdpbTest=new WDPBTest(metaDatas);
		try {
			Files.write(Paths.get("log.txt"), "exit-Executor,init ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void run() {
		try {
			Files.write(Paths.get("log.txt"), "Executor,run ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();
		List<MetaData> metaDatas=new ArrayList<MetaData>();
		for(int i=0;i<numberOfMetadatas;i++) {
			metaDatas.add(getGeneratedMetadata());
		}
		for (MetaData metaData : metaDatas) {
			wdpbTest.handleAllSelect(metaData);
		}
		try {
			Files.write(Paths.get("log.txt"), "exit-Executor,run ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private MetaData getGeneratedMetadata() {
		try {
			Files.write(Paths.get("log.txt"), "Executor,getGeneratedMetadata ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> statuses=new ArrayList<String>();
		MetaData metaData=new MetaData();
		metaData.itemName="item"+new Random().nextInt();
		for(int i=0;i<1000;i++) {
			statuses.add("status"+new Random().nextInt());
		}
		metaData.compositeStatus=statuses;
		try {
			Files.write(Paths.get("log.txt"), "exit-Executor,getGeneratedMetadata ".getBytes(),StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metaData;
	}
}
