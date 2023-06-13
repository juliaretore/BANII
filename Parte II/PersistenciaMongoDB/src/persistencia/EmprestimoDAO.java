package persistencia;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
	//    3- Multas:atualizar o valor por dia
	//    4- Cancelar as reservas depois de 7 dias
	
	// Revisar:
	//    1- pro próximo da fila, nao seria melhor fazer um sort? Como?
	// 


	
	public String proximo_fila_reserva(ObjectId id_livro)  {
		Document livro = collection_livro.find(eq("_id", id_livro)).first();
		List<Document>  reservas =  (List<Document>) livro.get("reservas");
		if (reservas.isEmpty()) return null;
		else return reservas.get(0).getString("usuario_reserva");
	}
	
	
	public void devolucao_emprestimo(String cid_emprestimo) throws Exception {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Document emprestimo = collection_emprestimo.find(eq("_id",  new ObjectId(cid_emprestimo))).first();
			Document exemplar = collection_exemplar.find(eq("_id",  new ObjectId(emprestimo.getString("id_exemplar")))).first();
			String id_livro = collection_exemplar.find(eq("_id",  new ObjectId(emprestimo.getString("id_exemplar")))).first().getString("livro");
			Document livro = collection_livro.find(eq("_id",  new ObjectId(id_livro))).first();
			String usuario = proximo_fila_reserva(new ObjectId(id_livro));
			collection_emprestimo.updateOne(eq("_id", new ObjectId(cid_emprestimo)), combine(set("situacao", 1), set("data_real_entr", dateFormat.format(date))));	
			if (usuario!=null) {
				List<Document>  reservas =  (List<Document>) livro.get("reservas");
				reservas.remove(0);
				collection_livro.updateOne(eq("_id", new  ObjectId(id_livro)), combine(set("reservas", reservas)));
				collection_exemplar.updateOne(eq("_id", exemplar.getObjectId("_id")), combine(set("usuario_reserva", usuario), set("data_reserva", dateFormat.format(date))));	
				JOptionPane.showMessageDialog(null, "Livro reservado. Não devolver para a prateleira");
			}
		}catch (Exception e ) {
			throw new InsertException("Erro ao devolver emprestimo");
		}
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
			throw new InsertException("Erro inserir emprestimo");

		}	
	}
	public void insert_emprestimo_reserva(String cid_exemplar, String cid_usuario, String cid_funcionario) throws Exception{
		try {
			Document exemplar = collection_exemplar.find(eq("_id",  new ObjectId(cid_exemplar))).first();
			collection_exemplar.updateOne(eq("_id", new ObjectId(cid_exemplar)), combine(set("data_reserva", null), set("usuario_reserva", null)));				                                                          
			insert_emprestimo(cid_exemplar, cid_usuario, cid_funcionario);
		}catch (Exception e) {
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
			reservas.add(document);
			collection_livro.updateOne(eq("_id", cid_livro), combine(set("reservas", reservas)));
		}catch (Exception e) {
			throw new InsertException("Erro inserir reserva");
		}	
	}
	

	public void pagar_multa(String cid_usuario) throws Exception{
		try {
			Bson query = Filters.and(Filters.eq("id_usuario", cid_usuario), Filters.eq("situacao", 1));
			collection_emprestimo.updateMany(query, combine(set("pagamento_multa", 0)));	
		}catch (Exception e) {
			throw new InsertException("Erro pagar multa");
		}	
	}
	
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

	public List<Object> select_historico_exemplar(String id_exemplar) throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
			MongoIterable<Document> emprestimos = collection_emprestimo.find(eq("id_exemplar", id_exemplar));
			for(Document emprestimo : emprestimos) {
				Document usuario = collection_usuario.find(eq("_id", new ObjectId(emprestimo.getString("id_usuario")))).first();
				String situacao = "";
				String pagamento_multa = "";
				
				if(emprestimo.getInteger("situacao") == 0) situacao="Não devolvido";
				else situacao="Devolvido";

				if(emprestimo.getInteger("pagamento_multa") == 1) pagamento_multa="Em aberto";
				else pagamento_multa = "Pago";
				
				Object[] linha  = {emprestimo.getObjectId("_id"), usuario.getString("nome"), emprestimo.getString("data_empr"), emprestimo.getString("data_est_entr"), emprestimo.getString("data_real_entr"), situacao, emprestimo.getDouble("multa"), pagamento_multa};
				lista.add(linha);		
			}
		}catch(Exception e) {
			throw new SelectException("Erro ao buscar historico para adicionar");
		}
		return lista;
	}	
	
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

	public List<Object> select_pagar_multas() throws Exception {
		List<Object> lista = new ArrayList<Object>();
		try {
	        Document matchStage = new Document("$match", new Document("situacao", 1).append("pagamento_multa", 1));
	        Document groupStage = new Document("$group", new Document("_id", "$id_usuario").append("total_multa", new Document("$sum", "$multa")));
	        MongoCursor<Document> cursor = collection_emprestimo.aggregate(Arrays.asList(matchStage, groupStage)).iterator();
	        while(cursor.hasNext()){
	            Document result = cursor.next();
				Document usuario = collection_usuario.find(eq("_id", new ObjectId(result.getString("_id")))).first();            
	        	Object[] linha  = {result.getString("_id"), usuario.getString("nome"), result.getDouble("total_multa")};
				lista.add(linha);
	        }		
		} catch(Exception e) {
			throw new SelectException("Erro ao buscar multas");
		}
		return lista;
	}
	

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
	
