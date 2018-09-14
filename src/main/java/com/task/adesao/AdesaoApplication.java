package com.task.adesao;

import com.task.adesao.constants.Ambiente;
import com.task.adesao.maker.ScriptGenerator;
import com.task.adesao.maker.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@SpringBootApplication
public class AdesaoApplication {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(AdesaoApplication.class, args);

		String fileLocation = "";
		ScriptGenerator generator = new ScriptGenerator(fileLocation);

		List<String> script = new ArrayList<>();

		script.addAll(Templates.getHeader(Ambiente.HEXT));
		script.addAll(generator.generate());
		script.add(Templates.getTrailer());

		for (String linha: script) {
			System.out.println(linha);
		}
	}

}
