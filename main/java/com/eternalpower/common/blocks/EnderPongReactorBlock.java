package com.eternalpower.common.blocks;

import com.eternalpower.EternalPower;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EnderPongReactorBlock extends BlockContainer {

	public EnderPongReactorBlock (int id, Material material) {
		super(material);
        //this.setBlockName("");
        //this.setCreativeTab(EternalPower.getCreativeTab());
        //setBlockTextureName(EternalPower.MODID + ":" + "enderPongReactor");
        //this.setBlockTextureName(EternalPower.MODID + ":" + "enderPongReactor");
        //this.setBlockTextureName("minecraft:cobblestone");
        //this.setUnlocalizedName("Ender Pong Reactor");
        //this.setRegistryName("enderPongReactor");
        this.setHardness(2.0F);
        this.setResistance(6.0F);
        this.setLightLevel(1.0F);
        this.setHarvestLevel("pickaxe", 3);
	}
	
	public static final void init() {

    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityEnderPongReactor();
	}
	
	
	@Override
    //public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float a, float b, float c)
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		playerIn.openGui((Object)EternalPower.getInstance(), 0, worldIn, (int)hitX, (int)hitY, (int)hitZ);
		return true;
    }
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
    public boolean isOpaqueCube(){
        return false;
    }
}
