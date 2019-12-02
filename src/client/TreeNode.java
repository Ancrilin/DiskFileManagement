package client;

import cn.scau.edu.base.*; 

import cn.scau.edu.base.Dir;
import cn.scau.edu.base.Super;

public class TreeNode {
	
	private Super DirOrFile = null;
	
	
	public TreeNode(Super DirOrFile) {
		this.DirOrFile = DirOrFile;
	}
	
	public TreeNode(Dir DirOrFile) {
		this.DirOrFile =(Super) DirOrFile;
	}
	
	public TreeNode(File DirOrFile) {
		this.DirOrFile =(Super) DirOrFile;
	}
	
	
	
	public Super getSuper() {
		return this.DirOrFile;
	}
	
	public void setSuper(Super DirOrFile) {
		this.DirOrFile = DirOrFile;
	}
	
	@Override
	public String toString() {
		if(DirOrFile!=null) {
			if(DirOrFile.isDir()) {
				Dir dir = (Dir)this.DirOrFile;
				return dir.getName();
			}
			
			if(!DirOrFile.isDir()) {
				File f = (File)DirOrFile;
				return f.getName();
			}
		}
		return null;
	}

}
