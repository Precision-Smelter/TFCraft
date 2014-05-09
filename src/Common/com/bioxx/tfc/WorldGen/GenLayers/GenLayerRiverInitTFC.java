package com.bioxx.tfc.WorldGen.GenLayers;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerRiverInitTFC extends GenLayerTFC
{
	public GenLayerRiverInitTFC(long par1, GenLayer par3GenLayer)
	{
		super(par1);
		this.parent = (GenLayerTFC) par3GenLayer;
	}

	/**
	 * Creates the random width of the river at the location
	 */
	@Override
	public int[] getInts(int xCoord, int zCoord, int xSize, int zSize)
	{
		int[] var5 = this.parent.getInts(xCoord, zCoord, xSize, zSize);
		int[] var6 = IntCache.getIntCache(xSize * zSize);

		for (int var7 = 0; var7 < zSize; ++var7)
		{
			for (int var8 = 0; var8 < xSize; ++var8)
			{
				this.initChunkSeed(var8 + xCoord, var7 + zCoord);
				var6[var8 + var7 * xSize] = var5[var8 + var7 * xSize] > 0 ? this.nextInt(2) + 2 : 0;
			}
		}
		return var6;
	}
}
