package cn.scau.edu.util;

import cn.scau.edu.base.Block;
import cn.scau.edu.base.Disk;

public class MemoryBlock {
	private Block block;
	private int index;
	private Disk disk = null;
	
	public MemoryBlock(Block block, int index, Disk disk) {
		this.block = block;
		this.index = index;
		this.disk = disk;
	}

	public Block getBlock() {
		return block;
	}

	public int getIndex() {
		return index;
	}

	public Disk getDisk() {
		return disk;
	}
	
	
}
