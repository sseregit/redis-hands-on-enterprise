package io.github.sseregit.redishandsonenterprise.domain.list.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.sseregit.redishandsonenterprise.domain.list.model.ListModel;
import io.github.sseregit.redishandsonenterprise.domain.list.model.request.ListRequest;
import io.github.sseregit.redishandsonenterprise.service.RedisList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "list", description = "list api")
@RestController
@RequestMapping("/api/v1/list")
@RequiredArgsConstructor
class ListController {

	private final RedisList redis;

	@PostMapping("/list-add-left")
	public void setNewValueToLeft(@RequestBody @Valid ListRequest req) {
		redis.addToListLeft(req.baseRequest().key(), req.name());
	}

	@PostMapping("/list-add-right")
	public void setNewValueToRight(@RequestBody @Valid ListRequest req) {
		redis.addToListRight(req.baseRequest().key(), req.name());
	}

	@GetMapping("/all")
	public List<ListModel> getAll(@RequestParam String key) {
		return redis.getAllData(key);
	}

}
