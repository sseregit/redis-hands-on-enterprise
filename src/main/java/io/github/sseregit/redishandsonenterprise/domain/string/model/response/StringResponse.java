package io.github.sseregit.redishandsonenterprise.domain.string.model.response;

import java.util.List;

import io.github.sseregit.redishandsonenterprise.domain.string.model.StringModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "redis string response")
public record StringResponse(
	@Schema(description = "set string response")
	List<StringModel> response
) {
}
