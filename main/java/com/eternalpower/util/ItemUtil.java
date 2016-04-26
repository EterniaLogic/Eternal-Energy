package com.eternalpower.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemUtil {

	public static boolean CompareItemStackList(ItemStack is1, String... items){
		boolean isSame = false;
		
		if(is1 == null) return false;
		if(items == null) return false;
		
		// Go through items
		for(int i=0; i<items.length; i++){
			try{
				if(Item.getIdFromItem(is1.getItem())==Item.getIdFromItem(Item.itemRegistry.getObject(new ResourceLocation(items[i])))){
					isSame = true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return isSame;
	}
}
