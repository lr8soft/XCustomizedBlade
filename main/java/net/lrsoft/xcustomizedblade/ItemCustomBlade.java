package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

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
	private String[] recipeItems;
	private JsonArray Enchantment;
	private int sa,standby,bladeduration,color;
	private boolean iswitched,useCustomRecipe;private float bladedamage;
	private String bladename,bladeModel,bladeTexture,showName;
	public ItemCustomBlade(int sa,int standby,int duration,int color,boolean iswithed,float bladedamage,String bladename,
			String showName,String bladeModel,String bladeTexture,JsonArray list,boolean useCustomRecipe,String[] recipeItems) {
		this.sa=sa;this.standby=standby;this.bladeduration=duration;this.iswitched=iswithed;
		this.bladedamage=bladedamage;this.bladeModel=bladeModel;this.bladeTexture=bladeTexture;
		this.bladename=bladename;this.color=color;this.showName=showName;this.Enchantment=list;
		this.recipeItems=recipeItems;this.useCustomRecipe=useCustomRecipe;
	}
	 @SubscribeEvent
	public void Init(InitEvent event) {
	      ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);  
	      NBTTagCompound tag = new NBTTagCompound();
	      customblade.setTagCompound(tag);
	      customblade.setStackDisplayName(showName);
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
		    		 	default:
		    		 		System.out.println("XCustomizedBlade Error:Unknown Enchantment name "+temp);
		    		 }
	    		}catch(NumberFormatException e) {
	    			System.out.println("XCustomizedBlade Error:Unknown strength type!");
	    		}
	    	  }
	      }
    	  ItemStack[] recipeNeed = new ItemStack[11];
    	  if(this.useCustomRecipe==true) {
        	  int j;
        	  for(j=1;j<11;j++) {
        		  String source=recipeItems[0];
        		  if(source!=null) {
        			 try {
        				 if(j==1) {
        					 recipeNeed[j]=new ItemStack(GameRegistry.findItem("flammpfeil.slashblade", recipeItems[j]),1);
        				 }else {
        					 System.out.println("XCustomizedBlade Info:"+recipeItems[j]+" in "+source);
        					 recipeNeed[j]=new ItemStack(GameRegistry.findItem(source, recipeItems[j]));        					 
        				 }
        			 if(recipeNeed[j]==null) 
        				 recipeNeed[j]=new ItemStack(GameRegistry.findBlock(source, recipeItems[j]));
        			 }catch(NullPointerException e) {}
        		  }
        	  }
        	  BladeRecipeInit(this,customblade,recipeNeed);
    	  }
	      GameRegistry.registerCustomItemStack(bladename, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(bladename);
	      NamedBladeManager.registerBladeSoul(tag, bladename);
	}
	 public static void BladeRecipeInit(ItemCustomBlade bladeObject,ItemStack blade,ItemStack recipe[]) {
		  SlashBlade.addRecipe(bladeObject.bladename, new RecipeAwakeBlade(blade, recipe[0],
	    		  new Object[]{"ABC",
	                           "DEF",
	                           "GHI",
	              Character.valueOf('A'),recipe[2],
	              Character.valueOf('B'), recipe[3],
	              Character.valueOf('C'), recipe[4],
	              Character.valueOf('D'), recipe[5],
	              Character.valueOf('E'), recipe[6],
	              Character.valueOf('F'), recipe[7],
	              Character.valueOf('G'), recipe[8],
	              Character.valueOf('H'), recipe[9],
	              Character.valueOf('I'), recipe[10],
	    		  }));
	 }
}
