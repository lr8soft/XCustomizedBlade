package net.lrsoft.xcustomizedblade;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	@Override
    public void preInit(FMLPreInitializationEvent event){
		super.isServerd=false;
        super.preInit(event);
    }
    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
    }
    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }
}
