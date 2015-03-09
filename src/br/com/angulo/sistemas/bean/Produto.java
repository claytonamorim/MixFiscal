package br.com.angulo.sistemas.bean;

import java.util.List;

//JavaBean de Produto
public class Produto {
	private int id;
	private String codBarra;
	private String fornecedor;
	private String descricao;
	private Double pCusto;
	private Double margem;
	private Double pVenda;
	private int estoque;
	private int estat1;
	private int estat2;
	private int estat3;
	private int qtdcpa;
	
	public Produto(){};
	
	public final static class ProdutoE{
		public static final String ID = "codinterno";
		public static final String COD_BARRAS = "codbarras";
		public static final String FORNECEDOR = "fornecedor";
		public static final String DESCRICAO = "descricao";
		public static final String PCUSTO = "pcusto";
		public static final String MARGEM = "margem";
		public static final String PVENDA = "pvenda";
		public static final String SALDO = "saldo";
		public static final String ESTAT1 = "estat1";
		public static final String ESTAT2 = "estat2";
		public static final String ESTAT3 = "estat3";
		public static final String QTD_CPA = "qtdcpa";
	}
	
	public Produto(int id,String codBarra,String descricao, String fornecedor, Double pCusto, Double margem, Double pVenda, int estoque, int estat1, int estat2, int estat3, int qtdcpa){
		this.id = id;
		this.codBarra = codBarra;
		this.descricao = descricao;
		this.pCusto = pCusto;
		this.margem = margem;
		this.pVenda = pVenda;
		this.estoque = estoque;
		this.estat1 = estat1;
		this.estat2 = estat2;
		this.estat3 = estat3;
		this.fornecedor = fornecedor;
		this.qtdcpa = qtdcpa;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	public String getLinha(){
		StringBuilder builder = new StringBuilder();
		String whiteSpace = " ";
		int cont;
		
		cont = 13 - codBarra.length();
		builder.append(codBarra);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		builder.append("\t");
		
		cont = 50 - descricao.length();
		builder.append(descricao);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		builder.append("\t");

		cont = 20 - fornecedor.length();
		builder.append(fornecedor);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		builder.append("\t");

		cont = 5 - String.valueOf(pCusto).length();
		builder.append(pCusto);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		builder.append("\t");
		
		cont = 5 - String.valueOf(margem).length();
		builder.append(margem);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		builder.append("\t");
		
		cont = 5 - String.valueOf(pVenda).length();
		builder.append(pVenda);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		builder.append("\t");
		
		cont = 6 - String.valueOf(estoque).length();
		builder.append(estoque);
		for (int i=0; i<cont; i++)
			builder.append(whiteSpace);
		
		
		return builder.toString();
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	
	public void setId(int novo){
		this.id = novo;
	}
	public int getId(){
		return id;
	}
	
	public void setCodBarra(String novo){
		this.codBarra = novo;
	}
	public String getCodBarra(){
		return codBarra;
	}
	public void setDescricao(String novo){
		this.descricao = novo;
	}
	public String getDescricao(){
		return descricao;
	}
	public void setFornecedor(String novo){
		
		this.fornecedor = novo;
	}
	public String getFornecedor(){
		return fornecedor;
	}
	public void setPCusto(Double novo){
		this.pCusto = novo;
	}
	public Double getPCusto(){
		return pCusto;
	}
	public void setMargem(Double novo){
		this.margem =novo;
	}
	public Double getMargem(){
		return margem;
	}
	public void setPVenda(Double novo){
		
		this.pVenda = novo;
	}
	public Double getPVenda(){
		return pVenda;
	}
	public void setEstoque(int novo){
		
		this.estoque = novo;
	}
	public int getEstoque(){
		return estoque;
	}
	
	public void setEstat1(int novo){
		this.estat1 = novo;
	}
	public int getEstat1(){
		return estat1;
	}
	public void setEstat2(int novo){
		this.estat2 = novo;
	}
	public int getEstat2(){
		return estat2;
	}
	public void setEstat3(int novo){
		this.estat3 = novo;
	}
	public int getEstat3(){
		return estat3;
	}
	
	public void setQtdCpa(Integer novo){
		if (novo != null)
			this.qtdcpa = novo;
		else
			this.qtdcpa = 0;
	}
	public int getQtdCpa(){
		return this.qtdcpa;
	}
	
	
	
}
