package com.eternalpower.client;

import com.eternalpower.CommonProxy;
import com.eternalpower.client.render.EnderPongReactorRender;
import com.eternalpower.common.blocks.TileEntityEnderPongReactor;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		
		System.out.println(ID+" "+tileEntity);
		
		switch (ID) {
			case 0: return new GuiEnderReactor(player.inventory, (TileEntityEnderPongReactor)tileEntity);
		}
		
		return tileEntity;
	}
	
	@Override
	public void load() {
	    super.load();
	    
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderPongReactor.class, new EnderPongReactorRender());

	    
	}
}