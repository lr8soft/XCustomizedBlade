package net.lrsoft.xcustomizedblade.XCBUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBToJsonArray {
	public static JsonArray StringToJsonArray(String[] input) {
		JsonArray returnArray=null;
		String head="{\r\n" + 
				"  \"temp\":[";
		String tail="]\r\n" + 
				"}";
		try {
			FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_temp.json");
			output.write(head.getBytes());
			for(int i=0;i<input.length;i++) {
				if(i<input.length-1) {
					output.write(input[i].getBytes());
					output.write(",".getBytes());
				}else {
					output.write(input[i].getBytes());
				}
			}
			output.write(tail.getBytes());
			output.close();
			JsonParser jp=new JsonParser();
			JsonObject json=(JsonObject)jp.parse(new FileReader(InfoShow.getNowPath()+"/XCustomizedBlade_temp.json"));
			returnArray=json.get("temp").getAsJsonArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnArray;
	}
	public static JsonArray IntToJsonArray(int[] input) {
		JsonArray returnArray=null;
		String head="{\r\n" + 
				"  \"temp\":[";
		String tail="]\r\n" + 
				"}";
		try {
			FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_temp.json");
			output.write(head.getBytes());
			for(int i=0;i<input.length;i++) {
				if(i<input.length-1) {
					output.write(String.valueOf(input[i]).getBytes());
					output.write(",".getBytes());
				}else {
					output.write(String.valueOf(input[i]).getBytes());
				}
			}
			output.write(tail.getBytes());
			output.close();
			JsonParser jp=new JsonParser();
			JsonObject json=(JsonObject)jp.parse(new FileReader(InfoShow.getNowPath()+"/XCustomizedBlade_temp.json"));
			returnArray=json.get("temp").getAsJsonArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnArray;
	}
	public static JsonArray FloatToJsonArray(float[] input) {
		JsonArray returnArray=null;
		String head="{\r\n" + 
				"  \"temp\":[";
		String tail="]\r\n" + 
				"}";
		try {
			FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_temp.json");
			output.write(head.getBytes());
			for(int i=0;i<input.length;i++) {
				if(i<input.length-1) {
					output.write(String.valueOf(input[i]).getBytes());
					output.write(",".getBytes());
				}else {
					output.write(String.valueOf(input[i]).getBytes());
				}
			}
			output.write(tail.getBytes());
			output.close();
			JsonParser jp=new JsonParser();
			JsonObject json=(JsonObject)jp.parse(new FileReader(InfoShow.getNowPath()+"/XCustomizedBlade_temp.json"));
			returnArray=json.get("temp").getAsJsonArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnArray;
	}
}
