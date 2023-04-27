package prueba.bd.dam;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import utils.DAO;

public class Principal {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		byte opcion = 0;
		do {
			System.out.println("¿Qué quieres hacer en la bd?\n\t0- Salir" + "\n\t1- Insertar usuario"
					+ "\n\t2- Borrar usuario" + "\n\t3- Modificar usuario" + "\n\t4- Consultar usuarios");
			opcion = Byte.parseByte(sc.nextLine());
			switch (opcion) {
			case 1:
				try {
					System.out.println("Dime email");
					String email = sc.nextLine();
					System.out.println("Dime contraseña");
					String pass = sc.nextLine();
					System.out.println("Dime nick");
					String nick = sc.nextLine();

					HashMap<String,Object> columnas=new HashMap<String,Object>();
					columnas.put("email",email);
					columnas.put("pass", pass);
					columnas.put("nick", nick);
					DAO.insertar("user",columnas);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					System.out.println("Dime el email del usuario que quieres borrar");
					String email = sc.nextLine();
					
					HashMap<String,Object> campos=new HashMap<String,Object>();
					campos.put("email", email);
					int filasBorradas=DAO.borrar("user",campos);
					
					
					System.out.println("Se han borrado "+filasBorradas+" filas.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:

				break;
			case 4:

				break;
			}
		} while (opcion != 0);

	}

}
