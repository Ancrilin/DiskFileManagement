package cn.scau.edu.base;

import cn.scau.edu.util.Buffer;
import cn.scau.edu.util.MainMemory;
import cn.scau.edu.util.OFFile;
import cn.scau.edu.util.OpenedTable;

public class FAT {
	private String disk_id;
	private Disk disk;
	private int blocks_num;//盘块大小
	private int freeSpace;//空闲空间
	private int []allocation;//文件分配表向量
	private int FAT_allocation;//文件分配表占用盘块数
	private Block [] blocks;//磁盘盘块
	private int start;//磁盘内容开始索引
	private int error=0;
	private Dir root;
	
	public FAT(String disk_id, int num, Block[] blocks, Disk disk) {
		this.disk_id = disk_id;
		this.blocks = blocks;//磁盘盘块
		this.disk = disk;
		this.blocks_num = num;//盘块大小
		this.freeSpace = blocks_num;//空闲盘块大小
		this.allocation = new int[this.blocks_num];//创建文件分配表空闲占用向量,默认为0表示空闲
		init();//文件分配表初始化
	}
	
	//设置文件分配表根目录
	public Dir setRoot() {
		if(root == null) {
			Dir root = this.newDir("root", null);
			//root.setDir();//设置为目录
			root.setSystemFile();//为系统目录文件，不能删除
			this.root = root;
		}
		return root;
	}
	
	//文件分配表初始化
	private void init() {
		double size = Math.ceil(this.blocks_num/64.0);//文件分配表占用盘块数向上取整
		this.FAT_allocation = (int)size;
		this.start = this.FAT_allocation;//文件分配表开始，即从文件分配表占用盘块后开始
		for(int i=0;i<this.FAT_allocation;i++) {//文件分配表占用
			this.blocks[i].setBlocksUsed();//块已使用
			this.allocation[i]=-1;
			this.freeSpace--;
		}
	}
	
	public int getFreeSpace() {//返回空闲空间大小
		return this.freeSpace;
	}
	
	//获得盘块数据
	public byte[] getBlockData(int index) {
		if(this.blocks[index].isAllocation()==false||this.allocation[index]==0) {//磁盘块空闲无法获取数据
			return null;
		}else {
//			System.out.println("blocks["+index+"]"+"is free");
			return this.blocks[index].getBlockData();
		}
	}
	
	//设置磁盘块数据
	public boolean setBlockData(int index, byte []data) {
		boolean flag = false;
		this.blocks[index].setBlockData(data);
		return flag;
	}
	
	//设置盘块空闲
	private void setFree(int index) {
		this.blocks[index].setBlocksFree();//设置盘块空闲
		this.allocation[index]=0;//文件分配表空闲
		this.freeSpace++;//空闲盘块数+1
	}
	
	//释放盘块,删除目录和文件时必须释放盘块
	private boolean releaseBlock(int index) {
		boolean flag = false;
		this.setFree(index);
		return true;
	}
	
	//设置盘块损坏，-2
	public void setErrorBlock(int index) {
		this.allocation[index]=-2;//-2为该盘块已损坏，无法使用
		this.freeSpace--;//盘块空闲数-1
		this.blocks[index].setBlocksUsed();//该盘块损坏，无法使用
		this.error++;
	}
	
	
	//申请空闲磁盘块，并设置指向下一块磁盘块，文件结束则为-1
	private int searchFreeBlocks(int state) {
		int free = -1;
		if(this.freeSpace!=0) {//磁盘仍有空闲空间
			for(int i=this.start;i<blocks_num;i++) {
				if(this.allocation[i] == 0 && this.blocks[i].isAllocation()==false) {//盘块空闲
					this.blocks[i].setBlocksUsed();
					this.allocation[i] = state;
					this.freeSpace--;
					free = i;
					break;
				}
			}
		}
		return free;
	}
	
	//创建目录
	public Dir newDir(String name, Dir parent) {
		Dir n_dir = null;
		boolean duplicate = false;//判断有无重复名
		if(!name.equals("root"))
		{
			for(File file:parent.getFile_list()) {
				if(file.getName().equals(name)) {
					duplicate = true;
					break;
				}
			}
			for(Dir dir:parent.getDir_list()) {
				if(dir.getName().equals(name)) {
					duplicate = true;
					break;
				}
			}
			if(parent.getName().equals(name)) {//与父目录同名
				duplicate = true;
			}
		}
		if(duplicate == false)//无重复则创建目录
		{
			int free = this.searchFreeBlocks(-1);//目录项的文件分配表值为-1
			if(free!=-1)
			{
				n_dir = new Dir(name, parent, this.disk);
				//n_dir.setDir();//目录
				n_dir.setOrdinaryFile();//普通目录文件
				n_dir.setBlock_start(free);
				if(!name.equals("root") && n_dir!=null)//非根目录的父目录才能添加目录
				{
					parent.addDir(n_dir);
				}
			}
		}
		return n_dir;//null为创建失败，可能有重复目录或文件名
	}
	
	//删除空目录,root目录无法删除
	public boolean deleteDir(Dir dir) {
		boolean flag = false;
		int index = dir.getBlock_start();
		if(dir.getDir_list().size() == 0 && dir.getFile_list().size() == 0) {//空目录才能删除
			dir.getParent().removeDirList(dir.getName());//父目录移除该要删除的目录
			this.releaseBlock(index);//释放盘块
			flag = true;
		}
		return flag;//可能该目录为空目录
	}
	
	//创建文件
	public File newFile(String name, Dir parent) {
		File new_file = null;
		boolean duplicate = false;//判断有无重复名
		for(File file:parent.getFile_list()) {
			if(file.getName().equals(name)) {
				duplicate = true;
				break;
			}
		}
		for(Dir dir:parent.getDir_list()) {
			if(dir.getName().equals(name)) {
				duplicate = true;
				break;
			}
		}
		if(parent.getName().equals(name)) {//与父目录同名
			duplicate = true;
		}
		if(duplicate == false) {//无重复名
			int free = this.searchFreeBlocks(-1);//刚创建文件大小为0，没有内容，只分配一个盘块
			if(free!=-1) {
				new_file = new File(name, parent, this.disk);
				new_file.setOrdinaryFile();//普通文件,可删除
				new_file.setBlock_start(free);//文件分配开始盘块号,并初始化文件读写指针
				new_file.setBlocks_num(1);//初始盘块数为1
				parent.addFile(new_file);//父目录增加链接
			}
		}
		return new_file;//null为创建失败，可能有重复目录或文件名
	}
	
	//删除文件,文件必须先关闭
	public boolean deleteFile(File file) {
		boolean flag = false;
		if(!OpenedTable.getInstance().isExist(file)) {//删除的文件必须没有打开,已打开表中不存在
			int index = file.getBlock_start();//从文件开始盘块删除
			int now = index;
			int next = this.allocation[now];//指向下一盘块
			this.releaseBlock(now);
			while(next!=-1) {//-1为当前盘块就是最后一个盘块
				now = next;
				next = this.allocation[now];
				this.releaseBlock(now);
			}
			flag = file.getParent().removeFileList(file.getName());//删除文件父目录的文件链接
		}
		return flag;
	}
	
	//读文件,要读的文件必须先打开,从读指针开始读,不会把#读出来
	public byte[] readFile(File file, int length, OFFile offile) {
		byte[] t_data = new byte[length];//创建要读取的长度字节数组
		int block_read = offile.getRead().getBlock_num();//读指针盘块
		int byte_read = offile.getRead().getByte_num();//读指针字节位置
		int real_length = 0;//真实读取长度
		System.out.println("read: "+block_read);
		for(int i=0;i<length;i++) {
			if(!MainMemory.getInstance().isBlockExist(this.blocks[block_read], block_read, this.disk)) {//盘块要先读入内存
				MainMemory.getInstance().addToMemory(this.blocks[block_read], block_read, this.disk);
			}
			if(this.getBlocks()[block_read].getBlockData()[byte_read]=='#') {//长度不够真实长度,'#'为文件结束,不读取#,指针不移动
				break;
			}
			t_data[i] = this.getBlocks()[block_read].getBlockData()[byte_read];
			real_length++;
			byte_read++;
			if(byte_read>=64) {//当前盘块已读完
				byte_read=0;
				block_read=this.allocation[block_read];//读下一盘块
			}
			
		}
		byte[] data = new byte[real_length];
		for(int i=0;i<real_length;i++) {//返回真实读取数据
			data[i] = t_data[i];
		}
		//file.setRead(block_read, byte_read);//修改读指针,文件读指针不用修改
		offile.setRead(block_read, byte_read);//设置已打开文件表读指针
		return data;
	}
	
	//写文件,要写的长度,要写的文件必须先打开,边写边申请新空间
	public void writeFile(File file, int length, Buffer buf, OFFile offile) {
		byte[] data = buf.getBuf_byte();//从缓冲区得到要写的字节数组
		int block_write = offile.getWrite().getBlock_num();//写块指针
		int byte_write = offile.getWrite().getByte_num();//写字节指针
		int n_length = 0;//实际写入文件长度
		for(int i=0;i<length;i++) {
			this.blocks[block_write].getBlockData()[byte_write] = data[i];
			if(data[i] == '#') {//结束符,不用指向下一字节,下次直接覆盖#
				break;
			}
			n_length++;//档次写入长度没有包含#
			byte_write++;//指针后移
			if(byte_write>=64) {//要指向下一盘块
				int free = this.searchFreeBlocks(-1);//申请新的空闲块
				this.allocation[block_write] = free;//之前空闲块指向新的空闲块
				block_write = free;//读指针盘块指向新的空闲块
				byte_write = 0;//读指针字节指向块头
				file.setBlocks_num(file.getBlocks_num()+1);//文件盘块数+1
			}
		}
		file.setWrite(block_write, byte_write);//修改文件写指针
		offile.setWrite(block_write, byte_write);//设置已打开文件表写指针
		file.setByte_num(file.getByte_num()+n_length);//文件大小增加,不包含#
	}
	
	//关闭文件,删除已打开文件表
	public boolean closeFile(File file) {
		boolean flag = false;
		if(OpenedTable.getInstance().isExist(file)) {
			flag = OpenedTable.getInstance().removeOFFile(file);
		}
		return flag;
	}
	
	public byte[] typeFile(File file) {
		byte[] data = new byte[file.getByte_num()];
		int block_read = file.getBlock_start();//读指针盘块
		int byte_read = 0;//读指针字节位置
		for(int i=0;i<file.getByte_num();i++) {
			if(!MainMemory.getInstance().isBlockExist(this.blocks[block_read], block_read, this.disk)) {//盘块要先读入内存
				MainMemory.getInstance().addToMemory(this.blocks[i], i, this.disk);
			}
			data[i] = this.blocks[block_read].getBlockData()[byte_read];
			byte_read++;
			if(byte_read>=64) {//当前盘块已读完
				byte_read=0;
				block_read=this.allocation[block_read];//读下一盘块
			}
			if(data[i]=='#') {//长度不够真实长度,'#'为文件结束
				break;
			}
		}
		return data;
	}

	public String getDisk_id() {
		return disk_id;
	}

	public int getBlocks_num() {
		return blocks_num;
	}

	public int[] getAllocation() {
		return allocation;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public int getStart() {
		return start;
	}

	public int getError() {
		return error;
	}

	public Dir getRoot() {
		return root;
	}
}
