package cn.scau.edu.base;

public class Super {
	private byte[] property = new byte[8];

	public byte[] getProperty() {
		return property;
	}

	public void setProperty(byte[] property) {
		this.property = property;
	}
	
	//目录
	public boolean isDir() {
		if(this.property[4]==1) {
			return true;
		}
		return false;
	}
	
	//设置目录属性
	public void setDir() {
		this.property[4]=1;
	}
	
	//普通文件
	public boolean isOrdinaryFile() {
		if(this.property[5]==1) {
			return true;
		}
		return false;
	}
	
	//设置普通文件属性,则系统文件属性位置为0
	public void setOrdinaryFile() {
		this.property[5]=1;
		this.property[6]=0;
	}
	
	//系统文件
	public boolean isSystemFile() {
		if(this.property[6]==1) {
			return true;
		}
		return false;
	}
	
	//设置系统文件
	public void setSystemFile() {
		this.property[6]=1;
		this.property[5]=0;
	}
	
	//只读文件
	public boolean isOnlyReadFile() {
		if(this.property[7]==1) {
			return true;
		}
		return false;
	}
	
	//设置只读文件，1则为只读，0为取消只读
	public void setOnlyReadFile(int flag) {
		if(flag == 1) {
			this.property[7]=1;
		}else {
			this.property[7]=0;
		}
	}
}
