package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.eventhandler.EventBus;
import mods.flammpfeil.slashblade.SlashBlade;

public class ConfigJsonReader {
	public String path;
	public JsonArray jsondata;
	public ConfigJsonReader(String inpath) {
		this.path=inpath;
	}
	public void itemInit() {
		int i,sa,standby,duration,color;boolean iswithed;JsonArray Enchantment=null;
		float bladedamage;String bladename,bladeModel,bladeTexture,showName;
		for(i=0;i<jsondata.size();i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			sa=temp.get("BladeSA").getAsInt();
			standby=temp.get("BladeStandBy").getAsInt();
			duration=temp.get("BladeDuration").getAsInt();
			color=temp.get("SwordColor").getAsInt();
			iswithed=temp.get("BladeWitched").getAsBoolean();
			bladedamage=temp.get("BladeDamge").getAsFloat();
			bladename=temp.get("BladeName").getAsString();
			showName=temp.get("BladeShowName").getAsString();
			bladeModel=temp.get("BladeModel").getAsString();
			bladeTexture=temp.get("BladeTexture").getAsString();
			try {
				Enchantment=temp.get("Enchantment").getAsJsonArray();
			}catch(NullPointerException e) {
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t enchantment.");
			}
				
			if(bladename==null||bladeModel==null||bladeTexture==null) break;
			ItemCustomBlade blade=new ItemCustomBlade(sa,standby,duration,color,
					iswithed,bladedamage,bladename,showName,bladeModel,bladeTexture,Enchantment);
			SlashBlade.InitEventBus.register(blade);
			System.out.println(sa+" "+standby+" "+duration+" "+color+""+iswithed+" "+
					bladedamage+" "+bladename+" "+bladeModel+" "+bladeTexture);
		}
		
	}
	public int readFromJson() {
		System.out.println("XCustomizedBlade_Config_Path:"+path);
		JsonParser jp=new JsonParser();
		try {
			JsonObject json=(JsonObject)jp.parse(new FileReader(path));
			jsondata=json.get("XCustomizedBladeConfig").getAsJsonArray();
			return 1;
		}catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            
        }
		return 0;
	}
	public void startJsonWork() {
		int ret=readFromJson();
		InfoShow mess=new InfoShow(ret);
		mess.showInfo();
	}
}
