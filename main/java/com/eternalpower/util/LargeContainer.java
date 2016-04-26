package com.eternalpower.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class LargeContainer extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return false;
	}

}
