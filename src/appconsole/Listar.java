/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package appconsole;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Motorista;
import model.Veiculo;
import model.Viagem;
import util.Util;

public class Listar {

	public Listar() {
		try {
			Util.conectar();
			EntityManager manager = Util.getManager();
			
			System.out.println("\n--- Listagem de viagens ---");
			TypedQuery<Viagem> query1 = manager.createQuery("select v from Viagem v", Viagem.class);
			List<Viagem> resultados1 = query1.getResultList();
			for (Viagem v : resultados1) {
				System.out.println(v);
			}

			System.out.println("\n--- Listagem de motoristas ---");
			TypedQuery<Motorista> query2 = manager.createQuery("select m from Motorista m", Motorista.class); 
			List<Motorista> resultados2 = query2.getResultList();
			for (Motorista m : resultados2) {
				System.out.println(m);
			}

			System.out.println("\n--- Listagem de veiculos ---");
			TypedQuery<Veiculo> query3 = manager.createQuery("select v from Veiculo v", Veiculo.class); 
			List<Veiculo> resultados3 = query3.getResultList();
			for (Veiculo v : resultados3) {
				System.out.println(v);
			}

			System.out.println("\n--- Listagem das viagens de cada veiculo ---");
			// Buscamos os veículos para varrer suas respectivas listas de viagens
			TypedQuery<Veiculo> query4 = manager.createQuery("select v from Veiculo v", Veiculo.class);
			List<Veiculo> resultados4 = query4.getResultList();
			for (Veiculo vc : resultados4) {
				System.out.println("\nVeiculo: " + vc.getPlaca() + " (Capacidade: " + vc.getCapacidade() + ")");
				if (vc.getListaViagem().isEmpty()) {
					System.out.println("  -> Nenhuma viagem cadastrada para este veiculo.");
				} else {
					for (Viagem vg : vc.getListaViagem()) {
						System.out.println("  -> ID: " + vg.getId() + " | Destino: " + vg.getDestino() + " | Data: " + vg.getData());
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
			
		Util.desconectar();
		System.out.println("\nfim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Listar();
	}
}