package dados;

public class Livro {
	private int id;
	private String isbn;
	private String titulo;
	private String colecao;
	private String editora;
	
	
	public Livro() {
		
	}
	
	public Livro(int id, String isbn, String titulo, String colecao, String editora) {
		this.setId(id);
		this.setIsbn(isbn);
		this.setTitulo(titulo);
		this.setColecao(colecao);
		this.setEditora(editora);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	
}
