package net.lrsoft.xcustomizedblade;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid="xcustomizedblade",name="XCustomizedBlade", version="0.8",dependencies="required-after:flammpfeil.slashblade")
public class XCustomizedBlade {
	@SidedProxy(clientSide = "net.lrsoft.xcustomizedblade.ClientProxy",serverSide = "net.lrsoft.xcustomizedblade.CommonProxy")
	public static CommonProxy proxy; 
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
	    proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
	    proxy.init(event);
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){    
		proxy.postInit(event);
	}
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
	    proxy.serverStarting(event);
	}
}   
