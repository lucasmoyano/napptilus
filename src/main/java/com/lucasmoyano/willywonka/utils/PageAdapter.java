package com.lucasmoyano.willywonka.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
public class PageAdapter<TEntity> {

	@Getter private long totalElements;
    @Getter private long totalPages;
    @Getter private long page;
    @Getter private long size;
    @Getter private List<TEntity> elements;

	public PageAdapter(List<TEntity> elements, long totalElements, long page, long size) {
		this.totalElements = totalElements;
		this.page = page;
		this.size = size;
		this.elements = elements;
        this.totalPages = (long) Math.floor(totalElements / size) + 1;
	}
}
