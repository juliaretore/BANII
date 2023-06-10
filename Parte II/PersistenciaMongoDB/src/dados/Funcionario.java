package dados;

public class Funcionario {
	private String id;
	private String nome;
	private String login;
	private String senha;
	private Double salario;
	private String turno;
	private int tipo;
	private String email;
	
	public Funcionario(String id, String nome, String login, String senha, double salario, String turno, int tipo, String email) {
		this.setId(id);
		this.setLogin(login);
		this.setNome(nome);
		this.setSenha(senha);
		this.setSalario(salario);
		this.setTurno(turno);
		this.setTipo(tipo);
		this.setEmail(email);
	}
	public Funcionario() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
