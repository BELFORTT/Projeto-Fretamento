package appconsole;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Motorista;
import modelo.Veiculo;
import modelo.Viagem;
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

		System.out.println("\n1---listar Viagens com veiculo de placa tal:");
		q = manager.query();
		q.constrain(Viagem.class);
		q.descend("veiculo").descend("placa").constrain("GAI-2A68");
		viagens = q.execute();
		for (Viagem v : viagens) {
			System.out.println(v);
		}

		System.out.println("quais os motoristas que tem mais de N viagens com destino X");
		q = manager.query();
		q.constrain(Motorista.class);
		q.constrain(new Filtro(1));
		List<Motorista> resultados = q.execute();
		System.out.println(resultados);


		
		Util.desconectar();

		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplica��o");
		
	}

	// =================================================
	public static void main(String[] args) {
		new Consultar();
	}
}



class Filtro implements Evaluation {
	private int n;

	public Filtro(int n) {
		this.n = n;
	}

	public void evaluate(Candidate candidate) {
		Motorista p = (Motorista) candidate.getObject();
		if (p.getListaViagens().size() == this.n)

			candidate.include(true);

		else

			candidate.include(false);

	}
	
}
