package net.lrsoft.xcustomizedblade.XCBItem;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.tileentity.DummyTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.enchantment.Enchantment;
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
	      ItemSlashBladeNamed.setBaseAttackModifier(tag, bladedamage);
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(iswitched));
	      ItemSlashBladeNamed.TextureName.set(tag, bladeTexture);
	      ItemSlashBladeNamed.ModelName.set(tag, bladeModel);
	      ItemSlashBladeNamed.SpecialAttackType.set(tag, sa);
	      ItemSlashBladeNamed.StandbyRenderType.set(tag, standby);
	      ItemSlashBladeNamed.SummonedSwordColor.set(tag,color);
	      if(this.Enchantment!=null&&this.Enchantment.size()%2==0) {
	    	  int i,strengthen=0;String temp;
	    	  for(i=0;i<this.Enchantment.size();i+=2) {
	    		try {
	    			 temp=this.Enchantment.get(i).getAsString();
		    		 strengthen=this.Enchantment.get(i+1).getAsInt();
		    		 switch(temp) {
		    		 	case "power":
		    		 		customblade.addEnchantment(Enchantments.POWER,strengthen); break;
		    		 	case "unbreaking":
		    		 		customblade.addEnchantment(Enchantments.UNBREAKING,strengthen); break;
		    		 	case "looting":
		    		 		customblade.addEnchantment(Enchantments.LOOTING,strengthen); break;
		    		 	case "sharpness":
		    		 		customblade.addEnchantment(Enchantments.SHARPNESS,strengthen); break;
		    		 	case "infinity":
		    		 		customblade.addEnchantment(Enchantments.INFINITY,strengthen); break;
		    		 	case "thorns":
		    		 		customblade.addEnchantment(Enchantments.THORNS,strengthen); break;
		    		 	case "knockback":
		    		 		customblade.addEnchantment(Enchantments.KNOCKBACK,strengthen); break;
		    		 	case "baneOfArthropods":
		    		 		customblade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS,strengthen); break;
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
        /*	  int j;
        	  for(j=1;j<11;j++) {
        		  String source=recipeItems[0];
        		  if(source!=null) {
        			 try {
        				 if(j==1) {
        					 recipeNeed[j]=new ItemStack(SlashBlade.findItem("flammpfeil.slashblade", recipeItems[j]),1);
        				 }else {
        					 System.out.println("XCustomizedBlade Info:"+recipeItems[j]+" in "+source);
        					 recipeNeed[j]=new ItemStack(GameRegistry.findItem(source, recipeItems[j]));        					 
        				 }
        			 if(recipeNeed[j]==null) 
        				 recipeNeed[j]=new ItemStack(GameRegistry.findBlock(source, recipeItems[j]));
        			 }catch(NullPointerException e) {}
        		  }
        	  }
        	  BladeRecipeInit(this,customblade,recipeNeed);*/
    	  }
    	  SlashBlade.registerCustomItemStack(bladename, customblade);
	      ItemSlashBladeNamed.NamedBlades.add(bladename);
	      NamedBladeManager.registerBladeSoul(tag, bladename);
	}
	 public static void BladeRecipeInit(ItemCustomBlade bladeObject,ItemStack blade,ItemStack recipe[]) {
	/*	  SlashBlade.addRecipe(bladeObject.bladename, new RecipeAwakeBlade(blade, recipe[0],
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
	    		  }));*/
	 }
}
