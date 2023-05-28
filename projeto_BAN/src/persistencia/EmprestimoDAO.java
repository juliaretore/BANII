package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dados.Funcionario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class EmprestimoDAO {
private static EmprestimoDAO instance = null;
	
	private PreparedStatement insert_emprestimo;
	private PreparedStatement insert_emprestimo_reserva;
	private PreparedStatement devolucao_emprestimo;
	private PreparedStatement renovar_emprestimo;
	private PreparedStatement atualizar_multas;
	private PreparedStatement verifica_datas_reservas;
	private PreparedStatement insert_reserva;
	private PreparedStatement delete_reserva;
	private PreparedStatement pagar_multa;
	private PreparedStatement select_data_emprestimo;
	private PreparedStatement select_historico_exemplar;
	private PreparedStatement select_emprestimos_correntes;
	private PreparedStatement select_pagar_multas;
	
	public static EmprestimoDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance==null) instance=new EmprestimoDAO();
		return instance;
	}
	
	private EmprestimoDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		insert_emprestimo =  conexao.prepareStatement("select cadastro_emprestimo(?, ?, ?)");
		insert_emprestimo_reserva = conexao.prepareStatement("select cadastro_emprestimo_reserva(?, ?, ?)");
		devolucao_emprestimo = conexao.prepareStatement("select devolucao_emprestimo(?)");
		atualizar_multas = conexao.prepareStatement("select atualiza_multas()");
		renovar_emprestimo = conexao.prepareStatement("select renovar_emprestimo(?)");
		verifica_datas_reservas = conexao.prepareStatement("select verifica_datas_reservas()");
		insert_reserva = conexao.prepareStatement("insert into reservas_livro values (?, ?)");
		delete_reserva = conexao.prepareStatement("delete from reservas_livro where id_livro=? and id_usuario=?");
		pagar_multa = conexao.prepareStatement("select pagamento_multas(?)");
		select_data_emprestimo = conexao.prepareStatement("select ((select c.tempo_empr from categoria c join usuario u on u.id_categoria=c.id where u.id=?)+current_date)");
		select_historico_exemplar = conexao.prepareStatement("select e.id,  u.nome, e.data_empr, e.data_est_entr, e.data_real_entr, e.situacao, e.multa, e.pagamento_multa from emprestimo e join usuario u on u.id=e.id_usuario where id_exemplar=?");
		select_emprestimos_correntes = conexao.prepareStatement("select e.id, u.nome, l.titulo, ex.id, e.data_empr, e.data_est_entr, e.renovacoes from emprestimo e join usuario u on u.id=e.id_usuario join exemplar ex on ex.id=e.id_exemplar join livro l on l.id=ex.id_livro where e.situacao=0");
		select_pagar_multas = conexao.prepareStatement("select e.id_usuario, u.nome, sum(multa) from emprestimo e join usuario u on u.id=e.id_usuario where e.situacao=1 and e.pagamento_multa=1 group by e.id_usuario, u.nome");
	}
	
	public void insert_emprestimo(int cid_exemplar, int cid_usuario, int cid_funcionario) throws InsertException, SelectException, JaCadastradoException{
			try {
				insert_emprestimo.setInt(1, cid_exemplar);
				insert_emprestimo.setInt(2, cid_usuario);
				insert_emprestimo.setInt(3, cid_funcionario);				
				insert_emprestimo.execute();
			}catch (SQLException e) {
				String texto[] = e.getMessage().split("\\r?\\n");
				JOptionPane.showMessageDialog(null, texto[0]);
			}	
	}
	
	public void insert_emprestimo_reserva(int cid_exemplar, int cid_usuario, int cid_funcionario) throws InsertException, SelectException, JaCadastradoException{
		try {
			insert_emprestimo_reserva.setInt(1, cid_exemplar);
			insert_emprestimo_reserva.setInt(2, cid_usuario);
			insert_emprestimo_reserva.setInt(3, cid_funcionario);				
			insert_emprestimo_reserva.executeUpdate();
		}catch (SQLException e) {
			String texto[] = e.getMessage().split("\\r?\\n");
			JOptionPane.showMessageDialog(null, texto[0]);
		}	
	}	

	public void devolucao_emprestimo(int cid_emprestimo) throws InsertException, SelectException, JaCadastradoException{
		try {
			devolucao_emprestimo.setInt(1, cid_emprestimo);
			devolucao_emprestimo.execute();
		}catch (SQLException e) {
			throw new InsertException("Erro na devolução do empréstimo");
		}	
	}
	
	public void renovar_emprestimo(int cid_emprestimo) throws InsertException, SelectException, JaCadastradoException{
		try {
			renovar_emprestimo.setInt(1, cid_emprestimo);
			renovar_emprestimo.execute();
		}catch (SQLException e) {
			String texto[] = e.getMessage().split("\\r?\\n");
			JOptionPane.showMessageDialog(null, texto[0]);
		}	
	}
	
	public void insert_reserva(int cid_livro, int cid_usuario) throws InsertException, SelectException, JaCadastradoException{
		try {
			insert_reserva.setInt(1, cid_livro);
			insert_reserva.setInt(2, cid_usuario);
			insert_reserva.executeUpdate();
		}catch (SQLException e) {
			throw new InsertException("Erro inserir reserva");
		}	
	}
	
	public void delete_reserva(int cid_livro, int cid_usuario) throws InsertException, SelectException, JaCadastradoException{
		try {
			delete_reserva.setInt(1, cid_livro);
			delete_reserva.setInt(2, cid_usuario);
			delete_reserva.executeUpdate();
		}catch (SQLException e) {
			throw new InsertException("Erro cancelar reserva");
		}	
	}
	
	public void pagar_multa(int cid_usuario) throws InsertException, SelectException, JaCadastradoException{
		try {
			pagar_multa.setInt(1, cid_usuario);
			pagar_multa.execute();
		}catch (SQLException e) {
			throw new InsertException("Erro pagar multa");
		}	
	}
	
	public void atualizar_multas() throws InsertException, SelectException, JaCadastradoException{
		try {
			atualizar_multas.execute();
		}catch (SQLException e) {
			throw new InsertException("Erro ao atualizar multas");
		}	
	}
	
	public void verifica_datas_reservas() throws InsertException, SelectException, JaCadastradoException{
		try {
			verifica_datas_reservas.execute();
		}catch (SQLException e) {
			throw new InsertException("Erro ao verificar datas reservas");
		}	
	}
	
	public String select_data_emprestimo(int cid_usuario) throws InsertException {
		try {
			select_data_emprestimo.setInt(1, cid_usuario);
			ResultSet rs = select_data_emprestimo.executeQuery();
			rs.next();
			return rs.getString(1);
		}catch (SQLException e) {
			throw new InsertException("Erro determinar a data de devolução do emprestimo");
		}
		
	}
	
	public List<Object> select_historico_exemplar(int id_exemplar) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_historico_exemplar.setInt(1, id_exemplar);
			ResultSet rs = select_historico_exemplar.executeQuery();
			while(rs.next()) {
				String situacao = "";
				String pagamento_multa = "";

				if(rs.getInt(6) == 0) situacao="Não devolvido";
				else situacao="Devolvido";
				
				if(rs.getInt(8) == 1) pagamento_multa="Em aberto";
				else pagamento_multa = "Pago";
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5), situacao, rs.getDouble(7), pagamento_multa};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar historico para adicionar");
		}
		return lista;
	}	
	
		
	public List<Object> select_emprestimos_correntes() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_emprestimos_correntes.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4),rs.getString(5), rs.getString(6), rs.getInt(7)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar emprestimos correntes");
		}
		return lista;
	}

	public List<Object> select_pagar_multas() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_pagar_multas.executeQuery();
			while(rs.next()) {
				if(rs.getDouble(3)>0) {
					Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getDouble(3)};
					lista.add(linha);
				}
				
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar multas");
		}
		return lista;
	}
	
	
}
	
