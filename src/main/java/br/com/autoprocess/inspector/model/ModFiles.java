package br.com.autoprocess.inspector.model;

import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import br.com.autoprocess.inspector.bean.Contexto;
import br.com.autoprocess.inspector.bean.TipoHeader;

public class ModFiles {
	
	/**
	 * Retorna uma lista de servicos GSI
	 * 
	 * @return
	 */
	public List<String> getServicesList() {
		
		List<String> retorno = new ArrayList<String>();
		
		// ------------------------
		// Carrega o diretorio
		// ------------------------
		File file = new File(getApplicationPath() + "\\GSI");
		
		// ------------------------
		// Cria o diretorio caso ele nao exista
		// ------------------------
		if (!file.exists()) {
			file.mkdir();
		} else {
			
			// ------------------------
			// Lista os diretorios
			// ------------------------
			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					retorno.add(f.getName());
				}
			}
		}
		
		// ------------------------
		// Ordena a lista
		// ------------------------
		Collections.sort(retorno, Collator.getInstance());
		
		return retorno;
	}
	
	/**
	 * Retorna uma lista de operacoes de um determinado servico GSI
	 * 
	 * @param service O nome do servico GSI
	 * @return
	 */
	public List<String> getOperationsList(String service) {
		
		List<String> retorno = new ArrayList<String>();
		
		// ------------------------
		// Carrega o diretorio
		// ------------------------
		File file = new File(getApplicationPath() + "\\GSI\\" + service);
		
		// ------------------------
		// Cria o diretorio caso ele nao exista
		// ------------------------
		if (!file.exists()) {
			file.mkdir();
		} else {
			for (File f : file.listFiles()) {
				if (!f.isDirectory() && f.getName().endsWith(".txt")) {
					retorno.add(f.getName().split(".txt")[0]);
				}
			}
		}
		
		// ------------------------
		// Ordena a lista
		// ------------------------
		Collections.sort(retorno, Collator.getInstance());
		
		return retorno;
	}
	
	/**
	 * Retorna uma lista de servicos MI
	 * 
	 * @return
	 */
	public List<String> getTransactionsList() {
		
		List<String> retorno = new ArrayList<String>();
		String quebraLinha = System.getProperty("line.separator");
		
		// ------------------------
		// Carrega o diretorio
		// ------------------------
		File file = new File(getApplicationPath() + "\\MI");
		
		// ------------------------
		// Cria o diretorio caso ele nao exista
		// ------------------------
		if (!file.exists()) {
			file.mkdir();
			
			// ------------------------
			// Cria um arquivo de exemplo dentro do diretorio
			// ------------------------
			try {
				
				// ------------------------
				// Armazena o conteudo que sera escrito no arquivo
				// ---------------------------
				StringBuilder conteudoArquivo = new StringBuilder();
				
				// ---------------------------
				// Escreve o conteudo no arquivo
				// ---------------------------
				conteudoArquivo.append("// ---------------------------------------------------------------------------" + quebraLinha);
				conteudoArquivo.append("// 1) Blank lines are ignored" + quebraLinha);
				conteudoArquivo.append("// 2) Lines started by double slashes are comments" + quebraLinha);
				conteudoArquivo.append("// 3) Follow this example layout files and by happy!" + quebraLinha);
				conteudoArquivo.append("// ---------------------------------------------------------------------------" + quebraLinha);
				
				conteudoArquivo.append(quebraLinha);
				
				conteudoArquivo.append("// Put the following text (inside brackets) at the left box to test with template layout file" + quebraLinha);
				conteudoArquivo.append("// [29John Doe            03Xbox 360  00900Super Man 00050Ted Bear  001001VW Golf   Black                              19830912]" + quebraLinha);
				
				conteudoArquivo.append(quebraLinha);
				
				conteudoArquivo.append("#########" + quebraLinha);
				conteudoArquivo.append("[input]" + quebraLinha);
				conteudoArquivo.append("#########" + quebraLinha);
				conteudoArquivo.append("age;2;N;Customer age" + quebraLinha);
				conteudoArquivo.append("name;20;A;Customer name" + quebraLinha);
				conteudoArquivo.append("toys;2;ARRAY;Amount of toys (variable array example)" + quebraLinha);
				conteudoArquivo.append("{" + quebraLinha);
				conteudoArquivo.append("	toy-name;10;A;The toy name" + quebraLinha);
				conteudoArquivo.append("	toy-price;3,2;N;A toy price (note, it is possible to separate the value with a comma)" + quebraLinha);
				conteudoArquivo.append("}" + quebraLinha);
				conteudoArquivo.append("cars;1;FIXED-ARRAY(3);A fixed array (fixed arrays must put a value between commas)" + quebraLinha);
				conteudoArquivo.append("{" + quebraLinha);
				conteudoArquivo.append("	car-name;10;A;" + quebraLinha);
				conteudoArquivo.append("	car-color;5;A;" + quebraLinha);
				conteudoArquivo.append("}" + quebraLinha);
				conteudoArquivo.append("birthday;8;N;" + quebraLinha);
				
				conteudoArquivo.append(quebraLinha);
				
				conteudoArquivo.append("#######" + quebraLinha);
				conteudoArquivo.append("[output]" + quebraLinha);
				conteudoArquivo.append("#######" + quebraLinha);
				conteudoArquivo.append("error-code;5;Example of output error code" + quebraLinha);
				conteudoArquivo.append("error-message;100;Example of output message error" + quebraLinha);
				
				// ---------------------------
				// Cria o arquivo caso nao exista
				// ---------------------------
				File template = new File(getApplicationPath() + "\\MI\\template.txt");
				
				// ---------------------------
				// Escreve o conteudo no arquivo
				// ---------------------------
				FileWriter arquivo = new FileWriter(template);
				arquivo.write(conteudoArquivo.toString());
				
				// ---------------------------
				// Fecha o arquivo
				// ---------------------------
				arquivo.close();
				
			} catch (Exception e) {
				// Apenas deixa de criar o arquivo de template
			}
			
		}
		
		// ------------------------
		// Lista os arquivos
		// ------------------------
		for (File f : file.listFiles()) {
			if (!f.isDirectory() && f.getName().endsWith(".txt")) {
				retorno.add(f.getName().split(".txt")[0]);
			}
		}
		
		// ------------------------
		// Ordena a lista
		// ------------------------
		Collections.sort(retorno, Collator.getInstance());
		
		return retorno;
	}
	
	/**
	 * Carrega os dados do arquivo de layout (entrada ou sa�da) em uma lista de Strings
	 * 
	 * @param arquivoLayout O nome do arquivo de layout
	 * @param ctx Indica se � entrada ou sa�da
	 * @return
	 */
	public List<String> loadLayout(String fileName, TipoHeader tipoTransacao, Contexto ctx) throws Exception {

		List<String> retorno = new ArrayList<String>();

		Contexto tipoLeitura = Contexto.ENTRADA; // default
		
		// ------------------------
		// Abre o arquivo de layout
		// ------------------------
		File file = new File(getApplicationPath() + "\\" + tipoTransacao + "\\" + fileName + ".txt");
		
		// ------------------------
		// Le o conteudo do arquivo
		// ------------------------
		Scanner linhas = null;
		
		try {
			linhas = new Scanner(file);

			// ------------------------
			// Armazena o numero da linha do arquivo
			// ------------------------
			int i = 1;
			
			// ------------------------
			// Transfere o conteudo do arquivo para a variavel de retorno
			// ------------------------
			while (linhas.hasNext()) {
				
				// ------------------------
				// Efetura a leitura da linha atual
				// ------------------------
				String linha = linhas.nextLine();
				
				// ------------------------
				// Retira espa�os desnecessarios e transforma o conteudo para caixa alta
				// ------------------------
				linha = linha.trim();
				
				// ------------------------
				// Le o conteudo do arquivo atraves de seu tipo (entrada/saida), desconsiderando comentarios
				// ------------------------
				if (!linha.isEmpty() && !linha.startsWith("#") && !linha.startsWith(";;") && !linha.startsWith("//")) {

					if (linha.toUpperCase().equals("[ENTRADA]") || linha.toUpperCase().equals("[INPUT]")) {
						tipoLeitura = Contexto.ENTRADA;
					} else if (linha.toUpperCase().equals("[SAIDA]") || linha.toUpperCase().equals("[OUTPUT]")) {
						tipoLeitura = Contexto.SAIDA;
					} else {
						if (ctx == tipoLeitura) {
							retorno.add(i +";" + linha);
						}
					}

				}

				i++;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			linhas.close();
		}
		
		return retorno;
	}
	
	/**
	 * Retorna o caminho onde a aplicacao esta sendo executada
	 * 
	 * @return caminho da aplicacao
	 */
	public String getApplicationPath() {
		
		String path = "";
		
		String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
		
		File file = new File(url).getParentFile();

		// ------------------------
		// Retorna o caminho quando a aplicacao esta dentro de um arquivo .jar
		// ------------------------
		if (file.getPath().contains(".jar")) {
			
			while (file.getPath().contains(".jar")) {
				file = file.getParentFile();
			}
			
			path = file.getPath().substring(6);
		} else {
			path = file.getPath();
		}

		try {
			return URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return path.replace("%20", " ");
		}
		
	}
	
}
