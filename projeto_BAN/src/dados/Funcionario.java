package dados;

public class Funcionario {
	private int id;
	private String nome;
	private String login;
	private String senha;
	private double salario;
	private String turno;
	private int tipo;
	
	public Funcionario(int id, String nome, String login, String senha, double salario, String turno, int tipo) {
		this.setId(id);
		this.setLogin(login);
		this.setNome(nome);
		this.setSenha(senha);
		this.setSalario(salario);
		this.setTurno(turno);
		this.setTipo(tipo);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
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
	
	
	
}
