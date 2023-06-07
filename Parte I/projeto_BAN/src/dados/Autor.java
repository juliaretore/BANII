package dados;

public class Autor {
	private int id;
	private String nome;
	private String nacionalidade;
	private String area;
	
	
	public Autor() {
		
	}
	
	public Autor(int id, String nome, String nacionalidade, String area) {
		this.setId(id);
		this.setNome(nome);
		this.setArea(area);
		this.setNacionalidade(nacionalidade);
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

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}


		
}
