package com.security.sequence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "sequenceGenerator")
@Data
public class Sequence {

	@Id
	private String sequence;
	private long seqValue;
	
}
