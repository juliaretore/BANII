package persistencia;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.types.ObjectId;


public class Conexao {
	
	static private MongoDatabase database = null;
	
	public static MongoDatabase getConexao() {
		if(database == null) {
			ConnectionString connString = new ConnectionString("mongodb://127.0.0.1:27017");
			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connString).retryWrites(true).build();
			MongoClient mongoClient = MongoClients.create(settings);
			database = mongoClient.getDatabase("biblioteca").withCodecRegistry(pojoCodecRegistry);
		}
		return database;
	}
	
	
	
}
