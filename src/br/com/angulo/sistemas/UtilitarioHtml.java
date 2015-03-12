package br.com.angulo.sistemas;

import java.util.List;

//import br.com.angulo.sistemas.bean.Caixa;
import br.com.angulo.sistemas.bean.Produto;
//import br.com.angulo.sistemas.bean.Venda;

public class UtilitarioHtml {
	public static String montarTabelaProdutos(List<Produto> produtos){
		StringBuilder builder = new StringBuilder();
		int countAtualizados = produtos.size();
		
		if (countAtualizados > 0)
			builder.append("<h3>" + countAtualizados + " REGISTRO(S) ATUALIZADO(S) </h3>");
		else{
			builder.append("<h3> NÃO FOI ATUALIZADO NENHUM REGISTRO! </h3>");
			return builder.toString();	//sai do fluxo, pois não desenhará nenhuma tabela
		}
		builder.append("<table border=1>");
		builder.append("<tr>");
		//builder.append("<b>");	//deixa os títulos em negrito
		builder.append("<td align=center><b>CODIGO DE BARRA</b></td>");
		builder.append("<td align=center><b>DESCRIÇÃO</b></td>");
		builder.append("<td align=center><b>FORNECEDOR</b></td>");
		builder.append("<td align=center><b>PCUSTO</b></td>");
		builder.append("<td align=center><b>MARGEM</b></td>");
		builder.append("<td align=center><b>PVENDA</b></td>");
		builder.append("<td align=center><b>ESTOQUE</b></td>");
		//builder.append("</b>");
		builder.append("</tr>");
		builder.append(System.getProperty("line.separator"));	//pula para a proxima linha, apenas para organizar melhor o codigo HTML

		for(Produto p: produtos){
			builder.append("<tr>");
			builder.append("<td>" + p.getCodBarra() + "</td>");
			builder.append("<td>" + p.getDescricao() + "</td>");
			builder.append("<td>" + p.getFornecedor() + "</td>");
			builder.append("<td>" + p.getPCusto() + "</td>");
			builder.append("<td>" + p.getMargem() + "</td>");
			builder.append("<td>" + p.getPVenda() + "</td>");
			builder.append("<td>" + p.getEstoque() + "</td>");
			builder.append("</tr>");

			builder.append(System.getProperty("line.separator"));	//pula para a proxima linha, apenas para organizar melhor o codigo HTML
		}
		builder.append("</table>");
		//builder.append("</html>");
		return builder.toString();
	}
	
	/*
	public static String montarTabelaCaixa(List<Caixa> caixas){
		return null;
	}
	
	public static String montarTabelaVendas(List<Venda> vendas){
		return null;
	}
	
	public static String montarTodasTabelas(List<Produto> produtos, List<Caixa> caixas, List<Venda> vendas){
		StringBuilder builder = new StringBuilder();
		builder.append(montarTabelaProdutos(produtos));
		builder.append(montarTabelaCaixa(caixas));
		builder.append(montarTabelaVendas(vendas));
		
		return builder.toString();
	}
	*/
	
}
