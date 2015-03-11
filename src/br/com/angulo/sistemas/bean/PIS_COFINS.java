package br.com.angulo.sistemas.bean;

import br.com.angulo.sistemas.reflection.Coluna;


public class PIS_COFINS {
	public static final String TABELA_PIS_COFINS = "MXF_VW_PIS_COFINS";
	
	private int codigo_produto;
	private int ean;
	private String descritivo_produto; 
	private String ncm;
	private String ncm_ex;
	private int cod_natureza_receita;
	private int credito_presumido;
	private String pis_cst_e;
	private String pis_cst_s;
	private double pis_alq_e;
	private double pis_alq_s;
	private String cofins_cst_e;
	private String cofins_cst_s;
	private double cofins_alq_e;
	private double cofins_alq_s;
	private String depto;
	private String secao;
	private String grupo;
	private String subgrupo;
	private String status;
	
	public PIS_COFINS(){}

	public PIS_COFINS(int codigo_produto, int ean, String descritivo_produto,
			String ncm, String ncm_ex, int cod_natureza_receita,
			int credito_presumido, String pis_cst_e, String pis_cst_s,
			double pis_alq_e, double pis_alq_s, String cofins_cst_e,
			String cofins_cst_s, double cofins_alq_e, double cofins_alq_s,
			String depto, String secao, String grupo, String subgrupo,
			String status) {
		super();
		this.codigo_produto = codigo_produto;
		this.ean = ean;
		this.descritivo_produto = descritivo_produto;
		this.ncm = ncm;
		this.ncm_ex = ncm_ex;
		this.cod_natureza_receita = cod_natureza_receita;
		this.credito_presumido = credito_presumido;
		this.pis_cst_e = pis_cst_e;
		this.pis_cst_s = pis_cst_s;
		this.pis_alq_e = pis_alq_e;
		this.pis_alq_s = pis_alq_s;
		this.cofins_cst_e = cofins_cst_e;
		this.cofins_cst_s = cofins_cst_s;
		this.cofins_alq_e = cofins_alq_e;
		this.cofins_alq_s = cofins_alq_s;
		this.depto = depto;
		this.secao = secao;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.status = status;
	}

	@Coluna(nome="codigo_produto", posicao=0)
	public int getCodigo_produto() {
		return codigo_produto;
	}
	@Coluna(nome="codigo_produto", posicao=0)
	public void setCodigo_produto(int codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	@Coluna(nome="ean", posicao=1)
	public int getEan() {
		return ean;
	}
	@Coluna(nome="ean", posicao=1)
	public void setEan(int ean) {
		this.ean = ean;
	}

	@Coluna(nome="descritivo_produto", posicao=2)
	public String getDescritivo_produto() {
		return descritivo_produto;
	}
	@Coluna(nome="descritivo_produto", posicao=2)
	public void setDescritivo_produto(String descritivo_produto) {
		this.descritivo_produto = descritivo_produto;
	}

	@Coluna(nome="ncm", posicao=3)
	public String getNcm() {
		return ncm;
	}
	@Coluna(nome="ncm", posicao=3)
	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	@Coluna(nome="ncm_ex", posicao=4)
	public String getNcm_ex() {
		return ncm_ex;
	}
	@Coluna(nome="ncm_ex", posicao=4)
	public void setNcm_ex(String ncm_ex) {
		this.ncm_ex = ncm_ex;
	}

	@Coluna(nome="cod_natureza_receita", posicao=5)
	public int getCod_natureza_receita() {
		return cod_natureza_receita;
	}
	@Coluna(nome="cod_natureza_receita", posicao=5)
	public void setCod_natureza_receita(int cod_natureza_receita) {
		this.cod_natureza_receita = cod_natureza_receita;
	}

	@Coluna(nome="credito_presumido", posicao=6)
	public int getCredito_presumido() {
		return credito_presumido;
	}
	@Coluna(nome="credito_presumido", posicao=6)
	public void setCredito_presumido(int credito_presumido) {
		this.credito_presumido = credito_presumido;
	}

	@Coluna(nome="pis_cst_e", posicao=7)
	public String getPis_cst_e() {
		return pis_cst_e;
	}
	@Coluna(nome="pis_cst_e", posicao=7)
	public void setPis_cst_e(String pis_cst_e) {
		this.pis_cst_e = pis_cst_e;
	}

	@Coluna(nome="pis_cst_s", posicao=8)
	public String getPis_cst_s() {
		return pis_cst_s;
	}
	@Coluna(nome="pis_cst_s", posicao=8)
	public void setPis_cst_s(String pis_cst_s) {
		this.pis_cst_s = pis_cst_s;
	}

	@Coluna(nome="pis_alq_e", posicao=9)
	public double getPis_alq_e() {
		return pis_alq_e;
	}
	@Coluna(nome="pis_alq_e", posicao=9)
	public void setPis_alq_e(double pis_alq_e) {
		this.pis_alq_e = pis_alq_e;
	}

	@Coluna(nome="pis_alq_s", posicao=10)
	public double getPis_alq_s() {
		return pis_alq_s;
	}
	@Coluna(nome="pis_alq_s", posicao=10)
	public void setPis_alq_s(double pis_alq_s) {
		this.pis_alq_s = pis_alq_s;
	}

	@Coluna(nome="cofins_cst_e", posicao=11)
	public String getCofins_cst_e() {
		return cofins_cst_e;
	}
	@Coluna(nome="cofins_cst_e", posicao=11)
	public void setCofins_cst_e(String cofins_cst_e) {
		this.cofins_cst_e = cofins_cst_e;
	}

	@Coluna(nome="cofins_cst_s", posicao=12)
	public String getCofins_cst_s() {
		return cofins_cst_s;
	}
	@Coluna(nome="cofins_cst_s", posicao=12)
	public void setCofins_cst_s(String cofins_cst_s) {
		this.cofins_cst_s = cofins_cst_s;
	}

	@Coluna(nome="cofins_alq_e", posicao=13)
	public double getCofins_alq_e() {
		return cofins_alq_e;
	}
	@Coluna(nome="cofins_alq_e", posicao=13)
	public void setCofins_alq_e(double cofins_alq_e) {
		this.cofins_alq_e = cofins_alq_e;
	}

	@Coluna(nome="cofins_alq_s", posicao=14)
	public double getCofins_alq_s() {
		return cofins_alq_s;
	}
	@Coluna(nome="cofins_alq_s", posicao=14)
	public void setCofins_alq_s(double cofins_alq_s) {
		this.cofins_alq_s = cofins_alq_s;
	}

	@Coluna(nome="depto", posicao=15)
	public String getDepto() {
		return depto;
	}
	@Coluna(nome="depto", posicao=15)
	public void setDepto(String depto) {
		this.depto = depto;
	}

	@Coluna(nome="secao", posicao=16)
	public String getSecao() {
		return secao;
	}
	@Coluna(nome="secao", posicao=16)
	public void setSecao(String secao) {
		this.secao = secao;
	}

	@Coluna(nome="grupo", posicao=17)
	public String getGrupo() {
		return grupo;
	}
	@Coluna(nome="grupo", posicao=17)
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@Coluna(nome="subgrupo", posicao=18)
	public String getSubgrupo() {
		return subgrupo;
	}
	@Coluna(nome="subgrupo", posicao=18)
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}

	@Coluna(nome="status", posicao=19)
	public String getStatus() {
		return status;
	}
	@Coluna(nome="status", posicao=19)
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}