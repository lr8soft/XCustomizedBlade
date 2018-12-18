package net.lrsoft.xcustomizedblade.XCBItem;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.flammpfeil.slashblade.SlashBlade;
import net.lrsoft.xcustomizedblade.EasyEditor.EasyCreateBlade;
import net.lrsoft.xcustomizedblade.EasyEditor.EasyNBTEditor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
public class ItemXCustomizedSTDTool extends Item{
	private String toolname;
	private EasyCreateBlade addwindow;
	private EasyNBTEditor editWindow;
	public ItemXCustomizedSTDTool(String name) {
		this.addwindow=null;
		this.editWindow=null;
		this.toolname=name;
	}
	public void ToolInit() {
		this.setUnlocalizedName("xcb."+toolname);
		this.setMaxStackSize(1);
		this.setCreativeTab(SlashBlade.tab);
		this.setTextureName("xcustomizedblade:XCDTools");
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
				addwindow=new EasyCreateBlade();
				addwindow.setVisible(true);
				player.addChatMessage(new ChatComponentTranslation("XCustomizedBlade:GUI已在前台打开！"));
			break;
		case "ChangeTheBlade":
				try {
					player.inventory.getStackInSlot(0).stackTagCompound.getString("ModelName");								
					editWindow=new EasyNBTEditor(player.inventory.getStackInSlot(0));
					editWindow.setVisible(true);
					player.addChatMessage(new ChatComponentTranslation("XCustomizedBlade:编辑器已在前台打开！"));
				}catch(Exception e) {
					player.addChatMessage(new ChatComponentTranslation("XCustomizedBlade:拔刀剑必须放在物品栏左数第一！"));
				}
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
		  		information.add("重启游戏设置生效");
		  		break;
			case "ChangeTheBlade":
				itemStack.setStackDisplayName("拔刀剑修改器");
		  		information.add("右键打开拔刀剑修改器GUI");
		  		information.add("拔刀放在物品栏最左");
		  		break;
		  }
	}
}
