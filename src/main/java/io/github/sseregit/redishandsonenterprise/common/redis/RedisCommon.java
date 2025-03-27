package io.github.sseregit.redishandsonenterprise.common.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	public <T> void addToSortedSet(String key, T value, Float score) {
		String jsonValue = gson.toJson(value);
		template.opsForZSet().add(key, jsonValue, score);
	}

	public <T> Set<T> rangeByScore(String key, Float minScore, Float maxScore, Class<T> clazz) {
		Set<String> jsonValues = template.opsForZSet().rangeByScore(key, minScore, maxScore);

		Set<T> result = new HashSet<>();
		if (jsonValues != null) {
			for (String jsonValue : jsonValues) {
				T v = gson.fromJson(jsonValue, clazz);
				result.add(v);
			}
		}

		return result;
	}

	public <T> Set<T> getTopNFromSortedSet(String key, int n, Class<T> clazz) {

		// template.opsForZSet().range(key, 0, -1); 모든값 가져오기

		Set<String> jsonValues = template.opsForZSet().reverseRange(key, 0, n - 1);

		Set<T> result = new HashSet<>();
		if (jsonValues != null) {
			for (String jsonValue : jsonValues) {
				T v = gson.fromJson(jsonValue, clazz);
				result.add(v);
			}
		}

		return result;
	}
}
