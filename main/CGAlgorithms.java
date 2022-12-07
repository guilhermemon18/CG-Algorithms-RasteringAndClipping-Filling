package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import classes.DrawPixel;
import classes.Edge;
import classes.Point;


//Algoritmo de rasterização do ponto médio para retas e para círculos: concluído
//Algoritmo para desenhar polígonos
//utilizar o algoritmo do ponto médio para retas;
//armazenar em estruturas adequadas para que possa preencher o polígono utilizando
//o algoritmo de preenchimento apresentado em aula (utilizando as estruturas ET e AET)
//Algoritmo de Cohen Sutherland para recorte de primitivas: concluído.

public class CGAlgorithms {

	private DrawPixel d;

	//variáveis Cohen-Sutherland
	private Integer yMax;
	private Integer yMin;
	private Integer xMax;
	private Integer xMin;

	//Códigos Cohen-Shuterland:
	private final int INSIDE = 0; // 0000
	private final int LEFT   = 1; // 0001
	private final int RIGHT  = 2; // 0010
	private final int BOTTOM = 4; // 0100
	private final int TOP    = 8; // 1000


	//variáveis preenchimento de polígonos:
	//private HashMap<Integer,Queue<Edge>> ET;
	//private Queue<Edge> AET;


	public void DDA(int x1, int y1, int x2, int y2) throws Exception {
		this.d = new DrawPixel();
		Integer length;
		float x, y, xinc, yinc;

		length = Math.abs(x2 - x1);//length recebe a variação de x

		if(Math.abs(y2-y1) > length)//se a variação de y for maior que a de x, então length recebe a variação de y.
			length = Math.abs(y2-y1);
		xinc = (float) (x2 - x1) / length;//valor de x a ser incrementado é a variação de x dividio pelo lenght.
		yinc = (float) (y2-y1) / length;//valor de y  a ser incrementado é a variação de y dividido pelo lenght.

		x = x1;
		y = y1;

		while(x <= x2) {
			//			System.out.println("y: " + y);
			//			System.out.println("yinc: " + yinc);
			this.d.writePixel(Math.round(x), Math.round(y),Color.RED);
			//System.out.println("(" + Math.round(x) + "," + Math.round(y) + ")" );
			x = x + xinc;
			y = y + yinc;
		}
		this.d.imgWrite("DDAReta.png");
	}



	//algoritmo do ponto médio para retas:
	public void midPointLine(int x1, int y1, int x2, int y2) throws Exception {
		this.d = new DrawPixel();
		int dx, dy, incE, incNE, d, x, y;

		dx = x2 - x1;//variação de x.
		dy = y2 - y1;//variação de y.
		d = 2 * dy - dx;//primeiro ponto médio M

		incE = 2 * dy;//incremento de E.
		incNE = 2 * (dy - dx);//incremento de NE.

		x = x1;//x começa com x1 inicial da reta.
		y = y1;//y começa com o y1 inicial da reta.
		this.d.writePixel(x, y,Color.PINK);//cria o pixel inicial.
		//System.out.println("(" + Math.round(x) + "," + Math.round(y) + ")" );

		while(x < x2) {//enquanto o x for menor que x2 destino, vai traçando os pontos.
			if(d <= 0) {
				d = d + incE;
				x = x +1;
			}else {
				d = d + incNE;
				x = x + 1;
				y = y + 1;
			}
			this.d.writePixel(x, y,Color.PINK);
			//System.out.println("(" + Math.round(x) + "," + Math.round(y) + ")" );

		}
		this.d.imgWrite("pontoMedioReta.png");
	}


	//algoritmo do ponto médio para retas:
	private void midPointLine(int x1, int y1, int x2, int y2, String fileName) throws Exception {
		this.d = new DrawPixel();
		int dx, dy, incE, incNE, d, x, y;

		dx = x2 - x1;//variação de x.
		dy = y2 - y1;//variação de y.
		d = 2 * dy - dx;//primeiro ponto médio M

		incE = 2 * dy;//incremento de E.
		incNE = 2 * (dy - dx);//incremento de NE.

		x = x1;//x começa com x1 inicial da reta.
		y = y1;//y começa com o y1 inicial da reta.
		this.d.writePixel(x, y);//cria o pixel inicial.
		System.out.println("(" + Math.round(x) + "," + Math.round(y) + ")" );

		while(x < x2) {//enquanto o x for menor que x2 destino, vai traçando os pontos.
			if(d <= 0) {
				d = d + incE;
				x = x +1;
			}else {
				d = d + incNE;
				x = x + 1;
				y = y + 1;
			}
			this.d.writePixel(x, y);
			System.out.println("(" + Math.round(x) + "," + Math.round(y) + ")" );

		}
		this.d.imgWrite(fileName);
	}


	//Algoritmo do ponto médio para círculos:
	//escreve o circulo sempre no centro da imagem.
	public void MidPointCircle(int r) throws Exception{
		this.d = new DrawPixel();
		int x, y;
		float d;
		/* Valores iniciais */
		x = 0;
		y = r;
		d = 5/4 - r;
		CirclePoints(x, y);
		while (y > x){
			if (d < 0){//Selecione E
				d = d + 2 * x + 3;
				x++;
			}else{//Selecione SE
				d = d + 2 * (x - y) + 5;
				x++;
				y--;
			}//end Selecione SE
			CirclePoints(x, y);
		}//end while
		this.d.imgWrite("pontoMedioCircunferência.png");
	}//end MidPointCircle



	//função auxiliar para desenhar os pontos.
	private void CirclePoints(int x, int y) throws Exception{
		Integer xPositivo = x + this.d.getCenter();
		Integer xNegativo = -x + this.d.getCenter();
		Integer yPositivo = y + this.d.getCenter();
		Integer yNegativo = -y + this.d.getCenter();

		//		System.out.println("XPositivo: " + xPositivo);
		//		System.out.println("Xnegativo: " + xNegativo);
		//		System.out.println("YPositivo: " + yPositivo);
		//		System.out.println("Ynegativo: " + yNegativo);

		this.d.writePixel(xPositivo, yPositivo);
		this.d.writePixel(xPositivo, yNegativo);
		this.d.writePixel(xNegativo, yPositivo);
		this.d.writePixel(xNegativo, yNegativo);
		this.d.writePixel(yPositivo, xPositivo);
		this.d.writePixel(yPositivo, xNegativo);
		this.d.writePixel(yNegativo, xPositivo);
		this.d.writePixel(yNegativo, xNegativo);
	}/* end CirclePoints */


	private int CalculaOutCode(float x, float y) {// Calcula outcode para ponto (x,y) e retorna-o
		int code = this.INSIDE;

		System.out.println("Ponto: (" + x + "," + y + ")");

		if (y > this.yMax )	{
			code |= TOP;
		}
		else if (y < this.yMin) {
			code |= BOTTOM;

		}

		if (x > this.xMax )	{
			code |= RIGHT;

		}
		else if (x < this.xMin) {
			code |= LEFT;

		}

		return code;
	}


	// Implementing Cohen-Sutherland algorithm
	// Clipping a line from P1 = (x2, y2) to P2 = (x2, y2)
	public void cohenSutherland(float x1, float y1, float x2, float y2) throws Exception
	{
		boolean aceito = false;
		int outcode0;
		int outcode1;
		int outcodeout;
		float x = 0, y = 0;


		//definição do retangulo a ser recortado.
		this.xMax = 2;
		this.xMin = -3;
		this.yMax = 6;
		this.yMin = 1;

		// Compute region codes for P1, P2
		outcode0 = CalculaOutCode(x1, y1);
		outcode1 = CalculaOutCode(x2, y2);

		while (true) {
			System.out.println(outcode0);
			System.out.println(outcode1);
			if ((outcode0 == 0) && (outcode1 == 0)) {
				// If both endpoints lie within rectangle
				aceito = true;
				break;
			}
			else if ((outcode0 & outcode1) != 0) {
				// If both endpoints are outside rectangle,
				// in same region
				break;
			}
			else {
				// Some segment of line lies within the
				// rectangle

				// At least one endpoint is outside the
				// rectangle, pick it.
				if (outcode0 != 0)
					outcodeout = outcode0;
				else
					outcodeout = outcode1;

				// Find intersection point;
				// using formulas y = y1 + slope * (x - x1),
				// x = x1 + (1 / slope) * (y - y1)

				System.out.println("Codeout: " + outcodeout);


				if ((outcodeout & TOP) != 0) {
					// point is above the clip rectangle
					x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
					y = yMax;
					System.out.println("Entrou TOP");
				}
				else if ((outcodeout & BOTTOM) != 0) {
					// point is below the rectangle
					x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
					y = yMin;
					System.out.println("Entrou BOTTOM");
				}
				else if ((outcodeout & RIGHT) != 0) {
					// point is to the right of rectangle
					y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
					x = xMax;
					System.out.println("Entrou RIGHT");
				}
				else if ((outcodeout & LEFT) != 0) {
					// point is to the left of rectangle
					y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
					x = xMin;
					System.out.println("Entrou LEFT");
				}

				// Now intersection point x, y is found
				// We replace point outside rectangle
				// by intersection point
				if (outcodeout == outcode0) {
					x1 = x;
					y1 = y;
					outcode0 = CalculaOutCode(x1, y1);
				}
				else {
					x2 = x;
					y2 = y;
					outcode1 = CalculaOutCode(x2, y2);
				}
			}
		}
		if (aceito) {
			Integer c = this.d.getCenter();
			midPointLine(Math.round(x1) + c,Math.round(y1) + c, Math.round( x2) + c,Math.round( y2) + c,"Cohen-SuttherLand.png");
			System.out.println("Line accepted from " + "(" +  x1 + ", "
					+ y1 + ")" +  " to "+ "(" + x2 + ", " +  y2 + ")");
		}
		else
			System.out.println("Line rejected");
	}

	//algoritmo de preenchimento de retangulo:
	public void preenchimentoRetangulo(int largura, int comprimento) throws Exception {

		//		Para y = ymin at ́e ymax do ret^angulo { Por linha de varredura }
		//		Para x = xmin at ́e xmax 
		//				{ Cada pixel dentro do bloco }
		//		write_pixel (x,y,valor)
		this.d = new DrawPixel();
		int ymin = this.d.getCenter() - largura / 2;
		int xmin = this.d.getCenter() - comprimento / 2;

		for(int y = ymin ; y < largura / 2 + this.d.getCenter(); y++) {
			for(int x = xmin; x < comprimento /2 + this.d.getCenter(); x++)
				this.d.writePixel(x, y, Color.CYAN);
		}
		this.d.imgWrite("PreenchimentoRetângulo.png");
	}

	//Obetem as arestas de um arquivo lido.
	public Queue<Edge> getEdgesOfFile(String fileName) throws IOException {
		Queue<Edge> l = new PriorityQueue<>();
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		String linha = buffRead.readLine();
		while(linha != null) {
			String values[] = linha.split(" ");

			Float x1 =  Float.valueOf(values[0]);
			Float y1 = Float.valueOf(values[1]);
			Float x2 = Float.valueOf(values[2]);
			Float y2 = Float.valueOf(values[3]);

			l.add(new Edge(new Point(x1,y1), new Point(x2,y2)));
			linha = buffRead.readLine();
		}
		buffRead.close();
		return l;
	}

	//Recebe as arestas e separa-as na tabela hashmap e retorna a tabela ET.
	private HashMap<Integer, Queue<Edge>> getET(Queue<Edge> edges){
		HashMap<Integer, Queue<Edge>> m = new HashMap<>();
		//Contém todas as 
		//arestas do polígono em ordem crescente de sua menor coordenada y

		for (Edge edge : edges) {
			Integer y = edge.getSourcePoint().getY().intValue();//obtem o y para avaliar e colocar na tabela.
			if(m.containsKey(y)) {//se já contém esse y, adicione apenas o a aresta do EDGE e ordene a lista.
				m.get(y).add(edge);
			}else {//caso n tenha  o y, precisa adiciona-lo e criar uma lista para ele.
				Queue<Edge> l = new PriorityQueue<Edge>();
				l.add(edge);
				m.put(y, l);
			}

		}

		return m;
	}



	//O objetivo do algoritmo SLPF é preencher (colorir) os pixels internos de um polígono, dados apenas os vértices da figura.
	//Então ele necessita a lista de edges lida do arquivo.
	public void preenchimentoPoligono(Queue<Edge> edges) throws IOException {
		System.out.println("Prenchimento de Polígonos!");//debug
		this.d = new DrawPixel(300);//instancia o desenhador com size 500 * 500.


		//ET Tabela de arestas globais:
		//		Contém todas as arestas do polígono em ordem crescente de sua menor coordenada y
		//		Possui p posições, uma para cada linha de varredura
		//		A cada posição p de ET associa uma lista encadeada (ymin=y)
		//		As arestas nesta lista estão ordenadas em ordem crescente  da coordenada x  de sua extremidade inferior (xmin)
		//		Cada nó armazena: ymax, o valor de x para o ymin e incremento em x (1/m)
		//		AET tabela de arestas ativas
		HashMap<Integer,Queue<Edge>> ET = getET(edges);
		List<Edge> AET = new LinkedList<>();//Inicializa a AET, tabela de arestas ativas, como vazia.

		Integer y = 0;//
		while(!ET.containsKey(y)) {//Obtém a menor coordenada y armazenada na ET; ou seja, o valor de y do primeiro bucket n˜ao vazio
			y++;
		}

		while(!ET.isEmpty() || !AET.isEmpty()) {//Repita até que a ET e a AET estejam vazias
			Queue<Edge> yEdges = ET.get(y);//obtem as arestas do bucket Y.
			Edge edge;
			if(yEdges != null) {
				while((edge = yEdges.peek()) != null && edge.getSourcePoint().getY().equals(y.floatValue())) {//Transfere do bucket y de ET para AET 
					//as arestas cujo ymin = y, mantendo a AET ordenada em x,
					yEdges.poll();
					AET.add(edge);
				}
				

			}
			
			Collections.sort(AET);
			System.out.println("AET para: y = " + y);
			for (Edge edge2 : AET) {
				System.out.println("[" +edge2.getDestinationPoint().getY()  + "|" + edge2.getSourcePoint().getX()  + "|" + 
			edge2.getM() + "]");
				//System.out.println(edge2);
			}

			//5-Retira os lados que possuem y = ymax. 
			Iterator<Edge> iterator = AET.iterator();
			while (iterator.hasNext()) {
				Edge s = iterator.next(); // must be called before you can call i.remove()
				if(s.getDestinationPoint().getY().compareTo(y.floatValue()) == 0  ) {
					iterator.remove();
				}
			}

			//6-Desenhe os pixels do bloco na linha de varredura y usando pares de coordenadas x da AET.
			iterator = AET.iterator();
			while (iterator.hasNext()) {
				Edge inicio = iterator.next();
				Edge fim = iterator.next();

				//escreve os pixels x que devem ser colocados!
				System.out.println("pinta (" + Math.round(inicio.getSourcePoint().getX()) + "," + y + ") a "
						+ "(" + Math.round(fim.getSourcePoint().getX()) + ","+ y + ")" );
				for(int j = Math.round(inicio.getSourcePoint().getX()); j <= Math.round(fim.getSourcePoint().getX()); j++) {
					try {
						//System.out.println("Pintando...");
						this.d.writePixel( this.d.getCenter()  + j, this.d.getCenter()  - y);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Deu um problema aqui no loop, fazer o que né kkkk");
					}
				}
			}

			ET.remove(y);//remove o y processado da tabela ET;

			//7-Incremente y de 1 
			y++;

			System.out.println("NewY: " + y);

			//7-Para cada aresta n˜ao vertical que permanece na AET, atualiza x para o novo y
			//8-Como o passo anterior pode ter desordenado a AET, reordena a AET.

			for (Edge edge2 : AET) {
				edge2.updateX();
			}
			//Collections.sort(AET);

		}
		System.out.println("AET = NUll");
		this.d.imgWrite("PreenchimentoPolígono.png");
	}



	public static void main(String[] args) {

		CGAlgorithms a = new CGAlgorithms();
		try {
			a.DDA(0, 0, 479, 479);
			a.midPointLine(1, 1, 450,450);
			a.MidPointCircle(100);
			a.cohenSutherland(-4,2 , -1, 7);
			a.preenchimentoRetangulo(250, 300);
			a.preenchimentoPoligono(a.getEdgesOfFile("file/testeslide.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
