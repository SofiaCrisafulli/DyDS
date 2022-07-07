package dyds.solid.ejA;

import java.util.ArrayList;

//Este repo es de juguete, cambiar a algo posta en otro Sprint!!!
public class RepositorioDummy implements Repositorio{

	private ArrayList<Contenido> dummyDB = new ArrayList<>();

	public boolean grabar(Contenido contenido) {
		//Codigo dummy para el ejericio
		dummyDB.add(contenido);
		return true;
	}

	public int getRepoSizeForTesting(){
		return dummyDB.size();
	}

}
