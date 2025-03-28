package io.github.sseregit.redishandsonenterprise.domain.sortedset.model.request;

import io.github.sseregit.redishandsonenterprise.common.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SortedSetRequest(
	BaseRequest baseRequest,
	@Schema(description = "name")
	@NotBlank
	@NotNull
	String name,
	@Schema(description = "score")
	@NotBlank
	@NotNull
	Float score
) {

}
