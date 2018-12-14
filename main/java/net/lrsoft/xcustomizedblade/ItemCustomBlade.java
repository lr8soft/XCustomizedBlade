package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemCustomBlade {
	private int sa,standby,bladeduration;
	private boolean iswitched;private float bladedamage;
	private String bladename,bladeModel,bladeTexture;
	public ItemCustomBlade(int sa,int standby,int duration,boolean iswithed,float bladedamage,String bladename,String bladeModel,String bladeTexture) {
		this.sa=sa;this.standby=standby;this.bladeduration=duration;this.iswitched=iswithed;
		this.bladedamage=bladedamage;this.bladeModel=bladeModel;this.bladeTexture=bladeTexture;
		this.bladename=bladename;
	}
	 @SubscribeEvent
	public void Init(InitEvent event) {
		String a=new String(sa+" "+standby+" "+bladeduration+" "+iswitched+" "+bladedamage+" "+bladename+" "+bladeModel+" "+bladeTexture);
		//JOptionPane.showMessageDialog(null,a);
		
	      ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);  
	      NBTTagCompound tag = new NBTTagCompound();
	      customblade.setTagCompound(tag);
	      ItemSlashBladeNamed.CurrentItemName.set(tag, bladename);
	      ItemSlashBladeNamed.CustomMaxDamage.set(tag, bladeduration);
	      ItemXCustomizedSTDBlade.setBaseAttackModifier(tag,  bladedamage);
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(iswitched));
	      ItemXCustomizedSTDBlade.TextureName.set(tag, bladeTexture);
	      ItemXCustomizedSTDBlade.ModelName.set(tag, bladeModel);
	      ItemXCustomizedSTDBlade.SpecialAttackType.set(tag, sa);
	      ItemXCustomizedSTDBlade.StandbyRenderType.set(tag, standby);
	  //    System.out.println(test.getString("TextureName")+"\n"+test.getString("ModelName"));
	   //   ItemSlashBlade.TextureName.
	//      ItemSlashBlade.SummonedSwordColor.set(tag,16711935);
	      GameRegistry.registerCustomItemStack(bladename, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(bladename);
	      NamedBladeManager.registerBladeSoul(tag, bladename);
	      
	}
}
