package controller;

import java.util.List;
import model.Veiculo;
import repositorio.Repositorio;
import repositorio.RepositorioVeiculo;

public class ControllerVeiculo {
	private ControllerVeiculo() {}

	private static RepositorioVeiculo repVeiculo = new RepositorioVeiculo();

	// ==========================================
	// LOCALIZAR VEICULO
	// ==========================================
	public static Veiculo localizarVeiculo(String placa) throws Exception {
		try {
			Repositorio.conectar();
			Veiculo v = repVeiculo.localizar(placa); // Busca pela Placa (Chave Primaria)
			return v;
		} catch (Exception e) {
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// CRIAR VEICULO
	// ==========================================
	public static void criarVeiculo(String placa, int capacity) throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();

			if (placa == null || placa.isBlank())
				throw new Exception("Criar veiculo - A placa e obrigatoria.");
			
			if (capacity <= 0)
				throw new Exception("Criar veiculo - A capacidade deve ser maior que zero.");

			Veiculo v = repVeiculo.localizar(placa);
			if (v != null) 
				throw new Exception("Criar veiculo - Ja existe um veiculo cadastrado com esta placa: " + placa);
			
			v = new Veiculo();
			v.setPlaca(placa);
			v.setCapacidade(capacity);
			
			repVeiculo.criar(v);
			Repositorio.commit();

		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// ALTERAR VEICULO
	// ==========================================
	public static void alterarVeiculo(String placa, int novaCapacidade) throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();
			
			Veiculo v = repVeiculo.localizar(placa);
			if (v == null)
				throw new Exception("Alterar veiculo - Veiculo nao encontrado com a placa: " + placa);

			if (novaCapacidade <= 0)
				throw new Exception("Alterar veiculo - A nova capacidade deve ser maior que zero.");

			v.setCapacidade(novaCapacidade);

			repVeiculo.atualizar(v); 
			Repositorio.commit();

		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// APAGAR VEICULO
	// ==========================================
	public static void apagarVeiculo(String placa) throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();
			
			Veiculo v = repVeiculo.localizar(placa);
			if (v == null)
				throw new Exception("Excluir veiculo - Veiculo inexistente com a placa: " + placa);
			
			repVeiculo.deletar(v);   
			Repositorio.commit();
			
		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// LISTAGENS E FILTROS
	// ==========================================
	public static List<Veiculo> listarVeiculos() {
		Repositorio.conectar();
		List<Veiculo> lista = repVeiculo.listar();
		Repositorio.desconectar();
		return lista;
	}
	
	// Buscar um unico veiculo pela placa (OTIMIZADO)
	public static Veiculo buscarVeiculoPorPlaca(String placa) {
		try {
			Repositorio.conectar();
			// Reutiliza o metodo de buscar pela chave primaria direto no EntityManager
			return repVeiculo.localizar(placa);
		} catch (Exception e) {
			return null;
		} finally {
			Repositorio.desconectar();
		}
	}
}