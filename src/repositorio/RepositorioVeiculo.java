package repositorio;

import java.util.List;
import jakarta.persistence.TypedQuery;
import model.Veiculo;
import util.Util;

public class RepositorioVeiculo extends Repositorio<Veiculo> {

	// REGRA: Usar JPQL com LEFT JOIN FETCH para trazer o veículo com suas viagens inicializadas para a view
	@Override
	public Veiculo localizar(Object chave) {
		String placa = (String) chave;
		TypedQuery<Veiculo> q = Util.getManager().createQuery("""
				select v from Veiculo v
				left join fetch v.viagens
				where v.placa = :p
				""", Veiculo.class);
		q.setParameter("p", placa);
		return q.getSingleResultOrNull();
	}

	@Override
	public List<Veiculo> listar() {
		TypedQuery<Veiculo> query = Util.getManager().createQuery(
				"SELECT v FROM Veiculo v ORDER BY v.placa", Veiculo.class);
		return query.getResultList();
	}

	// Usando o método seguro do professor que não crasha o sistema se a placa não existir
	public Veiculo buscarPorPlaca(String placa) {
		TypedQuery<Veiculo> query = Util.getManager().createQuery(
				"SELECT v FROM Veiculo v WHERE v.placa = :placa", Veiculo.class);
		query.setParameter("placa", placa);
		return query.getSingleResultOrNull(); 
	}
}