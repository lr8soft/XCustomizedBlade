package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.eventhandler.EventBus;
import mods.flammpfeil.slashblade.SlashBlade;
import net.lrsoft.xcustomizedblade.XCBItem.ItemCustomBlade;
import net.lrsoft.xcustomizedblade.XCBNetwork.XCBConfigClientSync;
import net.lrsoft.xcustomizedblade.XCBNetwork.XCBConfigServerSync;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

public class ConfigJsonReader {
	public boolean addToolRecipe;
	public JsonObject json;private JsonObject serverdata;
	public String path;
	public JsonArray jsondata;
	public boolean isServer;
	public int datalen;
	public ConfigJsonReader(String inpath,boolean isserver) {
		this.isServer=isserver;
		this.path=inpath;
		this.datalen=0;
		this.json=null;
		this.serverdata=null;
	}
	public void itemInit() {
		this.datalen=jsondata.size();
		String SEName=null;
		int i,sa = 1,standby=1,duration=200,color=16744192,SELevel=0;boolean iswithed=false;JsonArray Enchantment=null,recipeItems=null,recipeSource=null;
		float bladedamage=10;String bladename=null,bladeModel=null,bladeTexture=null,showName=null;
		String[] recipeName= new String[10];
		String[] recipeSourceName= new String[9];
		for(i=0;i<jsondata.size();i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			try {
				bladename=temp.get("BladeName").getAsString();
				showName=temp.get("BladeShowName").getAsString();
				bladeModel=temp.get("BladeModel").getAsString();
				bladeTexture=temp.get("BladeTexture").getAsString();
			}catch(NullPointerException e) {
				continue;
			}
			try {
				sa=temp.get("BladeSA").getAsInt();
				standby=temp.get("BladeStandBy").getAsInt();
				duration=temp.get("BladeDuration").getAsInt();
				color=temp.get("SwordColor").getAsInt();
				iswithed=temp.get("BladeWitched").getAsBoolean();
				bladedamage=temp.get("BladeDamge").getAsFloat();
			}catch(NullPointerException e) {}
			try {
				Enchantment=temp.get("Enchantment").getAsJsonArray();
			}catch(NullPointerException e) {
				Enchantment=null;
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t enchantment.");
			}
			try {
				SELevel=temp.get("SELevel").getAsInt();
				SEName=temp.get("BladeSE").getAsString();
			}catch(NullPointerException e) {
				SELevel=0;SEName=null;
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t SE.");
			}
			if(bladename==null||bladeModel==null||bladeTexture==null||showName==null) continue;
			ItemCustomBlade blade=new ItemCustomBlade(sa,standby,duration,color,
					iswithed,bladedamage,bladename,showName,bladeModel,bladeTexture,Enchantment,SEName,SELevel);
			SlashBlade.InitEventBus.register(blade);
		}
		
	}
	public XCDJsonInfo[] GetInfo() {
		XCDJsonInfo[] info = new XCDJsonInfo[jsondata.size()];
		this.datalen=jsondata.size();
		String SEName=null;
		int i,sa = 1,standby=1,duration=200,color=16744192,SELevel=0;boolean iswithed=false;JsonArray Enchantment=null;
		float bladedamage=10;String bladename=null,bladeModel=null,bladeTexture=null,showName=null;
		for(i=0;i<jsondata.size();i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			try {
				bladename=temp.get("BladeName").getAsString();
				showName=temp.get("BladeShowName").getAsString();
				bladeModel=temp.get("BladeModel").getAsString();
				bladeTexture=temp.get("BladeTexture").getAsString();
			}catch(NullPointerException e) {
				continue;
			}
			try {
				sa=temp.get("BladeSA").getAsInt();
				standby=temp.get("BladeStandBy").getAsInt();
				duration=temp.get("BladeDuration").getAsInt();
				color=temp.get("SwordColor").getAsInt();
				iswithed=temp.get("BladeWitched").getAsBoolean();
				bladedamage=temp.get("BladeDamge").getAsFloat();
			}catch(NullPointerException e) {}
			try {
				Enchantment=temp.get("Enchantment").getAsJsonArray();
			}catch(NullPointerException e) {
				Enchantment=null;
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t enchantment.");
			}
			try {
				SELevel=temp.get("SELevel").getAsInt();
				SEName=temp.get("BladeSE").getAsString();
			}catch(NullPointerException e) {
				SELevel=0;SEName=null;
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t SE.");
			}
			info[i]=new XCDJsonInfo(sa,standby,duration,color,
					iswithed,bladedamage,bladename,showName,bladeModel,bladeTexture,Enchantment,SEName,SELevel);
		}
		return info;
		
	}
	public int readFromJson() {
		double version;
		System.out.println("XCustomizedBlade TinyCore_Config_Path:"+path);
		JsonParser jp=new JsonParser();
		try {
			json=(JsonObject)jp.parse(new FileReader(path));
			version=json.get("XCustomizedBladeVER").getAsDouble();
			if(version>=1.3){
				try {
					serverdata=json.get("ServerInfo").getAsJsonObject();
					String hostname;int serverport;
					hostname=serverdata.get("ServerHostName").getAsString();
					serverport=serverdata.get("ServerPort").getAsInt();
					System.out.println("XCustomizedBlade:server info:"+hostname+":"+serverport);
					if(serverdata.get("SyncConfig").getAsBoolean()==false) {
						System.out.println("XCustomizedBlade:Won't sync to server->from config");
					}else {
						if(this.isServer) {
							System.out.println("XCustomizedBlade:SERVER Now start.");
							try {
								XCBConfigServerSync tcpServer=new XCBConfigServerSync(
										json.get("XCustomizedBladeConfig").getAsJsonArray(),
										json.get("XCustomizedSA").getAsJsonArray(),
										json.get("XCustomizedSE").getAsJsonArray()
										,serverport);
								tcpServer.start();
							}catch(NullPointerException e) {
								System.out.println("XCB TinyCore:Can\'t start sync!");
								e.printStackTrace();
							}
						}else {
							System.out.println("XCustomizedBlade:Start to connect with SERVER.");
							try {
								XCBConfigClientSync tcpClient=new XCBConfigClientSync(json,
										json.get("XCustomizedBladeConfig").getAsJsonArray(),
										json.get("XCustomizedSA").getAsJsonArray(),
										json.get("XCustomizedSE").getAsJsonArray(),
										hostname,serverport);
								tcpClient.ConfigStartSync();
							}catch(NullPointerException e2) {
								System.out.println("XCB TinyCore:Can\'t start sync from SERVER!");
								e2.printStackTrace();
							}
						}
					}
				}catch(Exception e) {
					System.out.println("XCustomizedBlade:No server info!");
				}
			}
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
}
