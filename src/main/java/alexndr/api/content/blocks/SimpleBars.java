package alexndr.api.content.blocks;

import alexndr.api.config.types.ConfigBlock;
import alexndr.api.helpers.game.IConfigureBlockHelper;
import alexndr.api.helpers.game.TooltipHelper;
import alexndr.api.registry.ContentCategories;
import alexndr.api.registry.ContentRegistry;
import alexndr.api.registry.Plugin;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SimpleBars extends BlockPane implements IConfigureBlockHelper<SimpleBars>
{
    protected Plugin plugin;
    protected ContentCategories.Block category;
    protected ConfigBlock entry;

    /**
     * Creates a simple bars (pane) block.
     * @param plugin The plugin the bars belong to
     * @param category The category of the bars block
     */
   public SimpleBars(Plugin plugin, Material materialIn, boolean canDrop, ContentCategories.Block category)
    {
        super(materialIn, canDrop);
        this.plugin = plugin;
        this.category = category;
    }

   @Override
   public SimpleBars setUnlocalizedName(String blockName) 
   {
       super.setUnlocalizedName(blockName);
       setRegistryName(this.plugin.getModId(), blockName);
       GameRegistry.register(this);
       GameRegistry.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
       ContentRegistry.registerBlock(this.plugin, this, blockName, this.category);
       return this;
   }


    @Override
    public ConfigBlock getConfigEntry()
    {
        return this.entry;
    }


    @Override
    public SimpleBars setConfigEntry(ConfigBlock entry)
    {
        this.entry = entry;
        this.setHardness(entry.getHardness());
        this.setResistance(entry.getResistance());
        this.setLightLevel(entry.getLightValue());
        this.setHarvestLevel(entry.getHarvestTool(), entry.getHarvestLevel());
        this.setCreativeTab(entry.getCreativeTab());
        this.setAdditionalProperties();
        return this;
    }


    @Override
    public SimpleBars addToolTip(String toolTip)
    {
        TooltipHelper.addTooltipToBlock(this, toolTip);
        return this;
    }


    @Override
    public void setAdditionalProperties()
    {
        if (entry.getValueByName("Unbreakable") != null
                        && entry.getValueByName("Unbreakable").isActive())
        {
            this.setBlockUnbreakable();
        }
    } // end setAdditionalProperties()

} // end class