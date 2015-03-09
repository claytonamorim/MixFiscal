package br.com.angulo.sistemas.bean;

import java.sql.Date;


public class Temp_ICMS_ENTRADA {
	public static final String TABELA_TEMP_ICMS_ENTRADA = "MXF_TMP_ICMS_ENTRADA";
	
	private int codigo_produto;
	private int ean;
	private String tipo_mva;
	private double mva;
	private Date mva_data_ini;
	private Date mva_data_fim;
	private int credito_outorgado;
	private int gera_debito;
	private int sub_rbc_alq;
	private String ei_cst;
	private double ei_alq;
	private double ei_alqst;
	private double ei_rbc;
	private double ei_rbcst;
	private String ed_cst;
	private double ed_alq;
	private double ed_alqst;
	private double ed_rbc;
	private double ed_rbcst;
	private String es_cst;
	private double es_alq;
	private double es_alqst;
	private double es_rbc;
	private double es_rbcst;
	private String nfi_cst;
	private String nfd_cst;
	private String nfs_csosn;
	private double nf_alq;
	private String fundamento_legal;
	
	public Temp_ICMS_ENTRADA(){}

	public Temp_ICMS_ENTRADA(int codigo_produto, int ean, String tipo_mva,
			double mva, Date mva_data_ini, Date mva_data_fim,
			int credito_outorgado, int gera_debito, int sub_rbc_alq,
			String ei_cst, double ei_alq, double ei_alqst, double ei_rbc,
			double ei_rbcst, String ed_cst, double ed_alq, double ed_alqst,
			double ed_rbc, double ed_rbcst, String es_cst, double es_alq,
			double es_alqst, double es_rbc, double es_rbcst, String nfi_cst,
			String nfd_cst, String nfs_csosn, double nf_alq,
			String fundamento_legal) {
		super();
		this.codigo_produto = codigo_produto;
		this.ean = ean;
		this.tipo_mva = tipo_mva;
		this.mva = mva;
		this.mva_data_ini = mva_data_ini;
		this.mva_data_fim = mva_data_fim;
		this.credito_outorgado = credito_outorgado;
		this.gera_debito = gera_debito;
		this.sub_rbc_alq = sub_rbc_alq;
		this.ei_cst = ei_cst;
		this.ei_alq = ei_alq;
		this.ei_alqst = ei_alqst;
		this.ei_rbc = ei_rbc;
		this.ei_rbcst = ei_rbcst;
		this.ed_cst = ed_cst;
		this.ed_alq = ed_alq;
		this.ed_alqst = ed_alqst;
		this.ed_rbc = ed_rbc;
		this.ed_rbcst = ed_rbcst;
		this.es_cst = es_cst;
		this.es_alq = es_alq;
		this.es_alqst = es_alqst;
		this.es_rbc = es_rbc;
		this.es_rbcst = es_rbcst;
		this.nfi_cst = nfi_cst;
		this.nfd_cst = nfd_cst;
		this.nfs_csosn = nfs_csosn;
		this.nf_alq = nf_alq;
		this.fundamento_legal = fundamento_legal;
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

	public String getTipo_mva() {
		return tipo_mva;
	}

	public void setTipo_mva(String tipo_mva) {
		this.tipo_mva = tipo_mva;
	}

	public double getMva() {
		return mva;
	}

	public void setMva(double mva) {
		this.mva = mva;
	}

	public Date getMva_data_ini() {
		return mva_data_ini;
	}

	public void setMva_data_ini(Date mva_data_ini) {
		this.mva_data_ini = mva_data_ini;
	}

	public Date getMva_data_fim() {
		return mva_data_fim;
	}

	public void setMva_data_fim(Date mva_data_fim) {
		this.mva_data_fim = mva_data_fim;
	}

	public int getCredito_outorgado() {
		return credito_outorgado;
	}

	public void setCredito_outorgado(int credito_outorgado) {
		this.credito_outorgado = credito_outorgado;
	}

	public int getGera_debito() {
		return gera_debito;
	}

	public void setGera_debito(int gera_debito) {
		this.gera_debito = gera_debito;
	}

	public int getSub_rbc_alq() {
		return sub_rbc_alq;
	}

	public void setSub_rbc_alq(int sub_rbc_alq) {
		this.sub_rbc_alq = sub_rbc_alq;
	}

	public String getEi_cst() {
		return ei_cst;
	}

	public void setEi_cst(String ei_cst) {
		this.ei_cst = ei_cst;
	}

	public double getEi_alq() {
		return ei_alq;
	}

	public void setEi_alq(double ei_alq) {
		this.ei_alq = ei_alq;
	}

	public double getEi_alqst() {
		return ei_alqst;
	}

	public void setEi_alqst(double ei_alqst) {
		this.ei_alqst = ei_alqst;
	}

	public double getEi_rbc() {
		return ei_rbc;
	}

	public void setEi_rbc(double ei_rbc) {
		this.ei_rbc = ei_rbc;
	}

	public double getEi_rbcst() {
		return ei_rbcst;
	}

	public void setEi_rbcst(double ei_rbcst) {
		this.ei_rbcst = ei_rbcst;
	}

	public String getEd_cst() {
		return ed_cst;
	}

	public void setEd_cst(String ed_cst) {
		this.ed_cst = ed_cst;
	}

	public double getEd_alq() {
		return ed_alq;
	}

	public void setEd_alq(double ed_alq) {
		this.ed_alq = ed_alq;
	}

	public double getEd_alqst() {
		return ed_alqst;
	}

	public void setEd_alqst(double ed_alqst) {
		this.ed_alqst = ed_alqst;
	}

	public double getEd_rbc() {
		return ed_rbc;
	}

	public void setEd_rbc(double ed_rbc) {
		this.ed_rbc = ed_rbc;
	}

	public double getEd_rbcst() {
		return ed_rbcst;
	}

	public void setEd_rbcst(double ed_rbcst) {
		this.ed_rbcst = ed_rbcst;
	}

	public String getEs_cst() {
		return es_cst;
	}

	public void setEs_cst(String es_cst) {
		this.es_cst = es_cst;
	}

	public double getEs_alq() {
		return es_alq;
	}

	public void setEs_alq(double es_alq) {
		this.es_alq = es_alq;
	}

	public double getEs_alqst() {
		return es_alqst;
	}

	public void setEs_alqst(double es_alqst) {
		this.es_alqst = es_alqst;
	}

	public double getEs_rbc() {
		return es_rbc;
	}

	public void setEs_rbc(double es_rbc) {
		this.es_rbc = es_rbc;
	}

	public double getEs_rbcst() {
		return es_rbcst;
	}

	public void setEs_rbcst(double es_rbcst) {
		this.es_rbcst = es_rbcst;
	}

	public String getNfi_cst() {
		return nfi_cst;
	}

	public void setNfi_cst(String nfi_cst) {
		this.nfi_cst = nfi_cst;
	}

	public String getNfd_cst() {
		return nfd_cst;
	}

	public void setNfd_cst(String nfd_cst) {
		this.nfd_cst = nfd_cst;
	}

	public String getNfs_csosn() {
		return nfs_csosn;
	}

	public void setNfs_csosn(String nfs_csosn) {
		this.nfs_csosn = nfs_csosn;
	}

	public double getNf_alq() {
		return nf_alq;
	}

	public void setNf_alq(double nf_alq) {
		this.nf_alq = nf_alq;
	}

	public String getFundamento_legal() {
		return fundamento_legal;
	}

	public void setFundamento_legal(String fundamento_legal) {
		this.fundamento_legal = fundamento_legal;
	}
	
	
	
}
