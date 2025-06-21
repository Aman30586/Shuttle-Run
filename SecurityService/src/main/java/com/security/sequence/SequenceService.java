package com.security.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {
	
	@Autowired
	SequenceRepository sequenceRepository;

	public long getNextSequence(String seqName) {
		Sequence sequence = sequenceRepository.findById(seqName).orElse(null);
		if(sequence == null) {
			sequence = new Sequence();
			sequence.setSequence(seqName);
			sequence.setSeqValue(103);
		}
		else {
			sequence.setSeqValue(sequence.getSeqValue() + 1);
		}
		sequenceRepository.save(sequence);
		return sequence.getSeqValue();
	}
}
