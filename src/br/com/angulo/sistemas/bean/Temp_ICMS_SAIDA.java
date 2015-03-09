package br.com.angulo.sistemas.bean;

public class Temp_ICMS_SAIDA {
	public static final String TABELA_TEMP_ICMS_SAIDA = "MXF_TMP_ICMS_SAIDA";
	
	private int codigo_produto;
	private int ean;
	private String sac_cst;
	private double sac_alq;
	private double sac_alqst;
	private double sac_rbc;
	private double sac_rbcst;
	private String sas_cst;
	private double sas_alq;
	private double sas_alqst;
	private double sas_rbc;
	private double sas_rbcst;
	private String svc_cst;
	private double svc_alq;
	private double svc_alqst;
	private double svc_rbc;
	private double svc_rbcst;
	private String snc_cst;
	private double snc_alq;
	private double snc_alqst;
	private double snc_rbc;
	private double snc_rbcst;
	private String fundamento_legal;
	
	public Temp_ICMS_SAIDA(){}

	public Temp_ICMS_SAIDA(int codigo_produto, int ean, String sac_cst,
			double sac_alq, double sac_alqst, double sac_rbc, double sac_rbcst,
			String sas_cst, double sas_alq, double sas_alqst, double sas_rbc,
			double sas_rbcst, String svc_cst, double svc_alq, double svc_alqst,
			double svc_rbc, double svc_rbcst, String snc_cst, double snc_alq,
			double snc_alqst, double snc_rbc, double snc_rbcst,
			String fundamento_legal) {
		super();
		this.codigo_produto = codigo_produto;
		this.ean = ean;
		this.sac_cst = sac_cst;
		this.sac_alq = sac_alq;
		this.sac_alqst = sac_alqst;
		this.sac_rbc = sac_rbc;
		this.sac_rbcst = sac_rbcst;
		this.sas_cst = sas_cst;
		this.sas_alq = sas_alq;
		this.sas_alqst = sas_alqst;
		this.sas_rbc = sas_rbc;
		this.sas_rbcst = sas_rbcst;
		this.svc_cst = svc_cst;
		this.svc_alq = svc_alq;
		this.svc_alqst = svc_alqst;
		this.svc_rbc = svc_rbc;
		this.svc_rbcst = svc_rbcst;
		this.snc_cst = snc_cst;
		this.snc_alq = snc_alq;
		this.snc_alqst = snc_alqst;
		this.snc_rbc = snc_rbc;
		this.snc_rbcst = snc_rbcst;
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

	public String getSac_cst() {
		return sac_cst;
	}

	public void setSac_cst(String sac_cst) {
		this.sac_cst = sac_cst;
	}

	public double getSac_alq() {
		return sac_alq;
	}

	public void setSac_alq(double sac_alq) {
		this.sac_alq = sac_alq;
	}

	public double getSac_alqst() {
		return sac_alqst;
	}

	public void setSac_alqst(double sac_alqst) {
		this.sac_alqst = sac_alqst;
	}

	public double getSac_rbc() {
		return sac_rbc;
	}

	public void setSac_rbc(double sac_rbc) {
		this.sac_rbc = sac_rbc;
	}

	public double getSac_rbcst() {
		return sac_rbcst;
	}

	public void setSac_rbcst(double sac_rbcst) {
		this.sac_rbcst = sac_rbcst;
	}

	public String getSas_cst() {
		return sas_cst;
	}

	public void setSas_cst(String sas_cst) {
		this.sas_cst = sas_cst;
	}

	public double getSas_alq() {
		return sas_alq;
	}

	public void setSas_alq(double sas_alq) {
		this.sas_alq = sas_alq;
	}

	public double getSas_alqst() {
		return sas_alqst;
	}

	public void setSas_alqst(double sas_alqst) {
		this.sas_alqst = sas_alqst;
	}

	public double getSas_rbc() {
		return sas_rbc;
	}

	public void setSas_rbc(double sas_rbc) {
		this.sas_rbc = sas_rbc;
	}

	public double getSas_rbcst() {
		return sas_rbcst;
	}

	public void setSas_rbcst(double sas_rbcst) {
		this.sas_rbcst = sas_rbcst;
	}

	public String getSvc_cst() {
		return svc_cst;
	}

	public void setSvc_cst(String svc_cst) {
		this.svc_cst = svc_cst;
	}

	public double getSvc_alq() {
		return svc_alq;
	}

	public void setSvc_alq(double svc_alq) {
		this.svc_alq = svc_alq;
	}

	public double getSvc_alqst() {
		return svc_alqst;
	}

	public void setSvc_alqst(double svc_alqst) {
		this.svc_alqst = svc_alqst;
	}

	public double getSvc_rbc() {
		return svc_rbc;
	}

	public void setSvc_rbc(double svc_rbc) {
		this.svc_rbc = svc_rbc;
	}

	public double getSvc_rbcst() {
		return svc_rbcst;
	}

	public void setSvc_rbcst(double svc_rbcst) {
		this.svc_rbcst = svc_rbcst;
	}

	public String getSnc_cst() {
		return snc_cst;
	}

	public void setSnc_cst(String snc_cst) {
		this.snc_cst = snc_cst;
	}

	public double getSnc_alq() {
		return snc_alq;
	}

	public void setSnc_alq(double snc_alq) {
		this.snc_alq = snc_alq;
	}

	public double getSnc_alqst() {
		return snc_alqst;
	}

	public void setSnc_alqst(double snc_alqst) {
		this.snc_alqst = snc_alqst;
	}

	public double getSnc_rbc() {
		return snc_rbc;
	}

	public void setSnc_rbc(double snc_rbc) {
		this.snc_rbc = snc_rbc;
	}

	public double getSnc_rbcst() {
		return snc_rbcst;
	}

	public void setSnc_rbcst(double snc_rbcst) {
		this.snc_rbcst = snc_rbcst;
	}

	public String getFundamento_legal() {
		return fundamento_legal;
	}

	public void setFundamento_legal(String fundamento_legal) {
		this.fundamento_legal = fundamento_legal;
	};
	
	
	
}
