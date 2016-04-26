package com.eternalpower;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.eternalpower.client.ClientProxy;
import com.eternalpower.client.CreativeTabsMod;
import com.eternalpower.client.GuiHandler;
import com.eternalpower.client.render.EnderPongReactorRender;
import com.eternalpower.common.blocks.EnderPongReactorBlock;
import com.eternalpower.common.blocks.TileEntityEnderPongReactor;
import com.eternalpower.config.Config;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

@Mod(modid = EternalPower.MODID, version = EternalPower.VERSION)
public class EternalPower
{
    public static final String MODID = "eternalpower";
    public static final String VERSION = "0.1";
    
    // Begin EnderPongReactor
    public static final Block enderPongReactor = (new EnderPongReactorBlock(509, Material.iron)).setStepSound(Block.soundTypeMetal).setUnlocalizedName("EnderPongReactor").setRegistryName("enderPongReactor");
    
    private static CreativeTabs creativeTab = new CreativeTabsMod("EternalPower");
	
	// handles GUI synchronization
	@SidedProxy(clientSide = "com.eternalpower.client.ClientProxy", serverSide = "com.eternalpower.CommonProxy")
	public static ClientProxy proxy;

	// allows for methods to access this mod's raw members
	@Instance("EternalPower")
	private static EternalPower instance;
	
	private Config config;
	private Logger logger;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        // System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX >> "+Blocks.dirt.getUnlocalizedName());
    	instance = this;
		logger = Logger.getLogger("EternalPower");
		logger.setLevel(Level.FINEST);
		
		
		//FMLCommonHandler.instance().bus().register(new PlayerListener());
		
		// Primary Thread for processing skills, player data, ect.
		/*if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			//clientplayer = new Character(Minecraft.getMinecraft().thePlayer);
			new Player(Minecraft.getMinecraft().thePlayer);
		}else if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			RegisterCommands();
		}*/
    	
    	logger.fine("The enderman can hear you scream. Ping. Pong.");
    }
    
    
    @EventHandler
    public void load (FMLInitializationEvent event) {
        /*proxy.registerRenderers();

        initCraftingAndSmelting();
        initBasicItems();
        initBasicBlocks();
        initDamageValuesAndMetadata();
        initPacketHandling();
        initPlants();
        initWorldGen();*/
    	
    	
    	
    	config = new Config();
    	proxy.load();
    	NetworkRegistry.INSTANCE.registerGuiHandler((Object)this, this.proxy);
    	initBasicBlocks();
    }
    
    private void initBasicBlocks() {
    	GameRegistry.registerBlock(enderPongReactor, "enderPongReactor");
    	enderPongReactor.setCreativeTab(EternalPower.getCreativeTab());
    	GameRegistry.registerTileEntity(TileEntityEnderPongReactor.class, "enderPongReactorTileEntity");
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, this.proxy);
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(enderPongReactor), 0, new ModelResourceLocation(MODID + ":models/block/enderReactor.obj", "inventory"));
		//RenderingRegistry.registerEntityRenderingHandler(EnderPongReactorTileEntity.class, new EnderPongReactorRender());
    	//NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    	
        //LanguageRegistry.addName(enderPongReactor, "Ender-Pong Generator");
        
        
    }
    
    private void initCraftingAndSmelting() {
        /*ItemStack dirtStack = new ItemStack(Block.dirt);
        ItemStack diamondsStack = new ItemStack(Item.diamond, 64);
        ItemStack blackWoolStack = new ItemStack(Block.cloth, 42, 15);
        ItemStack gravelStack = new ItemStack(Block.gravel);
        ItemStack cobbleStack = new ItemStack(Block.cobblestone);

        GameRegistry.addShapelessRecipe(diamondsStack, dirtStack);

        GameRegistry.addShapelessRecipe(diamondsStack, dirtStack, dirtStack,
                dirtStack, dirtStack, dirtStack, dirtStack, new ItemStack(
                        Block.sand), gravelStack, cobbleStack);

        GameRegistry.addRecipe(new ItemStack(Block.cobblestone), "xy", "yx",
                'x', dirtStack, 'y', gravelStack);

        GameRegistry.addRecipe(new ItemStack(Block.stone), "xyx", "y y", "xyx",
                'x', dirtStack, 'y', gravelStack);

        GameRegistry.addSmelting(Block.stone.blockID, new ItemStack(
                Block.stoneBrick), 0.1f);

        FurnaceRecipes.smelting().addSmelting(Block.cloth.blockID, 15,
                new ItemStack(Item.bed), 0.1f);*/
    }
    
    public static CreativeTabs getCreativeTab(){
		return creativeTab;
	}
    
    public static EternalPower getInstance(){
    	return instance;
    }
    
    public Logger getLogger(){
    	return logger;
    }
    
    public Config getConfig(){
    	return config;
    }
}