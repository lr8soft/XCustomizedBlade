package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mods.flammpfeil.slashblade.SlashBlade;
import net.lrsoft.xcustomizedblade.XCBFunc.EasyXCBEvent;
import net.lrsoft.xcustomizedblade.XCBItem.ItemXCustomizedTools;

public class CommonProxy {
	protected boolean isServerd;
	private String lrsoft;
	private  ConfigJsonReader jsonreader;
	public CommonProxy() {lrsoft="Garbagge code.jpeg";}
	 public void preInit(FMLPreInitializationEvent event){
		 this.isServerd=true;
		 ModInit minit=new ModInit();
		 minit.checkInit();
		 jsonreader=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		 InfoShow msg=new InfoShow(jsonreader.readFromJson());
		 msg.showInfo();
		 jsonreader.itemInit();
		 ItemXCustomizedTools.ItemInit();
	 }
	 public void init(FMLInitializationEvent event){
		 if(jsonreader.addToolRecipe==true) {
			 ItemXCustomizedTools.ItemRecipeInit();
		 }
		
	 }
	 public void postInit(FMLPostInitializationEvent event){}
	 public void serverStarting(FMLServerStartingEvent event){
		 new CommandLoader(event);
     }
}
class CommandLoader
{
    public CommandLoader(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new EasyXCBEvent());
    }
}