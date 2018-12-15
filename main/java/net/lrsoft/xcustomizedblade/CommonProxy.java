package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.flammpfeil.slashblade.SlashBlade;

public class CommonProxy {
	private String lrsoft;
	public CommonProxy() {lrsoft="Garbagge code(((";}
	 public void preInit(FMLPreInitializationEvent event){
		 ModInit minit=new ModInit();
		 minit.checkInit();
		 ConfigJsonReader jsonreader=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		 InfoShow msg=new InfoShow(jsonreader.readFromJson());
		 msg.showInfo();
		 jsonreader.itemInit();
		 ItemXCustomizedTools.ItemInit();
	 }
	 public void init(FMLInitializationEvent event){
		 ItemXCustomizedTools.ItemRecipeInit();
	 }
	 public void postInit(FMLPostInitializationEvent event){}
}
