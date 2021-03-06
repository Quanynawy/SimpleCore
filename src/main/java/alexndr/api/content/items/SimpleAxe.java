package alexndr.api.content.items;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import com.google.common.collect.Lists;

import alexndr.api.config.IConfigureItemHelper;
import alexndr.api.config.types.ConfigTool;
import alexndr.api.core.SimpleCoreAPI;
import alexndr.api.helpers.game.TooltipHelper;
import alexndr.api.registry.Plugin;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

/**
 * @author AleXndrTheGr8st
 */
public class SimpleAxe extends ItemAxe implements IConfigureItemHelper<SimpleAxe, ConfigTool>
{
	private final ToolMaterial material;
	private Plugin plugin;
//	private ContentCategories.Item category = ContentCategories.Item.TOOL;
	private ConfigTool entry;
	@SuppressWarnings("unused")
	private List<String> toolTipStrings = Lists.newArrayList();
	protected String name;

	/**
	 * Creates a simple axe, such as the Mythril Axe.
	 * @param plugin The plugin the tool belongs to
	 * @param material The ToolMaterial of the tool
	 * @param damage axe damage to entity
	 * @param speed axe weapon speed.
	 */
	public SimpleAxe(String axeName, Plugin plugin, ToolMaterial material, float damage, float speed) 
	{
		super(material, damage, speed);
		this.name = axeName;
		this.plugin = plugin;
		this.material = material;
		setUnlocalizedName(axeName);
        setRegistryName(plugin.getModId(), axeName);
	}
	
	/**
	 * constructor using helper functions for axe damage and speed.
	 * @param plugin The plugin the tool belongs to
	 * @param material The ToolMaterial of the tool
	 */
	public SimpleAxe(String axeName, Plugin plugin, ToolMaterial material) 
	{
		super(material, getAttackDamage(material), getAttackSpeed(material));
		this.name = axeName;
		this.plugin = plugin;
		this.material = material;
		setUnlocalizedName(axeName);
        setRegistryName(plugin.getModId(), axeName);
	}

	/**
	 * Lifted from Zot201's OnlySilver code, with permission.
	 * @param m
	 * @return
	 */
	protected static float getAttackDamage(ToolMaterial m) 
	{
		return 2 * (int) Math.rint(
				0.5 * (m.getAttackDamage() * 0.7058823529 + 6.352941176));
	}

	/**
	 * Lifted from Zot201's OnlySilver code, with permission.
	 * @param m
	 * @return
	 */
	protected static float getAttackSpeed(ToolMaterial m) 
	{
		return roundToFloatDecimal(
				(m.getHarvestLevel() + m.getEfficiency()) * 0.02312138728 - 3.275722543,
				RoundingMode.HALF_EVEN, 1);
	}

	/**
	 * Lifted from Zot201's OnlySilver code, with permission.
	 * @param a
	 * @param mode
	 * @param precision
	 * @return
	 */
	protected static float roundToFloatDecimal(double a, RoundingMode mode, int precision) {
		return new BigDecimal(a).round(new MathContext(precision, mode)).floatValue();
	}

		
	public void registerItemModel() {
		SimpleCoreAPI.proxy.registerItemRenderer(plugin, this, 0, name);
	}

	/**
	 * Returns the ConfigTool used by this tool.
	 * @return ConfigTool
	 */
	public ConfigTool getConfigEntry() {
		return this.entry;
	}
	
	/**
	 * Sets the ConfigTool to be used for this tool.
	 * @param entry ConfigTool
	 * @return SimpleAxe
	 */
	public SimpleAxe setConfigEntry(ConfigTool entry) {
		this.entry = entry;
		this.setHarvestLevel("axe", entry.getHarvestLevel());
		this.setAdditionalProperties();
		return this;
	}
	
	/**
	 * Adds a tooltip to the tool. Must be localised, so needs to be present in a localization file.
	 * @param toolTip Name of the localisation entry for the tooltip, as a String. Normal format is modId.theitem.info
	 * @return SimpleAxe
	 */
	public SimpleAxe addToolTip(String toolTip) {
		TooltipHelper.addTooltipToItem(this, toolTip);
		return this;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return ItemStack.areItemStacksEqual(this.material.getRepairItemStack(),repair) 
				? true : super.getIsRepairable(toRepair, repair);
	}
	
	public void setAdditionalProperties() {
//		if(entry.getValueByName("CreativeTab") != null && entry.getValueByName("CreativeTab").isActive()) {
//			this.setCreativeTab(entry.getCreativeTab());
//		}
	}
} // end class
