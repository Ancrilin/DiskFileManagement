package cn.scau.edu.util;

import java.util.ArrayList;
import java.util.List;

import cn.scau.edu.base.File;

public class OpenedTable {
	private static OpenedTable openedTable = null;
	private List<OFFile> table = new ArrayList<OFFile>();
	private int num;//允许打开文件数量
	
	public static OpenedTable getInstance() {
		if(openedTable == null) {
			openedTable = new OpenedTable();
		}
		return openedTable;
	}
	
	private OpenedTable() {
		this.num = 5;
	}
	
	//没有打开该文件,则打开后添加到已打开表中
	public boolean add(File file, int flag) {
		boolean result = false;
		if(!this.isExist(file) && this.table.size()<=this.num) {
			OFFile new_one = new OFFile();
			new_one.setDisk_path(file.getDisk_path());
			new_one.setPath(file.getPath());
			new_one.setFlag(flag);
			new_one.setProperty(file.getProperty());
			new_one.setBlock_start(file.getBlock_start());
			new_one.setRead(file.getBlock_start(), 0);
			new_one.setWrite(file.getWrite().getBlock_num(), file.getWrite().getByte_num());
			this.table.add(new_one);
			result = true;
		}
		return result;
	}
	
	//得到已打开文件
	public OFFile getOFFile(File file) {
		OFFile offile = null;
		for(int i=0;i<this.table.size();i++) {
			if(this.table.get(i).getDisk_path().equals(file.getDisk_path())) {
				offile = this.table.get(i);
				break;
			}
		}
		System.out.println(offile.getDisk_path());
		return offile;
	}
	
	//关闭文件调用,删除该已打开文件表
	public boolean removeOFFile(File file) {
		boolean flag = false;
		for(int i=0;i<this.table.size();i++) {
			if(this.table.get(i).getDisk_path().equals(file.getDisk_path())) {
				this.table.remove(i);
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean isExist(File file) {
		boolean result = false;
		for(int i=0;i<this.table.size();i++) {
			if(this.table.get(i).getDisk_path().equals(file.getDisk_path())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	//获得文件打开类型，-1为该文件没有打开,0表示读，1表示写
	public int getFlag(File file) {
		int flag = -1;
		for(int i=0;i<this.table.size();i++) {
			if(this.table.get(i).getDisk_path().equals(file.getDisk_path())) {
				flag = this.table.get(i).getFlag();
				break;
			}
		}
		return flag;
	}
	
	//获得当前已打开文件数量
	public int getOpenedSize() {
		return this.table.size();
	}
}
