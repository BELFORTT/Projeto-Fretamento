package modelo;

import java.util.ArrayList;

public class Motorista {
	private String cnh;
	private String nome;
	private ArrayList<Viagem> listaViagem = new ArrayList<Viagem>();

	public Motorista(String cnh, String nome) {
		this.cnh = cnh;
		this.nome = nome;

	}

	public ArrayList<Viagem> getListaViagens() {
		return listaViagem;
	}

	public void setListaViagens(ArrayList<Viagem> listaViagens) {
		listaViagem = listaViagens;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void novaViagem(Viagem viagem) {
		listaViagem.add(viagem);
	}
	
	public String getCnh() {
		return cnh;
	}
	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return " [cnh= " + cnh + ", nome=" + nome + "]";
	}

}
