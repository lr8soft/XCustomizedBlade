package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mods.flammpfeil.slashblade.SlashBlade;
import net.lrsoft.xcustomizedblade.XCBNetwork.EasyXCBEvent;
import net.lrsoft.xcustomizedblade.XCBSpecialAttack.SAJsonReader;

public class CommonProxy {
	protected boolean isServerd=true;
	private String lrsoft;
	private  ConfigJsonReader jsonreader;
	private SAJsonReader sareader;
	public CommonProxy() {lrsoft="Garbagge code.jpeg";}
	 public void preInit(FMLPreInitializationEvent event){
		 ModInit minit=new ModInit();
		 minit.checkInit();
		 jsonreader=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json",this.isServerd);
		 InfoShow msg=new InfoShow(jsonreader.readFromJson());
		 msg.showInfo();
		 jsonreader.itemInit();
		 sareader=new SAJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		 sareader.SAInit();
	 }
	 public void init(FMLInitializationEvent event){}
	 public void postInit(FMLPostInitializationEvent event){
		 System.out.println("Now running with XCustomizedBlade TinyCore,version without GUI.");
	 }
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
