package appconsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Veiculo;
import util.Util;


public class Alterar {

	public Alterar(){
		Util.conectar();
		ObjectContainer manager = Util.getManager();

		//alterar a potencia do motor da ferrari para 6.0 
		Query q = manager.query();
		q.constrain(Veiculo.class);
		q.descend("ListaViagens");
		List<String> resultados = q.execute();
		
		if(resultados.size() > 0){
			String resul = resultados.getLast();
			resultados.remove(resul);
			System.out.println("Último destino removido");
		}
		else
			System.out.println("Lista de viahgens vazia");
		

		
		Util.desconectar();

		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplica��o");
	}


	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

