package io.github.sseregit.redishandsonenterprise.domain.sortedset.controller;

import java.util.Set;

import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.SortedSet;
import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.request.SortedSetRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "sorted set", description = "sorted set api")
interface SortedSetController {

	void setSortedSet(SortedSetRequest req);

	Set<SortedSet> getSortedSet(String key, Float min, Float max);

	Set<SortedSet> getTopN(String key, Integer n);
}
