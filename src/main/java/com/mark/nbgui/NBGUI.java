package com.mark.nbgui;

import net.minecraft.block.BlockNote;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = NBGUI.MODID, version = NBGUI.VERSION, name = NBGUI.NAME, dependencies=NBGUI.DEPENDENCIES)

public class NBGUI {
    public static final String MODID = "NBGUI";
    public static final String VERSION = "1.0";
    public static final String NAME = "Note Block GUI";
    public static final String DEPENDENCIES = "required-after:FML";
    public static final BlockNote BlockNote = new BlockNote();
    public static final NoteBlock NoteBlock = new NoteBlock();

    @Instance("NBGUI")
    public static NBGUI instance;

    @SidedProxy(modId = "NBGUI",clientSide = "com.mark.nbgui.ClientProxy", serverSide = "com.mark.nbgui.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler public void preInit(FMLPreInitializationEvent event) {
    }
	
    @EventHandler
    public void load(FMLInitializationEvent event) {
    }
       
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}