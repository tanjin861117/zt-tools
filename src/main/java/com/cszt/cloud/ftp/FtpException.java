package com.cszt.cloud.ftp;

public class FtpException extends Exception {

	private static final long serialVersionUID = 4930607735421731564L;

	public FtpException() {
		super();
	}

	public FtpException(String message, Throwable cause) {
		super(message, cause);
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(Throwable cause) {
		super(cause);
	}

}