package dados;

public class Categoria {
	private int id;
	private String nome;
	private int tempo_empr;
	private int qnt_max_empr;
	
	
	public Categoria() {
		
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

	public int getTempo_empr() {
		return tempo_empr;
	}

	public void setTempo_empr(int tempo_empr) {
		this.tempo_empr = tempo_empr;
	}

	public int getQnt_max_empr() {
		return qnt_max_empr;
	}

	public void setQnt_max_empr(int qnt_max_empr) {
		this.qnt_max_empr = qnt_max_empr;
	}
	
}
