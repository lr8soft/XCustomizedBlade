package net.lrsoft.xcustomizedblade.EasyEditor;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class EasyXCBEvent extends CommandBase{
	@Override
	public String getCommandName() {
		return "xcb";
	}
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		String info=new String("xcb -add to open the GUI of create blade.\n"
				+ "    -edit to open the slashblade editor.");
		return info;
	}
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 1){
	           throw new WrongUsageException("Invalid input.");
	    }else{
	           EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
	           switch(args[0]) {
	           		case "-add":
	           			EasyCreateBlade addwindow=new EasyCreateBlade();
	    				addwindow.setVisible(true);
	           			break;
	           		case "-edit":
	    				try {
	    					player.inventory.getStackInSlot(0).getTagCompound().getString("ModelName");								
	    					EasyNBTEditor editWindow=new EasyNBTEditor(player.inventory.getStackInSlot(0));
	    					editWindow.setVisible(true);
	    				}catch(Exception e) {
	    					throw new WrongUsageException("SlashBlade must be placed in the first of inventory!");
	    				}
	           			break;
	           		default:
	           			throw new WrongUsageException("Invalid input.");
	           } 
	    }
	}
	
}
