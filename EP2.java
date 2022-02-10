/*********************************************************************************/
/**   ACH2002 - Introducao a Analise de Algoritmos                  			**/
/**   EACH-USP - Segundo Semestre de 2020                           			**/
/**   Turma: 94 - Professor: Flavio Luiz Coutinho                               **/
/**                                                                 			**/
/**   Priemiro Exercicio-Programa 2                                   			**/
/**                                                                 			**/
/**   Nome das alunas: 															**/
/** 				  Daniella Fernanda Cisterna Melo   Numero USP: 11796281    **/
/** 				  Gabriela Jie Han                  Numero USP: 11877423    **/
/**                                                                 			**/
/**   Data de entrega: 22/01/2021                                      			**/
/*********************************************************************************/
import java.io.*;
import java.util.*;

class Item {

	private int lin, col, value, weight;

	public Item(String s){

		String [] parts = s.split(" +");		
		lin = Integer.parseInt(parts[0]);
		col = Integer.parseInt(parts[1]);
		value = Integer.parseInt(parts[2]);
		weight = Integer.parseInt(parts[3]);
	}

	public Item(int lin, int col, int value, int weight){

		this.lin = lin;
		this.col = col;
		this.value = value;
		this.weight = weight;
	}

	public int getLin(){

		return lin;
	}

	public int getCol(){

		return col;
	}

	public int [] getCoordinates(){

		return new int [] { getLin(), getCol() } ;
	}

	public int getValue(){

		return value;
	}

	public int getWeight(){

		return weight;
	}

	public String toString(){

		return "Item: coordinates = (" + getLin() + ", " + getCol() + "), value = " + getValue() + " weight = " + getWeight();
	}
}

class Map {

	public static final char FREE = '.';

	private char [][] map;
	private Item [] items;
	private int nLin, nCol, nItems, startLin, startCol, endLin, endCol, size;

	public Map(String fileName){

		try{

			BufferedReader in = new BufferedReader(new FileReader(fileName));

			Scanner scanner = new Scanner(new File(fileName));

			
			nLin = scanner.nextInt();
			nCol = scanner.nextInt();

			map = new char[nLin][nCol];
			size = 0;

			for(int i = 0; i < nLin; i++){

				String line = scanner.next();
			
				for(int j = 0; j < nCol; j++){

					map[i][j] = line.charAt(j);

					if(free(i, j)) size++;
				}
			}

			nItems = scanner.nextInt();
			items = new Item[nItems];

			for(int i = 0; i < nItems; i++){

				items[i] = new Item(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
			}

			startLin = scanner.nextInt();
			startCol = scanner.nextInt();
			endLin = scanner.nextInt();
			endCol = scanner.nextInt();
		}
		catch(IOException e){

			System.out.println("Error loading map... :(");
			e.printStackTrace();
		}
	}

	public void print(){

		System.out.println("Map size (lines x columns): " + nLin + " x " + nCol);

		for(int i = 0; i < nLin; i++){

			for(int j = 0; j < nCol; j++){

				System.out.print(map[i][j]);
			}

			System.out.println();
		}

		System.out.println("Number of items: " + nItems);

		for(int i = 0; i < nItems; i++){

			System.out.println(items[i]);
		}
	}

	public boolean blocked(int lin, int col){

		return !free(lin, col);
	}

	public boolean free(int lin, int col){

		return map[lin][col] == FREE; 
	}

	public void step(int lin, int col){

		map[lin][col] = '*';
	}

	//desfaz o passo
	public void unStep(int lin, int col){

		map[lin][col] = '.';
	}

	public boolean finished(int lin, int col){

		return (lin == endLin && col == endCol); 
	}

	public int getStartLin(){

		return startLin;
	}

	public int getStartCol(){

		return startCol;
	}

	public int getSize(){

		return size;
	}

	public int nLines(){

		return nLin;
	}

	public int nColumns(){

		return nCol;
	}

	public Item getItem(int lin, int col){

		for(int i = 0; i < items.length; i++){

			Item item = items[i];

			if(item.getLin() == lin && item.getCol() == col) return item;
		}

		return null;
	}
}

/*********************************************************************************/
/*	Objeto e construtor criado para facilitar a encontrar a coordernada			 */
/*  do ponto no mapa		 													 */
/*********************************************************************************/ 
class Coordenadas{

	private int lin, col;

	//construtor
	public Coordenadas(int lin, int col){
		this.lin = lin;
		this.col = col;
	}

	public int getLin(){

		return lin;
	}

	public int getCol(){

		return col;
	}
}

/*********************************************************************************/
/*	Objeto e construtor criado para Armazenar duas listas. Uma com todos os 	 */
/*  caminhos possiveis e outra com todos os itens desse caminho.  	 			 */
/*  Alem disso, ha dois constutores, sendo a segunda a copia do primeira      	 */
/*  para que nao apontem para o mesmo objeto e alguns metodos auxiliares		 */
/*********************************************************************************/ 
class ArmazenandoCaminhos{

	private static ArmazenandoCaminhos melhorCaminho = null;

	//Lista com todos os caminhos possiveis
	private ArrayList<Coordenadas> ACaminho;
	//Lista com todos os itens do caminho
	private ArrayList<Item> AItens;

	private double tempoLevado;
	private int valorItens;
	private int valorPeso;
	
	//primeiro construtor
	public ArmazenandoCaminhos(){

		ACaminho = new ArrayList<>();
		AItens = new ArrayList<>();

		this.tempoLevado = -1;
		this.valorItens = 0;
		this.valorPeso = 0;
	}

	//segundo construtor = copia do construtor 1 para nao apontarem para o mesmo objeto 
	public ArmazenandoCaminhos(ArmazenandoCaminhos copia){
		
		//copia das listas
		ACaminho = new ArrayList<>(copia.ACaminho);
		AItens = new ArrayList<>(copia.AItens);

		this.tempoLevado = copia.tempoLevado;
		this.valorItens = copia.valorItens;
		this.valorPeso = copia.valorPeso;
	}

	//metodo que insere as coordenadas e o item e incrementa os atributos
	public void AdicionaValores(Item item, int lin, int col){
		
		Coordenadas co = new Coordenadas(lin, col);
		ACaminho.add(co);

		tempoLevado += (Math.pow((1.0 + valorPeso/10.0), 2));
		if(item != null){
			valorItens += item.getValue();
			valorPeso += item.getWeight();
			AItens.add(item);
		}
	}

	public double gettempoLevado(){

		return tempoLevado;
	}

	public int getvalorItens(){

		return valorItens;
	}

	public int getvalorPeso(){

		return valorPeso;
	}
	
	public ArrayList<Coordenadas> getACaminho(){

		return ACaminho;
	}

	public ArrayList<Item> getAItens(){

		return AItens;
	}

	public static ArmazenandoCaminhos getmelhorCaminho(){

		return melhorCaminho;
	}

	public static void setmelhorCaminho(ArmazenandoCaminhos bestWay){

		ArmazenandoCaminhos.melhorCaminho = bestWay;
	}

	public void Clone(ArmazenandoCaminhos copia){
				
		ACaminho = new ArrayList<>(copia.ACaminho);
		AItens = new ArrayList<>(copia.AItens);

		this.tempoLevado = copia.tempoLevado;
		this.valorItens = copia.valorItens;
		this.valorPeso = copia.valorPeso;
	}

	//metodo que imprime a saida
	public void printPassos(){
		
		//coloca o ponto quando for casa decimal ao inves da virgula
		Locale.setDefault(Locale.US);
		//print tamanho do caminho encontrado
		System.out.print(ACaminho.size()+" ");
		//print tempo para percorrer o caminho
		System.out.printf("%.2f", tempoLevado);
		System.out.println();
			
		//print as coordenadas do caminho, isto e, a lista ACaminho
		for (int i = 0; i<ACaminho.size(); i++){

			System.out.println(ACaminho.get(i).getLin()+" "+ACaminho.get(i).getCol());
		}

		//print a quantidade de itens coletados + valor total dos itens coletados + peso total dos itens coletados
		System.out.println(AItens.size()+" "+valorItens+" "+valorPeso);

		//print as coordenadas dos itens coletados, isto a lista AItens
		for (int i = 0; i<AItens.size(); i++){
			
			System.out.println(AItens.get(i).getLin()+" "+AItens.get(i).getCol());
		}

	}
}

/*********************************************************************************/ 
/* Classe principal																 */ 
/*********************************************************************************/ 
public class EP2{

	public static final boolean DEBUG = false;

/*********************************************************************************/ 
/* METODO passos() em que as chamadas recursivdas acontecem 			         */
/* para encontrar o melhor caminho												 */
/*********************************************************************************/ 
	public static void passos(Map map, int lin, int col, ArmazenandoCaminhos armazenandoCaminhos,
			Criterios critCaminho) {

		//verifica se e um caminho valido
		if (VerificaCoordenadas(map, lin, col)){

			//salvando o tem na lista
			Item item = map.getItem(lin, col);
			ArmazenandoCaminhos anterior = new ArmazenandoCaminhos(armazenandoCaminhos);
			armazenandoCaminhos.AdicionaValores(item, lin, col);

			//da o passo
			map.step(lin, col);
			
			//caso a entrada seja igual a saida
			if (map.finished(lin, col)) {

				//caso o melhor caminho for null ou um dos caminhos seja melhor
				if (ArmazenandoCaminhos.getmelhorCaminho() == null
						|| critCaminho.Compara(ArmazenandoCaminhos.getmelhorCaminho(), armazenandoCaminhos))
					ArmazenandoCaminhos.setmelhorCaminho(new ArmazenandoCaminhos(armazenandoCaminhos));

				//desfaz o passo
				map.unStep(lin, col);

				//volta para o caminho atual
				armazenandoCaminhos.Clone(anterior);
				return;
			}

			/* Backtracking Step */

			//ve se tem caminho para a DIREITA
			passos(map, lin, col + 1, armazenandoCaminhos, critCaminho);

			//ve se tem caminho para a ESQUERDA
			passos(map, lin, col - 1, armazenandoCaminhos, critCaminho);

			//ve se tem caminho para CIMA
			passos(map, lin + 1, col, armazenandoCaminhos, critCaminho);

			//ve se tem caminho para BAIXO
			passos(map, lin - 1, col, armazenandoCaminhos, critCaminho);

			//desfaz o passo
			map.unStep(lin, col);

			//volta para o caminho atual
			armazenandoCaminhos.Clone(anterior);
		}

		return;
	}

/*********************************************************************************/ 
/* METODO AUXILIAR SolucaoFinal() realcionada a interface, 						 */
/* isto e, Criterios.java e retorna a solucao de passos() 						 */ 
/*********************************************************************************/ 
	public static ArmazenandoCaminhos SolucaoFinal(Map map, int criterias) {

		Criterios critCaminho = null;

			if (criterias == 1)
				critCaminho = new CaminhoCurto();
			else if (criterias == 2)
				critCaminho = new CaminhoLongo();
			else if (criterias == 3)
				critCaminho = new CaminhoValioso();
			else if (criterias == 4)
				critCaminho = new CaminhoRapido();

		passos(map, map.getStartLin(), map.getStartCol(), new ArmazenandoCaminhos(), critCaminho);

		return ArmazenandoCaminhos.getmelhorCaminho();
	}

/*********************************************************************************/ 
/* METODO AUXILIAR VerificaCoordenadas() que verifica se a coordenada			 */
/* esta livre								 	 								 */ 
/*********************************************************************************/ 
	public static boolean VerificaCoordenadas(Map map, int lin, int col) {

		try {

			if (map.free(lin, col)) return true;

		} catch (Exception e){
		}
		return false;
	}

	public static void main(String[] args) throws IOException {

		Map map = new Map(args[0]);

		if (DEBUG) {
			map.print();
			System.out.println("---------------------------------------------------------------");
		}

		int criterias = Integer.parseInt(args[1]);
		ArmazenandoCaminhos armazenandoCaminhos = SolucaoFinal(map, criterias);
		armazenandoCaminhos.printPassos();
	}
}