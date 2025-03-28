package io.github.sseregit.redishandsonenterprise.domain.sortedset.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SortedSet {
	private final String name;
	private final Float score;
}
