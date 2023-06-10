package dados;

public class Livro {
	private String id;
	private String isbn;
	private String titulo;
	private String editora;
	
	
	public Livro() {
		
	}
	
	public Livro(String id, String isbn, String titulo, String editora) {
		this.setId(id);
		this.setIsbn(isbn);
		this.setTitulo(titulo);
		this.setEditora(editora);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	
}
