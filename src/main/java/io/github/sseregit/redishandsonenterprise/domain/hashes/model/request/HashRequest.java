package io.github.sseregit.redishandsonenterprise.domain.hashes.model.request;

import io.github.sseregit.redishandsonenterprise.common.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "redis hash collection request")
public record HashRequest(
	BaseRequest baseRequest,
	@Schema(description = "field")
	@NotBlank
	@NotNull
	String field,
	@Schema(description = "value")
	@NotBlank
	@NotNull
	String value
) {
}
