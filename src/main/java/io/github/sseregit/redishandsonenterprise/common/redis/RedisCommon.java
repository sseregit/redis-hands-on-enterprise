package io.github.sseregit.redishandsonenterprise.common.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.github.sseregit.redishandsonenterprise.domain.strategy.model.ValueWithTTL;
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

		if (result instanceof String json) {
			return gson.fromJson(json, clazz);
		}

		return null;
	}

	public void removeFromhash(String key, String field) {
		template.opsForHash().delete(key, field);
	}

	public void setBit(String key, long offset, boolean value) {
		template.opsForValue().setBit(key, offset, value);
	}

	public boolean getBit(String key, long offset) {
		return template.opsForValue().getBit(key, offset);
	}

	public <T> ValueWithTTL<T> getValueWithTTL(String key, Class<T> clazz) {
		T value = null;
		Long ttl = null;
		try {

			template.executePipelined(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection conn = (StringRedisConnection)connection;

					conn.get(key);
					conn.ttl(key);
					return null;
				}
			});

			/*List<Object> results = template.executePipelined((RedisCallback<Object>)connection -> {
				StringRedisConnection conn = (StringRedisConnection)connection;

				conn.get(key);
				conn.pTtl(key);

				return null;
			});

			value = gson.fromJson((String)results.get(0), clazz);
			ttl = Long.valueOf((String)results.get(1));*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ValueWithTTL<>(value, ttl);
	}

	public Long SumTwoKeyAndRnew(String key1, String key2, String resultKey) {

		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setLocation(new ClassPathResource("/lua/newKey.lua"));
		redisScript.setResultType(Long.class);

		List<String> keys = List.of(key1, key2, resultKey);

		return template.execute(redisScript, keys);


		/*return template.execute((RedisCallback<Long>)connection -> {
			byte[] scriptBytes = script.getBytes();
			byte[] key1Bytes = key1.getBytes();
			byte[] key2Bytes = key2.getBytes();
			byte[] resultKeyBytes = resultKey.getBytes();

			return (Long)connection.execute("EVAL", scriptBytes, key1Bytes, key2Bytes, resultKeyBytes);
		});*/
	}
}
