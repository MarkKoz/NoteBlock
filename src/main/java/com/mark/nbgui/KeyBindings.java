package com.mark.nbgui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {

    // Declare KeyBindings
    public static KeyBinding pong;

    public static void init() {
    	
    	/*Vanilla Categories
		* key.categories.movement (Movement): 
		* 	Everything related to moving your player across the world, like jumping and walking
    	* key.categories.inventory (Inventory): 
    	* 	Everything related to the player inventory, used for opening the inventory and all the hotbar slots by Vanilla
    	* key.categories.gameplay (Gameplay): 
    	* 	Everything related to the direct gameplay, for example attacking, picking a block etc.
    	* key.categories.multiplayer (Multiplayer): 
    	* 	Used for all the multiplayer specific key bindings, like opening chat etc.
    	* key.categories.misc (Miscellaneous): 
    	* 	A place for all the key bindings which don't fit the previous categories, taking screenshots, for example 
    	*/
    	
        // Define unlocalised binding name
        // the category with unlocalised name
        // key code or LWJGL constant
        pong = new KeyBinding("key.returnConstant", Keyboard.KEY_RETURN, "key.categories.inventory");

        // Register KeyBindings to the ClientRegistry
        ClientRegistry.registerKeyBinding(pong);
    }

}