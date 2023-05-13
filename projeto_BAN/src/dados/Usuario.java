package dados;

public class Usuario {
	private int id;
	private String nome;
	private String turno;
	private Endereco endereco;
	private Categoria categoria;
	
	public Usuario(int id, String nome, String turno, Endereco endereco, Categoria categoria) {
		this.setId(id);
		this.setNome(nome);
		this.setTurno(turno);
		this.setEndereco(endereco);
		this.setCategoria(categoria);
	}

	public Usuario() {
		
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
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
