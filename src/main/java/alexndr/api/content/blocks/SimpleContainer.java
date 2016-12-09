
package alexndr.api.content.blocks;

import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author cyhiggin
 * This class is a Compat work-alike of BlockContainer.
 */
public abstract class SimpleContainer extends CompatBlock implements ITileEntityProvider
{

	public SimpleContainer(Material blockMaterialIn, MapColor blockMapColorIn) 
	{
		super(blockMaterialIn, blockMapColorIn);
        this.isBlockContainer = true;
	}

	public SimpleContainer(Material materialIn) 
	{
		 this(materialIn, materialIn.getMaterialMapColor());
	}

	/* ------------- Most of the following cut & pasted from BlockContainer ----------- */
	/**
	 * check to see if bad neighboring block--true if cactus, false otherwise.
	 * @param worldIn world object block exists in
	 * @param pos block position of this block
	 * @param facing side of block to check for unneighborliness.
	 * @return true if neighbor is cactus, false otherwise.
	 */
    protected boolean isInvalidNeighbor(World worldIn, BlockPos pos, EnumFacing facing)
    {
        return worldIn.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS;
    }

    /**
     * Do we have a bad neighbor on any horizontal side?
     * @param worldIn
     * @param pos  world object block exists in
     * @return true if there is an invalid neighbor on any horizontal side, false otherwise.
     */
    protected boolean hasInvalidNeighbor(World worldIn, BlockPos pos)
    {
        return this.isInvalidNeighbor(worldIn, pos, EnumFacing.NORTH) 
        		|| this.isInvalidNeighbor(worldIn, pos, EnumFacing.SOUTH) 
        		|| this.isInvalidNeighbor(worldIn, pos, EnumFacing.WEST) 
        		|| this.isInvalidNeighbor(worldIn, pos, EnumFacing.EAST);
    } // end hasInvalidNeighbor()

    /**
     * The type of render function called. MODEL for mixed tesr and static model, 
     * MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but 
     * before the Tile Entity is updated.
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    /**
     * Called on both Client and Server when World#addBlockEvent is called. On the Server, this may perform additional
     * changes to the world, like pistons replacing the block with an extended base. On the client, the update may
     * involve replacing tile entities, playing sounds, or performing other visual actions to reflect the server side
     * changes.
     */
    @SuppressWarnings("deprecation")
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

} // end class
