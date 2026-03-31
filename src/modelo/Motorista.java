package modelo;

import java.util.ArrayList;

public class Motorista {
	private String cnh;
	private String nome;
	private ArrayList<String> ListaViagem = new ArrayList<String>();

	public Motorista(String cnh, String nome) {
		this.cnh = cnh;
		this.nome = nome;

	}

	public ArrayList<String> getListaViagens() {
		return ListaViagem;
	}

	public void setListaViagens(ArrayList<String> listaViagens) {
		ListaViagem = listaViagens;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void novaViagem(String viagem) {
		ListaViagem.add(viagem);
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
		return " [cnh=" + cnh + ", nome=" + nome + ", ListaViagem=" + ListaViagem + "]";
	}

}
