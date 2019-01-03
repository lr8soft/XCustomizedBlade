package net.lrsoft.xcustomizedblade.XCBSpecialAttack;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import mods.flammpfeil.slashblade.SlashBlade;
import net.lrsoft.xcustomizedblade.InfoShow;
import net.lrsoft.xcustomizedblade.XCBUtil.XCBToJsonArray;

public class SAJsonReader {
	public JsonObject jsoninfo;
	public String jsonpath;
	public JsonArray jsondata;
	private boolean willSARun;
	private JsonArray tSAStep,tStepCount,tStepDamage;
	public String[] SAStep;
	public int[] StepCount;
	public float[] StepDamage;
	public SAJsonReader(String path) {
		this.jsonpath=path;
		this.willSARun=true;
		JsonParser jp=new JsonParser();
		try {
			jsoninfo=(JsonObject)jp.parse(new FileReader(jsonpath));
			try {
				if(jsoninfo.get("XCustomizedBladeVER").getAsDouble()>=1.40) {
					jsondata=jsoninfo.get("XCustomizedSA").getAsJsonArray();
				}else {
					this.willSARun=false;
				}
			}catch(Exception e) {this.willSARun=false;}
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println("XCustomizedSA:Fail to load from config.");
			this.willSARun=false;
		}
	}
	public void SAInit() {
		if(willSARun) {
				for(int i=0;i<jsondata.size();i++) {
					try {
						JsonObject tempobj=jsondata.get(i).getAsJsonObject();
						tSAStep=tempobj.get("SAInfo").getAsJsonArray();
						tStepCount=tempobj.get("SAStep").getAsJsonArray();
						tStepDamage=tempobj.get("StepDamage").getAsJsonArray();
						SAStep = new String[tempobj.get("SACount").getAsInt()];
						StepCount= new int[tempobj.get("SACount").getAsInt()];
						StepDamage=new float[tempobj.get("SACount").getAsInt()];
						for(int j=0;j<tempobj.get("SACount").getAsInt();j++) {
							SAStep[j]=tSAStep.get(j).getAsString();
							StepCount[j]=tStepCount.get(j).getAsInt();
							StepDamage[j]=tStepDamage.get(j).getAsFloat();
						}
						XCustomizedSAInfo temp=new XCustomizedSAInfo(
								tempobj.get("SAName").getAsString(),tempobj.get("SANumber").getAsInt(),
								SAStep,StepCount,StepDamage,
								tempobj.get("SACount").getAsInt(),tempobj.get("SACost").getAsInt());
						SlashBlade.InitEventBus.register(temp);
					}catch(Exception e){
						System.out.println("XCustomizedSA:An error occur while loading.");
						continue;
					}
				}
		}else {
			System.out.println("XCustomizedSA:Customized SA won't run.");
		}
	}
}
