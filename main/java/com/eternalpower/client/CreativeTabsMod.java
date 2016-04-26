package com.eternalpower.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

public class CreativeTabsMod extends CreativeTabs {

	public CreativeTabsMod(String tabLabel)
	{
		super(tabLabel);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(Blocks.dirt);
	}

}