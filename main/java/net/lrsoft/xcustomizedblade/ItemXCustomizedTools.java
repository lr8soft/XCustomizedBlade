package net.lrsoft.xcustomizedblade;

public class ItemXCustomizedTools {
	public static ItemXCustomizedSTDTool AddNewBlade=new ItemXCustomizedSTDTool("AddNewBlade");
	public static ItemXCustomizedSTDTool ChangeTheBlade=new ItemXCustomizedSTDTool("ChangeTheBlade");
	public static void ItemInit() {
		AddNewBlade.ToolInit();
		ChangeTheBlade.ToolInit();
	}
	public static void ItemRecipeInit() {
		AddNewBlade.RecipeInit();;
		ChangeTheBlade.RecipeInit();
	}
}
