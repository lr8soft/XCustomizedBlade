package net.lrsoft.xcustomizedblade;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="xcustomizedblade",name="XCustomizedBlade", version="0.84",dependencies="required-after:flammpfeil.slashblade")
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
}   
