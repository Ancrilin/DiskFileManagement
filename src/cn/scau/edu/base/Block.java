package cn.scau.edu.base;

public class Block {
	private boolean allocation=false;//默认块空闲
	private byte [] data = new byte[64];//64个字节
	
	public Block() {
		
	}
	
	public boolean setBlockData(byte [] data) {//设置块内容
		this.data = data;
		this.allocation = true;
		return true;
	}
	
	public byte[] getBlockData() {//得到块内容
		return this.data;
	}
	
	public boolean saveFromString(String s) {
		boolean flag = false;
		this.data = s.getBytes();//转换为byte数组
		return flag;
	}
	
	public boolean isAllocation() {//是否已占用该块
		return this.allocation;
	}
	
	public void setBlocksFree() {//设置该块是否空闲
		this.allocation = false;
	}
	
	public void setBlocksUsed() {
		this.allocation = true;
	}
	
	public String getContentToString() {//转换为String
		String data_s = new String(data);
		return data_s;
	}
	
	public byte getByteByIndex(int index) {//获得第index个字节
		return data[index];
	}
}
