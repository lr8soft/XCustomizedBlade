package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
	protected boolean isClient;
	private String lrsoft;
	private  ConfigJsonReader jsonreader;
	public CommonProxy() {lrsoft="Garbagge code.jpeg";}
	 public void preInit(FMLPreInitializationEvent event){
		 this.isClient=false;
		 ModInit minit=new ModInit();
		 minit.checkInit();
		 jsonreader=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		 InfoShow msg=new InfoShow(jsonreader.readFromJson());
		 msg.showInfo();
		 jsonreader.itemInit();
	//	 ItemXCustomizedTools.ItemInit();
	 }
	 public void init(FMLInitializationEvent event){
		
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
        event.registerServerCommand(new XCBEventLoader());
    }
}
