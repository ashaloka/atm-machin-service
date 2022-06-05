package com.atm.machin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

	private final SingleItemMeta meta;

	private final T data;

	public ResponseWrapper(){
		this.meta = new SingleItemMeta();
		this.data = null;
	}

	public ResponseWrapper(T data){
		this.meta = new SingleItemMeta();
		this.data = data;
	}

	@Getter
	public static class  SingleItemMeta {

		private final UUID transactionId;

		SingleItemMeta(){
			this.transactionId = UUID.randomUUID();
		}
	}
}
