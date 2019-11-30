package cn.scau.edu.base;

public interface Super {
	public boolean isOrdinaryFile();
	public boolean setOrdinaryFile();
	public boolean isSystemFile();
	public boolean setSystemFile();
	public boolean isDir();
	public boolean isOnlyReadFile();
	public boolean setOnlyReadFile(int state);
}
