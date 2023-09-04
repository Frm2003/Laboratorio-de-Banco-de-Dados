package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.ClienteControle;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		ClienteControle cc = new ClienteControle();
		
		int opc = 0;
		
		while (opc != 9) {
			String cpf, nome, email, dataNasc;
			double limiteCred;
			
			System.out.println("1 - inserir \n2 - buscar \n3 - atualizar \n4 - remover");
			opc = sc.nextInt();
			
			try {
				switch (opc) {
				case 1:
					System.out.println("\n-------------------");
					System.out.println("INSERIR");
					
					System.out.print("Digite o cpf:");
					cpf = sc.next();
					System.out.print("Digite o nome:");
					nome = sc.next();
					System.out.print("Digite o email:");
					email = sc.next();
					System.out.print("Digite o limite de credito:");
					limiteCred = sc.nextDouble();
					System.out.print("Digite a data (dd/mm/aaaa):");
					dataNasc = sc.next();
					System.out.println(cc.inserir(cpf, nome, email, limiteCred, dataNasc));
					System.out.println("-------------------");
					break;
				case 2:
					System.out.println("\n-------------------");
					System.out.println("BUSCAR");
					
					System.out.print("Digite o cpf:");
					cpf = sc.next();
					System.out.println(cc.buscar(cpf).toString());
					System.out.println("-------------------");
					break;
				case 3:
					System.out.println("\n-------------------");
					System.out.println("ATUALIZAR");
					
					System.out.print("Digite o cpf:");
					cpf = sc.next();
					System.out.print("Digite o nome:");
					nome = sc.next();
					System.out.print("Digite o email:");
					email = sc.next();
					System.out.print("Digite o limite de credito:");
					limiteCred = sc.nextDouble();
					System.out.print("Digite a data (dd/mm/aaaa):");
					dataNasc = sc.next();
					
					cc.atualizar(cpf, nome, email, limiteCred, dataNasc);
					System.out.println("-------------------");
					break;
				case 4:
					System.out.println("\n-------------------");
					System.out.println("REMOVER");
					
					System.out.print("Digite o cpf:");
					cpf = sc.next();
					cc.remover(cpf);
					System.out.println("-------------------");
					break;
				case 9:
					System.exit(0);
					break;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
