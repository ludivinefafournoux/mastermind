import java.util.Random;
import Java.util.*;

public class Nbrdm {
	static int test[]={0,0,0,0};
	
	//Methode pour générer un nombre aléatoire
	public int nbrand(){
		Random rand = new Random();
		int nb = 1*(rand.nextInt(9-1))+1;
		return nb;
				
	}
	
	public static void main(String[] args){
		//Attribue 4 nombres aléatoires à chaque case d'un tableau
		for(int b=0;b<4;b++){
			test[b]=nbrand();
			System.out.println(test[b]);
		}
				
	}
		
}
