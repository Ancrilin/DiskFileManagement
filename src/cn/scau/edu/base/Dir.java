package cn.scau.edu.base;

import java.util.ArrayList;
import java.util.List;

public class Dir implements Super{
	private String name;
	private int block_start;//目录所在盘块,目录文件只有一个盘块
	private Dir parent = null;//父目录
	private List<Dir> dir_list = new ArrayList<Dir>();//子目录列表
	private List<File> file_list = new ArrayList<File>();//子文件列表
	private Disk disk;
	private String disk_path;
	private String path;//绝对路径
	
	public Dir(String name, Dir parent, Disk disk) {//目录名，父目录，磁盘
		this.name = name;
		this.parent = parent;
		if(name.equals("root")) {
			this.disk = disk;
		}else {
			this.disk = parent.getDisk();
		}
		init();
	}
	
	private void init() {
		this.disk_path = this.disk.getDisk_id() + ":/";
		if(this.name.equals("root")) {
			this.path = "root";
		}else {
			this.path = this.parent.getPath() + "/" + this.name;
		}
		byte[] property = new byte[8];//设置目录属性
		property[4] = 1;//设置为目录
		if(this.name.equals("root")) {//系统文件，不能删除
			property[6]=1;
		}
		this.setProperty(property);
	}
	
	//当前目录递归搜索
	public List<Super> search(String name) {
		List<Super> search_result = new ArrayList<Super>();//多搜索，返回列表
		for(Dir dir:this.dir_list) {
			if(dir.getName().equals(name)) {//符合目录或文件名,加入到列表
				search_result.add(dir);
			}
			search_result.addAll(dir.search(name));
		}
		for(File file:this.file_list) {
			if(file.getName().equals(name)) {
				search_result.add(file);
			}
		}
		return search_result;
	}
	
	//只搜索当前目录下的子目录和子文件
	public Super searchNow(String name) {
		Super result = null;
		for(Dir dir:this.dir_list) {
			if(dir.getName().equals(name)) {//符合目录或文件名,加入到列表
				result = dir;
				break;
			}
		}
		for(File file:this.file_list) {
			if(file.getName().equals(name)) {
				result = file;
				break;
			}
		}
		return result;
	}
	
	//从当前目录搜索绝对路径符合的目录，跳转指令实现
	public Dir searchDirByDiskPath(String disk_path) {
		Dir result = null;
		for(Dir dir:this.dir_list) {
			if(dir.getName().equals(disk_path)) {
				result = dir;
				break;
			}else {
				result = dir.searchDirByDiskPath(disk_path);
			}
		}
		return result;
	}

	public String getName() {
		return name;
	}
	
	public boolean removeDirList(String name) {//删除目录中的某个目录
		boolean flag = false;
		for(int i=0;i<this.dir_list.size();i++) {
			if(this.dir_list.get(i).getName().equals(name)) {
				this.dir_list.remove(i);
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean removeFileList(String name) {//删除目录中的某个文件
		boolean flag = false;
		for(int i=0;i<this.file_list.size();i++) {
			if(this.file_list.get(i).getName().equals(name)) {
				this.file_list.remove(i);
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean addDir(Dir dir) {
		boolean flag = false;
		flag = this.dir_list.add(dir);
		return flag;
	}
	
	public boolean addFile(File file) {
		boolean flag = false;
		flag = this.file_list.add(file);
		return flag;
	}

	public int getBlock_start() {
		return block_start;
	}

	public Dir getParent() {
		return parent;
	}

	public List<Dir> getDir_list() {
		return dir_list;
	}

	public List<File> getFile_list() {
		return file_list;
	}

	public Disk getDisk() {
		return disk;
	}

	public void setBlock_start(int block_start) {
		this.block_start = block_start;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getDiskPath() {
		return this.disk_path + this.path;
	}
	
	private byte[] getProperty() {
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
		return false;
	}
	
	@Override
	public boolean setOnlyReadFile(int state) {
		return false;
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
}
