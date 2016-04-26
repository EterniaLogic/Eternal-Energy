package com.eternalpower.common.blocks;

import com.eternalpower.util.ItemUtil;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SlotEnderReactor extends Slot{

	int slot;
	public SlotEnderReactor(IInventory par1iInventory, int index, int x, int y){
		super(par1iInventory,index,x,y);
		slot = index;
	}

	
	// Compare items to be used in the Ender Reactor
	@Override
	public boolean isItemValid(ItemStack is){
		switch(slot){
			// Initial firing mechanism Ender Pearls
			case 0: return Item.getIdFromItem(is.getItem())==Item.getIdFromItem(Items.ender_pearl);
			
			// Packet Ender Pearls
			case 1: return Item.getIdFromItem(is.getItem())==Item.getIdFromItem(Items.ender_pearl);
			
			// Packet mass materials
			case 2: return ItemUtil.CompareItemStackList(is, "gold_ingot", "gold_block", "iron_block","iron_ingot","cobblestone","dirt","diamond_block","diamond","redstone","redstone_block","gravel");
		}
		
		return false;
	}
}