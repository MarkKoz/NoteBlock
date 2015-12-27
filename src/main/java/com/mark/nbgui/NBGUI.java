package com.mark.nbgui;

import com.mark.nbgui.proxy.IProxy;
import com.mark.nbgui.reference.Reference;

import net.minecraft.block.BlockNote;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, dependencies=Reference.DEPENDENCIES)

public class NBGUI {
    public static SimpleNetworkWrapper network;

    @Instance(Reference.MODID)
    public static NBGUI instance;

    @SidedProxy(modId = Reference.MODID,clientSide = Reference.proxyClient, serverSide = Reference.proxyServer)
    public static IProxy proxy;
    
    @EventHandler public void preInit(FMLPreInitializationEvent event) {
    }
	
    @EventHandler
    public void init(FMLInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.channel);
        network.registerMessage(Message.Handler.class, Message.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(NBGUI.instance, new GUIHandler());
    	MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
    	KeyBindings.init();
    }
       
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}