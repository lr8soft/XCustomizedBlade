package net.lrsoft.xcustomizedblade.XCBNetwork;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.CharBuffer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.lrsoft.xcustomizedblade.ConfigJsonReader;
import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBConfigClientSync {
	private  Socket socket=null;
	private JsonObject oldJson;
	private JsonArray jsondata,sadata,sedata;
	private String hostname;
	private int port;
	private int runtime=0;
	public XCBConfigClientSync(JsonObject old,JsonArray input,JsonArray inputsa,JsonArray inputse,String hostname,int port) {
		this.jsondata=input;
		this.sadata=inputsa;
		this.sedata=inputse;
		this.hostname=hostname;
		this.port=port;
		this.oldJson=old;
		try {
			socket=new Socket(hostname,port);
			socket.setSoTimeout(6000);
		}catch(Exception e) {
			System.out.println("XCB Client Error:"+e.getMessage());
		}
	}
	public void ConfigStartSync() throws IOException{
		if(socket!=null) {
			try {
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				String inputInfo="",temp="";
				inputInfo = br.readLine();
				while((temp = br.readLine()) != null){
					inputInfo+=temp;
				}
				System.out.println("XCB Client:Input config:"+inputInfo);
				updataToJson(inputInfo);
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void updataToJson(String newinfo) {
		JsonParser jp=new JsonParser();
		JsonObject json=null;
		JsonArray newArray=null,saArray=null,seArray=null;
		String writeInfo="{\r\n" + 
				"  \"temp\":";
		String bodyInfo="\r\n,\"temp2\":";
		String bodyInfo2="\r\n,\"temp3\":";
		String tailInfo="\r\n}";
		String[] getInfo=newinfo.split(">>>>>");
		for(int i=0;i<getInfo.length;i++) {
			System.out.println(getInfo[i]);
		}
		JsonArray newarray=new JsonArray();
		Gson out=new Gson();
		try {
			OutputStreamWriter output=new OutputStreamWriter(new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_updata.json"),
					"UTF-8");
			output.write(writeInfo);
			output.write(getInfo[0]);
			output.write(bodyInfo);
			output.write(getInfo[1]);
			output.write(bodyInfo2);
			output.write(getInfo[2]);
			output.write(tailInfo);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {} catch (IOException e) {}
		try {
			json=(JsonObject)jp.parse(new FileReader(InfoShow.getNowPath()+"/XCustomizedBlade_updata.json"));
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			return;
		}
		try {
			newArray=json.get("temp").getAsJsonArray();
			saArray=json.get("temp2").getAsJsonArray();
			seArray=json.get("temp3").getAsJsonArray();
		}catch(Exception e) {
			return;
		}
		if(!newArray.equals(this.jsondata)||!saArray.equals(this.sadata)) {
			oldJson.remove("XCustomizedBladeConfig");
			oldJson.add("XCustomizedBladeConfig", newArray);
			oldJson.remove("XCustomizedSA");
			oldJson.add("XCustomizedSA", saArray);
			oldJson.remove("XCustomizedSE");
			oldJson.add("XCustomizedSE", seArray);
			Gson outJson=new Gson();
			try {
				FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade.json");
				output.write(outJson.toJson(oldJson).getBytes());
				output.close();
			} catch (FileNotFoundException e) {
				System.out.println("XCustomizedBlade Error:"+e.getMessage());
			} catch (IOException e) {
				System.out.println("XCustomizedBlade Error:"+e.getMessage());
			}
			System.out.println("XCB Client:Config has been updated from SERVER.");
		}else {
			System.out.println("XCB Client:Config is lastest version.");
		}
	}
}
