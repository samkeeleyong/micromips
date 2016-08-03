package com.comp.arch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionFormatRepository extends CrudRepository<InstFormat, String>{

	InstFormat findByInstruction(String instruction);
}
