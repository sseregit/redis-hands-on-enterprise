package io.github.sseregit.redishandsonenterprise.common.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisCommon {

	private final RedisTemplate<String, String> template;
	private final Gson gson;

	@Value("${spring.redis.default-time}")
	private Duration defaultExpireTime;

	public <T> T getData(String key, Class<T> clazz) {
		String jsonValue = template.opsForValue().get(key);

		if (jsonValue == null) {
			return null;
		}

		return gson.fromJson(jsonValue, clazz);
	}

	public <T> void setData(String key, T value) {
		String jsonValue = gson.toJson(value);
		template.opsForValue().set(key, jsonValue);
		template.expire(key, defaultExpireTime);
	}

	public <T> void multiSetdata(Map<String, T> datas) {
		Map<String, String> jsonMap = new HashMap<>();

		for (Map.Entry<String, T> data : datas.entrySet()) {
			jsonMap.put(data.getKey(), gson.toJson(data.getValue()));
		}

		template.opsForValue().multiSet(jsonMap);
	}
}
