package com.bfchengnuo.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {
	
	private FtpUtil(){}
	//输出日志
	private static Logger logger = Logger.getLogger(FtpUtil.class.getName());
	
	//创建FTPClient对象
	private static FTPClient ftp = new FTPClient();
	
	//定义FtpUtil对象
	private static volatile  FtpUtil INSTANCE = null;
	
	//单例模式 创建FtpUtil对象 
	public static FtpUtil getInstance() {
		if(INSTANCE == null) {
			 synchronized (FtpUtil.class) {
				 if(INSTANCE == null) {
					 INSTANCE = new FtpUtil();
				 }
			}
		}
		return INSTANCE;
	}
	
	/**
	 * 上传
	 * @param hostname ip地址
	 * @param port     端口号  ftp默认为21
	 * @param username ftp用户名
	 * @param password ftp密码
	 * @param ftppath  磁盘保存路径
	 * @param filename 保存文件名称
	 * @param input    上传二进制信息
	 * @return
	 */
	public boolean upload(String hostname,int port,String username,String password,
						  String ftppath,String filename,InputStream input) {
         try {
        	//设置编码为UTF-8
 			ftp.setControlEncoding("UTF-8");
        	// 连接FTP服务器  
			ftp.connect(hostname, port);
			// 登陆FTP服务器  
			ftp.login(username, password);
			
			//判断是否连接 连接尝试后的查询
			if(disConnect()) {
				logger.info("上传未连接到FTP，关闭资源");
				return false;
			}
			
			//设置类型为二进制文件
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			//设置上传目录   转移到FTP服务器目录  
			ftp.enterLocalPassiveMode();  
            ftp.changeWorkingDirectory(ftppath);  
            
            //保存上传信息
            return ftp.storeFile(filename, input);           
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭上传信息
				input.close();
				//打开连接之后 退出登录
				logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
         
         return false;
	}
	
	/**
	 * 下载
	 * @param hostname ip地址
	 * @param port     端口号  ftp默认为21
	 * @param username ftp用户名
	 * @param password ftp密码
	 * @param ftppath  磁盘保存路径
	 * @param filename 保存文件名称
	 * @return
	 */
	public InputStream download(String hostname,int port,String username,String password,
						  String ftppath,String filename) {
         try {
        	//设置编码为UTF-8
 			ftp.setControlEncoding("UTF-8");
        	// 连接FTP服务器  
			ftp.connect(hostname, port);
			// 登陆FTP服务器  
			ftp.login(username, password);
			
			//判断是否连接 连接尝试后的查询
			if(disConnect()) {
				logger.info("下载未连接到FTP，关闭资源");
				return null;
			}
			
			//设置类型为二进制文件
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			//设置上传目录   转移到FTP服务器目录  
			ftp.enterLocalPassiveMode();  
            ftp.changeWorkingDirectory(ftppath);  
            
            //下载上传信息
           return ftp.retrieveFileStream(filename);      
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//打开连接之后 退出登录
				logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
         
         return null;
	}
	
	/**
	 * 删除
	 * @param hostname ip地址
	 * @param port     端口号  ftp默认为21
	 * @param username ftp用户名
	 * @param password ftp密码
	 * @param ftppath  磁盘保存路径
	 * @param filename 保存文件名称
	 * @return
	 */
	public boolean remove(String hostname,int port,String username,String password,
						  String ftppath,String filename) {
         try {
        	//设置编码为UTF-8
 			ftp.setControlEncoding("UTF-8");
        	// 连接FTP服务器  
			ftp.connect(hostname, port);
			// 登陆FTP服务器  
			ftp.login(username, password);
			
			//判断是否连接 连接尝试后的查询
			if(disConnect()) {
				logger.info("删除未连接到FTP，关闭资源");
				return false;
			}
			
			//设置类型为二进制文件
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			//设置上传目录   转移到FTP服务器目录  
			ftp.enterLocalPassiveMode();  
            ftp.changeWorkingDirectory(ftppath);  
            
            //删除上传信息
           return ftp.deleteFile(filename);   
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//打开连接之后 退出登录
				logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
         
         return false;
	}
	
	//连接尝试后的查询  true表示未连接 false表示已连接
	private boolean disConnect() throws IOException {
		boolean b = !FTPReply.isPositiveCompletion(ftp.getReplyCode());
		 if (b) {  
			 logger.info("未连接到FTP，用户名或密码错误。");
			 //关闭连接
             ftp.disconnect();  
         } else {
        	 logger.info("FTP连接成功。");  
         }
		 
		 return b;
	}
	
	//退出
	public void logout() throws IOException {
		//打开连接之后 退出登录
		if(ftp.isConnected()) {
			//ftp退出登录
			ftp.logout();
			logger.info("FTP退出成功。");  
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		FtpUtil ftpUtil = FtpUtil.getInstance();
		//测试上传
		/*InputStream input = new FileInputStream("E:/1.txt");
		ftpUtil.upload("127.0.0.1", 21, "ay2853", "2853", "D:server/ftp", "测试1.txt", input);*/
		
		//测试下载
		/*InputStream input = ftpUtil.download("127.0.0.1", 21, "ay2853", "2853", "D:server/ftp", "测试1.txt");
		
		OutputStream out = new FileOutputStream("E:/测试1.txt");
		byte[] b = new byte[input.available()];
		while(input.read(b) != -1) {
			out.write(b);
		}*/
		
		//测试删除
		//ftpUtil.remove("127.0.0.1", 21, "ay2853", "2853", "D:server/ftp", "测试1.txt");
		
		//out.flush();
		//out.close();
		//input.close();
		
		
	}

}
