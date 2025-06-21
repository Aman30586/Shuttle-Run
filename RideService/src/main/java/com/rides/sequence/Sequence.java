package com.rides.sequence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "sequenceGenerator")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence {

	@Id
	private String sequence;
	private long seqValue;
	
}
