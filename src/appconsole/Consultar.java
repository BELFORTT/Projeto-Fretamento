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
		
		List<Viagem> viagens;

		System.out.println("\nlistar Viagens na data X:");
		q = manager.query();
		q.constrain(Viagem.class);
		q.descend("data").constrain("20/01/2026");
		viagens = q.execute();
		for (Viagem v : viagens) {
			System.out.println(v);
		}

		System.out.println("\nlistar Viagens com veiculo de placa X:");
		q = manager.query();
		q.constrain(Viagem.class);
		q.descend("veiculo").descend("placa").constrain("JFK-7D63");
		viagens = q.execute();
		for (Viagem v : viagens) {
			System.out.println(v);
		}

		System.out.println("quais os motoristas que tem mais de N viagens com destino X");
		q = manager.query();
		q.constrain(Motorista.class);
		q.constrain(new Filtro(1, "Juazeiro"));
		List<Motorista> resultados = q.execute();
		for (Motorista m : resultados) {
			System.out.println(m);
		}

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
    private String destino;

    public Filtro(int n, String destino) {
        this.n = n;
        this.destino = destino;
    }

    public void evaluate(Candidate candidate) {
        Motorista m = (Motorista) candidate.getObject();
        
        int contador = 0;
        
        for (Viagem d : m.getListaViagens()) {
            if (d.getDestino().equals(this.destino)) { 
                contador += 1;
            }
        }
        
        if (contador > this.n) {
            candidate.include(true);
        } else {
            candidate.include(false);
        }
    }
}
