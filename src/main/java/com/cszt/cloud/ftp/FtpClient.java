package com.cszt.cloud.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.util.TrustManagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <pre>
 * ftp客户端类
 * eg:
 * IFtpClient client = new FtpClient("10.11.3.80", 21, "test", "test");
 * 	try {
 * 			String remote = "/opt/test.zip";
 * 			String local = "D:\\test.zip";
 * 			client.upload(local, remote);
 * 		} catch (FtpException e) {
 * 			e.printStackTrace();
 * 		}
 * 
 * spring eg:
 * 
 * 
 * 
 * </pre>
 * 
 */
public class FtpClient implements IFtpClient {
	private static Logger logger = LoggerFactory.getLogger(FtpClient.class);
	// 使用二进制传输模式
	private boolean binaryTransfer = true;
	// printhash
	private boolean printHash = false;
	// 使用本地的主动模式(默认局部被动)
	private boolean localActive = false;
	// 使用EPSV IPv4（默认为false）
	private boolean useEpsvWithIPv4 = false;
	// use lenient future dates (server dates may be up to 1 day into future)
	private boolean lenient = false;
	// 保持活动超时时长 单位s
	private long keepAliveTimeout = -1;
	// 控制回复超时时长 单位ms
	private int controlKeepAliveReplyTimeout = -1;
	// 协议
	private String protocol = null; // SSL protocol
	// 信任管理器
	private String trustmgr = null;
	// 代理host
	private String proxyHost = null;
	// 代理port
	private int proxyPort = 80;
	// 代理帐号
	private String proxyUser = null;
	// 代理密码
	private String proxyPassword = null;

	// ftp服务器环境编码
	private String charset;

	// host
	private String host;
	// port
	private int port = -1;
	// ftp帐号
	private String username;
	// ftp密码
	private String password;

	// 网络连接超时时长
	private int connectTimeout = 5000;

	// ftp操作系统类型
	private String systemType;
	// ftp操作系统分割符
	private String separatorChar;

	public FtpClient() {
		super();
	}

	/**
	 * 
	 * 创建一个新的实例FtpClient.
	 * 
	 * @param host
	 *            ftp的host
	 * @param port
	 *            ftp的port
	 * @param username
	 *            ftp用户
	 * @param password
	 *            ftp密码
	 */
	public FtpClient(String host, int port, String username, String password) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	private FTPClient getFTPClient() throws FtpException {
		final FTPClient ftp;
		if (protocol == null) {
			if (proxyHost != null) {
				logger.info("Using HTTP proxy server: " + proxyHost);
				ftp = new FTPHTTPClient(proxyHost, proxyPort, proxyUser, proxyPassword);
			} else {
				ftp = new FTPClient();
			}
		} else {
			FTPSClient ftps;
			if (protocol.equals("true")) {
				ftps = new FTPSClient(Boolean.TRUE);
			} else if (protocol.equals("false")) {
				ftps = new FTPSClient(Boolean.FALSE);
			} else {
				String prot[] = protocol.split(",");
				if (prot.length == 1) { // Just protocol
					ftps = new FTPSClient(protocol);
				} else { // protocol,true|false
					ftps = new FTPSClient(prot[0], Boolean.parseBoolean(prot[1]));
				}
			}
			ftp = ftps;
			if ("all".equals(trustmgr)) {
				ftps.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
			} else if ("valid".equals(trustmgr)) {
				ftps.setTrustManager(TrustManagerUtils.getValidateServerCertificateTrustManager());
			} else if ("none".equals(trustmgr)) {
				ftps.setTrustManager(null);
			}
		}

		if (printHash) {
			ftp.setCopyStreamListener(createListener());
		}
		if (keepAliveTimeout >= 0) {
			ftp.setControlKeepAliveTimeout(keepAliveTimeout);
		}
		if (controlKeepAliveReplyTimeout >= 0) {
			ftp.setControlKeepAliveReplyTimeout(controlKeepAliveReplyTimeout);
		}
		if (StringUtils.isNotBlank(charset)) {
			ftp.setControlEncoding(charset);
		}
		ftp.setConnectTimeout(connectTimeout);
		// suppress login details
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), Boolean.TRUE));

		String serverMessage = null;
		try {
			int reply;
			if (port > 0) {
				ftp.connect(host, port);
			} else {
				ftp.connect(host);
			}
			serverMessage = "host " + host + " on " + (port > 0 ? port : ftp.getDefaultPort());
			logger.info("Connected to " + serverMessage);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new FtpException("FTP server refused connection." + serverMessage);
			}
			if (StringUtils.isBlank(username)) {// 用户为空，使用默认用户和密码
				username = "anonymous";
				password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
			}
			// 登录
			ftp.login(username, password);
			logger.info("Ftp server is " + serverMessage + "! Remote system is " + ftp.getSystemType());
			if (binaryTransfer) {
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
			} else {
				ftp.setFileType(FTP.ASCII_FILE_TYPE);
			}
			if (localActive) {
				ftp.enterLocalActiveMode();
			} else {
				ftp.enterLocalPassiveMode();
			}
			ftp.setUseEPSVwithIPv4(useEpsvWithIPv4);
			return ftp;
		} catch (Exception e) {
			closeFtp(ftp);
			throw new FtpException(e);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean upload(String local, String remote) throws FtpException {
		return upload(local, remote, Boolean.FALSE, Boolean.FALSE);
	}

	/**
	 * 上传文件 --断点续传
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean uploadResume(String local, String remote) throws FtpException {
		return upload(local, remote, Boolean.FALSE, Boolean.TRUE);
	}

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
	public boolean createDirecroty(String remote) throws FtpException {
		FTPClient ftp = null;
		try {
			if (StringUtils.isBlank(remote)) {
				logger.warn("CreateDirecroty error! Remote Direcroty is blank!");
				return false;
			}
			ftp = getFTPClient();
			if (StringUtils.isBlank(systemType)) {
				initSystemType(ftp);
			}
			String split = getSeparatorChar();
			String directory = remote.substring(0, remote.lastIndexOf(split) + 1);
			if (!directory.equalsIgnoreCase(split) && !ftp.changeWorkingDirectory(remote)) {
				// 如果远程目录不存在，则递归创建远程服务器目录
				int start = 0;
				int end = 0;
				if (directory.startsWith(split)) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf(split, start);
				while (true) {
					String subDirectory = remote.substring(start, end);
					if (!ftp.changeWorkingDirectory(subDirectory)) {
						if (ftp.makeDirectory(subDirectory)) {
							ftp.changeWorkingDirectory(subDirectory);
						} else {
							logger.error("createDirecroty error! " + remote + " ,Error Direcroty is " + subDirectory);
							return Boolean.FALSE;
						}
					}
					start = end + 1;
					end = directory.indexOf(split, start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new FtpException(e);
		} finally {
			closeFtp(ftp);
		}
	}

	/**
	 * 
	 * <pre>
	 * 获取ftp操作系统类型
	 * </pre>
	 * 
	 * @return
	 * @throws FtpException
	 */
	public String getSystemType() throws FtpException {
		if (StringUtils.isBlank(systemType)) {
			initSystemType();
		}
		return systemType;
	}

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
	public String getSeparatorChar() throws FtpException {
		if (StringUtils.isBlank(separatorChar)) {
			initSystemType();
		}
		return separatorChar;
	}

	private void initSystemType() throws FtpException {
		FTPClient ftp = null;
		try {
			ftp = getFTPClient();
			initSystemType(ftp);
		} catch (Exception e) {
			throw new FtpException(e);
		} finally {
			closeFtp(ftp);
		}
	}

	private void initSystemType(FTPClient ftp) throws IOException {
		systemType = ftp.getSystemType();
		if (StringUtils.indexOf(systemType, "UNIX") != -1) {
			separatorChar = "/";
		} else {
			separatorChar = "\\";
		}
	}

	/**
	 * 
	 * <pre>
	 * 重命名ftp文件
	 * </pre>
	 * 
	 * @param from
	 *            ftp 原名
	 * @param to
	 *            ftp 重命名
	 * @return 当且仅当重命名成功时，返回 true；否则返回 false
	 * @throws FtpException
	 */
	public boolean rename(String from, String to) throws FtpException {
		FTPClient ftp = null;
		InputStream input = null;
		OutputStream output = null;
		try {
			ftp = getFTPClient();
			return ftp.rename(from, to);
		} catch (Exception e) {
			throw new FtpException(e);
		} finally {
			closeInput(input);
			closeOutput(output);
			closeFtp(ftp);
		}
	}

	// 上传 local=本地文件路径 remote=ftp远程文件路径 hidden=是否包含隐藏文件true包含
	// resume=支持断点续传true支持
	private boolean upload(String local, String remote, boolean hidden, boolean resume) throws FtpException {
		FTPClient ftp = null;
		InputStream input = null;
		OutputStream output = null;
		RandomAccessFile rafile = null;
		try {
			ftp = getFTPClient();
			ftp.setListHiddenFiles(hidden);
			File localFile = new File(local);
			if (localFile == null || !localFile.exists()) {
				throw new Exception("Local file " + local + " is not exit.Please check!");
			}
			long localSize = localFile.length();
			FTPFile[] remoteFiles = ftp.listFiles(remote);
			if (remoteFiles != null && remoteFiles.length == 1 && resume) {
				long remoteSize = remoteFiles[0].getSize();
				if (remoteSize >= localSize) {
					return Boolean.TRUE;
				} else {
					ftp.setRestartOffset(remoteSize);
					output = ftp.appendFileStream(remote);
					rafile = new RandomAccessFile(localFile, "r");
					rafile.seek(remoteSize);
					writeRandomAccessFile(rafile, output);
				}
			} else {
				output = ftp.storeFileStream(remote);
				input = new FileInputStream(local);
				write(input, output);
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			throw new FtpException(e);
		} finally {
			closeInput(input);
			closeOutput(output);
			closeFtp(ftp);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoad(String local, String remote) throws FtpException {
		return downLoad(local, remote, Boolean.FALSE, Boolean.FALSE);
	}

	/**
	 * 下载文件--包括ftp隐藏文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoadHidden(String local, String remote) throws FtpException {
		return downLoad(local, remote, Boolean.TRUE, Boolean.FALSE);
	}

	/**
	 * 下载文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoadResume(String local, String remote) throws FtpException {
		return downLoad(local, remote, Boolean.FALSE, Boolean.TRUE);
	}

	/**
	 * 下载文件--包括ftp隐藏文件
	 * 
	 * @param local
	 *            本地文件路径＋文件名
	 * @param remote
	 *            ftp服务文件路径＋文件名
	 * @throws FtpException
	 */
	public boolean downLoadResumeHidden(String local, String remote) throws FtpException {
		return downLoad(local, remote, Boolean.TRUE, Boolean.TRUE);
	}

	// 下载 local=本地文件路径 remote=ftp远程文件路径 hidden=是否包含隐藏文件true包含
	// resume=支持断点续传true支持
	private boolean downLoad(String local, String remote, boolean hidden, boolean resume) throws FtpException {
		FTPClient ftp = null;
		OutputStream output = null;
		InputStream is = null;
		try {
			ftp = getFTPClient();
			FTPFile[] remoteFiles = ftp.listFiles(remote);
			if (remoteFiles == null || remoteFiles.length != 1) {
				throw new Exception("Remote file " + remote + " is not exit.Please check!");
			}
			long remoteSize = remoteFiles[0].getSize();
			ftp.setListHiddenFiles(hidden);
			File localFile = new File(local);
			if (!localFile.getParentFile().exists()) {
				localFile.getParentFile().mkdirs();
			}
			if (resume && localFile.exists()) {
				long localSize = localFile.length();
				if (localSize > remoteSize) {
					throw new Exception("Local file " + local + " size grant than Remote file " + remote + " .Please check!");
				}
				ftp.setRestartOffset(localSize);
				output = new FileOutputStream(localFile, Boolean.TRUE);
			} else {
				output = new FileOutputStream(localFile);
			}
			is = ftp.retrieveFileStream(remote);
			long retSize = write(is, output);
			logger.info("DownLoad remote file " + remote + " size " + retSize + " sucess!");
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.info("DownLoad remote file " + remote + " error!");
			throw new FtpException(e);
		} finally {
			closeOutput(output);
			closeInput(is);
			closeFtp(ftp);
		}
	}

	private long writeRandomAccessFile(RandomAccessFile is, OutputStream os) throws IOException {
		byte[] buff = new byte[8 * 1024];
		int b = 0;
		long ret = 0;
		while ((b = is.read(buff)) != -1) {
			os.write(buff, 0, b);
			ret = ret + b;
			if (ret % (1024 * buff.length) == 0) {
				os.flush();
			}
		}
		os.flush();
		return ret;
	}

	private long write(InputStream is, OutputStream os) throws IOException {
		byte[] buff = new byte[8 * 1024];
		int b = 0;
		long ret = 0;
		while ((b = is.read(buff)) != -1) {
			os.write(buff, 0, b);
			ret = ret + b;
			if (ret % (1024 * buff.length) == 0) {
				os.flush();
			}
		}
		os.flush();
		return ret;
	}

	/**
	 * 
	 * 列出指定目录下的所有非隐藏文件/目录
	 * 
	 * @param remote
	 *            ftp服务文件路径
	 * @return
	 * @throws FtpException
	 */
	public FTPFile[] listFiles(String remote) throws FtpException {
		return (FTPFile[]) listFilesByType(remote, Boolean.FALSE, LIST_ALL);
	}

	/**
	 * 列出指定目录下的所有文件/目录--包括隐藏文件/目录
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public FTPFile[] listFilesHidden(String remote) throws FtpException {
		return (FTPFile[]) listFilesByType(remote, Boolean.TRUE, LIST_ALL);
	}

	/**
	 * 列出指定目录下的所有非隐藏文件/目录名
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public String[] listFileNames(String remote) throws FtpException {
		return (String[]) listFilesByType(remote, Boolean.FALSE, LIST_ALLNAME);
	}

	/**
	 * 列出指定目录下的所有文件/目录名--包括隐藏文件/目录
	 * 
	 * @param remote
	 * @return
	 * @throws FtpException
	 */
	public String[] listFileNamesHidden(String remote) throws FtpException {
		return (String[]) listFilesByType(remote, Boolean.TRUE, LIST_ALLNAME);
	}

	private static final String LIST_ALL = "list_all";
	private static final String LIST_ALLNAME = "list_allName";

	// 列出文件
	private Object listFilesByType(String remote, boolean hidden, String type) throws FtpException {
		FTPClient ftp = null;
		try {
			ftp = getFTPClient();
			ftp.setListHiddenFiles(hidden);
			if (LIST_ALL == type) {
				if (lenient) {
					FTPClientConfig config = new FTPClientConfig();
					config.setLenientFutureDates(Boolean.TRUE);
					ftp.configure(config);
				}
				FTPFile[] files = ftp.listFiles(remote);
				return files;
			} else if (LIST_ALLNAME == type) {
				String[] filesName = ftp.listNames(remote);
				return filesName;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new FtpException(e);
		} finally {
			closeFtp(ftp);
		}
	}

	private void closeFtp(FTPClient ftp) {
		if (ftp != null) {
			try {
				ftp.logout();// logout
			} catch (Exception e) {
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}
	}

	private void closeInput(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}

	private void closeOutput(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}

	private CopyStreamListener createListener() {
		return new CopyStreamListener() {
			private long megsTotal = 0;

			// @Override
			public void bytesTransferred(CopyStreamEvent event) {
				bytesTransferred(event.getTotalBytesTransferred(), event.getBytesTransferred(), event.getStreamSize());
			}

			// @Override
			public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
				long megs = totalBytesTransferred / 1000000;
				for (long l = megsTotal; l < megs; l++) {
					logger.error("#");
				}
				megsTotal = megs;
			}
		};
	}

	/**
	 * 获取printHash
	 * 
	 * @return
	 */
	public boolean isPrintHash() {
		return printHash;
	}

	/**
	 * 设置printHash
	 * 
	 * @param printHash
	 */
	public void setPrintHash(boolean printHash) {
		this.printHash = printHash;
	}

	/**
	 * 获取代理host
	 * 
	 * @return
	 */
	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * 设置代理host
	 * 
	 * @param proxyHost
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	/**
	 * 获取代理port 默认80端口
	 * 
	 * @return
	 */
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * 设置代理port 默认80端口
	 * 
	 * @param proxyPort
	 */
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * 获取代理用户
	 * 
	 * @return
	 */
	public String getProxyUser() {
		return proxyUser;
	}

	/**
	 * 设置代理用户
	 * 
	 * @param proxyUser
	 */
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	/**
	 * 获取代理用户密码
	 * 
	 * @return
	 */
	public String getProxyPassword() {
		return proxyPassword;
	}

	/**
	 * 设置代理用户密码
	 * 
	 * @param proxyPassword
	 */
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	/**
	 * 获取ftp的host
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置ftp的host
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获取ftp的端口
	 * 
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置ftp的端口
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取ftp的用户
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置ftp的用户
	 * 
	 * @param ftpUser
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取ftp的登录密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置ftp的登录密码
	 * 
	 * @param ftpPass
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <pre>
	 * 获取二进制传输模式 true是,false否
	 * 默认二进制传输模式
	 * </pre>
	 * 
	 * @return
	 */
	public boolean isBinaryTransfer() {
		return binaryTransfer;
	}

	/**
	 * 设置二进制传输模式 true是,false否
	 * 
	 * @param binaryTransfer
	 */
	public void setBinaryTransfer(boolean binaryTransfer) {
		this.binaryTransfer = binaryTransfer;
	}

	/**
	 * <pre>
	 * 获取主动模式或被动模式 true主动模式 false被动模式
	 * 默认被动模式
	 * </pre>
	 * 
	 * @return
	 */
	public boolean isLocalActive() {
		return localActive;
	}

	/**
	 * 设置主动模式或被动模式 true主动模式 false被动模式
	 * 
	 * @param localActive
	 */
	public void setLocalActive(boolean localActive) {
		this.localActive = localActive;
	}

	/**
	 * 获取是否使用EPSV IPv4 true是 false否
	 * 
	 * @return
	 */
	public boolean isUseEpsvWithIPv4() {
		return useEpsvWithIPv4;
	}

	/**
	 * 设置是否使用EPSV IPv4 true是 false否
	 * 
	 * @param useEpsvWithIPv4
	 */
	public void setUseEpsvWithIPv4(boolean useEpsvWithIPv4) {
		this.useEpsvWithIPv4 = useEpsvWithIPv4;
	}

	public boolean isLenient() {
		return lenient;
	}

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	/**
	 * 获取保持活动超时时长 单位s 默认无时间限制-1
	 * 
	 * @return
	 */
	public long getKeepAliveTimeout() {
		return keepAliveTimeout;
	}

	/**
	 * 设置保持活动超时时长 单位s 默认无时间限制-1
	 * 
	 * @param keepAliveTimeout
	 */
	public void setKeepAliveTimeout(long keepAliveTimeout) {
		this.keepAliveTimeout = keepAliveTimeout;
	}

	/**
	 * 获取控制回复超时时长 单位ms 默认无时间限制-1
	 * 
	 * @return
	 */
	public int getControlKeepAliveReplyTimeout() {
		return controlKeepAliveReplyTimeout;
	}

	/**
	 * 设置控制回复超时时长 单位ms 默认无时间限制-1
	 * 
	 * @param controlKeepAliveReplyTimeout
	 */
	public void setControlKeepAliveReplyTimeout(int controlKeepAliveReplyTimeout) {
		this.controlKeepAliveReplyTimeout = controlKeepAliveReplyTimeout;
	}

	/**
	 * 获取协议
	 * 
	 * @return
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * <pre>
	 * 设置协议 值：
	 * true|false|protocol[,true|false] - use FTPSClient with the specified protocol and/or isImplicit setting
	 * </pre>
	 * 
	 * @param protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * 获取信任管理器
	 * 
	 * @return
	 */
	public String getTrustmgr() {
		return trustmgr;
	}

	/**
	 * <pre>
	 * 设置信任管理器
	 * all|valid|none - use one of the built-in TrustManager implementations (none = JVM default)
	 * </pre>
	 * 
	 * @param trustmgr
	 */
	public void setTrustmgr(String trustmgr) {
		this.trustmgr = trustmgr;
	}

	/**
	 * 获取连接超时时长 单位ms 默认5000ms
	 * 
	 * @return
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * 设置连接超时时长 单位ms 默认5000ms
	 * 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 获取ftp环境编码
	 * 
	 * @return
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * 设置ftp环境编码，未设置使用common.net中ftp自带编码iso-8859-1
	 * 
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

}
