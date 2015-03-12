package br.com.angulo.sistemas.bean;

import static br.com.angulo.sistemas.bean.Produto.ProdutoE.COD_BARRAS;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.DESCRICAO;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ESTAT1;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ESTAT2;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ESTAT3;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.FORNECEDOR;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ID;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.MARGEM;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.PCUSTO;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.PVENDA;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.QTD_CPA;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.SALDO;


import br.com.angulo.sistemas.reflection.Coluna;
import br.com.angulosistemas.dao.BeanExportaImporta;

//JavaBean de Produto
public class Produto implements BeanExportaImporta {
	public static final String TABELA_PRODUTO = "produto";
	
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
	@Coluna(nome="codinterno", posicao=0)
	public void setId(int novo){
		this.id = novo;
	}
	@Coluna(nome="codinterno", posicao=0)
	public int getId(){
		return id;
	}
	
	@Coluna(nome="codbarras", posicao=1)
	public void setCodBarra(String novo){
		this.codBarra = novo;
	}
	@Coluna(nome="codbarras", posicao=1)
	public String getCodBarra(){
		return codBarra;
	}
	
	@Coluna(nome="fornecedor", posicao=2)
	public void setFornecedor(String novo){
		this.fornecedor = novo;
	}
	@Coluna(nome="fornecedor", posicao=2)
	public String getFornecedor(){
		return fornecedor;
	}
	
	@Coluna(nome="descricao", posicao=3)
	public void setDescricao(String novo){
		this.descricao = novo;
	}
	@Coluna(nome="descricao", posicao=3)
	public String getDescricao(){
		return descricao;
	}
	
	
	@Coluna(nome="pcusto", posicao=4)
	public void setPCusto(Double novo){
		this.pCusto = novo;
	}
	@Coluna(nome="pcusto", posicao=4)
	public Double getPCusto(){
		return pCusto;
	}
	
	@Coluna(nome="margem", posicao=5)
	public void setMargem(Double novo){
		this.margem =novo;
	}
	@Coluna(nome="margem", posicao=5)
	public Double getMargem(){
		return margem;
	}
	
	@Coluna(nome="pvenda", posicao=6)
	public void setPVenda(Double novo){
		this.pVenda = novo;
	}
	@Coluna(nome="pvenda", posicao=6)
	public Double getPVenda(){
		return pVenda;
	}
	
	@Coluna(nome="saldo", posicao=7)
	public void setEstoque(int novo){
		this.estoque = novo;
	}
	@Coluna(nome="saldo", posicao=7)
	public int getEstoque(){
		return estoque;
	}
	
	@Coluna(nome="estat1", posicao=8)
	public void setEstat1(int novo){
		this.estat1 = novo;
	}
	@Coluna(nome="estat1", posicao=8)
	public int getEstat1(){
		return estat1;
	}
	
	@Coluna(nome="estat2", posicao=9)
	public void setEstat2(int novo){
		this.estat2 = novo;
	}
	@Coluna(nome="estat2", posicao=9)
	public int getEstat2(){
		return estat2;
	}
	
	@Coluna(nome="estat3", posicao=10)
	public void setEstat3(int novo){
		this.estat3 = novo;
	}
	@Coluna(nome="estat3", posicao=10)
	public int getEstat3(){
		return estat3;
	}
	
	@Coluna(nome="qtdcpa", posicao=11)
	public void setQtdCpa(Integer novo){
		if (novo != null)
			this.qtdcpa = novo;
		else
			this.qtdcpa = 0;
	}
	@Coluna(nome="qtdcpa", posicao=11)
	public int getQtdCpa(){
		return this.qtdcpa;
	}
	
	
	
	// +++++++++++++ Métodos da Interface BeanExportacao ++++++++++++++
		@Override
		public String getTableName() {
			return TABELA_PRODUTO;
		}

		@Override
		public int getQtdeCampos() {
			return 12;	//Essa tabela contém 12 campos.
		}

		@Override
		public int getCodigoProduto() {
			return id;
		}
		
		@Override
		public String createTableQuery(){
			System.out.println("nome da tabela concatenada=" + TABELA_PRODUTO);

			StringBuilder builder = new StringBuilder();
			builder.append("CREATE TABLE IF NOT EXISTS `" + TABELA_PRODUTO + "` (");
			builder.append("`codinterno` int(11) NOT NULL,");
			builder.append("`codbarras` varchar(14) NOT NULL,");
			builder.append("`fornecedor` varchar(60) NOT NULL,");
			builder.append("`descricao` varchar(70) NOT NULL,");
			builder.append("`pcusto` double NOT NULL,");
			builder.append("`margem` double NOT NULL,");
			builder.append("`pvenda` double NOT NULL,");
			builder.append("`saldo` int(11) NOT NULL,");
			builder.append("`estat1` int(11) NOT NULL,");
			builder.append("`estat2` int(11) NOT NULL,");
			builder.append("`estat3` int(11) NOT NULL,");
			builder.append("`qtdcpa` int(11) NOT NULL,");
			builder.append("PRIMARY KEY (`codinterno`)");
			builder.append(") ENGINE=InnoDB DEFAULT CHARSET=latin1;");			

			return builder.toString();		
		}
		
		@Override
		public String createReplaceQuery(){
			String queryReplace = "REPLACE into " + TABELA_PRODUTO + " (" +
					//String queryReplace = "REPLACE into " + tabela + " (" +
					ID + "," +
					COD_BARRAS + "," +
					FORNECEDOR + "," +
					DESCRICAO + "," +
					PCUSTO + "," +
					MARGEM + "," +
					PVENDA + "," +
					SALDO + "," +
					ESTAT1 + "," +
					ESTAT2 + "," +
					ESTAT3 + "," +
					QTD_CPA + ")" +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

			return queryReplace;
		}
	
	
}
