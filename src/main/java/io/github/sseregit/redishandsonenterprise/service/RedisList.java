package io.github.sseregit.redishandsonenterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.sseregit.redishandsonenterprise.common.redis.RedisCommon;
import io.github.sseregit.redishandsonenterprise.domain.list.model.ListModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisList {

	private final RedisCommon redis;

	public void addToListLeft(String key, String name) {
		ListModel model = new ListModel(name);
		redis.addToListLeft(key, model);
	}

	public void addToListRight(String key, String name) {
		ListModel model = new ListModel(name);
		redis.addToListRight(key, model);
	}

	public List<ListModel> getAllData(String key) {
		return redis.getAllList(key, ListModel.class);
	}
}
