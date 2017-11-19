package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
		GlobalInstrumentationString.contentBuffer.append("Executor,init ");
		List<MetaData> metaDatas=new ArrayList<MetaData>();
		for(int i=0;i<numberOfMetadatas;i++) {
			metaDatas.add(getGeneratedMetadata());
		}
		wdpbTest=new WDPBTest(metaDatas);
		GlobalInstrumentationString.contentBuffer.append("exit-Executor,init ");
	}
	public void run() {
		GlobalInstrumentationString.contentBuffer.append("Executor,run ");
		init();
		List<MetaData> metaDatas=new ArrayList<MetaData>();
		for(int i=0;i<numberOfMetadatas;i++) {
			metaDatas.add(getGeneratedMetadata());
		}
		for (int i=0;i<numberOfMetadatas;i++) {
			MetaData metaData=metaDatas.get(i);
			wdpbTest.handleAllSelect(metaData,numberOfMetadatas);
		}
		GlobalInstrumentationString.contentBuffer.append("exit-Executor,run ");
	}
	
	private MetaData getGeneratedMetadata() {
		GlobalInstrumentationString.contentBuffer.append("Executor,getGeneratedMetadata ");
		List<String> statuses=new ArrayList<String>();
		MetaData metaData=new MetaData();
		metaData.itemName="item"+new Random().nextInt();
		for(int i=0;i<1000;i++) {
			statuses.add("status"+new Random().nextInt());
		}
		metaData.compositeStatus=statuses;
		GlobalInstrumentationString.contentBuffer.append("exit-Executor,getGeneratedMetadata ");
		return metaData;
	}
	public static void apppendLargeFile(String content,String filePath) throws IOException {
		byte[] strBytes = content.getBytes();
		FileChannel channel = new FileOutputStream(filePath, true).getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
		buffer.put(strBytes);
		buffer.flip();
		channel.write(buffer);
		channel.close();
	}
}
