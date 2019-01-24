package net.lrsoft.xcustomizedblade.XCBReflect;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class XCBJarReader {
	private String path;
	public JsonObject jsoninfo;
	public JsonArray jsondata;
	private boolean willReflectRun=false;
	public XCBJarReader(String input) {
		this.path=input;
		JsonParser jp=new JsonParser();
		try {
			jsoninfo=(JsonObject)jp.parse(new FileReader(path));
			try {
				if(jsoninfo.get("XCustomizedReflect").getAsDouble()>=1.60) {
					jsondata=jsoninfo.get("XCustomizedReflect").getAsJsonArray();
				}else {
					this.willReflectRun=false;
				}
			}catch(Exception e) {this.willReflectRun=false;}
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println("XCustomizedReflect:Fail to load from config.");
			this.willReflectRun=false;
		}
	}
}
