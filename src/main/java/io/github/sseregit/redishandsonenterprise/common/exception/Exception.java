package io.github.sseregit.redishandsonenterprise.common.exception;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException {

	private final Interface anInterface;

	public Exception(Interface anInterface) {
		super(anInterface.getMessage());
		this.anInterface = anInterface;
	}
}
