package cn.scau.edu.util;

import cn.scau.edu.base.File;

public class Buffer {
	private File file = null;
	private byte[] buf_byte = null;
	private int index = -1;//缓冲区指针;
	private boolean used = false;

	public Buffer() {
		this.reset();
	}
	
	public void reset() {
		this.buf_byte = new byte[1024];//缓冲区为1024个字节
		this.file = null;
		this.index = -1;
		this.used = false;
	}
	
	public void set(File file) {
		reset();
		this.file = file;
		this.setUsed();
	}
	
	public String getString() {
		return new String(this.buf_byte);
	}
	
	public byte[] getBuf_byte() {
		return this.buf_byte;
	}
	
	public void setUsed() {
		this.used = true;
	}
	
	//该缓冲区是否已使用
	public boolean isUsed() {
		return this.used;
	}
	
	public void writeImmediately() {
		if(this.index>=0)//缓冲区中存在内容
			this.file.getDisk().getFat().writeFile(this.file, index+1, this, OpenedTable.getInstance().getOFFile(this.file));
		this.buf_byte = new byte[1024];//刷新缓冲区
		this.index = -1;
	}

	//写入到缓冲区
	public boolean write(byte c) {
		boolean flag = false;
		this.index++;
		this.buf_byte[this.index] = c;
		if(this.index>=1024) {
			this.file.getDisk().getFat().writeFile(this.file, 1024, this, OpenedTable.getInstance().getOFFile(this.file));
			this.buf_byte = new byte[1024];//刷新缓冲区
			this.index = 0;
		}
		flag = true;
		return flag;
	}

	public File getFile() {
		return file;
	}

	public int getIndex() {
		return index;
	}
}
