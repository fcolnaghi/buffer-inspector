package br.com.autoprocess.inspector.model;

import java.util.ArrayList;
import java.util.List;

public class ItemTabela {

	private String linhaArquivo;
	private String sequencia;
	private String nome;
	private String tipo;
	private int tamanho;
	private int tamanhoFixo; // Para arrays que tenham tamanho fixo
	private String valor;
	private String descricao;
	private boolean erro;
	private ItemTabela link;
	private ItemTabela origem;
	private List<ItemTabela> filhos;
	private int nivel;

	public ItemTabela() {
		filhos = new ArrayList<ItemTabela>();
	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getTamanhoFixo() {
		return tamanhoFixo;
	}

	public void setTamanhoFixo(int tamanhoFixo) {
		this.tamanhoFixo = tamanhoFixo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}

	public ItemTabela getLink() {
		return link;
	}

	public void setLink(ItemTabela link) {
		this.link = link;
	}

	public ItemTabela getOrigem() {
		return origem;
	}

	public void setOrigem(ItemTabela origem) {
		this.origem = origem;
	}

	public List<ItemTabela> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<ItemTabela> filhos) {
		this.filhos = filhos;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getLinhaArquivo() {
		return linhaArquivo;
	}

	public void setLinhaArquivo(String linhaArquivo) {
		this.linhaArquivo = linhaArquivo;
	}
	
}
