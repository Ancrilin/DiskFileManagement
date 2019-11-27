package cn.scau.edu.base;

public class Disk {
	private String disk_id;//盘符
	private Dir root;//根目录
	private int capacity;//容量，单位：字节
	private Block[] blocks;//盘块
	private int blocks_num;//盘块数
	private FAT fat;//文件分配表
	
	public Disk(String disk_id, int blocks_num) {//盘符与盘块数
		this.disk_id = disk_id;
		this.blocks_num = blocks_num;//盘块数
		this.blocks = createBlocks();//创建盘块
		this.fat = createFAT();//创建文件分配表
		this.root = createRootDir();//创建根目录
		this.capacity=this.blocks_num*64;
	}
	
	private Dir createRootDir() {//创建根目录
		Dir root = this.fat.setRoot();
		this.root = root;
		return root;
	}
	
	private FAT createFAT() {//创建文件分配表,大小等于盘块
		FAT fat = new FAT(this.disk_id, this.blocks_num, this.blocks, this);
		return fat;
	}
	
	private Block[] createBlocks() {
		Block[] blocks = new Block[this.blocks_num];
		for(int i=0;i<blocks_num;i++) {
			blocks[i] = new Block();
		}
		return blocks;
	}
	
	//磁盘空闲空间数
	public int getFreeSpace() {
		return this.fat.getFreeSpace();
	}
	
	//磁盘损坏盘块数
	public int getError() {
		return this.fat.getError();
	}

	public String getDisk_id() {
		return disk_id;
	}

	public Dir getRoot() {
		return root;
	}

	public int getCapacity() {
		return capacity;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public int getBlocks_num() {
		return blocks_num;
	}

	public FAT getFat() {
		return fat;
	}
	
	
}
