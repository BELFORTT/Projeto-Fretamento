package repositorio;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.TypedQuery;
import model.Viagem;
import util.Util; // Importando a classe util que gerencia o manager

public class RepositorioViagem extends Repositorio<Viagem> {

	// Implementando o método abstrato da mãe (busca por ID)
	@Override
	public Viagem localizar(Object chave) {
		// Como a chave vem como Object, transformamos para int (ID da Viagem)
		// JOIN FETCH para carregar o motorista e o veículo preventivamente :)))
		int id = (Integer) chave;
		TypedQuery<Viagem> q = Util.getManager().createQuery(
				"SELECT v FROM Viagem v LEFT JOIN FETCH v.veiculo LEFT JOIN FETCH v.motorista WHERE v.id = :id",
				Viagem.class);
		q.setParameter("id", id);
		
		try {
			return q.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// Implementando o método abstrato da mãe (listar todos)
	@Override
	public List<Viagem> listar() {
		TypedQuery<Viagem> query = Util.getManager().createQuery(
				"SELECT v FROM Viagem v ORDER BY v.id", Viagem.class);
		return query.getResultList();
	}

	public List<Viagem> listarPorDestino(String destino) {
		TypedQuery<Viagem> query = Util.getManager().createQuery(
				"SELECT v FROM Viagem v WHERE v.destino LIKE :dest", Viagem.class);
		query.setParameter("dest", "%" + destino + "%");
		return query.getResultList();
	}
	
	public List<Viagem> listarPorMotorista(String nome) {
		TypedQuery<Viagem> query = Util.getManager().createQuery(
				"SELECT v FROM Viagem v WHERE v.motorista.nome LIKE :moto", Viagem.class);
		query.setParameter("moto", "%" + nome + "%");
		return query.getResultList();
	}
	
	public Viagem localizarViagemComMotorista(int idViagem) {
		return Util.getManager()
			.createQuery("select v from Viagem v join fetch v.motorista where v.id = :id", Viagem.class)
			.setParameter("id", idViagem)
			.getSingleResult();
	}
	
	public List<Viagem> listarPorData(LocalDate data) {
		// Criamos a consulta JPQL buscando pelo atributo 'data' da entidade Viagem
		TypedQuery<Viagem> query = Util.getManager().createQuery(
				"SELECT v FROM Viagem v WHERE v.data = :data", 
				Viagem.class
		);
		query.setParameter("data", data);
		return query.getResultList();
	}
	
	public List<Viagem> listarPorPlaca(String placa){
		TypedQuery<Viagem> query = Util.getManager().createQuery(
				"SELECT v FROM Viagem v JOIN v.veiculo vc WHERE vc.placa = :p",
				Viagem.class);
		query.setParameter("p", placa);
		return query.getResultList();
	}
}