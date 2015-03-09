package br.com.angulo.sistemas.bean;

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

	public int getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(int codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public int getEan() {
		return ean;
	}

	public void setEan(int ean) {
		this.ean = ean;
	}

	public String getDescritivo_produto() {
		return descritivo_produto;
	}

	public void setDescritivo_produto(String descritivo_produto) {
		this.descritivo_produto = descritivo_produto;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getNcm_ex() {
		return ncm_ex;
	}

	public void setNcm_ex(String ncm_ex) {
		this.ncm_ex = ncm_ex;
	}

	public int getCod_natureza_receita() {
		return cod_natureza_receita;
	}

	public void setCod_natureza_receita(int cod_natureza_receita) {
		this.cod_natureza_receita = cod_natureza_receita;
	}

	public int getCredito_presumido() {
		return credito_presumido;
	}

	public void setCredito_presumido(int credito_presumido) {
		this.credito_presumido = credito_presumido;
	}

	public String getPis_cst_e() {
		return pis_cst_e;
	}

	public void setPis_cst_e(String pis_cst_e) {
		this.pis_cst_e = pis_cst_e;
	}

	public String getPis_cst_s() {
		return pis_cst_s;
	}

	public void setPis_cst_s(String pis_cst_s) {
		this.pis_cst_s = pis_cst_s;
	}

	public double getPis_alq_e() {
		return pis_alq_e;
	}

	public void setPis_alq_e(double pis_alq_e) {
		this.pis_alq_e = pis_alq_e;
	}

	public double getPis_alq_s() {
		return pis_alq_s;
	}

	public void setPis_alq_s(double pis_alq_s) {
		this.pis_alq_s = pis_alq_s;
	}

	public String getCofins_cst_e() {
		return cofins_cst_e;
	}

	public void setCofins_cst_e(String cofins_cst_e) {
		this.cofins_cst_e = cofins_cst_e;
	}

	public String getCofins_cst_s() {
		return cofins_cst_s;
	}

	public void setCofins_cst_s(String cofins_cst_s) {
		this.cofins_cst_s = cofins_cst_s;
	}

	public double getCofins_alq_e() {
		return cofins_alq_e;
	}

	public void setCofins_alq_e(double cofins_alq_e) {
		this.cofins_alq_e = cofins_alq_e;
	}

	public double getCofins_alq_s() {
		return cofins_alq_s;
	}

	public void setCofins_alq_s(double cofins_alq_s) {
		this.cofins_alq_s = cofins_alq_s;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getSecao() {
		return secao;
	}

	public void setSecao(String secao) {
		this.secao = secao;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}