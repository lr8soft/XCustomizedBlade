package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import mods.flammpfeil.slashblade.SlashBlade;

public class SAJsonReader {
	public JsonObject jsoninfo;
	public String jsonpath;
	public JsonArray jsondata;
	public SAJsonReader(String path) {
		this.jsonpath=path;
		JsonParser jp=new JsonParser();
		try {
			jsoninfo=(JsonObject)jp.parse(new FileReader(jsonpath));
			try {
				if(jsoninfo.get("XCustomizedBladeVER").getAsDouble()>=1.40) {
					jsondata=jsoninfo.get("XCustomizedSA").getAsJsonArray();
					for(int i=0;i<jsondata.size();i++) {
						JsonObject tempobj=jsondata.get(i).getAsJsonObject();
						JsonArray tSAStep=tempobj.get("SAInfo").getAsJsonArray();
						JsonArray tStepCount=tempobj.get("SAStep").getAsJsonArray();
						String[] SAStep = new String[tempobj.get("SACount").getAsInt()];
						int[] StepCount= new int[tempobj.get("SACount").getAsInt()];
						for(int j=0;j<tempobj.get("SACount").getAsInt();j++) {
							SAStep[j]=tSAStep.get(i).getAsString();
							StepCount[j]=tStepCount.get(i).getAsInt();
						}
						XCustomizedSAInfo temp=new XCustomizedSAInfo(
								tempobj.get("SAName").getAsString(),tempobj.get("SANumber").getAsInt(),SAStep,StepCount,
								tempobj.get("SACount").getAsInt(),tempobj.get("SACost").getAsInt());
						SlashBlade.InitEventBus.register(temp);
					}
				}
			}catch(Exception e) {}
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println("XCustomizedSA:Fail to load from config.");
		}
	}
}
