package com.cszt.cloud.ftp;

import org.apache.commons.net.ftp.FTPFile;

public interface IFtpClient {

	/**
	 * 上传文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean upload(String local, String remote) throws FtpException;

	/**
	 * 
	 * 重命名ftp文件。 不包含隐藏文件
	 * 
	 * @param from
	 *            ftp 原名
	 * @param to
	 *            ftp 重命名
	 * @return 当且仅当重命名成功时，返回 true；否则返回 false
	 * @throws FtpException
	 */
	public boolean rename(String from, String to) throws FtpException;

	/**
	 * 上传文件 --断点续传
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean uploadResume(String local, String remote) throws FtpException;

	/**
	 * 
	 * <pre>
	 * 创建目录 目录会逐级创建. 目录remote是相对路径，相对于ftp登录后的根
	 * </pre>
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public boolean createDirecroty(String remote) throws FtpException;

	/**
	 * 
	 * <pre>
	 * 获取ftp操作系统类型
	 * </pre>
	 * 
	 * @return
	 * @throws FtpException
	 */
	public String getSystemType() throws FtpException;

	/**
	 * 
	 * <pre>
	 * 获取ftp操作系统对应的文件分割符
	 * window ftp 是\
	 * linux ftp 是/
	 * </pre>
	 * 
	 * @return
	 * @throws FtpException
	 */
	public String getSeparatorChar() throws FtpException;

	/**
	 * 下载文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoad(String local, String remote) throws FtpException;

	/**
	 * 下载文件--包括ftp隐藏文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoadHidden(String local, String remote) throws FtpException;

	/**
	 * 下载文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoadResume(String local, String remote) throws FtpException;

	/**
	 * 下载文件--包括ftp隐藏文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoadResumeHidden(String local, String remote) throws FtpException;

	/**
	 * 
	 * 列出指定目录下的所有非隐藏文件/目录
	 * 
	 * @param remote
	 *            ftp服务文件路径
	 * @return
	 * @throws FtpException
	 */
	public FTPFile[] listFiles(String remote) throws FtpException;

	/**
	 * 列出指定目录下的所有文件/目录--包括隐藏文件/目录
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public FTPFile[] listFilesHidden(String remote) throws FtpException;

	/**
	 * 列出指定目录下的所有非隐藏文件/目录名
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public String[] listFileNames(String remote) throws FtpException;

	/**
	 * 列出指定目录下的所有文件/目录名--包括隐藏文件/目录
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public String[] listFileNamesHidden(String remote) throws FtpException;
}
