package appconsole;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import com.db4o.ObjectContainer;

import modelo.Veiculo;
import modelo.Viagem;
import modelo.Motorista;
import util.Util;

public class Cadastrar {

	public Cadastrar() {
		Util.conectar();
		ObjectContainer manager = Util.getManager();

		System.out.println("cadastrando...");
		Viagem viagem;
		Motorista motorista;
		Veiculo veiculo;

		veiculo = new Veiculo("OFD-7F68", 16);
		motorista = new Motorista("00001", "Armando");
		viagem = new Viagem("20/01/2026", "Maceió", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("robson");
		viagem.addPassageiros("maria");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();

		//veiculo = new Veiculo("UWU-7F38", 40);
		motorista = new Motorista("00002", "Aureliano José");
		viagem = new Viagem("20/01/2026", "Juazeiro", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("marcos");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();

		veiculo = new Veiculo("GAI-2A68", 33);
		//motorista = new Motorista("789565", "José Arcadio Buendia");
		viagem = new Viagem("21/10/2025", "Cabrobó", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("Amadeus");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();

		veiculo = new Veiculo("JFK-7D63", 25);
		motorista = new Motorista("00004", "Amaranta");
		viagem = new Viagem("24/03/2024", "Petrolina", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("Juquinha");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("OFD-7F68", 16);
		motorista = new Motorista("00005", "Caue");
		viagem = new Viagem("10/03/2026", "Natal", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("Kleber");
		viagem.addPassageiros("Maria");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("ABC-6E78", 20);
		motorista = new Motorista("00006", "Victor");
		viagem = new Viagem("28/01/2026", "Gramado", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("Julia");
		viagem.addPassageiros("Matheus");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("ABC-6E78", 20);
		motorista = new Motorista("00007", "Armando");
		viagem = new Viagem("28/01/2026", "São Paulo", veiculo, motorista);
		motorista.novaViagem(viagem.getDestino());
		veiculo.novaViagem(viagem.getDestino());
		viagem.addPassageiros("Yasmin");
		viagem.addPassageiros("João");
		manager.store(veiculo);
		manager.store(motorista);
		manager.store(viagem);
		manager.commit();

		Util.desconectar();

		System.out.println("fim do programa");
	}

	public void cadastrar() {
	}

	// =================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}
