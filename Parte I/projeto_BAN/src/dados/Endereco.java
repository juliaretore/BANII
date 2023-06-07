package dados;

public class Endereco {
	private int id;
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private int numero;
	private String complemento;
	
	public Endereco() {
		
	}
	
	public Endereco(int id, String cidade, String estado, String bairro, String rua, int numero, String complemento) {
		this.setId(id);
		this.setEstado(estado);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setRua(rua);
		this.setNumero(numero);
		this.setComplemento(complemento);
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String endereco_completo() {
		return this.getCidade()+" - "+getEstado()+". "+this.getRua()+", "+this.getBairro()+" n "+this.getNumero()+" "+this.getComplemento();
	}
	
	
	
}
