package cn.scau.edu.util;

import java.util.List;

import com.sun.org.glassfish.external.statistics.impl.StatsImpl;

import cn.scau.edu.base.Dir;
import cn.scau.edu.base.Disk;
import cn.scau.edu.base.FAT;
import cn.scau.edu.base.File;
import cn.scau.edu.base.Super;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Management management = Management.getInstance();//创建管理器,已存在根目录,C盘
		Dir root = management.getNow_root();//当前根目录
		System.out.println("pwd: "+management.getPwd().getDiskPath());
		System.out.println("root: "+management.getNow_root().getDiskPath());
		Dir d1 = management.makeDir("d1");
		File f1 = management.makeFile("f1");
		System.out.println("d1: "+d1.getDiskPath());
		System.out.println("f1: "+f1.getDisk_path());
		Dir pwd = management.selectDir(d1);
		System.out.println("pwd: "+management.getPwd().getDiskPath());
		File f2 = management.makeFile("f2");
		String data = "write to f2 write to f2 write to f2 write to f2 write to f2 write to f2 write to f2 write to f2#";
		management.writeFile(f2, data);
		management.updateBuffer();
		System.out.println(management.readFile(f2, 50));
		System.out.println(management.readFile(f2, 300));
		
	}

}
