package alexndr.api.content.gui;

import alexndr.api.content.tiles.TileEntitySimpleFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
abstract public class SimpleFurnaceGui extends GuiContainer 
{
    protected ResourceLocation furnaceGuiTextures; 
    protected TileEntitySimpleFurnace tileFurnace;
    protected InventoryPlayer playerInv;
  
	public SimpleFurnaceGui(Container inventorySlotsIn, ResourceLocation textureResources,
							InventoryPlayer player, TileEntitySimpleFurnace te) 
    {
		super(inventorySlotsIn);
		this.furnaceGuiTextures = textureResources;
		this.tileFurnace = te;
		this.playerInv = player;
	}

    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
    {
        String s = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (tileFurnace.isBurning()) 
		{
			i1 = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
		}

		i1 = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
    
    protected int getCookProgressScaled(int pixels)
    {
        int i = this.tileFurnace.getField(TileEntitySimpleFurnace.FIELD_COOK_TIME);
        int j = this.tileFurnace.getField(TileEntitySimpleFurnace.FIELD_TOTAL_COOK_TIME);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    protected int getBurnLeftScaled(int pixels)
    {
        int i = this.tileFurnace.getField(TileEntitySimpleFurnace.FIELD_ITEM_BURN_TIME);

        if (i == 0)
        {
            i = this.tileFurnace.getMaxCookTime();
        }
        return this.tileFurnace.getField(TileEntitySimpleFurnace.FIELD_BURN_TIME) * pixels / i;
    }

} // end class
