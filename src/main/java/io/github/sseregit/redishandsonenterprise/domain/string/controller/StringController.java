package io.github.sseregit.redishandsonenterprise.domain.string.controller;

import io.github.sseregit.redishandsonenterprise.domain.string.model.request.MultiStringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.request.StringRequest;
import io.github.sseregit.redishandsonenterprise.domain.string.model.response.StringResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "string", description = "string api")
interface StringController {

	void setString(StringRequest req);

	StringResponse getString(String key);

	void multiString(MultiStringRequest req);
}
