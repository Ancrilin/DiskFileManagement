package cn.scau.edu.base;

import cn.scau.edu.util.Pointer;

public class File extends Super{
	private String name;//文件名
	private Dir parent;//父目录
	private String content="";//文件内容
	private int blocks_num;//文件盘块数
	private int block_start;//文件起始盘块
	private String path;//绝对路径
	private String disk_path;
	private Disk disk;
	private Pointer read;
	private Pointer write;
	private int byte_num;//字节数，文件大小
	
	public File(String name, Dir parent, Disk disk) {
		this.name = name;
		this.parent = parent;
		this.disk = disk;
		this.path = this.parent.getPath() + "/" + this.name;
		this.disk_path = this.disk.getDisk_id() + ":/" + this.path;
	}
	
	public void updateBlocks_num() {//刷新文件盘块数
		double size = Math.ceil(this.content.length()/64);//文件内容占用盘块数向上取整
		int blocks_len = (int)size;
		this.blocks_num = blocks_len;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getBlocks_num() {
		return blocks_num;
	}

	public void setBlocks_num(int blocks_num) {
		this.blocks_num = blocks_num;
	}

	public int getBlock_start() {
		return block_start;
	}

	//设置文件开始符合并初始化文件读写指针
	public void setBlock_start(int block_start) {
		this.block_start = block_start;
		this.read = new Pointer();
		this.write = new Pointer();
		this.read.setBlock_num(this.block_start);
		this.read.setByte_num(0);
		this.write.setBlock_num(this.block_start);
		this.write.setByte_num(0);
		this.byte_num = 0;
	}

	public Dir getParent() {
		return parent;
	}

	public String getPath() {
		return path;
	}

	public String getDisk_path() {
		return disk_path;
	}
	
	//普通文件
	public boolean isOrdinaryFile() {
		return super.isOrdinaryFile();
	}
	
	//设置普通文件属性,则系统文件属性位置为0
	public void setOrdinaryFile() {
		super.setOrdinaryFile();
	}

	public Pointer getRead() {
		return read;
	}

	public void setRead(int block_num, int byte_num) {
		this.read.setBlock_num(block_num);
		this.read.setByte_num(byte_num);
	}

	public Pointer getWrite() {
		return write;
	}

	public void setWrite(int block_num, int byte_num) {
		this.write.setBlock_num(block_num);
		this.write.setByte_num(byte_num);
	}

	public int getByte_num() {
		return byte_num;
	}

	public void setByte_num(int byte_num) {
		this.byte_num = byte_num;
	}

	public Disk getDisk() {
		return disk;
	}
	
	
	
}
