package io.github.sseregit.redishandsonenterprise.domain.string.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.sseregit.redishandsonenterprise.domain.string.model.request.MultiStringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.request.StringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.response.StringResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/set")
@RequiredArgsConstructor
class StringControllerImpl implements StringController {
	@Override
	public void setString(@RequestBody @Valid StringRequest req) {

	}

	@Override
	public StringResponse getString(@RequestParam @Valid String key) {
		return null;
	}

	@Override
	public void multiString(@RequestBody @Valid MultiStringRequest req) {

	}
}
