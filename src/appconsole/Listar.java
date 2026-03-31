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

import modelo.Motorista;
import modelo.Veiculo;
import modelo.Viagem;
import util.Util;

public class Listar {

	public Listar(){
		Util.conectar();
		ObjectContainer manager = Util.getManager();
		
		Query q;
		
		System.out.println("\n---listagem de carros: ");
		q = manager.query();
		q.constrain(Veiculo.class);
		List<Veiculo> carros = q.execute();
		for(Veiculo c: carros){
			System.out.println(c);
		}
		
		System.out.println("\n---listagem de Viagens: ");
		q = manager.query();
		q.constrain(Viagem.class);
		List<Viagem> Viagens = q.execute();
		for(Viagem v: Viagens){
			System.out.println(v);
		}	
		
		
		System.out.println("\n---listagem de motoristas: ");
		q = manager.query();
		q.constrain(Motorista.class);
		List<Motorista> motoristas = q.execute();
		for(Motorista m: motoristas){
			manager.activate(m, 5);
			System.out.println(m);
		}
		
		Util.desconectar();
		
		System.out.println("\n aviso: feche sempre o plugin OME antes de executar aplica��o");
		
	}

	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}

