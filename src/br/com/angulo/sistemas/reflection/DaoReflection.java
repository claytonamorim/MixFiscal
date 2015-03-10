package br.com.angulo.sistemas.reflection;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.angulo.sistemas.Log;
import br.com.angulo.sistemas.bean.Produto;

public final class DaoReflection {
	public static void carregarPreparedStatement(Object objeto, PreparedStatement pstmt, Class<?> classe){
		try{
			for(Method metodo : classe.getDeclaredMethods()){
				if (metodo.isAnnotationPresent(Coluna.class)){ 
					//System.out.println(metodo.getName());
					Coluna anotacao = metodo.getAnnotation(Coluna.class);
					if(metodo.getReturnType() == int.class){
						//System.out.println("Tipo inteiro=" + metodo.invoke(objeto) + "Posicao=" + anotacao.posicao());
						pstmt.setInt(anotacao.posicao() + 1, (int) metodo.invoke(objeto));	//posicao acresentada mais 1 pois PreparedStatement começa do 1, não do 0
					}
					if(metodo.getReturnType() == String.class){
						//System.out.println("Tipo String=" + metodo.invoke(objeto) +  "Posicao=" +anotacao.posicao());
						pstmt.setString(anotacao.posicao() + 1, (String) metodo.invoke(objeto));	//posicao acresentada mais 1 pois PreparedStatement começa do 1, não do 0
					}
					if(metodo.getReturnType() == Double.class){
						//System.out.println("Tipo Double=" + metodo.invoke(objeto) + "Posicao=" + anotacao.posicao());
						pstmt.setDouble(anotacao.posicao() + 1, (Double) metodo.invoke(objeto));	//posicao acresentada mais 1 pois PreparedStatement começa do 1, não do 0
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
	}
	
	
	public static void carregarDoResultSet(Object objeto, ResultSet result, Class<?> classe){
		//Carrega o Object vazio com dados da ResultSet
		try{
			for(Method metodo: classe.getDeclaredMethods()){
				Coluna anotacao = metodo.getAnnotation(Coluna.class);
			
				if (metodo.getParameterTypes()[0] == Integer.class)
					metodo.invoke(objeto, result.getInt(anotacao.posicao()));
				if (metodo.getParameterTypes()[0] == String.class)
					metodo.invoke(objeto,  result.getString(anotacao.posicao()));
				if(metodo.getParameterTypes()[0] == Double.class)
					metodo.invoke(objeto,  result.getDouble(anotacao.posicao()));
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
		}
	}
	
	
	public static boolean verificaAlteracao(Object b1, Object b2, Class<?> classe){
		try{
			for(Method metodo: classe.getDeclaredMethods()){
				if(metodo.getReturnType() == Void.class)
					continue;
				
				if(metodo.getReturnType() == String.class)
					if(metodo.invoke(b1).toString().equalsIgnoreCase(metodo.invoke(b2).toString()))
						continue;
					else
						return false;
				if(metodo.getReturnType() == Integer.class)
					if(Integer.parseInt((String)metodo.invoke(b1)) == Integer.parseInt((String)metodo.invoke(b2)))
						continue;
					else
						return false;
				if(metodo.getReturnType() == Double.class)
					if(Double.parseDouble((String) metodo.invoke(b1)) == Double.parseDouble((String) metodo.invoke(b2)))
						continue;
					else
						return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
		
		return true;	//caso todas as comparações sejam verdadeiras então trata-se do mesmo registro
	}
	
	
}
