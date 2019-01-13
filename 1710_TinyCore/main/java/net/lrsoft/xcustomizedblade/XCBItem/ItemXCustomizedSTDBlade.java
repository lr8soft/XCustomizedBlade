package net.lrsoft.xcustomizedblade.XCBItem;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import net.lrsoft.xcustomizedblade.InfoShow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemXCustomizedSTDBlade extends ItemSlashBlade {
	static public TagPropertyAccessor.TagPropertyString TextureName = new TagPropertyAccessor.TagPropertyString("TextureName");
    static public TagPropertyAccessor.TagPropertyString ModelName = new TagPropertyAccessor.TagPropertyString("ModelName");
	public ItemXCustomizedSTDBlade(ToolMaterial par2EnumToolMaterial, float defaultBaseAttackModifier) {
		super(par2EnumToolMaterial, defaultBaseAttackModifier);
	}
    static public ResourceLocation getModelTexture(ItemStack par1ItemStack){
        NBTTagCompound tag = getItemTagCompound(par1ItemStack);
        if(TextureName.exists(tag)){
            String textureName = TextureName.get(tag);
            ResourceLocation loc;
            if(!textureMap.containsKey(textureName))
            {
            	loc = new ResourceLocation(InfoShow.getNowPath()+"\\"+textureName+".png");
                textureMap.put(textureName,loc);
            }else{
                loc = textureMap.get(textureName);
            }
            return loc;
        }
        return ((ItemSlashBlade)par1ItemStack.getItem()).getModelTexture();
    }
    static  public ResourceLocation getModelLocation(ItemStack par1ItemStack){
        NBTTagCompound tag = getItemTagCompound(par1ItemStack);
        if(ModelName.exists(tag)){
            String modelName = ModelName.get(tag);
            ResourceLocation loc;
            if(!modelMap.containsKey(modelName))
            {
            	loc = new ResourceLocation(InfoShow.getNowPath()+"\\"+modelName+".png");
                modelMap.put(modelName,loc);
            }else{
                loc = (ResourceLocation) modelMap.get(modelName);
            }
            return loc;
        }
        return  ((ItemSlashBlade)par1ItemStack.getItem()).getModel();
    }

}
