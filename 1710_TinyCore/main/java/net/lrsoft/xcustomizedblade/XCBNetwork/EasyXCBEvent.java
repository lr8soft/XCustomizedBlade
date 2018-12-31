package net.lrsoft.xcustomizedblade.XCBNetwork;

import net.lrsoft.xcustomizedblade.XCustomizedBlade;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class EasyXCBEvent extends CommandBase{
	@Override
	public String getCommandName() {
		return "xcb -info show XCustomizedBlade Tiny Core information.";
	}
	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		String info=new String("xcb");
		return info;
	}
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 1){
	           throw new WrongUsageException("Invalid input.");
	    }else{
	           EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
	           switch(args[0]) {
	           		case "-info":
	           			System.out.println("XCustomizedBlade TinyCore CoreVER 1.46\nPowered by LT_lrsoft");
	           	    	break;
	           		default:
	           			throw new WrongUsageException("Invalid input.");
	           } 
	    }
	}
	
}
