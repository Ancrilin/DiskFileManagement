package cn.scau.edu.base;

import client.TreeNode;
import cn.scau.edu.util.Pointer;
import javafx.scene.control.TreeItem;

public class File implements Super{
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
	private TreeItem<TreeNode> treeItem = null;
	
	public File(String name, Dir parent, Disk disk) {
		this.name = name;
		this.parent = parent;
		this.disk = disk;
		this.disk_path = this.parent.getDiskPath() + "/" + this.name;
		this.path = this.disk_path;
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
		this.read.setByte_num(8);//跳过属性
		this.write.setBlock_num(this.block_start);
		this.write.setByte_num(8);//跳过属性
		this.byte_num = 0;
		this.setOrdinaryFile();
	}

	public Dir getParent() {
		return parent;
	}

	public String getDisk_path() {
		return disk_path;
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
	
	public byte[] getProperty() {
		byte[] property = new byte[8];
		for(int i=0;i<8;i++) {
			property[i] = this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[i];
		}
		return property;
	}
	
	private boolean setProperty(byte[] property) {
		for(int i=0;i<8;i++) {
			this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[i] = property[i];
		}
		return true;
	}
	
	//目录
	@Override
	public boolean isDir() {
		boolean flag = false;
		if(this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[4]==1) {
			flag = true;
		}
		return flag;
	}
		
	//设置系统文件
	@Override
	public boolean setSystemFile() {
		boolean flag = false;
		this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[6]=1;
		this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[5]=0;
		return true;
	}
		
	@Override
	public boolean isSystemFile() {
		boolean flag = false;
		if(this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[6]==1) {
			flag = true;
		}
		return flag;
	}
		
	@Override
	public boolean isOnlyReadFile() {
		boolean flag = false;
		if(this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[7]==1) {
			flag = true;
		}
		return flag;
	}
		
	@Override
	public boolean setOnlyReadFile(int state) {//1设为只读,0取消只读
		boolean flag = false;
		this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[7]=(byte)state;
		return true;
	}
		
	@Override
	public boolean isOrdinaryFile() {
		boolean flag = false;
		if(this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[5]==1) {
			flag = true;
		}
		return flag;
	}
		
	@Override
	public boolean setOrdinaryFile() {
		boolean flag = false;
		this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[6]=0;
		this.getDisk().getFat().getBlocks()[this.block_start].getBlockData()[5]=1;
		return true;
	}
	
	public TreeItem<TreeNode> getTreeItem()
	{
		return this.treeItem;
	}
	
	public void setTreeItem(TreeItem<TreeNode> node)
	{
		this.treeItem = node;
	}
	
}
