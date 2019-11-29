package cn.scau.edu.util;

import java.util.List;

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
		System.out.println(root.getDiskPath());//Disk_path为包含磁盘的绝对路径
		System.out.println(root.getPath());//path为不包含磁盘的绝对路径
		Dir pwd = management.getPwd();//获得当前目录
		Dir d1 = management.makeDir("d1");//新建目录，在当前目录下新建
		System.out.println(d1.getDiskPath());
		management.selectDir(d1);//在当前目录下跳转子目录,即在当前目录中选择子目录进入
		pwd = management.getPwd();
		System.out.println(pwd.getDiskPath());//当前目录修改为d1
		File f1 = management.makeFile("f1");//在当前目录新建文件,新建的文件大小为0,不含任何内容
		System.out.println(f1.getDisk_path());
		String data = "write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 write to f1 end#";//要写入文件f1的内容,写入的内容在文件尾后面追加
		boolean result1 = management.writeFile(f1, data);//写入缓冲区成功返回true，只读文件不可写
		System.out.println(result1);
		management.updateBuffer();
		System.out.println(management.readFile(f1, 5000));
		File f2 = management.makeFile("f2");
		System.out.println("pwd size: "+pwd.getFile_list().size());
		System.out.println(management.writeFile(f2, "2"+data));
		management.updateBuffer();
		System.out.println(management.readFile(f2, 5000));
		Dir d2 = management.makeDir("d2");
		System.out.println(d2.getDiskPath());
		pwd = management.selectDir(d2);
		System.out.println(management.getPwd().getDiskPath());
		File f3 = management.makeFile("f3");
		System.out.println(f3.getDisk_path());
		System.out.println(management.writeFile(f3, "3"+data));
		management.updateBuffer();
		System.out.println(management.readFile(f3, 50));
		System.out.println(management.readFile(f3, 200));
		List<Super> search_f1 = management.searchFromRoot("d2");
		Dir s_f1 = (Dir)search_f1.get(0);
		System.out.println(s_f1.getDiskPath());
	}

}
