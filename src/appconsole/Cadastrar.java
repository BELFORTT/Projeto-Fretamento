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
		Veiculo veiculo;

		veiculo = new Veiculo("OFD-7F68", 16);
		Motorista motorista1 = new Motorista("00001", "Armando");
		viagem = new Viagem("20/01/2026", "Maceió", veiculo, motorista1);
		motorista1.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("robson");
		viagem.addPassageiros("maria");
		manager.store(veiculo);
		manager.store(motorista1);
		manager.store(viagem);
		manager.commit();

		//veiculo = new Veiculo("UWU-7F38", 40);
		Motorista motorista2 = new Motorista("00002", "Aureliano José");
		viagem = new Viagem("20/01/2026", "Juazeiro", veiculo, motorista2);
		motorista2.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("marcos");
		manager.store(veiculo);
		manager.store(motorista2);
		manager.store(viagem);
		manager.commit();

		veiculo = new Veiculo("GAI-2A68", 33);
		viagem = new Viagem("21/10/2025", "Cabrobó", veiculo, motorista2);
		motorista2.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Amadeus");
		manager.store(veiculo);
		manager.store(motorista2);
		manager.store(viagem);
		manager.commit();

		veiculo = new Veiculo("JFK-7D63", 25);
		Motorista motorista3= new Motorista("00003", "Amaranta");
		viagem = new Viagem("24/03/2024", "Petrolina", veiculo, motorista3);
		motorista3.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Juquinha");
		manager.store(veiculo);
		manager.store(motorista3);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("LIC-7C38", 16);
		Motorista motorista4 = new Motorista("00004", "Caue");
		viagem = new Viagem("10/03/2026", "Natal", veiculo, motorista4);
		motorista4.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Kleber");
		viagem.addPassageiros("Maria");
		manager.store(veiculo);
		manager.store(motorista4);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("ABC-6E78", 20);
		Motorista motorista5 = new Motorista("00005", "Victor");
		viagem = new Viagem("28/01/2026", "Gramado", veiculo, motorista5);
		motorista5.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Julia");
		viagem.addPassageiros("Matheus");
		manager.store(veiculo);
		manager.store(motorista5);
		manager.store(viagem);
		manager.commit();
		
		viagem = new Viagem("28/02/2026", "Maceió", veiculo, motorista2);
		motorista2.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Yasmin");
		viagem.addPassageiros("João");
		manager.store(veiculo);
		manager.store(motorista2);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("BBB-7F58", 10);
		viagem = new Viagem("20/03/2026", "Rio de Janeiro", veiculo, motorista1);
		motorista1.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Joao");
		viagem.addPassageiros("Maria");
		viagem.addPassageiros("Robson");
		viagem.addPassageiros("Marta");
		manager.store(veiculo);
		manager.store(motorista1);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("CCC-5G20", 10);
		viagem = new Viagem("20/03/2026", "Maceió", veiculo, motorista1);
		motorista1.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Joana");
		viagem.addPassageiros("Maria");
		manager.store(veiculo);
		manager.store(motorista1);
		manager.store(viagem);
		manager.commit();
		
		veiculo = new Veiculo("AAA-3F50", 7);
		viagem = new Viagem("20/03/2026", "Juazeiro", veiculo, motorista2);
		motorista2.novaViagem(viagem);
		veiculo.novaViagem(viagem);
		viagem.addPassageiros("Joana");
		viagem.addPassageiros("Maria");
		manager.store(veiculo);
		manager.store(motorista2);
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
