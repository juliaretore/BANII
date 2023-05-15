package dados;

import javax.xml.crypto.Data;

public class Exemplar {
	
	private int id;
	private String colecao;
	private int prateleira;
	private int estante;
	private int id_livro;
	private int id_usuario_reserva;
	private String data_reserva;
	public String getColecao() {
		return colecao;
	}
	public void setColecao(String colecao) {
		this.colecao = colecao;
	}
	public int getPrateleira() {
		return prateleira;
	}
	public void setPrateleira(int prateleira) {
		this.prateleira = prateleira;
	}
	public int getEstante() {
		return estante;
	}
	public void setEstante(int estante) {
		this.estante = estante;
	}
	public int getId_livro() {
		return id_livro;
	}
	public void setId_livro(int id_livro) {
		this.id_livro = id_livro;
	}
	public int getId_usuario_reserva() {
		return id_usuario_reserva;
	}
	public void setId_usuario_reserva(int id_usuario_reserva) {
		this.id_usuario_reserva = id_usuario_reserva;
	}
	public String getData_reserva() {
		return data_reserva;
	}
	public void setData_reserva(String data_reserva) {
		this.data_reserva = data_reserva;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
