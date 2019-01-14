package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import net.lrsoft.xcustomizedblade.XCBSpecialAttack.SAJsonReader;
import net.lrsoft.xcustomizedblade.XCBSpecialEffect.SEJsonReader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
	protected boolean isServer=true;
	private String lrsoft;
	private  ConfigJsonReader jsonreader;
	private SAJsonReader sareader;
	private SEJsonReader sereader;
	public CommonProxy() {lrsoft="Garbagge code.jpeg";}
	 public void preInit(FMLPreInitializationEvent event){
		 ModInit minit=new ModInit();
		 minit.checkInit();
		 jsonreader=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json",this.isServer);
		 InfoShow msg=new InfoShow(jsonreader.readFromJson());
		 msg.showInfo();
		 jsonreader.itemInit();
		 //BladeInit finished
		 sareader=new SAJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		 sareader.SAInit();
		 
		 sereader=new SEJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		 sereader.SEInit();
	 }
	 public void init(FMLInitializationEvent event){}
	 public void postInit(FMLPostInitializationEvent event){}
}

