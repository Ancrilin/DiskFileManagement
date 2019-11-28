package cn.scau.edu.util;

import java.util.ArrayList;
import java.util.List;

import cn.scau.edu.base.Dir;
import cn.scau.edu.base.Disk;
import cn.scau.edu.base.File;
import cn.scau.edu.base.Super;

//文件和目录的创建只能在当前目录下创建，即目录pwd
public class Management {
	private static Management management = null;
	private OpenedTable openedTable;
	private List<Disk> disk_list = new ArrayList<Disk>();
	private Dir pwd;//当前目录
	private Disk now_disk;
	private String path;//当前绝对路径
	private Dir now_root;
	private Buffer buffer1 = new Buffer();//双缓冲
	private Buffer buffer2 = new Buffer();
	
	public static Management getInstance() {
		if(management == null) {
			management = new Management();
		}
		return management;
	}
	
	//默认新建C盘,大小为128个盘块
	private Management() {
		this.now_disk = new Disk("C", 128);
		this.disk_list.add(now_disk);//添加至磁盘列表
		this.now_root = this.now_disk.getRoot();
		this.pwd = this.now_root;
		this.path = this.now_root.getPath();
		this.openedTable = OpenedTable.getInstance();
	}
	
	//磁盘调用api
	
	//切换磁盘,传入盘符，如果结果为false则不存在该盘符,每次切换磁盘时都默认修改pwd当前目录为切换后磁盘的根目录
	public boolean changeDisk(String disk_name) {
		boolean flag = false;
		for(int i=0;i<this.disk_list.size();i++) {
			if(this.disk_list.get(i).getDisk_id().equals(disk_name)) {
				this.now_disk = this.disk_list.get(i);
				this.now_root = this.now_disk.getRoot();
				this.pwd = this.now_root;
				this.path = this.now_root.getPath();
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	//新建磁盘,传入盘符和盘块数,结果为null则存在相同盘符名
	public Disk newDisk(String disk_name, int blocks_num) {
		Disk new_disk = null;
		boolean duplicate = false;
		for(int i=0;i<this.disk_list.size();i++) {
			if(this.disk_list.get(i).getDisk_id().equals(disk_name)){
				duplicate = true;
				break;
			}
		}
		if(duplicate == false) {
			new_disk = new Disk(disk_name, blocks_num);
			this.disk_list.add(new_disk);
		}
		return new_disk;
	}
	
	//目录调用api
	
	//新建目录，null为创建失败，可能有重复目录或文件名
	public Dir makeDir(String name) {
		Dir dir = this.now_disk.getFat().newDir(name, this.pwd);
		return dir;
	}
	
	//当前目录搜索文件或目录,null则文件或目录不存在
	public Super search(String name) {
		Super result = this.pwd.search(name);
		return result;
	}
	
	//从当前磁盘根目录开始
	public Super searchFromRoot(String name) {
		Super result = this.now_disk.getRoot().search(name);
		return result;
	}
	
	//删除目录，只能删除空目录,false可能该目录不为空目录,或该目录为系统目录
	public boolean removeDir(Dir dir) {
		if(dir.isOrdinaryFile()) {//普通文件或目录才可以删除
			return this.now_disk.getFat().deleteDir(dir);
		}else {
			return false;
		}
	}
	
	//通过绝对路径修改当前目录
	public boolean changeDirFromRoot(String disk_path) {
		boolean flag = false;
		Dir now = this.now_root.searchDirByDiskPath(disk_path);
		if(now!=null) {
			this.pwd = now;
			flag = true;
		}
		return flag;
	}
	
	//选择目录,鼠标点击目录要跳转时调用
	public Dir selectDir(Dir dir) {
		Dir selected = null;
		for(int i=0;i<this.pwd.getDir_list().size();i++) {
			if(this.pwd.getDir_list().get(i).getName().equals(dir.getName())){
				selected = this.pwd.getDir_list().get(i);
				this.pwd = selected;
				this.path = this.pwd.getPath();
				break;
			}
		}
		return selected;
	}
	
	//选择父目录,鼠标点击父目录返回上一目录时调用,当前目录为根目录时,返回null
	public Dir selectParent() {
		Dir parent = null;
		if(!pwd.getName().equals("root")) {
			parent = pwd.getParent();
			this.pwd = parent;
			this.path = this.pwd.getPath();
		}
		return parent;
	}
	
	//显示目录的目录
	public List<Dir> showDirsDir(Dir dir){
		return dir.getDir_list();
	}
	//显示目录的文件
	public List<File> showDirsFile(Dir dir){
		return dir.getFile_list();
	}
	
	//文件调用api
	
	//从当前目录新建文件，null为创建失败，可能有重复目录或文件名
	public File makeFile(String name) {
		File file = null;
		boolean duplicate = false;//当前目录有无重复名文件
		for(int i=0;i<this.pwd.getFile_list().size();i++) {
			if(this.pwd.getFile_list().get(i).getName().equals(name)) {
				duplicate = true;
				break;
			}
		}
		if(this.pwd.getName().equals(name)) {//是否与当前目录名重复
			duplicate = true;
		}
		if(duplicate == false) {
			file = this.now_disk.getFat().newFile(name, this.pwd);
		}
		return file;
	}
	
	//删除文件,文件必须先关闭
	public boolean deleteFile(File file) {
		boolean flag = false;
		if(!this.openedTable.isExist(file)) {//必须先关闭文件,已打开表中不存在
			if(file.isSystemFile()) {//系统文件不能删除
				return flag;
			}
			flag = this.now_disk.getFat().deleteFile(file);
			System.out.println("delete "+flag);
		}
		return flag;
	}
	
	//打开文件,读与写一起
	public File openFile() {
		File file = null;
		
		return file;
	}
	
	//读文件,不能写,从文件指针读length长度，遇到文件结束时则返回不足长度
	public String readFile(File file, int length) {
		byte[] data = null;
		if(!openedTable.isExist(file)) {//文件没打开，则打开文件
			boolean open = openedTable.add(file, 0);//读打开文件
			if(open==false) {//打开失败
				return null;
			}
		}
		data = this.getNow_disk().getFat().readFile(file, length, openedTable.getOFFile(file));
		return new String(data);
	}
	
	//显示文件所有内容
	public String typeFile(File file) {
		byte [] data = null;
		if(!openedTable.isExist(file)) {
			boolean open = openedTable.add(file, 0);//读打开文件
			if(open==false) {//打开失败
				return null;
			}
		}
		data = this.getNow_disk().getFat().typeFile(file);
		return new String(data);
	}
	
	//写文件
	public boolean writeFile(File file, String data) {
		boolean flag = false;
		if(file.isOnlyReadFile()) {//该文件为只读文件
			return false;
		}
		if(!openedTable.isExist(file)) {
			boolean open = openedTable.add(file, 1);//写打开文件
			if(open==false) {//打开失败
				return false;
			}
		}
		Buffer buf = null;
		if(buffer1.isUsed()&&buffer2.isUsed()) {
			this.linkBuffer(file, buffer1);
			buf = buffer1;
		}
		if(!buffer2.isUsed()) {
			this.linkBuffer(file, buffer2);
			buf = buffer2;
		}
		if(!buffer1.isUsed()) {
			this.linkBuffer(file, buffer1);
			buf = buffer1;
			buffer2.reset();
		}
		byte[] data_byte = data.getBytes();
		for(int i=0;i<data.length();i++) {
			flag = buf.write(data_byte[i]);
		}
		return flag;
	}
	
	public boolean linkBuffer(File file, Buffer buf) {
		boolean flag = false;
		if(buf.isUsed())
			buf.writeImmediately();
		buf.reset();
		buf.set(file);
		return flag;
	}
	
	public boolean closeFile(File file) {//关闭文件,如果该缓冲区中有已写的内容,则直接写入
		if(buffer1.isUsed()&&buffer1.getFile().getDisk_path().equals(file.getDisk_path())) {
			buffer1.writeImmediately();
			buffer1.reset();
		}
		if(buffer2.isUsed()&&buffer2.getFile().getDisk_path().equals(file.getDisk_path())) {
			buffer2.writeImmediately();
			buffer2.reset();
		}
		return this.getNow_disk().getFat().closeFile(file);
	}
	
	//更改文件属性,0为只读，1为可写，2为改为系统文件，3为改为普通文件
	public boolean changeFileProperty(File file, int state) {
		boolean flag = false;
		if(state==0) {
			file.setOnlyReadFile(1);
		}
		if(state==1) {
			file.setOnlyReadFile(0);//取消只读
		}
		if(state==2) {//改为系统文件则取消为普通文件
			file.setSystemFile();
		}
		if(state==3) {//改为普通文件则取消为系统文件
			file.setOrdinaryFile();
		}
		return flag;
	}
	
	
	//立即更新缓冲区
	public void updateBuffer() {
		if(buffer1.isUsed())
			buffer1.writeImmediately();
		if(buffer2.isUsed())
			buffer2.writeImmediately();
	}

	//磁盘列表,默认已存在C盘
	public List<Disk> getDisk_list() {
		return disk_list;
	}

	//当前路径
	public Dir getPwd() {
		return pwd;
	}

	//当前磁盘
	public Disk getNow_disk() {
		return now_disk;
	}

	//当前磁盘绝对路径
	public String getPath() {
		return path;
	}

	//当前磁盘根目录
	public Dir getNow_root() {
		return now_root;
	}
	
	//返回从磁盘开始的绝对路径
	public String getDisk_path() {
		return this.now_disk.getDisk_id() + this.path;
	}

	public static Management getManagement() {
		return management;
	}

	public OpenedTable getOpenedTable() {
		return openedTable;
	}

	public Buffer getBuffer1() {
		return buffer1;
	}

	public Buffer getBuffer2() {
		return buffer2;
	}
}
