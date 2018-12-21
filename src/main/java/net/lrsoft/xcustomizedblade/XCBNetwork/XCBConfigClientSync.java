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
	private JsonArray jsondata;
	private String hostname;
	private int port;
	public XCBConfigClientSync(JsonObject old,JsonArray input,String hostname,int port) {
		this.jsondata=input;
		this.hostname=hostname;
		this.port=port;
		this.oldJson=old;
		try {
			socket=new Socket(hostname,port);
		}catch(Exception e) {
			System.out.println("XCB Client:Failed to create socket.Wrong address or port?");
		}
	}
	public void ConfigStartSync() throws IOException{
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
	private void updataToJson(String newinfo) {
		JsonParser jp=new JsonParser();
		JsonObject json=null;
		JsonArray newArray=null;
		String writeInfo="{\r\n" + 
				"  \"temp\":";
		String tailInfo="\r\n}";
		JsonArray newarray=new JsonArray();
		Gson out=new Gson();
		try {
			OutputStreamWriter output=new OutputStreamWriter(new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_updata.json"),
					"UTF-8");
			output.write(writeInfo);
			output.write(newinfo);
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
		}catch(Exception e) {
			return;
		}
		if(!newArray.equals(this.jsondata)) {
			oldJson.remove("XCustomizedBladeConfig");
			oldJson.add("XCustomizedBladeConfig", newArray);
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
