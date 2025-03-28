package io.github.sseregit.redishandsonenterprise.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.github.sseregit.redishandsonenterprise.common.redis.RedisCommon;
import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.SortedSet;
import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.request.SortedSetRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisSortedSet {
	private final RedisCommon redis;

	public void setSortedSet(SortedSetRequest req) {
		SortedSet model = new SortedSet(req.name(), req.score());
		redis.addToSortedSet(req.baseRequest().key(), model, req.score());
	}

	public Set<SortedSet> getSetDataByRange(String key, Float min, Float max) {
		return redis.rangeByScore(key, min, max, SortedSet.class);
	}

	public Set<SortedSet> getTopN(String key, Integer n) {
		return redis.getTopNFromSortedSet(key, 0, SortedSet.class);
	}
}
