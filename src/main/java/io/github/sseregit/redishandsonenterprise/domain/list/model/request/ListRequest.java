package io.github.sseregit.redishandsonenterprise.domain.list.model.request;

import io.github.sseregit.redishandsonenterprise.common.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ListRequest(
	BaseRequest baseRequest,
	@Schema(description = "name")
	@NotBlank
	@NotNull
	String name
) {
}
