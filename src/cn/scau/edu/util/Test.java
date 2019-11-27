package cn.scau.edu.util;

import cn.scau.edu.base.Dir;
import cn.scau.edu.base.Disk;
import cn.scau.edu.base.FAT;
import cn.scau.edu.base.File;

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
		String data = "write to f1";//要写入文件f1的内容,写入的内容在文件尾后面追加
		boolean result1 = management.writeFile(f1, data);//写入缓冲区成功返回true，只读文件不可写
		System.out.println(result1);
		management.updateBuffer();//刷新缓冲区，写入文件时，只有缓冲区满才会写入磁盘块，这里手动刷新缓冲区
		String f1_data = management.readFile(f1, data.length());//读取文件指定长度的内容
		System.out.println(f1_data);
		management.changeFileProperty(f1, 2);//修改文件为系统文件
		System.out.println("delete file:"+management.deleteFile(f1));//文件删除失败
		management.changeFileProperty(f1, 3);//修改文件为只读文件
		System.out.println("1opentable:"+OpenedTable.getInstance().getOpenedSize());
		System.out.println("delete file:"+management.deleteFile(f1));//文件删除失败，该文件没有关闭
		System.out.println("1parent file size:"+f1.getParent().getFile_list().size());
		management.closeFile(f1);//关闭文件
		System.out.println("2opentable:"+OpenedTable.getInstance().getOpenedSize());
		System.out.println("delete file:"+management.deleteFile(f1));
		System.out.println("2parent file size:"+f1.getParent().getFile_list().size());
	}

}
