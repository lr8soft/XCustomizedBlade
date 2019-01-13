package net.lrsoft.xcustomizedblade.XCBItem;

import java.io.File;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.lrsoft.xcustomizedblade.SBMcEnchantment;
public class ItemCustomBlade {
	private JsonArray Enchantment;
	private int sa,standby,bladeduration,color;
	private boolean iswitched;private float bladedamage;
	private String bladename,bladeModel,bladeTexture,showName;
	public ItemCustomBlade(int sa,int standby,int duration,int color,boolean iswithed,float bladedamage,String bladename,
			String showName,String bladeModel,String bladeTexture,JsonArray list) {
		this.sa=sa;this.standby=standby;this.bladeduration=duration;this.iswitched=iswithed;
		this.bladedamage=bladedamage;this.bladeModel=bladeModel;this.bladeTexture=bladeTexture;
		this.bladename=bladename;this.color=color;this.showName=showName;this.Enchantment=list;
	}
	 @SubscribeEvent
	public void Init(InitEvent event) {
	      ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);  
	      NBTTagCompound tag = new NBTTagCompound();
	      customblade.setTagCompound(tag);
	      customblade.setStackDisplayName(showName);
	      SpecialEffects.addEffect(customblade, "TestSE", 50);
	      ItemSlashBladeNamed.CurrentItemName.set(tag, bladename);
	      ItemSlashBladeNamed.CustomMaxDamage.set(tag, bladeduration);
	      ItemXCustomizedSTDBlade.setBaseAttackModifier(tag,  bladedamage);
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(iswitched));
	      ItemXCustomizedSTDBlade.TextureName.set(tag, bladeTexture);
	      ItemXCustomizedSTDBlade.ModelName.set(tag, bladeModel);
	      ItemXCustomizedSTDBlade.SpecialAttackType.set(tag, sa);
	      ItemXCustomizedSTDBlade.StandbyRenderType.set(tag, standby);
	      ItemSlashBlade.SummonedSwordColor.set(tag,color);
	      if(this.Enchantment!=null&&this.Enchantment.size()%2==0) {
	    	  int i,strengthen=0;String temp;
	    	  for(i=0;i<this.Enchantment.size();i+=2) {
	    		try {
	    			 temp=this.Enchantment.get(i).getAsString();
		    		 strengthen=this.Enchantment.get(i+1).getAsInt();
		    		 switch(temp) {
		    		 	case "power":
		    		 		customblade.addEnchantment(SBMcEnchantment.power,strengthen); break;
		    		 	case "unbreaking":
		    		 		customblade.addEnchantment(SBMcEnchantment.unbreaking,strengthen); break;
		    		 	case "looting":
		    		 		customblade.addEnchantment(SBMcEnchantment.looting,strengthen); break;
		    		 	case "sharpness":
		    		 		customblade.addEnchantment(SBMcEnchantment.sharpness,strengthen); break;
		    		 	case "infinity":
		    		 		customblade.addEnchantment(SBMcEnchantment.infinity,strengthen); break;
		    		 	case "thorns":
		    		 		customblade.addEnchantment(SBMcEnchantment.thorns,strengthen); break;
		    		 	case "knockback":
		    		 		customblade.addEnchantment(SBMcEnchantment.knockback,strengthen); break;
		    		 	case "baneOfArthropods":
		    		 		customblade.addEnchantment(SBMcEnchantment.baneOfArthropods,strengthen); break;
		    		 	case "blastProtection":
		    		 		customblade.addEnchantment(SBMcEnchantment.blastProtection,strengthen); break;
		    		 	case "featherFalling":
		    		 		customblade.addEnchantment(SBMcEnchantment.featherFalling,strengthen); break;
		    		 	case "fireAspect":
		    		 		customblade.addEnchantment(SBMcEnchantment.fireAspect,strengthen); break;
		    			case "fireProtection":
		    		 		customblade.addEnchantment(SBMcEnchantment.fireProtection,strengthen); break;
		    			case "flame":
		    		 		customblade.addEnchantment(SBMcEnchantment.flame,strengthen); break;
		    			case "fortune":
		    		 		customblade.addEnchantment(SBMcEnchantment.fortune,strengthen); break;
		    			case "projectileProtection":
		    		 		customblade.addEnchantment(SBMcEnchantment.projectileProtection,strengthen); break;
		    			case "protection":
		    		 		customblade.addEnchantment(SBMcEnchantment.protection,strengthen); break;
		    			case "punch":
		    		 		customblade.addEnchantment(SBMcEnchantment.punch,strengthen); break;
		    			case "respiration":
		    		 		customblade.addEnchantment(SBMcEnchantment.respiration,strengthen); break;
		    			case "silkTouch":
		    		 		customblade.addEnchantment(SBMcEnchantment.silkTouch,strengthen); break;
		    			case "smite":
		    		 		customblade.addEnchantment(SBMcEnchantment.smite,strengthen); break;
		    		 	default:
		    		 		System.out.println("XCustomizedBlade Error:Unknown Enchantment name "+temp);
		    		 }
	    		}catch(NumberFormatException e) {
	    			System.out.println("XCustomizedBlade Error:Unknown strength type!");
	    		}
	    	  }
	      }
	      GameRegistry.registerCustomItemStack(bladename, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(bladename);
	      NamedBladeManager.registerBladeSoul(tag, bladename);
	}
	 public static void BladeRecipeInit(ItemCustomBlade bladeObject,ItemStack blade,ItemStack recipe[]) {
		 
	 }
}
