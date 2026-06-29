package controller;

/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import model.Motorista;
import model.Veiculo;
import model.Viagem;
import repositorio.Repositorio;
import repositorio.RepositorioViagem;
import repositorio.RepositorioMotorista;
import repositorio.RepositorioVeiculo;

public class ControllerViagem {
	private ControllerViagem() {
	}

	private static RepositorioViagem repViagem = new RepositorioViagem();
	private static RepositorioMotorista repMotorista = new RepositorioMotorista();
	private static RepositorioVeiculo repVeiculo = new RepositorioVeiculo();

	// ==========================================
	// LOCALIZAR VIAGEM
	// ==========================================
	public static Viagem localizarViagem(int id) throws Exception {
		try {
			Repositorio.conectar();
			return repViagem.localizar(id);
		} catch (Exception e) {
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// CRIAR VIAGEM (Ajustado para receber Strings)
	// ==========================================
	public static void criarViagem(LocalDate dataFormatada, String destino, String cnh, String placa) throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();

			// Busca os objetos acoplados ao EntityManager atual do repositorio
			Motorista mot = repMotorista.localizar(cnh); 
			Veiculo vei = repVeiculo.localizar(placa);
			
			if (mot == null)
				throw new Exception("Criar viagem - Motorista nao encontrado com o CNH fornecido.");

			if (vei == null)
				throw new Exception("Criar viagem - Veiculo nao encontrado com a placa fornecida.");

			// Cria a viagem associando os objetos sincronizados
			Viagem v = new Viagem(dataFormatada, destino, vei, mot);

			// Sincroniza as listas bilaterais
			mot.adicionarViagem(v);
			vei.adicionarViagem(v);
			
			repViagem.criar(v);
			Repositorio.commit();

		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// ALTERAR VIAGEM (Simples)
	// ==========================================
	public static void alterarViagem(int id, String dataStr, String destino, List<String> passageiros)
			throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();

			Viagem v = repViagem.localizar(id);
			if (v == null)
				throw new Exception("Alterar viagem - Viagem inexistente com o ID: " + id);

			if (passageiros != null) {
				v.setNomePas(passageiros);
			}

			if (destino != null && !destino.isBlank()) {
				v.setDestino(destino);
			}

			if (dataStr != null) {
				try {
					LocalDate dataFormatada = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					v.setData(dataFormatada);
				} catch (DateTimeParseException e) {
					throw new Exception("Alterar viagem - formato de data invalido: " + dataStr);
				}
			}

			repViagem.atualizar(v);
			Repositorio.commit();

		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// APAGAR VIAGEM
	// ==========================================
	public static void apagarViagem(int id) throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();

			Viagem v = repViagem.localizar(id);
			if (v == null)
				throw new Exception("Excluir viagem - Viagem inexistente com o ID: " + id);

			if (v.getMotorista() != null) {
				v.getMotorista().removerViagem(v);
				v.setMotorista(null);
			}
			if (v.getVeiculo() != null) {
				v.getVeiculo().removerViagem(v);
				v.setVeiculo(null);
			}
			if (v.getNomePas() != null) {
				v.getNomePas().clear();
			}

			repViagem.deletar(v);
			Repositorio.commit();

		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}

	// ==========================================
	// LISTAGENS & CONSULTAS
	// ==========================================
	public static List<Viagem> listarViagens() {
		Repositorio.conectar();
		List<Viagem> lista = repViagem.listar();
		Repositorio.desconectar();
		return lista;
	}

	public static List<Viagem> listarViagensPorDestino(String destino) {
		Repositorio.conectar();
		List<Viagem> lista = repViagem.listarPorDestino(destino);
		Repositorio.desconectar();
		return lista;
	}

	public static List<Viagem> consultarViagensDoMotorista(String cnh) {
		Repositorio.conectar();
		List<Viagem> result = repViagem.listarPorMotorista(cnh);
		Repositorio.desconectar();
		return result;
	}

	public static List<Viagem> consultarViagensNaData(String dataStr) throws Exception {
		try {
			LocalDate dataFormatada = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			Repositorio.conectar();
			List<Viagem> result = repViagem.listarPorData(dataFormatada);
			Repositorio.desconectar();
			return result;
		} catch (DateTimeParseException e) {
			throw new Exception("Consulta por data - formato invalido: " + dataStr);
		}
	}

	// ==========================================
	// ALTERAR VIAGEM AVANCADO (Uso direto no Swing)
	// ==========================================
	public static void alterarViagem(int idSelecionada, String destino, String motorista, String placa, String cnh) throws Exception {
		try {
			Repositorio.conectar();
			Repositorio.begin();
			Viagem v = repViagem.localizar(idSelecionada);
			
			if (v == null)
				throw new Exception("Alterar viagem - Registro inexistente com ID: " + idSelecionada);			
			
			v.setDestino(destino);
			
			// Atualizando vinculo do Motorista de forma bidirecional
			if (v.getMotorista() != null) {
				v.getMotorista().removerViagem(v);
			}
			
			Motorista m = repMotorista.localizar(cnh); 
			if (m != null) {
				v.setMotorista(m); 
				m.adicionarViagem(v);
			} else {
				m = new Motorista();
				m.setCnh(cnh);
				m.setNome(motorista);
				repMotorista.criar(m); 
				v.setMotorista(m);
				m.adicionarViagem(v);
			}
			
			// Atualizando vinculo do Veiculo de forma bidirecional
			if (v.getVeiculo() != null) {
				v.getVeiculo().removerViagem(v);
			}
			
			Veiculo vec = repVeiculo.localizar(placa);
			if (vec != null) {
				v.setVeiculo(vec);              
				vec.adicionarViagem(v);
			} else {
				vec = new Veiculo();
				vec.setPlaca(placa);
				vec.setCapacidade(40); 
				repVeiculo.criar(vec);
				v.setVeiculo(vec);
				vec.adicionarViagem(v);
			}

			repViagem.atualizar(v); 
			Repositorio.commit();

		} catch (Exception e) {
			Repositorio.rollback();
			throw e;
		} finally {
			Repositorio.desconectar();
		}
	}
	
	public static Viagem localizarViagemComMotorista(int idViagem) throws Exception {
		if (idViagem <= 0) {
			throw new Exception("ID de viagem inválido para busca.");
		}
		try {
			RepositorioViagem repo = new RepositorioViagem();
			Viagem viagem = repo.localizarViagemComMotorista(idViagem);

			if (viagem == null) {
				throw new Exception("Viagem não encontrada no sistema.");
			}
			return viagem;
		} finally {
			Repositorio.desconectar();
		}
	}
}