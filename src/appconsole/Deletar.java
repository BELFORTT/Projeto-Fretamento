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
import modelo.Viagem;
import util.Util;


public class Deletar {

	public Deletar(){
		Util.conectar();
		ObjectContainer manager = Util.getManager();
		
		
		Query q = manager.query();
		q.constrain(Viagem.class);
		q.descend("motorista").descend("nome").constrain("Armando");
		List<Viagem> resultados = q.execute();
		
		if(resultados.size() > 0){
			Viagem c = resultados.getFirst();
			System.out.println("localizou a viagem: " + c);
			manager.delete(c);
			manager.commit();
			System.out.println("A viagem com motorista Armando foi deletada");
		}
		else
			System.out.println("Viagem nao localizado");

		Util.desconectar();
		
	}

	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}

