package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

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
	public boolean CustomRecipe,isServer;
	public int datalen;
	public ConfigJsonReader(String inpath,boolean isserver) {
		this.isServer=isserver;
		this.path=inpath;
		this.CustomRecipe=false;
		this.datalen=0;
		this.json=null;
		this.serverdata=null;
	}
	public void itemInit() {
		this.datalen=jsondata.size();
		int i,sa = 1,standby=1,duration=200,color=16744192;boolean iswithed=false;JsonArray Enchantment=null,recipeItems=null;
		float bladedamage=10;String bladename=null,bladeModel=null,bladeTexture=null,showName=null;String[] recipeName= new String[11];
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
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t enchantment.");
			}
			if(this.CustomRecipe==true) {
				System.out.println("XCustomizedBlade Info:Using custom recipe");
				try {
					recipeItems=temp.get("BladeRecipe").getAsJsonArray();
				}catch(NullPointerException e) {
					System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t recipe.");
					this.CustomRecipe=false;
				}
				for(int j=0;j<11;j++) {
					try {
						if(recipeItems.get(j).isJsonNull()==false)
							recipeName[j]=recipeItems.get(j).getAsString();
					}catch(NullPointerException e) {
						continue;
					}
				}
			}
			if(bladename==null||bladeModel==null||bladeTexture==null||showName==null) continue;
			ItemCustomBlade blade=new ItemCustomBlade(sa,standby,duration,color,
					iswithed,bladedamage,bladename,showName,bladeModel,bladeTexture,Enchantment,CustomRecipe,recipeName);
			SlashBlade.InitEventBus.register(blade);
			System.out.println(sa+" "+standby+" "+duration+" "+color+" "+iswithed+" "+
					bladedamage+" "+bladename+" "+bladeModel+" "+bladeTexture);
		}
		
	}
	public XCDJsonInfo[] GetInfo() {
		XCDJsonInfo[] info = new XCDJsonInfo[jsondata.size()];
		this.datalen=jsondata.size();
		int i,sa = 1,standby=1,duration=200,color=16744192;boolean iswithed=false;JsonArray Enchantment=null,recipeItems=null;
		float bladedamage=10;String bladename=null,bladeModel=null,bladeTexture=null,showName=null;String[] recipeName= new String[11];
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
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t enchantment.");
			}
			try {
				Enchantment=temp.get("Enchantment").getAsJsonArray();
			}catch(NullPointerException e) {
				System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t enchantment.");
			}
			if(this.CustomRecipe==true) {
				System.out.println("XCustomizedBlade Info:Using custom recipe");
				try {
					recipeItems=temp.get("BladeRecipe").getAsJsonArray();
				}catch(NullPointerException e) {
					System.out.println("XCustomizedBlade Warning:"+bladename+":"+showName+" haven\'t recipe.");
					this.CustomRecipe=false;
				}
				for(int j=0;j<11;j++) {
					try {
						if(recipeItems.get(j).isJsonNull()==false)
							recipeName[j]=recipeItems.get(j).getAsString();
					}catch(NullPointerException e) {
						continue;
					}
				}
			}
			info[i]=new XCDJsonInfo(sa,standby,duration,color,
					iswithed,bladedamage,bladename,showName,bladeModel,bladeTexture,Enchantment,CustomRecipe,recipeName);
		}
		return info;
		
	}
	public int readFromJson() {
		double version;
		System.out.println("XCustomizedBlade_Config_Path:"+path);
		JsonParser jp=new JsonParser();
		try {
			json=(JsonObject)jp.parse(new FileReader(path));
			version=json.get("XCustomizedBladeVER").getAsDouble();
			if(version>=0.9) {
				addToolRecipe=json.get("ToolRecipe").getAsBoolean();
			}
			if(version>=1.3){
				try {
					serverdata=json.get("ServerInfo").getAsJsonObject();
					String hostname;int serverport;
					hostname=serverdata.get("ServerHostName").getAsString();
					serverport=serverdata.get("ServerPort").getAsInt();
					System.out.println("XCustomizedBlade:Start to sync to server "+hostname+":"+serverport);
					if(serverdata.get("SyncConfig").getAsBoolean()==false) {
						System.out.println("XCustomizedBlade:Won't sync to server->from config");
					}else {
						if(this.isServer) {
							XCBConfigServerSync tcpServer=new XCBConfigServerSync(
									json.get("XCustomizedBladeConfig").getAsJsonArray(),serverport);
							tcpServer.start();
						}else {
							XCBConfigClientSync tcpClient=new XCBConfigClientSync(json,json.get("XCustomizedBladeConfig").getAsJsonArray(),
									hostname,serverport);
							tcpClient.ConfigStartSync();
						}
					}
				}catch(Exception e) {
					System.out.println("XCustomizedBlade:No server info!");
				}

			}
			jsondata=json.get("XCustomizedBladeConfig").getAsJsonArray();
			CustomRecipe=json.get("CustomizedRecipe").getAsBoolean();
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
	public void changeToJson(XCDJsonInfo info,int datalen) {
		System.out.println("XCustomizedBlade Info:Try to replace the old blade->"+info.bladename);
		for(int i=0;i<datalen;i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			try {
				if(temp.get("BladeName").getAsString().equals(info.bladename)) {
					temp.remove("BladeShowName");temp.addProperty("BladeShowName", info.showName);
					temp.remove("BladeModel");temp.addProperty("BladeModel",info.bladeModel);
					temp.remove("BladeTexture");temp.addProperty("BladeTexture",info.bladeTexture);
					temp.remove("BladeDamge");temp.addProperty("BladeDamge",info.bladedamage);
					temp.remove("BladeDuration");temp.addProperty("BladeDuration",info.bladeduration);
					temp.remove("BladeWitched");temp.addProperty("BladeWitched",info.iswitched);
					temp.remove("BladeStandBy");temp.addProperty("BladeStandBy",info.standby);
					temp.remove("BladeSA");temp.addProperty("BladeSA", info.sa);
					temp.remove("SwordColor");temp.addProperty("SwordColor",info.color);
					//this.jsondata.add(temp);
				//	this.json.add("XCustomizedBladeConfig", jsondata);
					this.json.remove("XCustomizedBladeConfig");
					this.json.add("XCustomizedBladeConfig", jsondata);
					Gson out=new Gson();
					try {
						FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade.json");
						output.write(out.toJson(json).getBytes());
						output.close();
					} catch (FileNotFoundException e) {
						System.out.println("XCustomizedBlade Error:"+e.getMessage());
					} catch (IOException e) {
						System.out.println("XCustomizedBlade Error:"+e.getMessage());
					}
				}
			}catch(NullPointerException e) {
				continue;
			}
		}
		this.readFromJson();
	}
	public boolean isExisted(String name) {
		for(int i=0;i<jsondata.size();i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			try {
				if(name.equals(temp.get("BladeName").getAsString())) {
					System.out.println("XCustomizedBlade Warning:"+temp.get("BladeName").getAsString()+"~"+name+"is existed.");
					return true;
				}
			}catch(NullPointerException e) {
				continue;
			}
		}
		return false;
	}
	public int addToJson(XCDJsonInfo info) {
		if(info.bladename==null||info.showName==null||this.json==null) return 0;
		boolean isexisted=isExisted(info.bladename);
		System.out.println("XCustomizedBlade:Is blade existed?"+isexisted);
		if(isexisted==true) {
			 changeToJson(info,jsondata.size());
			 return -2;
		}else {
			JsonObject temp=new JsonObject();
			temp.addProperty("BladeName", info.bladename);
			temp.addProperty("BladeShowName", info.showName);
			temp.addProperty("BladeModel",info.bladeModel);
			temp.addProperty("BladeTexture",info.bladeTexture);
			temp.addProperty("BladeDamge",info.bladedamage);
			temp.addProperty("BladeDuration",info.bladeduration);
			temp.addProperty("BladeWitched",info.iswitched);
			temp.addProperty("BladeStandBy",info.standby);
			temp.addProperty("BladeSA", info.sa);
			temp.addProperty("SwordColor",info.color);
			this.jsondata.add(temp);
			this.json.add("XCustomizedBladeConfig", jsondata);
			Gson out=new Gson();
			try {
				FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade.json");
				output.write(out.toJson(json).getBytes());
				output.close();
			} catch (FileNotFoundException e) {
				return -1;
			} catch (IOException e) {
				return -1;
			}
			this.readFromJson();
			return 1;
		}
	}
	public int deleteToJson(String name) {
		for(int i=0;i<datalen;i++) {
			JsonObject temp=jsondata.get(i).getAsJsonObject();
			if(temp.get("BladeName").getAsString().equals(name)) {
				temp.remove("BladeShowName");temp.remove("BladeModel");
				temp.remove("BladeTexture");temp.remove("BladeDamge");
				temp.remove("BladeDuration");temp.remove("BladeWitched");
				temp.remove("BladeStandBy");temp.remove("BladeSA");
				temp.remove("SwordColor");temp.remove("BladeName");
				this.json.remove("XCustomizedBladeConfig");
				this.json.add("XCustomizedBladeConfig", jsondata);
				Gson out=new Gson();
				try {
					FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade.json");
					output.write(out.toJson(json).getBytes());
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

}
