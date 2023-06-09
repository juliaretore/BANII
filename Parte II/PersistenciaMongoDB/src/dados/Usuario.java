package dados;

import java.util.List;

public class Usuario {
	private String id;
	private String nome;
	private String turno;
	private Endereco endereco;
	private Categoria categoria;
	private String email;
	private List<String> telefones;

	public Usuario() {
		
	}


	public List<String> getTelefones() {
		return telefones;
	}


	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
