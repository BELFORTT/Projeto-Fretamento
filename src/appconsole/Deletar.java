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


public class Deletar {

	public Deletar(){
		Util.conectar();
		ObjectContainer manager = Util.getManager();
		
		//apagar o carro "porche", o seu motor e seu motorista
		Query q = manager.query();
		q.constrain(Veiculo.class);
		q.descend("nome").constrain("porche");
		List<Veiculo> resultados = q.execute();
		
		if(resultados.size() > 0){
			Veiculo c = resultados.getFirst();
			System.out.println("localizou o carro: " + c);
			manager.delete(c);
			manager.commit();
			System.out.println("carro porche, motor e motorista deletados");
		}
		else
			System.out.println("carro nao localizado");

		Util.desconectar();
		
		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplica��o");
	}



	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}

