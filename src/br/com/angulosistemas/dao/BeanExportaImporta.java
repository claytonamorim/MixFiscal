package br.com.angulosistemas.dao;

public interface BeanExportaImporta {
	public String getTableName();
	
	public int getQtdeCampos();
	
	public int getCodigoProduto();
	
	public String createTableQuery();
	
	public String createReplaceQuery();
}
