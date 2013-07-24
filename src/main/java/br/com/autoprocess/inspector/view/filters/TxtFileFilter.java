package br.com.autoprocess.inspector.view.filters;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileFilter;

public class TxtFileFilter extends FileFilter {

	public boolean accept(File f) {

		if (f.isDirectory()) {    
            return true;    
        }    
    
        String extension = getExtension(f.getName());
        
		if (extension.equals(".txt")) {
			return true;
		} else {
			return false;
		}
		
	}

	public String getDescription() {
		return "Text documents (*.txt)";
	}
	
	/**
	 * Retorna a extensão do arquivo
	 * 
	 * @param fileName
	 * @return
	 */
	private String getExtension(String fileName) {

		Pattern padrao = Pattern.compile(".+(\\..+)");
		
		Matcher matcher = padrao.matcher(fileName);
		
		String extensao = "";
		
		if (matcher.find()) {
			extensao = matcher.group(1);
		}

		return extensao;
		
	}

}
