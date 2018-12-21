package net.lrsoft.xcustomizedblade.XCBNetwork;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.lrsoft.xcustomizedblade.ConfigJsonReader;
import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBConfigClientSync {
	private  Socket socket=null;
	private JsonArray jsondata;
	private String hostname;
	private int port;
	public XCBConfigClientSync(JsonArray input,String hostname,int port) {
		this.jsondata=input;
		this.hostname=hostname;
		this.port=port;
		try {
			socket=new Socket(hostname,port);
		}catch(Exception e) {
			System.out.println("XCB Client:Failed to create socket.Wrong address or port?");
		}
	}
	public void ConfigStartSync() throws IOException{
		try {
			InputStream input=socket.getInputStream();
			byte[] inputInfo=new byte[4096];
			input.read(inputInfo);
			updataToJson(inputInfo);
			System.out.println(inputInfo);
			socket.close();
		} catch (Exception e) {
			System.out.println("XCB Client:IO Error.");
		}
	}
	private void updataToJson(byte[] newinfo) {
		String writeInfo="{\r\n" + 
				"  \"temp\":";
		String tailInfo="\r\n}";
		JsonArray newarray=new JsonArray();
	/*	ConfigJsonReader jsondata=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json",false);
		jsondata.readFromJson();
		jsondata.json.remove("XCustomizedBladeConfig");
		jsondata.json.addProperty("XCustomizedBladeConfig", newinfo);*/
		Gson out=new Gson();
		try {
			FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_updata.json");
			output.write(writeInfo.getBytes());
			output.write(newinfo);
			output.write(tailInfo.getBytes());
			output.close();
		} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
}
