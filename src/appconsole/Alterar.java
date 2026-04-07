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


public class Alterar {

	public Alterar(){
		Util.conectar();
		ObjectContainer manager = Util.getManager();

		Query q = manager.query();
		q.constrain(Viagem.class);
		q.descend("veiculo").descend("placa").constrain("ABC-6E78");
		List<Viagem> resultados = q.execute();
		
		if(resultados.size() > 0){
			Viagem viagem = resultados.get(0);
			Veiculo veiculo = viagem.getVeiculo();
			viagem.setVeiculo(null);
			veiculo.remover(viagem);
			System.out.println("Veiculo removido da viagem: " + viagem.getDestino());
			
			manager.store(viagem);
			manager.store(veiculo);
			manager.commit();
		}
		else
			System.out.println("Veiculo nao encontrado");
		


		Util.desconectar();

		System.out.println("\n\n aviso: feche sempre o plugin OME antes de executar aplica��o");
	}


	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

