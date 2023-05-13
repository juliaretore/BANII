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
	private PreparedStatement update_funcionario;
	private PreparedStatement delete_funcionario;

	
	private PreparedStatement select_bibliotecarios;
	private PreparedStatement select_assistentes;
	private PreparedStatement select_assistentes_bibliotecarios;
	private PreparedStatement select_all;
	

	public static FuncionarioDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance==null) instance=new FuncionarioDAO();
		return instance;
	}
	
	private FuncionarioDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		select_new_id_funcionario = conexao.prepareStatement("select nextval('id_funcionario')");
		insert_funcionario =  conexao.prepareStatement("insert into funcionario values (?,?,?,?,?,?,?)");
		update_funcionario = conexao.prepareStatement("update funcionario set login=?, nome=?, salario=?, turno=?, tipo=? where id=?");
		delete_funcionario = conexao.prepareStatement("delete from funcionario where id=?");

		
		select_bibliotecarios = conexao.prepareStatement("select id, nome, login, turno, salario from funcionario where tipo=1");
		select_assistentes = conexao.prepareStatement("select * from endereco where id=?");
		select_assistentes_bibliotecarios = conexao.prepareStatement("select u.id, u.nome, c.nome, u.turno, endereco from usuario u join categoria c on u.id_categoria=c.id");
		select_all = conexao.prepareStatement("select ");
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
				insert_funcionario.executeUpdate();
			}catch (SQLException e) {
				throw new InsertException("Erro ao inserir funcionário.");
			}		
	}

	public void update_funcionario(Funcionario funcionario) throws UpdateException, SelectException, NaoCadastradoException{
		try {
			update_funcionario.setString(1, funcionario.getLogin());
			update_funcionario.setString(2, funcionario.getNome());
			update_funcionario.setDouble(3, funcionario.getSalario());
			update_funcionario.setString(4, funcionario.getTurno());
			update_funcionario.setInt(5, funcionario.getTipo());
			update_funcionario.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar funcionário.");
		}
	}
	
	public void delete_funcionario(int funcionario) throws DeleteException, SelectException, NaoCadastradoException{
		try {
			delete_funcionario.setInt(1, funcionario);
			delete_funcionario.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar funcionário");
		}
	}
	


	
//	public  Endereco select_endereco(int id_endereco) throws SelectException {
//		Endereco endereco = new Endereco();
//		try {
//			select_endereco.setInt(1, id_endereco);
//			ResultSet rs = select_endereco.executeQuery();
//			if(rs.next()) {
//				endereco.setId(rs.getInt(1));
//				endereco.setEstado(rs.getString(2));
//				endereco.setCidade(rs.getString(3));
//				endereco.setBairro(rs.getString(4));
//				endereco.setRua(rs.getString(5));
//				endereco.setNumero(rs.getInt(6));
//				endereco.setComplemento(rs.getString(7));		
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar endereço");
//		}
//		return endereco;
//	}
	
//	public List<Object> select_telefones(int id_usuario) throws SelectException {
//		List<Object> lista = new ArrayList<Object>();
//		try {
//			select_telefones.setInt(1, id_usuario);
//			ResultSet rs = select_telefones.executeQuery();
//			while(rs.next()) {
//				Object[] linha  = {rs.getString(1)};
//				lista.add(linha);
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar endereço");
//		}
//		return lista;	
//	}
//	
	public List<Object> select_bibliotecarios() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_bibliotecarios.executeQuery();
			while(rs.next()) {
				String tipo;
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getDouble(5)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela de bibliotecários");
		}
		return lista;
	}
//
//	public List<Integer> select_usuarios() throws SelectException {
//		List<Integer> lista = new ArrayList<Integer>();
//		try {
//			ResultSet rs = select_usuarios.executeQuery();
//			while(rs.next()) {
//				lista.add(rs.getInt(1));
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar usuários");
//		}
//		return lista;
//	}
	



}
	