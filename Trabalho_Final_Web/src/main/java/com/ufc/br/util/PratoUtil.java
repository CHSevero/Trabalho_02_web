package com.ufc.br.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class PratoUtil {

	public static void salvarImagens(String caminho, MultipartFile imagem) {
		// TODO Auto-generated method stub
		File file = new File(caminho);
		try {
			FileUtils.writeByteArrayToFile(file, imagem.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
