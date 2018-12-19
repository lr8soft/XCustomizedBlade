package net.lrsoft.xcustomizedblade;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.lrsoft.xcustomizedblade.XCBFunc.XCBNetworkMessageHandler;
import net.lrsoft.xcustomizedblade.XCBFunc.XCBNetworkSynchronize;

@Mod(modid="xcustomizedblade",name="XCustomizedBlade", version="1.30",dependencies="required-after:flammpfeil.slashblade")
public class XCustomizedBlade {
	@SidedProxy(clientSide = "net.lrsoft.xcustomizedblade.ClientProxy",serverSide = "net.lrsoft.xcustomizedblade.CommonProxy")
	private static CommonProxy proxy; 
	private static SimpleNetworkWrapper network;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
	    proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
	    network = NetworkRegistry.INSTANCE.newSimpleChannel("xcustomizedblade");
	    if(proxy.isServerd) {
	    	network.registerMessage(XCBNetworkMessageHandler.class, XCBNetworkSynchronize.class, 0, Side.CLIENT);
	    }else {
	    	network.registerMessage(XCBNetworkMessageHandler.class, XCBNetworkSynchronize.class, 0, Side.SERVER);
	    }
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
    public static SimpleNetworkWrapper getNetwork() {
    	network.registerMessage(XCBNetworkMessageHandler.class, XCBNetworkSynchronize.class, 0, Side.SERVER);
        return network;
    }
}   
