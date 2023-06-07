package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dados.Endereco;
import dados.Usuario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class UsuarioDAO {
private static UsuarioDAO instance = null;
	
	private PreparedStatement select_new_id_usuario;
	private PreparedStatement select_new_id_endereco;
	private PreparedStatement insert_endereco;
	private PreparedStatement insert_usuario;
	private PreparedStatement insert_telefone;
	private PreparedStatement update_usuario;
	private PreparedStatement update_endereco;
	private PreparedStatement update_telefone;
	private PreparedStatement select_table;
	private PreparedStatement select_endereco;
	private PreparedStatement select_telefones;
	private PreparedStatement delete_telefone;
	private PreparedStatement select_table_emprestimos;
	

	public static UsuarioDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance==null) instance=new UsuarioDAO();
		return instance;
	}
	
	private UsuarioDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		select_new_id_usuario = conexao.prepareStatement("select nextval('id_usuario')");
		select_new_id_endereco = conexao.prepareStatement("select nextval('id_endereco')");
		insert_endereco =  conexao.prepareStatement("insert into endereco values (?,?,?,?,?,?,?)");
		insert_usuario =  conexao.prepareStatement("insert into usuario values (?,?,?,?,?,?)");
		insert_telefone = conexao.prepareStatement("insert into telefone values (?,?)");
		update_usuario = conexao.prepareStatement("update usuario set nome=?, turno=?, id_categoria=?, endereco=?, email=? where id=?");
		update_endereco = conexao.prepareStatement("update endereco set estado=?, cidade=?, bairro=?, rua=?, numero=?, complemento=? where id=?");
		update_telefone = conexao.prepareStatement("update telefone set numero=? where numero=? and id_usuario=?");
		select_endereco = conexao.prepareStatement("select * from endereco where id=?");
		select_table = conexao.prepareStatement("select u.id, u.nome, c.nome, u.turno, endereco, u.email from usuario u join categoria c on u.id_categoria=c.id");
		select_telefones = conexao.prepareStatement("select numero from telefone where id_usuario=?");
		delete_telefone = conexao.prepareStatement("delete from telefone where id_usuario=? and numero=?");
		select_table_emprestimos = conexao.prepareStatement("select u.id, u.nome, c.nome, u.turno from usuario u join categoria c on u.id_categoria=c.id");

	}
	
	private int select_new_id_usuario() throws SelectException{
		try {
			ResultSet rs = select_new_id_usuario.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar novo ID da tabela usuário");
		}
		return 0;
	}

	private int select_new_id_endereco() throws SelectException{
		try {
			ResultSet rs = select_new_id_endereco.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar novo ID da tabela endereço");
		}
		return 0;
	}
	
	public void insert_usuario(Usuario usuario) throws InsertException, SelectException, JaCadastradoException{
			try {
				int id_endereco =  insert_endereco(usuario.getEndereco());
				insert_usuario.setInt(1, select_new_id_usuario());
				insert_usuario.setString(2, usuario.getNome());
				insert_usuario.setInt(3, id_endereco);
				insert_usuario.setString(4, usuario.getTurno());
				insert_usuario.setInt(5, usuario.getCategoria().getId());
				insert_usuario.setString(6, usuario.getEmail());
				insert_usuario.executeUpdate();
			}catch (SQLException e) {
				throw new InsertException("Erro ao inserir usuário.");
			}		
	}

	public void insert_telefone(int usuario, String telefone) throws InsertException {
		try {
			insert_telefone.setInt(1, usuario);
			insert_telefone.setString(2, telefone);
			insert_telefone.executeUpdate();
		}catch (SQLException e) {
			throw new InsertException("Erro ao inserir telefone.");
		}
	}
	
	public int insert_endereco(Endereco endereco) throws InsertException, SelectException, JaCadastradoException{
		try {
			int id = select_new_id_endereco();
			insert_endereco.setInt(1, id);
			insert_endereco.setString(2, endereco.getEstado());
			insert_endereco.setString(3,  endereco.getCidade());
			insert_endereco.setString(4, endereco.getBairro());
			insert_endereco.setString(5, endereco.getRua());
			insert_endereco.setInt(6, endereco.getNumero());
			insert_endereco.setString(7, endereco.getComplemento());
			insert_endereco.executeUpdate();
			return id;
		}catch (SQLException e) {
			throw new InsertException("Erro ao inserir endereço.");
		}		
	}

	public void update_usuario(Usuario usuario) throws UpdateException, SelectException, NaoCadastradoException{

		update_endereco(usuario.getEndereco());
		try {
			update_usuario.setString(1, usuario.getNome());
			update_usuario.setString(2, usuario.getTurno());
			update_usuario.setInt(3, usuario.getCategoria().getId());
			update_usuario.setInt(4, usuario.getEndereco().getId());
			update_usuario.setString(5, usuario.getEmail());
			update_usuario.setInt(6, usuario.getId());
			update_usuario.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar usuário.");
		}
	}
	
	public void update_endereco(Endereco endereco) throws UpdateException, SelectException, NaoCadastradoException{
		try {
			update_endereco.setString(1, endereco.getEstado());
			update_endereco.setString(2, endereco.getCidade());			
			update_endereco.setString(3, endereco.getBairro());
			update_endereco.setString(4, endereco.getRua());
			update_endereco.setInt(5, endereco.getNumero());
			update_endereco.setString(6, endereco.getComplemento());
			update_endereco.setInt(7, endereco.getId());
			update_endereco.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar endereço do usuário.");
		}
	}

	public void update_telefone(int usuario, String novo_telefone,  String antigo_telefone) throws UpdateException {
		try {
			update_telefone.setString(1, novo_telefone);
			update_telefone.setString(2, antigo_telefone);
			update_telefone.setInt(3, usuario);
			update_telefone.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar o telefone do usuário.");
		}
	}
	
	public  Endereco select_endereco(int id_endereco) throws SelectException {
		Endereco endereco = new Endereco();
		try {
			select_endereco.setInt(1, id_endereco);
			ResultSet rs = select_endereco.executeQuery();
			if(rs.next()) {
				endereco.setId(rs.getInt(1));
				endereco.setEstado(rs.getString(2));
				endereco.setCidade(rs.getString(3));
				endereco.setBairro(rs.getString(4));
				endereco.setRua(rs.getString(5));
				endereco.setNumero(rs.getInt(6));
				endereco.setComplemento(rs.getString(7));		
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar endereço");
		}
		return endereco;
	}
	
	public List<Object> select_telefones(int id_usuario) throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			select_telefones.setInt(1, id_usuario);
			ResultSet rs = select_telefones.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getString(1)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar endereço");
		}
		return lista;	
	}
	
	public List<Object> select_table() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_table.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), select_endereco(rs.getInt(5)).endereco_completo(), rs.getInt(5), rs.getString(6)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela");
		}
		return lista;
	}
	
	public List<Object> select_table_emprestimos() throws SelectException {
		List<Object> lista = new ArrayList<Object>();
		try {
			ResultSet rs = select_table_emprestimos.executeQuery();
			while(rs.next()) {
				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4)};
				lista.add(linha);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar dados para preencher a tabela");
		}
		return lista;
	}

	
	public void delete_telefone(int usuario, String telefone) throws DeleteException, SelectException, NaoCadastradoException{
		try {
			delete_telefone.setInt(1, usuario);
			delete_telefone.setString(2, telefone);
			delete_telefone.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar telefone");
		}
	}

}
	
