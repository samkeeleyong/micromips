package com.comp.arch;

import memory.MemoryHolder;
import memory.RegisterValuesHolder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.comp.arch.web.InstFormat;

@SpringBootApplication
public class ComparchApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ComparchApplication.class, args);
		
		// initialization
		for (int i = 0; i <= 31; i++) {
			RegisterValuesHolder.addOrEdit("R" + i, "0000000000000000");
		}
		
		RegisterValuesHolder.addOrEdit("R1", "0000000000000001");
		RegisterValuesHolder.addOrEdit("R2", "0000000000000002");
		RegisterValuesHolder.addOrEdit("R8", "1234567891234567");
		
		String[] values = new String [] {"87","DC","FD","88","97","12","34","56","78","11","22","33","44","55","66","FF"};
		String[] hexValues = PipelineMap.getHexValues("3000", 17, true, 1);
		for (int i = 0; i < values.length; i++) {
			MemoryHolder.addOrEdit(hexValues[i], values[i]);
		}
		
		InstructionFormatRepository repository =(InstructionFormatRepository)ctx.getBean("instructionFormatRepository");
		setUpDb(repository);
		System.out.println("Please go to localhost:8090.");
	}

	private static void setUpDb(InstructionFormatRepository repository) {
		InstFormat instFormat1 = new InstFormat();
		instFormat1.setInstructionType(InstructionType.I);
		instFormat1.setVariables(null);
		instFormat1.setOpCode("011001");
		instFormat1.setRs(null);
		instFormat1.setRt(null);
		instFormat1.setRd(null);
		instFormat1.setFifthParam("");
		instFormat1.setFunc("");
		instFormat1.setInstruction("DADDIU");
		instFormat1.setInstructionFormatString("DADDIU rt, rs, immediate");
		instFormat1.setImmediate(true);
		instFormat1.setOffset(false);
		instFormat1.setImmediate(true);
		repository.save(instFormat1);


		InstFormat instFormat2 = new InstFormat();
		instFormat2.setInstructionType(InstructionType.I);
		instFormat2.setVariables(null);
		instFormat2.setOpCode("110111");
		instFormat2.setRs(null);
		instFormat2.setRt(null);
		instFormat2.setRd(null);
		instFormat2.setFifthParam("null");
		instFormat2.setFunc("null");
		instFormat2.setInstruction("LD");
		instFormat2.setInstructionFormatString("LD rt, offset(base)");
		instFormat2.setImmediate(false);
		instFormat2.setOffset(true);
		instFormat2.setImmediate(false);
		repository.save(instFormat2);

		InstFormat instFormat3 = new InstFormat();
		instFormat3.setInstructionType(InstructionType.I);
		instFormat3.setVariables(null);
		instFormat3.setOpCode("111111");
		instFormat3.setRs(null);
		instFormat3.setRt(null);
		instFormat3.setRd(null);
		instFormat3.setFifthParam("");
		instFormat3.setFunc("");
		instFormat3.setInstruction("SD");
		instFormat3.setInstructionFormatString("SD rt, offset(base)");
		instFormat3.setImmediate(false);
		instFormat3.setOffset(true);
		instFormat3.setImmediate(false);
		repository.save(instFormat3);

		InstFormat instFormat4 = new InstFormat();
		instFormat4.setInstructionType(InstructionType.R);
		instFormat4.setVariables(null);
		instFormat4.setOpCode("000000");
		instFormat4.setRs(null);
		instFormat4.setRt(null);
		instFormat4.setRd(null);
		instFormat4.setFifthParam("00000");
		instFormat4.setFunc("100101");
		instFormat4.setInstruction("OR");
		instFormat4.setInstructionFormatString("OR rd,rs,rt");
		instFormat4.setImmediate(false);
		instFormat4.setOffset(false);
		instFormat4.setImmediate(false);
		repository.save(instFormat4);

		InstFormat instFormat5 = new InstFormat();
		instFormat5.setInstructionType(InstructionType.R);
		instFormat5.setVariables(null);
		instFormat5.setOpCode("000000");
		instFormat5.setRs(null);
		instFormat5.setRt(null);
		instFormat5.setRd(null);
		instFormat5.setFifthParam("00000");
		instFormat5.setFunc("101010");
		instFormat5.setInstruction("SLT");
		instFormat5.setInstructionFormatString("SLT rd,rs,rt");
		instFormat5.setImmediate(false);
		instFormat5.setOffset(false);
		instFormat5.setImmediate(false);
		repository.save(instFormat5);

		InstFormat instFormat6 = new InstFormat();
		instFormat6.setInstructionType(InstructionType.R);
		instFormat6.setVariables(null);
		instFormat6.setOpCode("000000");
		instFormat6.setRs(null);
		instFormat6.setRt(null);
		instFormat6.setRd(null);
		instFormat6.setFifthParam("00000");
		instFormat6.setFunc("010110");
		instFormat6.setInstruction("DSRLV");
		instFormat6.setInstructionFormatString("DSRLV rd,rt,rs");
		instFormat6.setImmediate(false);
		instFormat6.setOffset(false);
		instFormat6.setImmediate(false);
		repository.save(instFormat6);


		InstFormat instFormat7 = new InstFormat();
		instFormat7.setInstructionType(InstructionType.I);
		instFormat7.setVariables(null);
		instFormat7.setOpCode("000101");
		instFormat7.setRs(null);
		instFormat7.setRt(null);
		instFormat7.setRd(null);
		instFormat7.setFifthParam("null");
		instFormat7.setFunc("");
		instFormat7.setInstruction("BNE");
		instFormat7.setInstructionFormatString("BNE rs,rt,offset");
		instFormat7.setImmediate(false);
		instFormat7.setOffset(true);
		instFormat7.setImmediate(false);
		repository.save(instFormat7);


		InstFormat instFormat8 = new InstFormat();
		instFormat8.setInstructionType(InstructionType.J);
		instFormat8.setVariables(null);
		instFormat8.setOpCode("000010");
		instFormat8.setRs(null);
		instFormat8.setRt(null);
		instFormat8.setRd(null);
		instFormat8.setFifthParam("");
		instFormat8.setFunc("");
		instFormat8.setInstruction("J");
		instFormat8.setInstructionFormatString("J instr_index");
		instFormat8.setImmediate(false);
		instFormat8.setOffset(false);
		instFormat8.setImmediate(false);
		repository.save(instFormat8);

		
		InstFormat instFormat9 = new InstFormat();
		instFormat9.setInstructionType(InstructionType.I);
		instFormat9.setVariables(null);
		instFormat9.setOpCode("null");
		instFormat9.setRs(null);
		instFormat9.setRt(null);
		instFormat9.setRd(null);
		instFormat9.setFifthParam("");
		instFormat9.setFunc("");
		instFormat9.setInstruction("NOP");
		instFormat9.setInstructionFormatString("NOP");
		instFormat9.setImmediate(false);
		instFormat9.setOffset(false);
		instFormat9.setImmediate(false);
		repository.save(instFormat9);
	}
}
