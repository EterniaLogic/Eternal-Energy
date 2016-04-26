package com.eternalpower.client;

import com.eternalpower.client.render.EnderPongReactorRender;
import com.eternalpower.common.blocks.TileEntityEnderPongReactor;
import com.eternalpower.common.gui.ContainerEnderReactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
        
	public GuiHandler(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderPongReactor.class, new EnderPongReactorRender());
	}
	
	//returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
            
            if(tileEntity instanceof TileEntityEnderPongReactor){
                    return new ContainerEnderReactor(player.inventory, (TileEntityEnderPongReactor) tileEntity);
            }
            
            return null;
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                    int x, int y, int z) {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
            
            if(tileEntity instanceof TileEntityEnderPongReactor){
                    return new GuiEnderReactor(player.inventory, (TileEntityEnderPongReactor) tileEntity);
            }
            
            return null;
    }
}