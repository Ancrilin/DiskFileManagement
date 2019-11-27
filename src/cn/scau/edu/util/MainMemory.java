package cn.scau.edu.util;

import java.util.ArrayList;
import java.util.List;

import cn.scau.edu.base.Block;
import cn.scau.edu.base.Disk;

public class MainMemory {
	private static MainMemory mainMemory = null;
	private List<MemoryBlock> mainMemoryList = new ArrayList<MemoryBlock>();
	
	public static MainMemory getInstance() {
		if(mainMemory == null) {
			mainMemory = new MainMemory();
		}
		return mainMemory;
	}
	
	private MainMemory() {
		this.mainMemoryList.clear();
	}
	
	public boolean isBlockExist(Block block, int index, Disk disk) {
		boolean flag = false;
		for(int i=0;i<mainMemoryList.size();i++) {//磁盘相同,盘块号相同则为指向同一盘块
			if(this.mainMemoryList.get(i).getDisk().getDisk_id().equals(disk.getDisk_id())&&this.mainMemoryList.get(i).getIndex()==index) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean addToMemory(Block block, int index, Disk disk) {
		boolean flag = false;
		MemoryBlock n_block = new MemoryBlock(block, index, disk);
		this.mainMemoryList.add(n_block);
		flag = true;
		return flag;
	}
	
	//移除该盘块
	public boolean deleteBlock(int index, Disk disk) {
		boolean flag = false;
		for(int i=0;i<mainMemoryList.size();i++) {//磁盘相同,盘块号相同则为指向同一盘块
			if(this.mainMemoryList.get(i).getDisk().getDisk_id().equals(disk.getDisk_id())&&this.mainMemoryList.get(i).getIndex()==index) {
				this.mainMemoryList.remove(i);
				flag = true;
				break;
			}
		}
		return flag;
	}
	
}
