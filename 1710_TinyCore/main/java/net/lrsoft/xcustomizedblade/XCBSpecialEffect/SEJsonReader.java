package net.lrsoft.xcustomizedblade.XCBSpecialEffect;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;

public class SEJsonReader {
	private JsonObject jsoninfo;
	private JsonArray jsondata;
	private String jsonpath;
	private boolean willSERun=true;
	public SEJsonReader(String path) {
		this.jsonpath=path;
		JsonParser jp=new JsonParser();
		try {
			jsoninfo=(JsonObject)jp.parse(new FileReader(jsonpath));
			try {
				if(jsoninfo.get("XCustomizedBladeVER").getAsDouble()>=1.50) {
					jsondata=jsoninfo.get("XCustomizedSE").getAsJsonArray();
				}else {
					this.willSERun=false;
				}
			}catch(Exception e) {this.willSERun=false;}
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println("XCustomizedSE:Fail to load from config.");
			this.willSERun=false;
		}
	}
	public void SEInit() {
		if(willSERun) {
			System.out.println("XCustomizedSE:Start to load from config.");
			System.out.println("XCustomizedSE:All "+jsondata.size()+" SEs.");
			for(int i=0;i<jsondata.size();i++) {
				try {
					JsonObject tempobj=jsondata.get(i).getAsJsonObject();
					String name=tempobj.get("SEName").getAsString();
					int cost=tempobj.get("SECost").getAsInt();
					int level=tempobj.get("SELevel").getAsInt();
					JsonArray step=tempobj.get("SEStep").getAsJsonArray();
					JsonArray runtime=tempobj.get("SERuntime").getAsJsonArray();
					JsonArray damage=tempobj.get("SEDamage").getAsJsonArray();
					String[] stepName=new String[step.size()];
					int[] stepRuntime=new int[step.size()];
					double[] stepDamage=new double[step.size()];
					for(int len=0;len<step.size();len++) {
						stepName[len]=step.get(len).getAsString();
						stepRuntime[len]=runtime.get(len).getAsInt();
						stepDamage[len]=damage.get(len).getAsDouble();
					}
					if(name==null||step==null||runtime==null||damage==null) continue;
					XCustomizedSEStandard SEInfo=new XCustomizedSEStandard(name,cost,level,stepName,stepRuntime,stepDamage);
					SpecialEffects.register(new XCustomziedSpecialEffect(SEInfo));
				}catch(Exception error) {
					System.out.println("XCustomizedSE:An error occur while loading.");
					error.printStackTrace();
					continue;
				}
			}
		}else {
			System.out.println("XCustomizedSE:Custom SE won\'t run.");
		}
	}
}
