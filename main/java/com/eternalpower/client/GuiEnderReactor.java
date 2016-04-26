package com.eternalpower.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.opengl.GL11;

import com.eternalpower.EternalPower;
import com.eternalpower.common.blocks.TileEntityEnderPongReactor;
import com.eternalpower.common.gui.ContainerEnderReactor;

//public class SkillGui extends GuiScreen
public class GuiEnderReactor extends GuiContainer
{
	public GuiEnderReactor(InventoryPlayer inventoryPlayer, TileEntityEnderPongReactor tile){
		super(new ContainerEnderReactor(inventoryPlayer, tile));
		initGui();
	}
	
	public FontRenderer frender = Minecraft.getMinecraft().fontRendererObj;
	public int posX=0, posY=0;
	int state=0;
	
	
	int mouseAboveId=-1; // useful for detecting the current skill that the mouse is over
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		drawDefaultBackground();
		
		
		
		
		int widx = 241;
		int heiy = 96;
		
		//this.drawRect(this.width/2-widx/2, this.height/2-heiy/2, , new java.awt.Color(100,100,100,255).getRGB());
		ResourceLocation imgloc = new ResourceLocation(EternalPower.MODID,"textures/gui/enderReactor_Projection.png");
		this.mc.renderEngine.bindTexture(imgloc);
		//this.drawTexturedModalRect(this.width/2-widx/2, this.height/2-heiy/2, 0, 0, widx, heiy);
	}
	
	@SideOnly(Side.CLIENT)
	public void drawMouseHint(int mouseX, int mouseY){
	  mouseAboveId=-1;
	  // for loop to detect mouse location!
	  /*for(int i=0;i<7;i++){
	    // detect if mouse is in this area
	    if(mouseX < (k+xList[i]+20) && mouseY < (l+yList[i]+20)){
	      if(mouseX > (k+xList[i]) && mouseY > (l+yList[i])){
			List list = new ArrayList();
			mouseAboveId=i;
			
			
	
			
			this.drawHoveringText(list, (int)mouseX, (int)mouseY, frender);
	      }
	    }
	  }*/
	}
	
	public void drawContent(int idState){
		// draws tables of magic, ect
		FontRenderer frender = Minecraft.getMinecraft().fontRendererObj;
		
		
		/*
		// state determines what list is used.
		frender.drawString(profession.getProfessionName()+" tree", posX+2, posY+40, 10);
		// loop through table
		for(int i=0;i<abilities.getAbilitiesCount();i++){
		  char[] iText = Character.toChars(i+65); // A-G names on the file
		  Ability ability = abilities.getAbility(i+1);
		  // Draw every item from the table
		  
		  // detect mouse over
		  if(mouseAboveId == i){
			/*GL11.glBegin(GL11.GL_QUADS);
			
			drawLoc(EssencePVP.MODID+":textures/"+treeAddr[state]+iText[0]+".png",posX+xSizeOfTexture/2+xList[i]+5, posY+yList[i]+5, 15, 15,false);
		    
		    GL11.glEnd();* /
			GL11.glColor4f(0.8f,0.8f,0.8f,1f);
			//drawLoc(EssencePVP.MODID+":textures/shadedskill.png",posX+xSizeOfTexture/2+xList[i], posY+yList[i], 20, 20,false);
			drawLoc(EssencePVP.MODID+":textures/"+ability.getAbilityIcon()+".png",posX+xSizeOfTexture/2+xList[i], posY+yList[i], 20, 20,true);
			GL11.glColor4f(1.0f,1.0f,1.0f,1.0f);
		  }else{
			  
		    drawLoc(EssencePVP.MODID+":textures/"+profession.getProfessionIcon()+".png",posX+xSizeOfTexture/2+xList[i], posY+yList[i], 20, 20,true);
		  }
		  
		}*/
	}
	
	
	
	// Button actions
	protected void actionPerformed(GuiButton button)
	{
		/*if(button.id < professions.getProfessionCount()){
			state=button.id;
		}else{
			switch(button.id)
			{
			default:
				break;
			}
		}*/
		
		//Packet code here
        //PacketDispatcher.sendPacketToServer(packet); //send packet
	}
	
	@Override
	public void initGui(){
		buttonList.add(new GuiButton(1, 10, 52, 20, 20, "Test btn"));
	}
	
	@SideOnly(Side.CLIENT)
	public void drawLoc(String loc, int x, int y, int width, int height, boolean resetcolor){
		if(resetcolor) GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation imgloc = new ResourceLocation(loc);
		this.mc.renderEngine.bindTexture(imgloc);
		drawTexturedModalRectNoUV(x,y,width,height);
	}
	
	// Modified excerpt from Gui.class
	@SideOnly(Side.CLIENT)
	 public void drawTexturedModalRectNoUV(int x, int y, int width, int height)
	 {
	     /*Tessellator tessellator = Tessellator.getInstance();
	     tessellator.getWorldRenderer().
	     tessellator.startDrawingQuads();    
	     tessellator.addVertexWithUV(x        , y + height, 0, 0.0, 1.0);
	     tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
	     tessellator.addVertexWithUV(x + width, y         , 0, 1.0, 0.0);
	     tessellator.addVertexWithUV(x        , y         , 0, 0.0, 0.0);
	     tessellator.draw();*/
	 }
	
	@Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
			//draw text and stuff here
            //the parameters for drawString are: string, x, y, color
            fontRenderer.drawString("Tiny", 8, 6, 4210752);
            //draws "Inventory" or your regional equivalent
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

	 @Override
     protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                     int par3) {
             //draw your Gui here, only thing you need to change is the path
             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
             this.mc.renderEngine.bindTexture(new ResourceLocation("minecraft","/gui/trap.png"));
             int x = (width - xSize) / 2;
             int y = (height - ySize) / 2;
             this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
     }
}