package com.eternalpower.client.render;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.pipeline.LightUtil;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;


// https://github.com/TechStack/TechStack-s-HeavyMachineryMod/blob/master/src/main/java/com/projectreddog/machinemod/utility/MachineModModelHelper.java
public class EnderPongReactorRender extends TileEntitySpecialRenderer {
	protected OBJModel model;
	protected IFlexibleBakedModel bakedModel;
	public static final String ALL_PARTS = "ALL";
			
	public EnderPongReactorRender(){
		super();
		
		try {
			ResourceLocation loc = new ResourceLocation("eternalpower", "models/block/enderReactor.obj");
			//System.out.println("TTTTTTTTTs "+loc.getResourcePath());
			
			model = (OBJModel) OBJLoader.instance.loadModel(loc);
			HandleModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void HandleModel(){
		// Bake a texture on
		//model.bake(new OBJModel.OBJState(model, false), Attributes.DEFAULT_BAKED_FORMAT, textureGetterFlipV);
		bakedModel = getModelsForGroups(model).get(ALL_PARTS);
	}
    
    
    
    
    
    public static Function<ResourceLocation, TextureAtlasSprite> textureGetterFlipV = new Function<ResourceLocation, TextureAtlasSprite>() {
		public TextureAtlasSprite apply(ResourceLocation location) {
			return DummyAtlasTextureFlipV.instance;
		}
	};
    
    
    private static class DummyAtlasTextureFlipV extends TextureAtlasSprite {
		public static DummyAtlasTextureFlipV instance = new DummyAtlasTextureFlipV();

		protected DummyAtlasTextureFlipV() {
			super("dummyFlipV");
		}

		@Override
		public float getInterpolatedU(double u) {
			return (float) u / 16;
		}

		@Override
		public float getInterpolatedV(double v) {
			return (float) v / -16;
		}
	}
    
    public static HashMap<String, IFlexibleBakedModel> getModelsForGroups(OBJModel objModel) {

		HashMap<String, IFlexibleBakedModel> modelParts = new HashMap<String, IFlexibleBakedModel>();

		if (!objModel.getMatLib().getGroups().keySet().isEmpty()) {
			for (String key : objModel.getMatLib().getGroups().keySet()) {
				String k = key;
				if (!modelParts.containsKey(key)) {
					modelParts.put(k, objModel.bake(new OBJModel.OBJState(ImmutableList.of(k), false), Attributes.DEFAULT_BAKED_FORMAT, textureGetterFlipV));

					// can use a list strings as a OBJModel.OBJState Turning those group objects on or off accordngly
				}
			}
		}

		modelParts.put(ALL_PARTS, objModel.bake(objModel.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, textureGetterFlipV));

		return modelParts;
	}


	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		try{
			GL11.glPushMatrix(); 
			GL11.glTranslated(x+0.5, y+0.5, z+0.5); // This is necessary to make our rendering happen in the right place.
			//GL11.glScaled(1/16.f, 1/16.f, 1/16.f);
			Tessellator tessellator = Tessellator.getInstance();
	    	this.bindTexture(getTextureLocation());
	    	
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			worldrenderer.begin(GL11.GL_QUADS, bakedModel.getFormat());
			
			for (BakedQuad bakedQuad : bakedModel.getGeneralQuads()) {
				LightUtil.renderQuadColor(worldrenderer, bakedQuad, -1);
	
			}
	
			tessellator.draw();
			
			
	    	GL11.glPopMatrix();
	    	
	    	
	    	//Minecraft.getMinecraft().thePlayer.posX
	    	//renderParticle(x, y+1, z, 0.2, 0.2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Rendering a single particle
	public void renderParticle(double x, double y, double z, double width, double height, double angle){
		GL11.glPushMatrix(); 
		GL11.glTranslated(x+0.5, y+0.5, z+0.5); // This is necessary to make our rendering happen in the right place.
		
		
		GL11.glColor4d(1, 0, 0, 0.4f);
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2d(0, 0);
		GL11.glVertex2d(0, height);
		GL11.glVertex2d(width, height);
		GL11.glVertex2d(width, 0);		
		
		
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	
    public ResourceLocation getTextureLocation(){
    	return new ResourceLocation("eternalpower", "textures/block/enderReactor.png");
    }
}


