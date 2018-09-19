package com.task.adesao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class AdesaoApplication {

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AdesaoApplication.class, args);

/*
		String fileLocation = "/home/marcus/Documentos/Incidentes/INC000029909130 - Omni - HEXT/Instituicao_Participante_Atuacao_Adesao_OMNI_14092018_HEXT_v2.xlsx";
		ScriptGenerator generator = new ScriptGenerator(fileLocation);

		List<String> script = new ArrayList<>();

		script.addAll(Templates.getHeader(Ambiente.HEXT));
		script.addAll(generator.generate());
		script.add(Templates.getTrailer());

		for (String linha: script) {
			System.out.println(linha);
		}
*/
	}
}
