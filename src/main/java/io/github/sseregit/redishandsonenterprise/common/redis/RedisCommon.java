package io.github.sseregit.redishandsonenterprise.common.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	@Value("${spring.data.redis.default-time}")
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

	public <T> List<T> rangeByScore(String key, Float minScore, Float maxScore, Class<T> clazz) {
		Set<String> jsonValues = template.opsForZSet().rangeByScore(key, minScore, maxScore);

		List<T> result = new ArrayList<>();
		if (jsonValues != null) {
			for (String jsonValue : jsonValues) {
				T v = gson.fromJson(jsonValue, clazz);
				result.add(v);
			}
		}

		return result;
	}

	public <T> List<T> getTopNFromSortedSet(String key, int n, Class<T> clazz) {

		// template.opsForZSet().range(key, 0, -1); 모든값 가져오기

		Set<String> jsonValues = template.opsForZSet().reverseRange(key, 0, n - 1);

		List<T> result = new ArrayList<>();
		if (jsonValues != null) {
			for (String jsonValue : jsonValues) {
				T v = gson.fromJson(jsonValue, clazz);
				result.add(v);
			}
		}

		return result;
	}

	public <T> void addToListLeft(String key, T value) {
		String jsonValue = gson.toJson(value);
		template.opsForList().leftPush(key, jsonValue);
	}

	public <T> void addToListRight(String key, T value) {
		String jsonValue = gson.toJson(value);
		template.opsForList().rightPush(key, jsonValue);
	}

	public <T> List<T> getAllList(String key, Class<T> clazz) {
		List<String> jsonValues = template.opsForList().range(key, 0, -1);

		List<T> result = new ArrayList<>();
		if (jsonValues != null) {
			for (String jsonValue : jsonValues) {
				T v = gson.fromJson(jsonValue, clazz);
				result.add(v);
			}
		}

		return result;
	}

	public <T> void removeFromList(String key, T value) {
		String jsonValue = gson.toJson(value);
		template.opsForList().remove(key, 1, jsonValue);
	}

	public <T> void putInHash(String key, String field, T value) {
		String jsonValue = gson.toJson(value);
		template.opsForHash().put(key, field, jsonValue);
	}

	public <T> T getFromHash(String key, String field, Class<T> clazz) {
		Object result = template.opsForHash().get(key, field);

		if (result != null) {
			return clazz.cast(result);
		}

		return null;
	}

	public void removeFromhash(String key, String field) {
		template.opsForHash().delete(key, field);
	}
}
