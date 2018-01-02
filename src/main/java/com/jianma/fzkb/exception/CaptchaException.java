package com.jianma.fzkb.exception;


public class CaptchaException extends Exception {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {

		super();

	}

	public CaptchaException(String message, Throwable cause) {

		super(message, cause);

	}

	public CaptchaException(String message) {

		super(message);

	}

	public CaptchaException(Throwable cause) {

		super(cause);

	}
}
