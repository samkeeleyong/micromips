package com.comp.arch.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comp.arch.InstFormat;
import com.comp.arch.InstructionFormatRepository;
import com.comp.arch.InstructionFormatTranslator;
import com.comp.arch.InstructionFormatTranslatorFactory;
import com.comp.arch.PipelineMap;

import cycle.Cycle;

@Controller
public class MainController {

	@Autowired
	private InstructionFormatTranslatorFactory translatorFactory;
	
	@Autowired
	private InstructionFormatRepository instructionFormatRepository;
	
    @RequestMapping(value={"/main", ""}, method=RequestMethod.GET)
    public String mainPage(Model model, RedirectAttributes redirectAttributes) {
        return "main";
    }
    
    @RequestMapping(value="/greeting", method=RequestMethod.POST)
    public String greetingSubmit(@RequestParam String instructions, Model model, RedirectAttributes redirectAttributes) {
    	redirectAttributes.addFlashAttribute("instructions", instructions);
    	
    	
    	// CONVERTING to opcode
    	Map<String, String> instructionMap = new HashMap<>();
    	String[] instructionSplit = instructions.split("\n");
    	String[] instructionCommands = new String[instructionSplit.length];
    	
    	int i = 0;
    	for(String instructionLine: instructionSplit) {
    		String instructionCommand = instructionLine.split(" ")[0];
    		instructionCommands[i] = instructionCommand;
    		InstFormat instructionFormat = instructionFormatRepository.findByInstruction(instructionCommand);
    		InstructionFormatTranslator translator = translatorFactory.getInstance(instructionFormat.getInstructionType());
    		String opcode = translator.translate(instructionLine, instructionCommand);
    		
    		System.out.println("OPCode for " + instructionLine + " is " + opcode);
    		instructionMap.put(instructionLine, opcode);
    		i++;
    	}
    	redirectAttributes.addFlashAttribute("instructionMap", instructionMap);
    	// END CONVERT
    	
    	
    	PipelineMap pipelineMap = new PipelineMap();
		
		List<String> opcodes = new ArrayList<>();
		opcodes.add("014C102A");
//		opcodes.add("DC0A1000");
//		opcodes.add("640C0006");
//		opcodes.add("640A0004");
//		opcodes.add("6441FF55");
		
		pipelineMap.setUpMipCycles(opcodes, 5, instructionCommands);
		Cycle previousCycle = new Cycle();
		for(Cycle cycle: pipelineMap.getCycles()) {
			System.out.println("Cycle:" + cycle.getCycleName());
			previousCycle = cycle.processCycle(previousCycle);
		}
        return "redirect:main";
    }
}