package io.github.sseregit.redishandsonenterprise.domain.strategy.model;

public record ValueWithTTL<T>(
	T value,
	Long TTL
) {
}
