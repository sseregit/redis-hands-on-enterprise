package io.github.sseregit.redishandsonenterprise.service;

import org.springframework.stereotype.Service;

import io.github.sseregit.redishandsonenterprise.common.redis.RedisCommon;
import io.github.sseregit.redishandsonenterprise.domain.hashes.model.HashModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisHash {
	private final RedisCommon redis;

	public void putInHash(String key, String field, String value) {
		HashModel model = new HashModel(value);
		redis.putInHash(key, field, model);
	}

	public HashModel getFromHash(String key, String field) {
		return redis.getFromHash(key, field, HashModel.class);
	}
}
