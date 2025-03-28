package io.github.sseregit.redishandsonenterprise.domain.sortedset.controller;

import java.util.List;

import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.SortedSet;
import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.request.SortedSetRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "sorted set", description = "sorted set api")
interface SortedSetController {

	void setSortedSet(SortedSetRequest req);

	List<SortedSet> getSortedSet(String key, Float min, Float max);

	List<SortedSet> getTopN(String key, Integer n);
}
