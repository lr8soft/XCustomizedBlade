package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

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
			try {
				for(int i=0;i<jsondata.size();i++) {
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
				}
			}catch(Exception e) {
				System.out.println("XCustomizedSA:Fail to init SA.");
				this.willSARun=false;
			}

		}
	}
	public boolean isExisted(String name) {
		for(int i=0;i<jsondata.size();i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			try {
				if(name.equals(temp.get("SAName").getAsString())) {
					System.out.println("XCustomizedSA Warning:"+temp.get("BladeName").getAsString()+"~"+name+"is existed.");
					return true;
				}
			}catch(NullPointerException e) {
				continue;
			}
		}
		return false;
	}
	public int deleteToJson(String name) {
		for(int i=0;i<jsondata.size();i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			if(temp.get("SAName").getAsString().equals(name)) {
				temp.remove("SAName");temp.remove("SANumber");
				temp.remove("SACost");temp.remove("SAInfo");
				temp.remove("StepDamage");temp.remove("SAStep");
				temp.remove("BladeStandBy");temp.remove("BladeSA");
				temp.remove("SACount");
				this.jsoninfo.remove("XCustomizedSA");
				this.jsoninfo.add("XCustomizedSA", jsondata);
				Gson out=new Gson();
				try {
					FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade.json");
					output.write(out.toJson(jsoninfo).getBytes());
					output.close();
				} catch (FileNotFoundException e) {
					System.out.println("XCustomizedBlade Error:"+e.getMessage());
					return -1;
				} catch (IOException e) {
					System.out.println("XCustomizedBlade Error:"+e.getMessage());
					return -1;
				}
				return 1;
			}
		}
		return 0;
	}
	public int addToJson(String name,int num,int cost,String[] step,int[] count,int[] damage) {
		JsonObject temp=new JsonObject();
		temp.addProperty("SAName", name);temp.addProperty("SANumber", num);
		JsonArray test=new JsonArray();
		temp.add("SAInfo", test);
		for(int i=0;i<step.length;i++) {
			temp.get("SAInfo").getAsJsonArray();
		}
		temp.addProperty("SACount", step.length);
		this.jsondata.add(temp);
		this.jsoninfo.add("XCustomizedSA", jsondata);
		Gson out=new Gson();
		try {
			FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade.json");
			output.write(out.toJson(jsoninfo).getBytes());
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("XCustomizedBlade Error:"+e.getMessage());
			return 0;
		} catch (IOException e) {
			System.out.println("XCustomizedBlade Error:"+e.getMessage());
			return 0;
		}
		return 1;
	}
}
