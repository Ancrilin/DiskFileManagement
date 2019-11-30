package cn.scau.edu.util;

public class OFFile {
	private String disk_path;
	private byte[] property;
	private int block_start;
	private int flag;//0以读方式打开，1以写方式打开
	private Pointer read = new Pointer();//读指针位置
	private Pointer write = new Pointer();//写指针位置
	
	public OFFile() {
		
	}

	public String getDisk_path() {
		return disk_path;
	}

	public void setDisk_path(String disk_path) {
		this.disk_path = disk_path;
	}

	public byte[] getProperty() {
		return property;
	}

	public void setProperty(byte[] property) {
		this.property = property;
	}

	public int getBlock_start() {
		return block_start;
	}

	public void setBlock_start(int block_start) {
		this.block_start = block_start;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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
	
}
