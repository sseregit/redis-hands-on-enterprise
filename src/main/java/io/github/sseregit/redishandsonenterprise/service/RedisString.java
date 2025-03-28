package io.github.sseregit.redishandsonenterprise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.github.sseregit.redishandsonenterprise.common.redis.RedisCommon;
import io.github.sseregit.redishandsonenterprise.domain.string.model.StringModel;
import io.github.sseregit.redishandsonenterprise.domain.string.model.request.MultiStringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.request.StringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.response.StringResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisString {
	private final RedisCommon redis;

	public void set(StringRequest req) {
		String key = req.baseRequest().key();
		StringModel newModel = new StringModel(key, req.name());

		redis.setData(key, newModel);
	}

	public StringResponse get(String key) {
		StringModel result = redis.getData(key, StringModel.class);

		List<StringModel> res = new ArrayList<>();

		if (result != null) {
			res.add(result);
		}

		return new StringResponse(res);
	}

	public void multiSet(MultiStringRequest req) {
		Map<String, Object> dataMap = new HashMap<>();

		for (int i = 0; i < req.names().length; i++) {
			String name = req.names()[i];
			String key = "key:" + (i + 1);

			StringModel newModel = new StringModel(key, name);

			dataMap.put(key, newModel);
		}

		redis.multiSetdata(dataMap);
	}
}
