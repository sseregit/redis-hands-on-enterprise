package io.github.sseregit.redishandsonenterprise.domain.sortedset.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.SortedSet;
import io.github.sseregit.redishandsonenterprise.domain.sortedset.model.request.SortedSetRequest;
import io.github.sseregit.redishandsonenterprise.service.RedisSortedSet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sorted-set")
@RequiredArgsConstructor
class SortedSetControllerImpl implements SortedSetController {

	private final RedisSortedSet redis;

	@Override
	@PostMapping(value = "/sorted-set-collection", consumes = "application/json")
	public void setSortedSet(@RequestBody @Valid SortedSetRequest req) {
		redis.setSortedSet(req);
	}

	@Override
	@GetMapping("/get-sorted-set-by-range")
	public List<SortedSet> getSortedSet(@RequestParam @Valid String key, @RequestParam @Valid Float min,
		@RequestParam @Valid Float max) {
		return redis.getSetDataByRange(key, min, max);
	}

	@Override
	@GetMapping("/get-sorted-set-by-top")
	public List<SortedSet> getTopN(@RequestParam @Valid String key, @RequestParam @Valid Integer n) {
		return redis.getTopN(key, n);
	}
}
