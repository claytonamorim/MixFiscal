package br.com.angulo.sistemas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Utilitario {

	//este método lê o arquivo um arquivo txt
	public static String lerArquivoTxt(String filename) throws IOException{
		File file = new File(filename);
		FileReader reader;
		String bancoTxt = null;

		StringBuilder builder = new StringBuilder();
		reader = new FileReader(file);
		BufferedReader input = new BufferedReader(reader);
		String linha;
		while ((linha = input.readLine()) != null){
			linha = linha.trim();	//utilizando método trim() para retirar espaços do arquivo de configuracao
			builder.append(linha);
			builder.append(System.getProperty("line.separator"));
		}
		input.close();
		bancoTxt = builder.toString();

		return bancoTxt;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//método para separar as linhas do angulo.txt, que são distribuídas em um Array tipo String
	public static String[] splitTxt(String bancoTxt){
		String bancoSplited[] = bancoTxt.split("\\r\\n|\\r|\\n");
		return  bancoSplited;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//método para separar os campos das linhas, que são distribuídos em um Array tipo String
	public static String[] splitTab(String linha){
		String linhaSplited[] = linha.split("\\t");
		return linhaSplited;
	}

	public static String[] splitTab(String linha, int qtd){
		String[] linhaSplitedCompleta = new String[qtd];
		String[] linhaSplited = linha.split("\\t",qtd);
		for(int i = 0; i<linhaSplitedCompleta.length; i++){
			if(i<linhaSplited.length ){
				linhaSplitedCompleta[i] = linhaSplited[i];	
			}else
				linhaSplitedCompleta[i] = null;
		}
		return linhaSplitedCompleta;
	}
	/////////////////////////////////////////////////////////////////////////////
	//método para testar se uma variável é numérica ou não
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// apenas chega aqui se o 'if' não retornar 'false'
		return true;
	}

	public static int getInteger(String s){
		try{
			return Integer.parseInt(s);
		}	catch(Exception e){
			return 0;
		}
	}

	public static Double getDouble(String s){
		try{
			return Double.parseDouble(s);
		}catch(Exception e){
			return 0.0;
		}
	}

	public static void delay(long time){
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	
}
