package net.lrsoft.xcustomizedblade;

import net.lrsoft.xcustomizedblade.EasyEditor.EasyCreateBlade;
import net.lrsoft.xcustomizedblade.EasyEditor.EasyNBTEditor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;

public class XCBEventLoader extends CommandBase{
	@Override
	public String getName() {
		return "xcb";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		String info=new String("xcb -add to open the GUI of create blade.\n"
				+ "    -edit to open the slashblade editor.");
		return info;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		System.out.println(args);
		if (args.length > 1){
	           throw new WrongUsageException("Unknown command!");
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
	           			throw new WrongUsageException("Unknown command!");
	           } 
	    }
		
	}
	
}
