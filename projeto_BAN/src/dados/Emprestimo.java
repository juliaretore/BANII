package dados;

public class Emprestimo {
	private int id;
	private double multa;
	private int pagamento;
	private int renovacoes;
	private String data_empr;
	private String data_est_entr;
	private String data_real_entr;
	private int situacao;
	
	public Emprestimo() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMulta() {
		return multa;
	}
	public void setMulta(double multa) {
		this.multa = multa;
	}
	public int getPagamento() {
		return pagamento;
	}
	public void setPagamento(int pagamento) {
		this.pagamento = pagamento;
	}
	public int getRenovacoes() {
		return renovacoes;
	}
	public void setRenovacoes(int renovacoes) {
		this.renovacoes = renovacoes;
	}
	public String getData_empr() {
		return data_empr;
	}
	public void setData_empr(String data_empr) {
		this.data_empr = data_empr;
	}
	public String getData_est_entr() {
		return data_est_entr;
	}
	public void setData_est_entr(String data_est_entr) {
		this.data_est_entr = data_est_entr;
	}
	public String getData_real_entr() {
		return data_real_entr;
	}
	public void setData_real_entr(String data_real_entr) {
		this.data_real_entr = data_real_entr;
	}
	public int getSituacao() {
		return situacao;
	}
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
	
	
	
}
