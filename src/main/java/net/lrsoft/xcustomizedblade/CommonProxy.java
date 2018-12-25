package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import net.lrsoft.xcustomizedblade.XCBSpeicalAttack.SAJsonReader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
	protected boolean isServer=true;
	private String lrsoft;
	private  ConfigJsonReader jsonreader;
	private SAJsonReader sareader;
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
	 }
	 public void init(FMLInitializationEvent event){}
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
