package com.comp.arch.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import memory.MemoryHolder;
import memory.RegisterValuesHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comp.arch.InstFormat;
import com.comp.arch.InstructionFormatRepository;
import com.comp.arch.InstructionFormatTranslator;
import com.comp.arch.InstructionFormatTranslatorFactory;
import com.comp.arch.PipelineMap;

import cycle.Cycle;
import cycle.MipCycle;

@Controller
public class MainController {

	@Autowired
	private InstructionFormatTranslatorFactory translatorFactory;
	
	@Autowired
	private InstructionFormatRepository instructionFormatRepository;
	
	private PipelineMap pipelineMap = null;
	private Cycle previousCycle = new Cycle();
	private Map<String, String> currentOpcodesForSingleStep;
	private String[] instructionSplit; 
	private int currentOpcodeCounter;

	public static Map<String, String> offsetMap;
	
    @RequestMapping(value={"/main", ""}, method=RequestMethod.GET)
    public String mainPage(Model model, RedirectAttributes redirectAttributes) {

    	model.addAttribute("registerValues", RegisterValuesHolder.getRegistervalues());
    	model.addAttribute("dataValues", MemoryHolder.getMemory());
        return "main";
    }
    
    @RequestMapping(value={"/registerValues"}, method=RequestMethod.GET)
    public String getRegisterValues(Model model, RedirectAttributes redirectAttributes) {

    	model.addAttribute("registerValues", RegisterValuesHolder.getRegistervalues());
        return "registerValues";
    }
    
    @RequestMapping(value="/changeRegisterValues", method=RequestMethod.POST)
    public String changeRegisterValues(RegisterValuesDto registerValuesDto, Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	for (int i = 0; i <= 31; i++) {
    		String registerValue = (String)RegisterValuesDto.class.getMethod("getR" + i).invoke(registerValuesDto);
    		RegisterValuesHolder.addOrEdit("R" + i, registerValue);
    	}
    	return "redirect:/registerValues";
    }
    
    @RequestMapping(value={"/dataValues"}, method=RequestMethod.GET)
    public String getDataValues(Model model, RedirectAttributes redirectAttributes) {

    	model.addAttribute("dataValues", MemoryHolder.getMemory());
        return "dataValues";
    }
    
    @RequestMapping(value="/changeDataValues", method=RequestMethod.POST)
    public String changeDataValues(@RequestParam Map<String,String> allRequestParams, Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	
    	for (String str: allRequestParams.keySet()) {
    		MemoryHolder.addOrEdit(str, allRequestParams.get(str));
    	}
    	return "redirect:/dataValues";
    }
    
    @ResponseBody
    @RequestMapping(value={"/getDataValues"}, method=RequestMethod.GET)
    public Map<String, String> getDataValuesAjax(Model model, RedirectAttributes redirectAttributes) {

        return MemoryHolder.getMemory();
    }
    
    @ResponseBody
    @RequestMapping(value={"/getRegisterValues"}, method=RequestMethod.GET)
    public Map<String, String> getRegisterValuesAjax(Model model, RedirectAttributes redirectAttributes) {

        return RegisterValuesHolder.getRegistervalues();
    }
    
    @ResponseBody
    @RequestMapping(value={"/getOpcodesForSingleStep"}, method=RequestMethod.GET)
    public String getOpcodesForSingleStep(Model model, RedirectAttributes redirectAttributes) {
    	String result = instructionSplit[currentOpcodeCounter] + "-" + currentOpcodesForSingleStep.get(instructionSplit[currentOpcodeCounter]);
    	currentOpcodeCounter++;
    	
        return  result;
    }
    
    @ResponseBody
    @RequestMapping(value="/single-step", method=RequestMethod.POST)
    public MipCycle singleStep(@RequestParam String instructions, @RequestParam int nthCycle, Model model) {
    	
    	List<String> opcodes = new ArrayList<>();
    	
    	// CONVERTING to opcode
    	Map<String, String> instructionMap = new LinkedHashMap<>();
    	instructionSplit = instructions.split("\n");
    	String[] instructionCommands = new String[instructionSplit.length];
    	
    	Map<String, String> offsetMap = calculateOffsetMap(instructions);
    	translateToOpcode(opcodes, instructionMap, instructionSplit, instructionCommands, offsetMap);
    	// END CONVERT
    	
		if (nthCycle == 0) {
			currentOpcodesForSingleStep = new HashMap<>();
			pipelineMap = new PipelineMap();
			currentOpcodeCounter = 0;
			
			Cycle.setCurrentNpc("0000");
	    	Cycle.setCurrentPc("0000");
			pipelineMap.setUpMipCycles(opcodes, instructionCommands, instructionSplit, offsetMap);
		}
		for (int i = 0; i < instructionCommands.length; i++) {
			currentOpcodesForSingleStep.put(instructionSplit[i], opcodes.get(i));	
		}
		
		System.out.println(pipelineMap.getCycles().size());
		/*if (nthCycle >= pipelineMap.getCycles().size()) {
    		return null;
    	}*/
		
		previousCycle = pipelineMap.getCycles().get(nthCycle).processCycle(previousCycle);
		
		return previousCycle.getMipCycles().get(0);  
    }
    
    @RequestMapping(value="/execute", method=RequestMethod.POST)
    public String execute(@RequestParam String instructions, Model model, RedirectAttributes redirectAttributes) {
    	redirectAttributes.addFlashAttribute("instructions", instructions);
    	
    	List<String> listOfErrors = validate(instructions);
    	if(!listOfErrors.isEmpty()) {
    		redirectAttributes.addFlashAttribute("errors", listOfErrors);
    		return "redirect:main";
    	}
    	Cycle.setCurrentNpc("0000");
    	Cycle.setCurrentPc("0000");
    	
    	instructions = instructions.replaceAll("NOP", "DADDIU R0, R0, #0000");
    	
    	List<String> opcodes = new ArrayList<>();
    	
    	// CONVERTING to opcode
    	Map<String, String> instructionMap = new LinkedHashMap<>();
    	String[] instructionSplit = instructions.split("\n");
    	String[] instructionCommands = new String[instructionSplit.length];
    	
    	offsetMap = calculateOffsetMap(instructions);
    	System.out.println("OffsetMap: " + offsetMap);
    	translateToOpcode(opcodes, instructionMap, instructionSplit, instructionCommands, offsetMap);
    	redirectAttributes.addFlashAttribute("instructionMap", instructionMap);
    	// END CONVERT
    	
    	
    	PipelineMap pipelineMap = new PipelineMap();
		LinkedHashMap<String, MipCycle> cycleMap = new LinkedHashMap<>();
		int j = 1;
		
		
		pipelineMap.setUpMipCycles(opcodes, instructionCommands, instructionSplit, offsetMap);
		Cycle previousCycle = new Cycle();
		for(Cycle cycle: pipelineMap.getCycles()) {
			try {
				previousCycle = cycle.processCycle(previousCycle);
				MipCycle mipCycle = previousCycle.getMipCycles().get(0);
				if (mipCycle.wasProcessed()) {
					cycleMap.put("Cycle" + j, mipCycle);
					j++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		redirectAttributes.addFlashAttribute("cycleMap", cycleMap);
		
        return "redirect:main";
    }
    
	private List<String> validate(String instructions) {
		List<String> listOfErrors = new ArrayList<>();
		List<String> listOfInstructions = Arrays.asList(instructions.split("\n"));
		List<String> listOfPossibleInstructions = new ArrayList<>();
		
		for(InstFormat instFormat: (List<InstFormat>) instructionFormatRepository.findAll()) {
			listOfPossibleInstructions.add(instFormat.getInstruction());
		}
		
		// valid instructions
		for(String instruction: listOfInstructions) {
			if (!listOfPossibleInstructions.contains(instruction.split(" ")[0].trim())) {
				String inst = instruction.split(" ")[0];
				if (inst.split(":").length > 1) {
					inst = inst.split(":")[1].trim();
					if (!listOfPossibleInstructions.contains(inst.trim())) {
						listOfErrors.add(inst + " is not a valid instruction");
					}
				} else {
					listOfErrors.add(inst + " is not a valid instruction");
				}
				
			}
		}
		
		Set<String> listOfLabels = new HashSet<>();
		for(String instruction: listOfInstructions) {
			if (instruction.startsWith("J")) {
				listOfLabels.add(instruction.split(" ")[1].trim());
			}
			if (instruction.startsWith("BNE")) {
				listOfLabels.add(instruction.split(",")[2].trim());
			}
		}
		for(String label: listOfLabels) {
			boolean found = false;
			for(String instruction: listOfInstructions) {
				if (instruction.startsWith(label)) {
					found = true;
				}
			}
			if (!found) {
				listOfErrors.add(label + " not found!");
			}
		}
		
		for(String instruction: listOfInstructions) {
			String pattern = "";
			
			if(instruction.startsWith("DADDIU")) {
				pattern = "([a-zA-Z0-9]*:)?DADDIU(\\s)*R\\d*,(\\s)*R\\d*,(\\s)*#[0-9a-f]{4}(\\s)*";
			}
			if (instruction.startsWith("DSRLV")) {
				pattern = "([a-zA-Z0-9]*:)?DSRLV(\\s)*R\\d*,(\\s)*R\\d*,(\\s)*R\\d*(\\s)*";
			}
			/*if (instruction.startsWith("SLT")) {
				pattern = "([a-zA-Z0-9]*:)?SLT\\s*R\\d*,\\s*R\\d*,\\s*R\\d*";
			}*/
			/*if (instruction.startsWith("BNE")) {
				pattern = "([a-zA-Z0-9]*:)?BNE\\s*R\\d*,\\s*R\\d*,\\s*[a-zA-Z0-9]*";
			}*/
			if (instruction.startsWith("LD")) {
				pattern = "([a-zA-Z0-9]*:)?LD\\s*R\\d*,\\s*[0-9a-z]{4}\\(R\\d*\\)\\s*";
			}
			if (instruction.startsWith("SD")) {
				pattern = "([a-zA-Z0-9]*:)?SD\\s*R\\d*,\\s*\\d{4}\\(R\\d*\\)\\s*";
			}
			/*if (instruction.startsWith("OR")) {
				pattern = "([a-zA-Z0-9]*:)?OR\\s*R\\d*,\\s*R\\d*,\\s*[a-zA-Z0-9]*";
			}*/
			
			
			if (!pattern.isEmpty()) {
				if (!instruction.matches(pattern)) {
					listOfErrors.add("Instruction (" + instruction + ") not conforming to pattern");
				}
			}
		}
		return listOfErrors;
	}

	private Map<String, String> calculateOffsetMap(String instructions) {
		Map<String, String> map = new HashMap<>();

		String[] instructionsSplit = instructions.split("\n");
		
		for (int i = 0 ; i < instructionsSplit.length; i++) {
			String instruction = instructionsSplit[i];
			if (instructionsSplit[i].startsWith("BNE") || instructionsSplit[i].startsWith("J")) {
				String toFind = ""; 
				if (instructionsSplit[i].startsWith("J")) {
					toFind = instruction.split(" ")[1].trim();
				} else {
					toFind = instruction.split(",")[instruction.split(",").length - 1].trim();
				}
				for (int j = i; j < instructionsSplit.length; j++) {
					
					if (instructionsSplit[j].trim().startsWith(toFind)) {
						map.put(instruction, (j - i) + "");
						break;
					}
				}
			}
		}
		
		return map;
	}

	private void translateToOpcode(List<String> opcodes,
			Map<String, String> instructionMap, String[] instructionSplit,
			String[] instructionCommands, Map<String, String> offsetMap) {
		int i = 0;
    	for(String instructionLine: instructionSplit) {
    		String instructionCommand = instructionLine.split(" ")[0];
    		if (instructionCommand.split(":").length > 1) {
    			instructionCommands[i] = instructionCommand.split(":")[1];
    		} else {
    			instructionCommands[i] = instructionCommand;
    		}
    		System.out.println("INstruction Command:" + instructionCommands[i]);
    		InstFormat instructionFormat = instructionFormatRepository.findByInstruction(instructionCommands[i]);
    		System.out.println(instructionFormat);
    		InstructionFormatTranslator translator = translatorFactory.getInstance(instructionFormat.getInstructionType());
    		String opcode = translator.translate(instructionLine, instructionCommands[i], offsetMap);
    		
    		instructionMap.put(instructionLine, opcode);
    		opcodes.add(opcode);
    		i++;
    	}
	}
}