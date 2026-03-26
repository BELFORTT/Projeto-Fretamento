package modelo;

import java.util.ArrayList;

public class Veiculo {
	private String placa;
	private int capacidade;
	private ArrayList<String> listaViagem = new ArrayList<String>();
	
	public Veiculo(String placa, int capacidade) {
		this.placa = placa;
		this.capacidade = capacidade;
	}
	
	public void novaViagem(String viagem) {
		listaViagem.add(viagem);
	}
	
	public Veiculo(String placa) {
		this.placa = placa;
	}

	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "[placa=" + placa + ", capacidade=" + capacidade + ", listaViagem=" + listaViagem + "]";
	}
	

}
