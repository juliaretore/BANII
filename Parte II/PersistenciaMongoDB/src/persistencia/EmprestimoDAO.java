package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

import apresentacao.EmprestimoView;
import dados.Funcionario;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;

public class EmprestimoDAO {
	private static EmprestimoDAO instance = null;
	private static MongoCollection<Document> collection_exemplar;
	private static MongoCollection<Document> collection_livro;
	private static MongoCollection<Document> collection_emprestimo;
	private static MongoCollection<Document> collection_usuario;
	private static MongoCollection<Document> collection_categoria;

	private static MongoDatabase connection;

	public static EmprestimoDAO getInstance() throws Exception {
		if (instance == null)  instance = new EmprestimoDAO();
		return instance;
	}
	
	private EmprestimoDAO() throws  Exception {
	connection = Conexao.getConexao();
	try {
		collection_exemplar = connection.getCollection("exemplar");
		collection_livro = connection.getCollection("livro");
		collection_emprestimo = connection.getCollection("emprestimo");
		collection_usuario = connection.getCollection("usuario");
		collection_categoria = connection.getCollection("categoria");

	} catch (Exception e) {
		throw new SelectException("Erro ao conectar a coleção");
	}
}
	
//	private EmprestimoDAO() throws ClassNotFoundException, SQLException, SelectException{
//		Connection conexao = Conexao.getConexao();
//		insert_emprestimo =  conexao.prepareStatement("select cadastro_emprestimo(?, ?, ?)");
//		insert_emprestimo_reserva = conexao.prepareStatement("select cadastro_emprestimo_reserva(?, ?, ?)");
//		devolucao_emprestimo = conexao.prepareStatement("select devolucao_emprestimo(?)");
//		atualizar_multas = conexao.prepareStatement("select atualiza_multas()");
//		renovar_emprestimo = conexao.prepareStatement("select renovar_emprestimo(?)");
//		verifica_datas_reservas = conexao.prepareStatement("select verifica_datas_reservas()");
//		insert_reserva = conexao.prepareStatement("select cadastra_reserva(?,?)");
//		delete_reserva = conexao.prepareStatement("delete from reservas_livro where id_livro=? and id_usuario=?");
//		pagar_multa = conexao.prepareStatement("select pagamento_multas(?)");
//		select_data_emprestimo = conexao.prepareStatement("select ((select c.tempo_empr from categoria c join usuario u on u.id_categoria=c.id where u.id=?)+current_date)");
//		select_historico_exemplar = conexao.prepareStatement("select e.id,  u.nome, e.data_empr, e.data_est_entr, e.data_real_entr, e.situacao, e.multa, e.pagamento_multa from emprestimo e join usuario u on u.id=e.id_usuario where id_exemplar=?");
//		select_emprestimos_correntes = conexao.prepareStatement("select e.id, u.nome, l.titulo, ex.id, e.data_empr, e.data_est_entr, e.renovacoes from emprestimo e join usuario u on u.id=e.id_usuario join exemplar ex on ex.id=e.id_exemplar join livro l on l.id=ex.id_livro where e.situacao=0");
//		select_pagar_multas = conexao.prepareStatement("select e.id_usuario, u.nome, sum(multa) from emprestimo e join usuario u on u.id=e.id_usuario where e.situacao=1 and e.pagamento_multa=1 group by e.id_usuario, u.nome");

//	}
	
//	public void insert_emprestimo(int cid_exemplar, int cid_usuario, int cid_funcionario) throws InsertException, SelectException, JaCadastradoException{
//			try {
//				insert_emprestimo.setInt(1, cid_exemplar);
//				insert_emprestimo.setInt(2, cid_usuario);
//				insert_emprestimo.setInt(3, cid_funcionario);				
//				insert_emprestimo.execute();
//			}catch (SQLException e) {
//				String texto[] = e.getMessage().split("\\r?\\n");
//				JOptionPane.showMessageDialog(null, texto[0]);
//			}	
//	}
//	
//	public void insert_emprestimo_reserva(int cid_exemplar, int cid_usuario, int cid_funcionario) throws InsertException, SelectException, JaCadastradoException{
//		try {
//			insert_emprestimo_reserva.setInt(1, cid_exemplar);
//			insert_emprestimo_reserva.setInt(2, cid_usuario);
//			insert_emprestimo_reserva.setInt(3, cid_funcionario);				
//			insert_emprestimo_reserva.execute();
//		}catch (SQLException e) {
//			String texto[] = e.getMessage().split("\\r?\\n");
//			JOptionPane.showMessageDialog(null, texto[0]);
//		}	
//	}	
//
//	public String devolucao_emprestimo(int cid_emprestimo) throws InsertException, SelectException, JaCadastradoException, SQLWarning{
//		try {
//			devolucao_emprestimo.setInt(1, cid_emprestimo);
//			devolucao_emprestimo.execute();
//			if(devolucao_emprestimo.getWarnings()!=null) return devolucao_emprestimo.getWarnings().getMessage();
//		}catch (SQLException e ) {
//			String texto[] = e.getMessage().split("\\r?\\n");
//			JOptionPane.showMessageDialog(null, texto[0]);
//		}
//		return "";
//	}
//	
	
	public Integer dias_emprestimo(ObjectId id_usuario) throws Exception{
		try {
			Document usuario = collection_usuario.find(eq("_id", id_usuario)).first();
			Document categoria = collection_categoria.find(eq("_id", usuario.getInteger("categoria_id"))).first();
			return categoria.getInteger("tempo_empr");
		}catch (Exception e) {
			throw new InsertException("Erro ao buscar dias do emprestimo");
		}
	}

//	public void renovar_emprestimo(ObjectId cid_emprestimo) throws Exception{
//	try {
////		renovar_emprestimo.setInt(1, cid_emprestimo);
////		renovar_emprestimo.execute();
//		Document exmprestimo = collection_emprestimo.find(eq("_id", cid_emprestimo)).first();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		Date data_estimada = new Date();
//	    Calendar c = Calendar.getInstance();
//	    c.setTime(data_estimada);
//	    c.add(Calendar.DATE, 15);
//	    data_estimada= c.getTime();
//	    EmprestimoView.tfData.setText(String.valueOf(dateFormat.format(data_estimada))); 
//
//	}catch (Exception e) {
////		String texto[] = e.getMessage().split("\\r?\\n");
////		JOptionPane.showMessageDialog(null, texto[0]);
//	}	
//}
	
	public void insert_reserva(ObjectId cid_livro, String cid_usuario, String data) throws Exception{
		try {
			Document livro = collection_livro.find(eq("_id", cid_livro)).first();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date data_formatada = dateFormat.parse(data);
			Document document = new Document(
					"usuario_reserva", cid_usuario)
					.append("data_reserva", data_formatada);
			List<Document>  reservas =  (List<Document>) livro.get("reservas");
			reservas.add(document);
			collection_livro.updateOne(eq("_id", cid_livro), combine(set("reservas", reservas)));
		}catch (Exception e) {
			throw new InsertException("Erro inserir reserva");
		}	
	}
//	
//	public void delete_reserva(int cid_livro, int cid_usuario) throws InsertException, SelectException, JaCadastradoException{
//		try {
//			delete_reserva.setInt(1, cid_livro);
//			delete_reserva.setInt(2, cid_usuario);
//			delete_reserva.executeUpdate();
//		}catch (SQLException e) {
//			throw new InsertException("Erro cancelar reserva");
//		}	
//	}
//	
//	public void pagar_multa(int cid_usuario) throws InsertException, SelectException, JaCadastradoException{
//		try {
//			pagar_multa.setInt(1, cid_usuario);
//			pagar_multa.execute();
//		}catch (SQLException e) {
//			throw new InsertException("Erro pagar multa");
//		}	
//	}
//	
//	public void atualizar_multas() throws InsertException, SelectException, JaCadastradoException{
//		try {
//			atualizar_multas.execute();
//		}catch (SQLException e) {
//			throw new InsertException("Erro ao atualizar multas");
//		}	
//	}
//	
//	public void verifica_datas_reservas() throws InsertException, SelectException, JaCadastradoException{
//		try {
//			verifica_datas_reservas.execute();
//		}catch (SQLException e) {
//			throw new InsertException("Erro ao verificar datas reservas");
//		}	
//	}
//	
//	public String select_data_emprestimo(int cid_usuario) throws InsertException {
//		try {
//			select_data_emprestimo.setInt(1, cid_usuario);
//			ResultSet rs = select_data_emprestimo.executeQuery();
//			rs.next();
//			return rs.getString(1);
//		}catch (SQLException e) {
//			throw new InsertException("Erro determinar a data de devolução do emprestimo");
//		}
//		
//	}
//	
//	public List<Object> select_historico_exemplar(int id_exemplar) throws SelectException {
//		List<Object> lista = new ArrayList<Object>();
//		try {
//			select_historico_exemplar.setInt(1, id_exemplar);
//			ResultSet rs = select_historico_exemplar.executeQuery();
//			while(rs.next()) {
//				String situacao = "";
//				String pagamento_multa = "";
//
//				if(rs.getInt(6) == 0) situacao="Não devolvido";
//				else situacao="Devolvido";
//				
//				if(rs.getInt(8) == 1) pagamento_multa="Em aberto";
//				else pagamento_multa = "Pago";
//				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5), situacao, rs.getDouble(7), pagamento_multa};
//				lista.add(linha);
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar historico para adicionar");
//		}
//		return lista;
//	}	
//	
//		
//	public List<Object> select_emprestimos_correntes() throws SelectException {
//		List<Object> lista = new ArrayList<Object>();
//		try {
//			ResultSet rs = select_emprestimos_correntes.executeQuery();
//			while(rs.next()) {
//				Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4),rs.getString(5), rs.getString(6), rs.getInt(7)};
//				lista.add(linha);
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar emprestimos correntes");
//		}
//		return lista;
//	}
//
//	public List<Object> select_pagar_multas() throws SelectException {
//		List<Object> lista = new ArrayList<Object>();
//		try {
//			ResultSet rs = select_pagar_multas.executeQuery();
//			while(rs.next()) {
//				if(rs.getDouble(3)>0) {
//					Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getDouble(3)};
//					lista.add(linha);
//				}
//				
//			}
//		}catch(SQLException e) {
//			throw new SelectException("Erro ao buscar multas");
//		}
//		return lista;
//	}
//	

//("select u.id, u.nome, l.isbn, l.titulo, e.id, 7-abs(e.data_reserva-current_date) from exemplar e join livro l on l.id=e.id_livro join usuario u on u.id=e.id_usuario_reserva where id_usuario_reserva is not null");

//	public List<Object> select_reservas_ativas() throws Exception {
//		List<Object> lista = new ArrayList<Object>();
//		try {
////			ResultSet rs = select_reservas_ativas.executeQuery();
////			while(rs.next()) {
////					Object[] linha  = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5),rs.getInt(6)};
////					lista.add(linha);				
////			}
//			 Bson query = Filters.and(
//	            Filters.ne("usuario_reserva", null));
//	            Document result = (Document) collection_emprestimo.find(query).first();
//			MongoIterable<Document> exemplares = collection_exemplar.find();
//			for(Document livro : livros) {
//				List<Document>  reservas =  (List<Document>) livro.get("reservas");
//				for(Document reserva: reservas) {
//					ObjectId objId_usuario = new ObjectId(reserva.getString("usuario_reserva"));
//					Document usuario = collection_usuario.find(eq("_id", objId_usuario)).first();
//					String nome = usuario.getString("nome");
//					Object[] linha  = {nome, livro.getString("isbn"), livro.getString("titulo"),  reserva.getDate("data_reserva")};		
//					lista.add(linha);
//				}
//			}
//		}catch(Exception e) {
//			throw new SelectException("Erro ao buscar reservas");
//		}
//		return lista;
//	}

	public List<Object> select_fila_reserva() throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> livros = collection_livro.find();
			for(Document livro : livros) {
				List<Document>  reservas =  (List<Document>) livro.get("reservas");
				for(Document reserva: reservas) {
					ObjectId objId_usuario = new ObjectId(reserva.getString("usuario_reserva"));
					Document usuario = collection_usuario.find(eq("_id", objId_usuario)).first();
					String nome = usuario.getString("nome");
					Object[] linha  = {nome, livro.getString("isbn"), livro.getString("titulo"),  reserva.getDate("data_reserva")};		
					lista.add(linha);
				}
			}
			
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar fila de reserva");
		}
		return lista;
	}
	
	
	
}
	
