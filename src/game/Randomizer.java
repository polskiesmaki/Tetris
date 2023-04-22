package game;

import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Randomizer {
	
	StoneShape tetriminio;
	Pane root;
	double STONE_SIZE;
	double WIDTH;
	public Randomizer(Pane root2, StoneShape tetriminio, double stoneSize, double width){
		this.root = root2;
		this.tetriminio = tetriminio;
		this.STONE_SIZE = stoneSize;
		this.WIDTH = width;
	}
	
	//Erstellt verschiedene Figuren, basierend auf einem zufälligen int, es wird das root pane mit übergeben
	//so wird der CompositeStone  dem Pane zugeordnet. Dannach kann man die draw() Methode des Stones aufrufen.
	public StoneShape spawnStone() {
		Random random = new Random();
		int rand;
		 rand = random.nextInt(5);
		switch (rand) {
		
		//
		case 0:
		tetriminio = new CompositeStone(root, List.of(
    			new StonePart(root,60			,0,STONE_SIZE	,STONE_SIZE	, Color.RED),
    			new StonePart(root,60				,0+STONE_SIZE	,STONE_SIZE	,STONE_SIZE, Color.RED),
    			new StonePart(root,60				,0+2*STONE_SIZE	,STONE_SIZE	,STONE_SIZE, Color.RED),
    			new StonePart(root,60			,0+3*STONE_SIZE	,STONE_SIZE	,STONE_SIZE, Color.RED)
    					));
		tetriminio.rotable = true;
		break;
		case 1:
		tetriminio = new CompositeStone(root, List.of(
    			new StonePart(root,60			,0				,STONE_SIZE	,STONE_SIZE, Color.AZURE),
    			new StonePart(root,60			,0+STONE_SIZE	,STONE_SIZE	,STONE_SIZE, Color.AZURE),
    			new StonePart(root,60			,0+2*STONE_SIZE	,STONE_SIZE	,STONE_SIZE, Color.AZURE),
    			new StonePart(root,60+STONE_SIZE	,0+2*STONE_SIZE	,STONE_SIZE	,STONE_SIZE, Color.AZURE)
    					));
		tetriminio.rotable = true;
		break;
		case 2:
		tetriminio = new CompositeStone(root, List.of(
    			new StonePart(root,60			,STONE_SIZE		,STONE_SIZE	,STONE_SIZE, Color.GREEN),
    			new StonePart(root,60+STONE_SIZE	,STONE_SIZE		,STONE_SIZE	,STONE_SIZE, Color.GREEN),
    			new StonePart(root,60+STONE_SIZE	,0				,STONE_SIZE	,STONE_SIZE, Color.GREEN),
    			new StonePart(root,60+STONE_SIZE*2	,0				,STONE_SIZE	,STONE_SIZE, Color.GREEN)
    					));
		tetriminio.rotable = true;
		break;
		//Rechteck
		case 3:
		tetriminio = new CompositeStone(root, List.of(
    			new StonePart(root,60			,0				,STONE_SIZE	,STONE_SIZE, Color.YELLOW),
    			new StonePart(root,60			,STONE_SIZE		,STONE_SIZE	,STONE_SIZE, Color.YELLOW),
    			new StonePart(root,60+STONE_SIZE	,0				,STONE_SIZE	,STONE_SIZE, Color.YELLOW),
    			new StonePart(root,60+STONE_SIZE	,STONE_SIZE		,STONE_SIZE	,STONE_SIZE, Color.YELLOW)
    					));
		tetriminio.rotable = false;
		break;
		case 4:
		tetriminio = new CompositeStone(root, List.of(
    			new StonePart(root,60				,0				,STONE_SIZE	,STONE_SIZE, Color.BLUE),
    			new StonePart(root,60+STONE_SIZE	,0				,STONE_SIZE	,STONE_SIZE, Color.BLUE),
    			new StonePart(root,60+STONE_SIZE*2	,0				,STONE_SIZE	,STONE_SIZE, Color.BLUE),
    			new StonePart(root,60+STONE_SIZE	,STONE_SIZE		,STONE_SIZE	,STONE_SIZE, Color.BLUE)
    					));
		tetriminio.rotable = true;
		break;
		
		}
		
		return tetriminio;
	}
	
	
	
	
}
