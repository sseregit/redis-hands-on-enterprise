package io.github.sseregit.redishandsonenterprise.domain.string.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.sseregit.redishandsonenterprise.domain.string.model.request.MultiStringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.request.StringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.response.StringResponse;
import io.github.sseregit.redishandsonenterprise.service.RedisString;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/set")
@RequiredArgsConstructor
class StringControllerImpl implements StringController {

	private final RedisString redis;

	@Override
	@PostMapping(value = "/set-string-collection", consumes = "application/json")
	public void setString(@RequestBody @Valid StringRequest req) {
		redis.set(req);
	}

	@Override
	@GetMapping(value = "/get-string-collection", produces = "application/json")
	public StringResponse getString(@RequestParam @Valid String key) {
		return redis.get(key);
	}

	@Override
	@PostMapping(value = "/multi-set-collection", consumes = "application/json")
	public void multiString(@RequestBody @Valid MultiStringRequest req) {
		redis.multiSet(req);
	}
}
