package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dados.Funcionario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class FuncionarioDAO {
private static FuncionarioDAO instance = null;
	
	private PreparedStatement select_new_id_funcionario;
	private PreparedStatement insert_funcionario;
	private PreparedStatement insert_supervisao;
	private PreparedStatement update_funcionario;
	private PreparedStatement inativa_funcionario;
	private PreparedStatement select_bibliotecarios;
	private PreparedStatement select_assistentes;
	private PreparedStatement select_assistentes_bibliotecario;
	private PreparedStatement select_adicionar_supervisao;
	private PreparedStatement delete_supervisoes;
	private PreparedStatement delete_supervisoes_a;
	private PreparedStatement delete_supervisao;
	
	

	public static FuncionarioDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance==null) instance=new FuncionarioDAO();
		return instance;
	}
	
	private FuncionarioDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		select_new_id_funcionario = conexao.prepareStatement("select nextval('id_funcionario')");
		insert_funcionario =  conexao.prepareStatement("insert into funcionario values (?,?,?,?,?,?,?,?,?)");
		insert_supervisao = conexao.prepareStatement("insert into supervisao values (?,?)");
		update_funcionario = conexao.prepareStatement("update funcionario set login=?, nome=?, salario=?, turno=?, email=? where id=?");
		inativa_funcionario = conexao.prepareStatement("update funcionario set ativo=0 where id=?");
		delete_supervisoes = conexao.prepareStatement("delete from supervisao where id_bibliotecario=?");
		delete_supervisoes_a = conexao.prepareStatement("delete from supervisao where id_assistente=?");
		delete_supervisao = conexao.prepareStatement("delete from supervisao where id_bibliotecario=? and id_assistente=?");
		select_bibliotecarios = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=1 and ativo=1");
		select_assistentes = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=2 and ativo=1");
		select_assistentes_bibliotecario = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=2 and ativo=1 and id in (select id_assistente from supervisao where id_bibliotecario=?)");
		select_adicionar_supervisao = conexao.prepareStatement("select id, nome, login, turno, salario, email from funcionario where tipo=2 and ativo=1 and id not in (select id_assistente from supervisao where id_bibliotecario=?)");
	}
	
	private int select_new_id_funcionario() throws SelectException{
		try {
			ResultSet rs = select_new_id_funcionario.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar novo ID da tabela funcionário");
		}
		return 0;
	}
	
	public void insert_funcionario(Funcionario funcionario) throws InsertException, SelectException, JaCadastradoException{
			try {
				insert_funcionario.setInt(1, select_new_id_funcionario());
				insert_funcionario.setString(2, funcionario.getLogin());
				insert_funcionario.setString(3, funcionario.getSenha());
				insert_funcionario.setString(4, funcionario.getNome());
				insert_funcionario.setDouble(5, funcionario.getSalario());
				insert_funcionario.setString(6, funcionario.getTurno());
				insert_funcionario.setInt(7, funcionario.getTipo());
				insert_funcionario.setString(8, funcionario.getEmail());
				insert_funcionario.setInt(9, 1);
				insert_funcionario.executeUpdate();
			}catch (SQLException e) {
				throw new InsertException("Erro ao inserir funcionário.");
			}		
	}
	
	public void insert_supervisao(int id_assistente, int id_bibliotecario) throws InsertException, SelectException, JaCadastradoException{
		try {
			insert_supervisao.setInt(1, id_assistente);
			insert_supervisao.setInt(2, id_bibliotecario);
			insert_supervisao.executeUpdate();
		}catch (SQLException e) {
			throw new InsertException("Erro ao inserir supervisão.");
		}		
}

	public void update_funcionario(Funcionario funcionario) throws UpdateException, SelectException, NaoCadastradoException{
		try {
			update_funcionario.setString(1, funcionario.getLogin());
			update_funcionario.setString(2, funcionario.getNome());
			update_funcionario.setDouble(3, funcionario.getSalario());
			update_funcionario.setString(4, funcionario.getTurno());
			update_funcionario.setString(5, funcionario.getEmail());
			update_funcionario.setInt(6, funcionario.getId());
			update_funcionario.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar funcionário.");
		}
	}
	
	public void inativa_funcionario(int funcionario, int op) throws DeleteException, SelectException, NaoCadastradoException{
		try {
			
			if(op==0) {       //bibliotecario
				delete_supervisoes.setInt(1, funcionario);
				delete_supervisoes.executeUpdate();
			}else if(op==1) { //assistesnte
				delete_supervisoes_a.setInt(1, funcionario);
				delete_supervisoes_a.executeUpdate();
			}
			inativa_funcionario.setInt(1, funcionario);
			inativa_funcionario.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar funcionário");
		}
	}
	
	public void delete_supervisao(int bibliotecario, int assistente) throws DeleteException, SelectException, NaoCadastradoException{
		try {	
			delete_supervisao.setInt(1, bibliotecario);
			delete_supervisao.setInt(2, assistente);
			delete_supervisao.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar supervisão");
		}
	}
	
	public List<Object> select_bibliotecarios() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_bibliotecarios.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getDouble(5), rs.getString(6)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de bibliotecários");
		}
		return lista;
	}
	
	
	public List<Object> select_assistentes() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_assistentes.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getDouble(5), rs.getString(6)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de assistentes");
		}
		return lista;
	}
	
	
	
public List<Object> select_assistentes_bibliotecario(int id_bibliotecario) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_assistentes_bibliotecario.setInt(1, id_bibliotecario);
			ResultSet rs = select_assistentes_bibliotecario.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getDouble(5), rs.getString(6)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de assistentes");
		}
		return lista;
	}	

		

public List<Object> select_adicionar_supervisao(int id_bibliotecario) throws SelectException {
	List<Object> lista = new ArrayList<Object>();
	try {
		select_adicionar_supervisao.setInt(1, id_bibliotecario);
		ResultSet rs = select_adicionar_supervisao.executeQuery();
		while(rs.next()) {
			Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getDouble(5), rs.getString(6)};
			lista.add(linha);
		}
	}catch(SQLException e) {
		throw new SelectException("Erro ao buscar os assistentes para supervisão");
	}
	return lista;
}



}
	
