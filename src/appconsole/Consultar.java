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
import modelo.Motorista;
import util.Util;

public class Consultar {

	public Consultar() {
		Util.conectar();
		ObjectContainer manager = Util.getManager();

		Query q;
		List<Veiculo> carros;
		List<Motorista> motoristas;
		List<Viagem> viagens;

		System.out.println("\n1---listar Viagens na data tal:");
		q = manager.query();
		q.constrain(Viagem.class);
		q.descend("data").constrain("20/01/2026");		
		viagens = q.execute();
		for (Viagem v : viagens) {
			System.out.println(v);
		}

/*
		System.out.println("\n3---listar motoristas PB");
		q = manager.query();
		// completar a consulta...
		motoristas = q.execute();
		for (Motorista m : motoristas) {
			System.out.println(m);
		}

		System.out.println("\n4---listar carros que possui motor 1.0");
		q = manager.query();
		// completar a consulta...
		carros = q.execute();
		for (Veiculo car : carros) {
			System.out.println(car);
		}

		System.out.println("\n5---listar carros que possui motorista PB");
		q = manager.query();
		// completar a consulta...
		carros = q.execute();
		for (Veiculo car : carros) {
			System.out.println(car);
		}

		System.out.println("\n6---listar quantidade de carros que possui motorista PB");
		q = manager.query();
		// completar a consulta...
		long quantidade = q.execute().size();
		System.out.println(quantidade);

		
		System.out.println("\n7---listar carros ordenados pelo nome do motorista");
		q = manager.query();
		// completar a consulta...
		carros = q.execute();
		for (Veiculo car : carros) {
			System.out.println(car);
		}
*/
		Util.desconectar();

		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplica��o");
	}

	// =================================================
	public static void main(String[] args) {
		new Consultar();
	}
}
