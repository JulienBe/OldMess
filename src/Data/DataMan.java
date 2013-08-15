package Data;

import java.sql.Connection;

import vaisseaux.TypesArmes;

public class DataMan {
	
	Connection connection;
	Database db;

	public DataMan() {
		creerDb();
	}
	
	

	private void creerDb() {
		db = new Database();
	}

	public int getNvArme(TypesArmes arme) {
		return db.getNvArme(arme);
	}



	public void recupProfil() {
//		CSG.profil.init();
	}
}
