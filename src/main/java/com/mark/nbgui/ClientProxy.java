package com.mark.nbgui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
	@Override
	public void openGUI()
	{
	     Minecraft.getMinecraft().displayGuiScreen(new GUI());
	}
}
