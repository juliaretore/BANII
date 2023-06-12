package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import com.mongodb.client.model.Sorts;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

import exceptions.InsertException;
import exceptions.SelectException;

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
	
	//TO-DO:
	//    1- não permitir empréstimo se usuario tem livros atrasados, multa ou excedeu o numero max de emprestimos;
	//    2- não permitir mais de 3 renovações em um empréstimo;
	
	// Revisar:
	//    1- pro próximo da fila, nao seria melhor fazer um sort? Como?

	
//	private EmprestimoDAO() throws ClassNotFoundException, SQLException, SelectException{
//		devolucao_emprestimo = conexao.prepareStatement("select devolucao_emprestimo(?)");
//		atualizar_multas = conexao.prepareStatement("select atualiza_multas()");
//		verifica_datas_reservas = conexao.prepareStatement("select verifica_datas_reservas()");
//		delete_reserva = conexao.prepareStatement("delete from reservas_livro where id_livro=? and id_usuario=?");
//		pagar_multa = conexao.prepareStatement("select pagamento_multas(?)");
//		select_historico_exemplar = conexao.prepareStatement("select e.id,  u.nome, e.data_empr, e.data_est_entr, e.data_real_entr, e.situacao, e.multa, e.pagamento_multa from emprestimo e join usuario u on u.id=e.id_usuario where id_exemplar=?");
//		select_pagar_multas = conexao.prepareStatement("select e.id_usuario, u.nome, sum(multa) from emprestimo e join usuario u on u.id=e.id_usuario where e.situacao=1 and e.pagamento_multa=1 group by e.id_usuario, u.nome");
//	}
	
	public String proximo_fila_reserva(ObjectId id_livro)  {
		Document livro = collection_livro.find(eq("_id", id_livro)).first();
		List<Document>  reservas =  (List<Document>) livro.get("reservas");
		if (reservas.isEmpty()) return null;
		else return reservas.get(0).getString("usuario_reserva");
	}
	
	public String devolucao_emprestimo(String cid_emprestimo) throws Exception {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Document emprestimo = collection_emprestimo.find(eq("_id",  new ObjectId(cid_emprestimo))).first();
			String id_livro = collection_exemplar.find(eq("_id",  new ObjectId(emprestimo.getString("id_exemplar")))).first().getString("livro");
			Document livro = collection_livro.find(eq("_id",  new ObjectId(id_livro))).first();
			String usuario = proximo_fila_reserva(new ObjectId(id_livro));
			collection_emprestimo.updateOne(eq("_id", new ObjectId(cid_emprestimo)), combine(set("situacao", 1), set("data_real_entr", dateFormat.format(date))));	
			if (usuario!=null) {
				collection_exemplar.updateOne(eq("_id", new ObjectId(emprestimo.getString("id_exemplar"))), combine(set("usuario_reserva", usuario), set("data_reserva", dateFormat.format(date))));	
				List<Document>  reservas =  (List<Document>) livro.get("reservas");
				reservas.remove(0);
				collection_livro.updateOne(eq("_id", new  ObjectId(id_livro)), combine(set("reservas", reservas)));
				JOptionPane.showMessageDialog(null, "Livro reservado. Não devolver para a prateleira");
			}

		}catch (Exception e ) {
			throw new InsertException("Erro ao devolver emprestimo");
		}
		return "";
	}

	
	public Integer dias_emprestimo(ObjectId id_usuario) throws Exception{
		try {
			Document usuario = collection_usuario.find(eq("_id", id_usuario)).first();
			Document categoria = collection_categoria.find(eq("_id", usuario.getInteger("categoria_id"))).first();
			return categoria.getInteger("tempo_empr");
		}catch (Exception e) {
			throw new InsertException("Erro ao buscar dias do emprestimo");
		}
	}

	public void insert_emprestimo(String cid_exemplar, String cid_usuario, String cid_funcionario) throws Exception{
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    int dias = dias_emprestimo(new ObjectId(cid_usuario));
		    Calendar c = Calendar.getInstance();
		    c.add(c.DATE, dias);
		    Date data_estimada= c.getTime();
		    System.out.println(data_estimada);
		    Date date = new Date();
		    Document document = new Document(
					"multa", 0.0)
					.append("pagamento_multa", 0)
					.append("renovacoes", 0)
					.append("data_empr", dateFormat.format(date))
					.append("data_est_entr", dateFormat.format(data_estimada))
					.append("data_real_entr", null)
					.append("id_usuario", cid_usuario)
					.append("id_funcionario", cid_funcionario)
					.append("id_exemplar", cid_exemplar)
					.append("situacao", 0);
			collection_emprestimo.insertOne(document);
		}catch (Exception e) {
//			String texto[] = e.getMessage().split("\\r?\\n");
//			JOptionPane.showMessageDialog(null, texto[0]);
			throw new InsertException("Erro inserir emprestimo");

		}	
	}
	public void insert_emprestimo_reserva(String cid_exemplar, String cid_usuario, String cid_funcionario) throws Exception{
		try {
			Document exemplar = collection_exemplar.find(eq("_id",  new ObjectId(cid_exemplar))).first();
			collection_exemplar.updateOne(eq("_id", new ObjectId(cid_exemplar)), combine(set("data_reserva", null), set("usuario_reserva", null)));				                                                          
			insert_emprestimo(cid_exemplar, cid_usuario, cid_funcionario);
		}catch (Exception e) {
//			String texto[] = e.getMessage().split("\\r?\\n");
//			JOptionPane.showMessageDialog(null, texto[0]);
			throw new InsertException("Erro inserir emprestimo");
		}	
	}

	public void renovar_emprestimo(ObjectId cid_emprestimo) throws Exception{
	try {
		Document emprestimo = collection_emprestimo.find(eq("_id", cid_emprestimo)).first();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    int dias = dias_emprestimo(new ObjectId(emprestimo.getString("id_usuario")));
	    Calendar c = Calendar.getInstance();
	    c.setTime(emprestimo.getDate(("data_est_entr")));
	    c.add(c.DATE, dias);
	    Date data_estimada= c.getTime();
		collection_emprestimo.updateOne(eq("_id", cid_emprestimo), combine(set("renovacoes", (emprestimo.getInteger("renovacoes")+1)), set("data_est_entr", data_estimada)));				                                                          
	}catch (Exception e) {
//		String texto[] = e.getMessage().split("\\r?\\n");
//		JOptionPane.showMessageDialog(null, texto[0]);
		throw new InsertException("Erro inserir renovar");

	}	
}
	
	public void insert_reserva(ObjectId cid_livro, String cid_usuario) throws Exception{
		try {
			Document livro = collection_livro.find(eq("_id", cid_livro)).first();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		    Date date = new Date();
			Document document = new Document(
					"usuario_reserva", cid_usuario)
					.append("data_reserva", dateFormat.format(date));
			List<Document>  reservas =  (List<Document>) livro.get("reservas");
			reservas.add(document);System.out.println("g");
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
	
	public List<Object> select_emprestimos_correntes() throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> emprestimos = collection_emprestimo.find(eq("situacao",0));
			for(Document emprestimo : emprestimos) {
				Document usuario = collection_usuario.find(eq("_id", new ObjectId(emprestimo.getString("id_usuario")))).first();
				Document exemplar = collection_exemplar.find(eq("_id",  new ObjectId(emprestimo.getString("id_exemplar")))).first();
				Document livro = collection_livro.find(eq("_id",  new ObjectId(exemplar.getString("livro")))).first();
				Object[] linha  = {emprestimo.getObjectId("_id"), usuario.getString("nome"), livro.getString("titulo"),  exemplar.getObjectId("_id"), emprestimo.getString("data_empr"), emprestimo.getString("data_est_entr"), emprestimo.getInteger("renovacoes")};		
				lista.add(linha);
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar emprestimos correntes");
		}
		return lista;
	}
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
	

	public List<Object> select_reservas_ativas() throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> exemplares = collection_exemplar.find(Filters.ne("usuario_reserva", null));
			for(Document exemplar : exemplares) {
				Document usuario = collection_usuario.find(eq("_id", new ObjectId(exemplar.getString("usuario_reserva")))).first();
				Document livro = collection_livro.find(eq("_id",  new ObjectId(exemplar.getString("livro")))).first();
				Object[] linha  = {exemplar.getString("usuario_reserva"), usuario.getString("nome"), livro.getString("isbn"), livro.getString("titulo"),  exemplar.getObjectId("_id")};		
				lista.add(linha);
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar reservas");
		}
		return lista;
	}

	public List<Object> select_fila_reserva() throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> livros = collection_livro.find();
			for(Document livro : livros) {
				List<Document>  reservas =  (List<Document>) livro.get("reservas");
				for(Document reserva: reservas) {
					Document usuario = collection_usuario.find(eq("_id", new ObjectId(reserva.getString("usuario_reserva")))).first();
					Object[] linha  = {usuario.getString("nome"), livro.getString("isbn"), livro.getString("titulo"),  reserva.getString("data_reserva")};		
					lista.add(linha);
				}
			}
			
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar fila de reserva");
		}
		return lista;
	}
	
	
	
}
	
