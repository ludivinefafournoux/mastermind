public class Algo {

	public static void main(String[] args){
		//Le nombre de pions bien placé
		int redflag = 0;
		
		//Le nombre de pion mal placé
		int whiteflag = 0;
		
		//La réponse a trouver
		int solution[] ={1,5,7,3};
		
		//La proposition de l'utilisateur
		int prop[]={7,7,7,7};
		
		//Tableau qui match les pions bien placés
		int match[]={0,0,0,0};
				
		//On boucle pour voir s'il y a un pion bien placé en vérifiant si solution[i]==prop[i]
		for(int i=0;i<4;i++){
			//Si oui, on marque le drapeau rouge
			if(solution[i]==prop[i]){
				redflag=redflag+1;
				match[i]=1;
			}
		}
		
		//On boucle pour voir s'il y a d'autres pion mal placé	
		for(int u=0;u<4;u++){
			
			for(int a=0;a<4;a++){
				//Si match[a]==0, cela veut dire qu'on a pas trouvé précédemment que solution[i]==prop[i]
				//Si on trouve qu'un des nombres restants dans prop est présent dans solution, et qu'il ne se trouve pas au même rang dans le 2 tableau, alors la condition est valide
				if(prop[u]==solution[a] && match[a]==0){
					whiteflag=whiteflag+1;
				}
				
			}
			
		}
		
		System.out.println("Le nombre de pion bien placé est"+redflag);
		System.out.println("Le nombre de pion mal placé est"+whiteflag);
	}
		
		

}

