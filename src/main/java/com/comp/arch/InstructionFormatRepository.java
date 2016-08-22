package com.comp.arch;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.comp.arch.web.InstFormat;

@Component
public class InstructionFormatRepository{

	public Map<String, InstFormat> map = new HashMap<>();
	
	public InstFormat findByInstruction(String instruction) {
		return map.get(instruction);
	};
	
	public void save(InstFormat format) {
		map.put(format.getInstruction(), format);
	}

	public Collection<InstFormat> findAll() {
		return map.values();
	}
}
