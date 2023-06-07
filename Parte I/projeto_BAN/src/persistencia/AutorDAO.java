package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dados.Autor;
import dados.Funcionario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class AutorDAO {
private static AutorDAO instance = null;
	
	private PreparedStatement select_new_id_autor;
	private PreparedStatement insert_autor;
	private PreparedStatement update_autor;
	private PreparedStatement delete_autor;
	
	

	public static AutorDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance==null) instance=new AutorDAO();
		return instance;
	}
	
	private AutorDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		select_new_id_autor = conexao.prepareStatement("select nextval('id_autor')");
		insert_autor =  conexao.prepareStatement("insert into autor values (?,?,?,?)");
		update_autor = conexao.prepareStatement("update autor set nacionalidade=?, area=?, nome=? where id=?");
		delete_autor = conexao.prepareStatement("delete from autor where id=?");
	}
	
	private int select_new_id_autor() throws SelectException{
		try {
			ResultSet rs = select_new_id_autor.executeQuery();
			if(rs.next()) return rs.getInt(1);
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar novo ID da tabela autor");
		}
		return 0;
	}
	
	public void insert_autor(Autor autor) throws InsertException, SelectException, JaCadastradoException{
			try {
				insert_autor.setInt(1, select_new_id_autor());
				insert_autor.setString(2, autor.getNacionalidade());
				insert_autor.setString(3, autor.getArea());
				insert_autor.setString(4, autor.getNome());
				insert_autor.executeUpdate();
			}catch (SQLException e) {
				throw new InsertException("Erro ao inserir autor.");
			}		
	}
	
	public void update_autor(Autor autor) throws UpdateException, SelectException, NaoCadastradoException{
		try {
			update_autor.setString(1, autor.getNacionalidade());
			update_autor.setString(2, autor.getArea());
			update_autor.setString(3, autor.getNome());
			update_autor.setInt(4, autor.getId());
			update_autor.executeUpdate();
		}catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar autor.");
		}
	}
	
	public void delete_autor(int autor) throws DeleteException, SelectException, NaoCadastradoException{
		try {
			delete_autor.setInt(1, autor);
			delete_autor.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar autor");
		}
	}
	
}