package net.lrsoft.xcustomizedblade;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class ItemXCustomizedSTDTool extends Item{
	private String toolname;
	private EasyCreateBlade addwindow,changewindow;
	public ItemXCustomizedSTDTool(String name) {
		this.addwindow=null;
		this.toolname=name;
	}
	public void ToolInit() {
		this.setUnlocalizedName("xcb."+toolname);
		this.setMaxStackSize(1);
		this.setCreativeTab(SlashBlade.tab);
		switch(this.toolname) {
		case "AddNewBlade":
		
			break;
		case "ChangeTheBlade":

			break;	
		
		}
		GameRegistry.registerItem(this, toolname);
		
	}
	public void RecipeInit() {
		switch(this.toolname) {
			case "AddNewBlade":
				 GameRegistry.addRecipe(new ItemStack(this, 1), 
		    			   new Object[] {"XAX", "ABA", "XAX", 
		    	'X', new ItemStack(Items.stick), 'A',new ItemStack(Items.redstone),'B',new ItemStack(Items.book),});
				break;
			case "ChangeTheBlade":
				 GameRegistry.addRecipe(new ItemStack(this, 1), 
		    			   new Object[] {"XAX", "ABA", "XAX", 
		    	'X', new ItemStack(Items.stick), 'A',new ItemStack(Items.glowstone_dust),'B',new ItemStack(Items.book),});
				break;	
			
		}
	}
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		switch(this.toolname) {
		case "AddNewBlade":
			if(this.addwindow==null) {
				this.addwindow=new EasyCreateBlade();
				this.addwindow.setVisible(true);
			}else {
				addwindow.setVisible(true);
			}
			break;
		case "ChangeTheBlade":

			break;	
		
		}
		 return itemStack;
	}
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List information, boolean advancedTooltip){
		  switch(this.toolname) {
		  	case "AddNewBlade":
		  		itemStack.setStackDisplayName("简易拔刀剑编辑器");
		  		information.add("右键打开简易拔刀剑编辑器GUI");
		  		break;
			case "ChangeTheBlade":
				itemStack.setStackDisplayName("拔刀剑修改器");
		  		information.add("右键打开拔刀剑修改器GUI");
		  		break;
		  }
	}
}
