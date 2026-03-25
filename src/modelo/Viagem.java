package modelo;

import java.util.ArrayList;

public class Viagem {
	private int id;
	private String data;
	private String destino;
	private Veiculo veiculo;
	private Motorista motorista;
	private ArrayList<String> NomePas = new ArrayList<String>();
	
	public Viagem(String data, String destino, Veiculo veiculo, Motorista motorista) {
		this.data = data;
		this.destino = destino;
		this.veiculo = veiculo;
		this.motorista = motorista;
	}

	@Override
	public String toString() {
		return "id=" + id + " ,"+ "data=" + data + ", " + " destino= " + destino + ", \n"
								+ "veiculo=" + veiculo + ", \n"
										+ "motorista=" + motorista + ", \n"
												+ "NomePas=" + NomePas + "\n";
	}
	
	public void addPassageiros(String passageiro) {
		NomePas.add(passageiro);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public ArrayList<String> getNomePas() {
		return NomePas;
	}

	public void setNomePas(ArrayList<String> nomePas) {
		NomePas = nomePas;
	}
	
}
