package modelo;

import java.util.ArrayList;

public class Veiculo {
	private String placa;
	private int capacidade;
	private ArrayList<Viagem> listaViagem = new ArrayList<Viagem>();
	
	public Veiculo(String placa, int capacidade) {
		this.placa = placa;
		this.capacidade = capacidade;
	}
	
	public void novaViagem(Viagem viagem) {
		listaViagem.add(viagem);
	}
	
	public Veiculo(String placa) {
		this.placa = placa;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public ArrayList<Viagem> getListaViagem() {
		return listaViagem;
	}

	public void setListaViagem(ArrayList<Viagem> listaViagem) {
		this.listaViagem = listaViagem;
	}

	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "[placa= " + placa + ", capacidade= " + capacidade + ", listaViagem= " + listaViagem + "]"; 	}

	public void remover(Viagem viagem) {
		this.listaViagem.remove(viagem);
	}
	

}
