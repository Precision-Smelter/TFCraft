package TFC.Blocks;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityBloomery;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockBloomery extends BlockTerraContainer
{
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	public static final int headBlockToFootBlockMap[][] = {
		{
			0, 1
		}, {
			-1, 0
		}, {
			0, -1
		}, {
			1, 0
		}
	};

	public BlockBloomery(int i, int tex)
	{
		super(i, Material.rock);
		this.blockIndexInTexture = tex;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z) & 4;
		if(meta == 0)
			return 0;
		else
			return 15;

	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;

		if((TileEntityBloomery)world.getBlockTileEntity(i, j, k)!=null)
		{
			TileEntityBloomery te;
			te = (TileEntityBloomery)world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if(te.isValid)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 26, world, i, j, k);
			}
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return world.isBlockOpaqueCube(i, j-1, k) && world.isBlockOpaqueCube(i, j+1, k);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		j = j & 3;
		
		if(i == 0 || i == 1) {
			return 64;
		}

		if(j == 0 && i == 2	) {
			return blockIndexInTexture+lit;
		}
		if(j == 1 && i == 5) {
			return blockIndexInTexture+lit;
		}
		if(j == 2 && i == 3) {
			return blockIndexInTexture+lit;
		}
		if(j == 3 && i == 4) {
			return blockIndexInTexture+lit;
		}
		return 64;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		if(!world.isRemote)
		{
			xCoord = i;
			yCoord = j;
			zCoord = k;
			int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
			world.setBlockMetadataWithNotify(i, j, k, l);
		}

	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k) 
	{
		if(!world.isRemote)
		{
			meta = world.getBlockMetadata(i, j, k);
			int[] dir = headBlockToFootBlockMap[meta & 3];
			if(world.getBlockId(i+dir[0], j, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+1, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+1, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+1, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+2, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+2, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+2, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+3, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+3, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+3, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+4, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+4, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+4, k+dir[1]);
			}
			world.setBlock(i, j, k, 0);
			world.markBlockForUpdate(i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		meta = world.getBlockMetadata(i, j, k) & 3;
		int[] dir = headBlockToFootBlockMap[meta];


		if(!world.isBlockOpaqueCube(i, j-1, k) || !world.isBlockOpaqueCube(i, j+1, k))
		{
			((TileEntityBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
			world.markBlockForUpdate(i,j,k);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{       
		//dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Core.terraBloomery, 1));
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityBloomery();
	}
}
