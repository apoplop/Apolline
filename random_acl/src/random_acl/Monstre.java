package random_acl;


public class Monstre extends Personnage {
	GamePanel panel;
	int test = 0;
	
	public Monstre(GamePanel panel, int posi_x, int posi_y) {
		super(panel, posi_x, posi_y);
		this.panel = panel;
	}
	
	public void deplacerMonstre() {
		if (panel.labyrinthe.mapTable[this.getPosi_x()][this.getPosi_y()+1].equals("0")) {
			test+=1;
			this.deplacerBas();
		}
	}
	public void drawMonstre() {
		this.drawPersonnage("file:src/images/monstre.png");
	}
}
