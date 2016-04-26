package com.eternalpower;

import com.eternalpower.common.blocks.TileEntityEnderPongReactor;
import com.eternalpower.common.gui.ContainerEnderReactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public static String ITEMS_PNG = "/tutorial/generic/items.png";
	public static String BLOCK_PNG = "/tutorial/generic/block.png";
	
	// Client stuff
	public void registerRenderers () {
		// Nothing here as this is the server side proxy
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
        // TileEntity tile = world.getBlockTileEntity(x, y, z);
        
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
        
        if(tileEntity instanceof TileEntityEnderPongReactor){
                return new ContainerEnderReactor(player.inventory, (TileEntityEnderPongReactor) tileEntity);
        }
        
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
	    /*
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        if (!(tileEntity instanceof GenericTileEntity)) {
        	return null;
        }
        
        return new GenericGui(player.inventory, (GenericTileEntity) tileEntity);
        */
	    
	    return null;
	}
	
	public void load() {
	    //FMLCommonHandler.instance().bus().register(tickTimer);
	}
}