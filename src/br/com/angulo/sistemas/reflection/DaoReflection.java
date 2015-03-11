package br.com.angulo.sistemas.reflection;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.angulo.sistemas.Log;
import static br.com.angulo.sistemas.Utilitario.getDouble;
import static br.com.angulo.sistemas.Utilitario.getInteger;

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
				if (metodo.isAnnotationPresent(Coluna.class)){
					Coluna anotacao = metodo.getAnnotation(Coluna.class);
			
					if (metodo.getParameterTypes()[0] == int.class)
						metodo.invoke(objeto, result.getInt(anotacao.posicao()));
					if (metodo.getParameterTypes()[0] == String.class)
						metodo.invoke(objeto,  result.getString(anotacao.posicao()));
					if(metodo.getParameterTypes()[0] == Double.class)
						metodo.invoke(objeto,  result.getDouble(anotacao.posicao()));
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
	}
	
	public static String converterResultsetParaCSV(ResultSet result, Class<?> classe){
		StringBuilder builder = new StringBuilder();
		Coluna anotacao;
	
		try{
			result.beforeFirst();
			while(result.next()){
				for(Method metodo: classe.getDeclaredMethods()){
					if(metodo.isAnnotationPresent(Coluna.class)){
						anotacao = metodo.getAnnotation(Coluna.class);
					
						if(metodo.getReturnType() == int.class)
							builder.append(result.getInt(anotacao.posicao()));
						else if(metodo.getReturnType() == String.class)
							builder.append(result.getString(anotacao.posicao()));
						else if(metodo.getReturnType() == Double.class)
							builder.append(result.getDouble(anotacao.posicao()));
						
						builder.append("|"); //adiciona um pipe para fechar o campo CSV
					}
				}// fim FOR
				
				builder.append(System.getProperty("line.separator"));	//pula para a proxima linha
			
			}//fim WHILE ResultSet
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
		
		return builder.toString();
	}
	
	
	public static boolean verificaAlteracao(Object obj1, Object obj2, Class<?> classe){
		try{
			for(Method metodo: classe.getDeclaredMethods()){
				if(metodo.getReturnType() == Void.class)
					continue;
				
				if(metodo.getReturnType() == String.class)
					if(metodo.invoke(obj1).toString().equalsIgnoreCase(metodo.invoke(obj2).toString()))
						continue;
					else
						return false;
				if(metodo.getReturnType() == int.class)
					if(Integer.parseInt((String)metodo.invoke(obj1)) == Integer.parseInt((String)metodo.invoke(obj2)))
						continue;
					else
						return false;
				if(metodo.getReturnType() == Double.class)
					if(Double.parseDouble((String) metodo.invoke(obj1)) == Double.parseDouble((String) metodo.invoke(obj2)))
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
	
	public static void getItensFromTxt(Object obj, String[] linhaSplited, Class<?> classe){
		try{
			for(Method metodo: classe.getDeclaredMethods()){
				if (metodo.isAnnotationPresent(Coluna.class)){
					Coluna anotacao = metodo.getAnnotation(Coluna.class);
					
					if(metodo.getParameterTypes()[0] == int.class){	//se for encontrado parametro, então trata-se de metodo Setter
						metodo.invoke(obj, getInteger(linhaSplited[anotacao.posicao()]));
						break;
					}
					if(metodo.getParameterTypes()[0] == String.class){	//se for encontrado parametro, então trata-se de metodo Setter
						metodo.invoke(obj, linhaSplited[anotacao.posicao()]);
						break;
					}
					if(metodo.getParameterTypes()[0] == Double.class){	//se for encontrado parametro, então trata-se de metodo Setter
						metodo.invoke(obj, getDouble(linhaSplited[anotacao.posicao()]));
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
	}
	
	
}
