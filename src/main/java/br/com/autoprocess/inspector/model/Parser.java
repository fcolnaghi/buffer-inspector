package br.com.autoprocess.inspector.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.autoprocess.inspector.exceptions.IncompleteBufferException;
import br.com.autoprocess.inspector.exceptions.InvalidArrayLengthException;
import br.com.autoprocess.inspector.exceptions.InvalidLayoutException;

public class Parser {

	private List<ItemTabela> itens;
	private List<String> layout;
	private StringBuilder buffer;

	private List<ItemTabela> saida;
	
	private boolean error = false;
	
	/**
	 * Construtor da classe
	 * 
	 * @param layout O layout da transacao
	 * @param buffer O buffer que contem os dados
	 */
	public Parser(List<String> layout, String buffer) {
		this.itens = new ArrayList<ItemTabela>();
		this.saida = new ArrayList<ItemTabela>();
		
		this.layout = layout;
		this.buffer = new StringBuilder(buffer);
	}

	/**
	 * Faz o casamento entre o layout e o buffer
	 * 
	 * @return
	 */
	public List<ItemTabela> match() {
		
		try {
			
			// ------------------------
			// Transforma o conteudo do arquivo em uma lista encadeada 
			// ------------------------
			prepareLayout();
			
			// ------------------------
			// Realiza o parse
			// ------------------------
			doParse(itens, "", 1, 0);

		} catch (IncompleteBufferException e) {
			adicionaItemErro(e.getMessage());
		} catch (InvalidLayoutException e) {
			adicionaItemErro(e.getMessage());
		}
		
		// ------------------------
		// Se ao final do processo o buffer nao estiver vazio (e nao houver algum erro anterior), adiciona seu conteudo em uma nova linha denominada 'resto' 
		// ------------------------
		if (buffer.length() > 0 && !error) {
			
			ItemTabela resto = new ItemTabela();
			
			resto.setNome("Remaining content");
			resto.setValor(buffer.toString());
			resto.setTipo("A");
			resto.setTamanho(buffer.length());
			
			saida.add(resto);
		}

		// ------------------------
		// Retorna a lista de itens 
		// ------------------------
		return saida;
		
	}
	
	/**
	 * 
	 * 
	 * @return
	 * @throws InvalidLayoutException 
	 */
	public void prepareLayout() throws InvalidLayoutException {
		
		// ------------------------
		// Se o layout estiver vazio, retorna uma excecao
		// ------------------------
		if (layout == null || layout.size() == 0) {

			adicionaItemErro("Layout file is empty.");

		} else {
				
			// ------------------------
			// Cria uma pilha para controlar os itens que tem filhos
			// ------------------------
			Stack<List<ItemTabela>> pilha = new Stack<List<ItemTabela>>();
			
			// ------------------------
			// Cria um no auxiliar
			// ------------------------
			ItemTabela no = new ItemTabela();
			
			// ------------------------
			// Percorre todas as linhas do arquivo
			// ------------------------
			for (String linha : layout) {
						
				// ------------------------
				// Descobre o numero da linha
				// ------------------------
				String numeroLinha = linha.substring(0, linha.indexOf(";"));
				
				// ------------------------
				// Separa o numero da linha do conteï¿½do
				// ------------------------
				linha = linha.substring(linha.indexOf(";") + 1, linha.length());
				
				// ------------------------
				// Descobre a posicao onde abre a chave de sub-lista
				// ------------------------
				int posicao = linha.indexOf('{');
				
				// ------------------------
				// Se encontrou a chave de abertura
				// ------------------------
				if (posicao != -1) {
					
					// ------------------------
					// Se a chave nao estiver sozinha em uma linha, retoma a partir da linha anterior
					// Ex: "QTD-TITULARES;2;N; {"
					// ------------------------
					if (posicao > 0) {
						
						// ------------------------
						// Retira a chave da linha
						// ------------------------
						String linhaSemChave = linha.substring(0, posicao);
						
						// ------------------------
						// Cria o no
						// ------------------------
						no = novoItem(numeroLinha, linhaSemChave);
						
						// ------------------------
						// Adiciona a linha atual na lista
						// ------------------------
						itens.add(no);
						
					}
					
					// ------------------------
					// Empilha a lista pai
					// ------------------------
					pilha.push(itens);
					
					// ------------------------
					// Seta como a lista atual os nos do filho (que serao criados)
					// ------------------------
					itens = no.getFilhos();
					
				} else if (linha.equals("}")) {
					
					// ------------------------
					// Se a pilha estiver vazia retorna uma excecao
					// ------------------------
					if (pilha.empty()) {
						
						throw new InvalidLayoutException(numeroLinha);
						
					} else {
						// ------------------------
						// Desempilha a lista pai
						// ------------------------
						itens = pilha.pop();
					}
					
				} else {
					
					// ------------------------
					// Cria o no
					// ------------------------
					no = novoItem(numeroLinha, linha);
					
					// ------------------------
					// Adiciona a linha atual na lista
					// ------------------------
					itens.add(no);
					
				}
				
			}
			
			// ------------------------
			// Limpa a pilha (caso necessario) para a proxima execucao
			// ------------------------
			while (! pilha.empty()) {
				pilha.pop();
			}
			
		}

	}

	/**
	 * 
	 * 
	 * @param lista
	 * @param ocorrencias
	 * @param nivel
	 * @throws IncompleteBufferException
	 * @throws InvalidArrayLengthException 
	 */
	private void doParse(List<ItemTabela> lista, String nomePai, int ocorrencias, int nivel) throws IncompleteBufferException {
		
		// ------------------------
		// Percorre todas as ocorrencias (em caso de array)
		// ------------------------
		for (int i = 0; i < ocorrencias && !error; i++) {
			
			// ------------------------
			// Adiciona um cabecalho, caso necessario
			// ------------------------
			if (nivel > 0) {
				if (i == 0) {
					addHeader(nomePai, i, nivel);
					nivel++;
				} else {
					addHeader(nomePai, i, nivel - 1);
				}
			}
			
			// ------------------------
			// Percorre todos os itens da lista
			// ------------------------
			for (int item = 0; item < lista.size() && !error; item++) {
				
				// ------------------------
				// Recupera o item da lista
				// ------------------------
				ItemTabela no = lista.get(item);
				
				// ------------------------
				// Adiciona o nï¿½ na lista de saida e ja recupera o seu conteudo
				// ------------------------
				try {
					String valorNo = addItem(no.getNome(), no.getTipo(), no.getTamanho(), no.getDescricao(), no.getFilhos().size(), nivel);
					
					// ------------------------
					// Tratamento de tabela de erros para a PGA
					// ------------------------
					if (no.getNome().toUpperCase().startsWith("HEADER-PGA-CODIGO-RETORNO")) {
						
						// ------------------------
						// Verifica se ï¿½ uma tabela de erros
						// ------------------------
						if (valorNo.equals("2547834883") || valorNo.equals("2547834886") || valorNo.equals("2682053626")) {

							try {
								
								// ------------------------
								// Recupera a quantidade de codigos de fracasso
								// ------------------------
								int qtdCodigosFracasso = Integer.parseInt(buffer.substring(0, 2));
								
								// ------------------------
								// Apaga a substring do buffer
								// ------------------------
								buffer.delete(0, 2);
								
								// ------------------------
								// Adiciona todos os codigos de fracasso na tabela
								// ------------------------
								for (int codigo = 0; codigo < qtdCodigosFracasso; codigo++) {
									
									// ------------------------
									// Corrige o tamanho (se for necessario) para nao dar erro quando for buscar o valor
									// ------------------------
									int tamanho = 10;
									
									if (tamanho > buffer.length()) {
										tamanho = buffer.length();
									}
									
									// ------------------------
									// Recupera o valor do buffer pela quantidade de caracteres
									// ------------------------
									String valor = buffer.substring(0, tamanho);
									
									// ------------------------
									// Apaga a substring do buffer
									// ------------------------
									buffer.delete(0, tamanho);
									
									// ------------------------
									// Adiciona o fracasso na saida
									// ------------------------
									ItemTabela fracasso = new ItemTabela();
									
									fracasso.setNome("HEADER-PGA-CODIGO-RETORNO-"+ codigo);
									fracasso.setTipo("N");
									fracasso.setValor(valor);
									fracasso.setTamanho(tamanho);
									fracasso.setNivel(nivel + 1);
									
									// ------------------------
									// Adiciona na saida
									// ------------------------
									saida.add(fracasso);
									
								}
								
							} catch (Exception e) {
								// ------------------------
								// Apenas evita disparar um erro se nao conseguir fazer substring
								// ------------------------
							}
							
						}
					}
					
					if (no.getLink() != null) { // ???
						
						// ------------------------
						// Procura pelo campo de saida que da origem a qtde de ocorrencias
						// ------------------------
						boolean achou = false;
						ItemTabela noAuxiliar = null;
						
						for (int s = 0; (!achou) && (s < saida.size()); s++) {
							noAuxiliar = saida.get(s);
							achou = (noAuxiliar.getOrigem() == no.getLink()); // ???
						}
						
						if (achou) {
							valorNo = noAuxiliar.getValor();
						}
					}
					
					// ------------------------
					// Se o item atual tiver filhos, chama a funcao recursivamente
					// ------------------------
					if (no.getFilhos().size() > 0 && valorNo.length() > 0) {
						
						// ------------------------
						// Recupera a quantidade de ocorrencias dinamicas
						// ------------------------
						int qtdFilhos = Integer.valueOf(valorNo);
						
						// ------------------------
						// Se houver um valor de tamanho fixo, assume este como o tamanho do array
						// ------------------------
						if (no.getTamanhoFixo() > 0) {
							qtdFilhos = no.getTamanhoFixo();
						}
						
						// ------------------------
						// Chama a funcao recursivamente
						// ------------------------
						doParse(no.getFilhos(), no.getNome(), qtdFilhos, nivel + 1);
						
					}
					
				} catch (InvalidArrayLengthException e) {
					adicionaItemErro(e.getMessage());
				}

			}
		}
		
	}

	/**
	 * Quebra uma string e cria um ItemTabela
	 * 
	 * @param linha
	 * @return
	 * @throws InvalidLayoutException 
	 */
	private ItemTabela novoItem (String numeroLinha, String linha) throws InvalidLayoutException {
		
		ItemTabela retorno = new ItemTabela();
		
		// ------------------------
		// Separa a string atraves do token ';'
		// ------------------------
		Scanner s = new Scanner(linha);
		s.useDelimiter(";");
		
		// ------------------------
		// Seta o numero da linha
		// ------------------------
		retorno.setLinhaArquivo(numeroLinha);
		
		// ------------------------
		// Seta o nome
		// ------------------------
		retorno.setNome(s.next().trim());
		
		// ------------------------
		// Seta o tamanho
		// ------------------------
		try {
			String tamanho = s.next().trim();
			
			// ------------------------
			// Se tiver uma virgula, soma as casas decimais no tamanho
			// -----------------------
			if (tamanho.contains(",")) {

				// ------------------------
				// Remove os espacos
				// ------------------------
				tamanho = tamanho.replaceAll(" ", "");

				// ------------------------
				// Recupera o valor inteiro e as casas decimais
				// ------------------------
				Pattern padrao = Pattern.compile("(\\d*),(\\d*)");
				Matcher matcher = padrao.matcher(tamanho);
								
				if (matcher.find()) {
					
					int inteiro = Integer.parseInt(matcher.group(1));
					int decimais = Integer.parseInt(matcher.group(2));
					
					tamanho = String.valueOf(inteiro + decimais);
				}
				
			}
			
			retorno.setTamanho(Integer.parseInt(tamanho));
			
		} catch (Exception e) { // Algum caracter invï¿½lido na posicao onde deveria ser o tamanho
			s.close();
			
			throw new InvalidLayoutException(numeroLinha);
		}
		
		// ------------------------
		// Seta o tipo
		// ------------------------
		String tipo = s.next().trim();
		
		tipo = tipo.toUpperCase();
		
		// ------------------------
		// Se tiver um parenteses, significa que e um array de tamanho fixo
		// ------------------------
		if (tipo.contains("(")) {

			// ------------------------
			// Remove os espacos
			// ------------------------
			tipo = tipo.replaceAll(" ", "");

			// ------------------------
			// Recupera o tipo e o tamanho em uma string no formato "tipo(tamanho)"
			// ------------------------
			Pattern padrao = Pattern.compile("(.*)\\((.*)\\)");
			Matcher matcher = padrao.matcher(tipo);
			
			if (matcher.find()) {
				retorno.setTipo(matcher.group(1));
				
				// ------------------------
				// Tenta setar o tamanho fixo
				// ------------------------
				try {
					retorno.setTamanhoFixo(Integer.parseInt(matcher.group(2)));
				} catch (NumberFormatException e) {
					retorno.setErro(true);
				}
			}
			
		} else {
			retorno.setTipo(tipo);
		}
		
		// ------------------------
		// Seta a descricao (se houver)
		// ------------------------
		try {
			String descricao = s.next();
			
			if (descricao != null) {
				retorno.setDescricao(descricao.trim());
			}
		} catch (Exception e) {
			retorno.setDescricao("");
		} finally {
			s.close();
		}
		
		return retorno;
		
	}
	
	/**
	 * 
	 * 
	 * @param nome
	 * @param ocorrencia
	 * @param nivel
	 */
	private void addHeader(String nome, int ocorrencia, int nivel) {
		
		ItemTabela header = new ItemTabela();
		
		header.setNivel(nivel);
		header.setNome(nome + "-" + ocorrencia);
		
		saida.add(header);
	}
	
	/**
	 * 
	 * @param nome O nome do item
	 * @param tipo O tipo do item
	 * @param tamanho O tamanho do item
	 * @param descricao A descricao (tooltip) do item
	 * @param qtdFilhos A quantidade de itens filhos
	 * @param nivel O nï¿½vel
	 * @return
	 * @throws IncompleteBufferException
	 * @throws InvalidArrayLengthException 
	 */
	private String addItem(String nome, String tipo, int tamanho, String descricao, int qtdFilhos, int nivel) throws IncompleteBufferException, InvalidArrayLengthException {
		
		ItemTabela retorno = new ItemTabela();
		
		// ------------------------
		// Forca um erro caso o buffer seja menor do que o esperado pelo layout
		// ------------------------
		if (buffer.length() == 0) {
			throw new IncompleteBufferException();
		}

		// ------------------------
		// Seta o nivel
		// ------------------------
		if (nivel > 0) {
			retorno.setNivel(nivel);
		}
		
		// ------------------------
		// Seta o nome
		// ------------------------
		retorno.setNome(nome);
		
		// ------------------------
		// Seta o tipo
		// ------------------------
		retorno.setTipo(tipo);

		// ------------------------
		// Seta o tamanho
		// ------------------------
		retorno.setTamanho(tamanho);

		
		// ------------------------
		// Seta a descricao
		// ------------------------
		retorno.setDescricao(descricao);
		
		// ------------------------
		// Corrige o tamanho (se for necessario) para nao dar erro quando for buscar o valor
		// ------------------------
		if (tamanho > buffer.length()) {
			tamanho = buffer.length();
		}
		
		// ------------------------
		// Recupera o valor do buffer pela quantidade de caracteres
		// ------------------------
		String valor = buffer.substring(0, tamanho);

		// ------------------------
		// Apaga a substring do buffer
		// ------------------------
		buffer.delete(0, tamanho);

		// ------------------------
		// Tratamento para arrays
		// ------------------------
		if (qtdFilhos > 0) {
			
			// ------------------------
			// Identifica raiz do array
			// ------------------------
			retorno.setSequencia("-");
			
			// ------------------------
			// Se o tamanho do array for invalido, retorna uma excecao
			// ------------------------
			if (!valor.matches("\\d+")) {
				throw new InvalidArrayLengthException();
			}
			
		}
		
		// ------------------------
		// Substitui os espacos no final de uma string por um caracter visivel
		// ------------------------
		if (tipo.equals("A")) {
			valor = valor.trim();
			
			for (int j = valor.length(); j < retorno.getTamanho(); j++) {
				valor += "·";
			}
		}
		
		// ------------------------
		// Se o tipo for numerico e tiver algum espaco no valor, substitui cada espaco por um caracter visivel
		// ------------------------
		if (tipo.equals("N")) {
			valor = valor.replaceAll(" ", "·");

			retorno.setValor(valor);
		}
		
		// ------------------------
		// Seta o valor no no
		// ------------------------
		retorno.setValor(valor);
		
		// ------------------------
		// Adiciona na saida
		// ------------------------
		saida.add(retorno);
		
		// ------------------------
		// Retorna o valor
		// ------------------------
		return valor;

	}

	/**
	 * Adiciona um item de erro no array de retorno
	 * 
	 * @param message A mensagem de erro
	 */
	private void adicionaItemErro(String message) {
		
		// ------------------------
		// Indica que ocorreu um erro
		// ------------------------
		error = true;
		
		// ------------------------
		// Cria um item na tabela para descrever a mensagem
		// ------------------------
		ItemTabela erro = new ItemTabela();
		
		erro.setErro(true);
		erro.setNome("ERROR");
		erro.setValor(message);
		
		saida.add(erro);
		
	}
	
}
