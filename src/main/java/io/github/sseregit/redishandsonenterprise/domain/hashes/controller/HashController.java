package io.github.sseregit.redishandsonenterprise.domain.hashes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.sseregit.redishandsonenterprise.domain.hashes.model.HashModel;
import io.github.sseregit.redishandsonenterprise.domain.hashes.model.request.HashRequest;
import io.github.sseregit.redishandsonenterprise.service.RedisHash;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "hash", description = "hash api")
@RestController
@RequestMapping("/api/v1/hash")
@RequiredArgsConstructor
public class HashController {

	private final RedisHash redis;

	@PostMapping("/put-hashes")
	public void PutHashes(@RequestBody HashRequest req) {
		redis.putInHash(req.baseRequest().key(), req.field(), req.value());
	}

	@GetMapping("/get-hash-value")
	public HashModel getHashes(@RequestParam @Valid String key, @RequestParam @Valid String field) {
		return redis.getFromHash(key, field);
	}
}
