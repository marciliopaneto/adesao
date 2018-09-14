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

		String fileLocation = "/home/marcus/Documentos/Incidentes/INC000029909130 - Omni - HEXT/Instituicao_Participante_Atuacao_Adesao_OMNI_14092018_HEXT_v2.xlsx";
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
