package RaketeGame;

import basis.*;

public class SimulationRakete {
	// Objects
	private Fenster window;
	private Knopf kStart, kStop;
	private Rocket rocket;
	private Lines finish;
	private Numbers num;

	// Variables
	private boolean exit;
	private boolean start, stop;
	private double streckung;
	private int maxPoints;
	
	// Constructor
	public SimulationRakete() {
		streckung = 0.5;
		maxPoints = 1000;
		window = new Fenster();
		window.setzeTitel("StyleRocket");
		window.setzeGroesse(400, Hilfe.monitorHoehe() - 200);
		window.setzePosition(Hilfe.monitorBreite() / 2 - 200, 100);
		kStop = new Knopf("Stop", window.breite() - 100, window.hoehe() - 150, 100, 50);
		kStart = new Knopf("Start", window.breite() - 100, window.hoehe() - 200, 100, 50);
		rocket = new Rocket(window.breite() / 2, window.hoehe() - 55, streckung);
		finish = new Lines(window.breite(), 200);
		num = new Numbers(window.breite(), 200);
		window.setzeHintergrundFarbe(Farbe.rgb(130, 160, 200));
		start = stop = false;
	}

	// Methods
	public void fly() {
		while (num.getPoints() < maxPoints) {
			finish.drawFinishLine();
			finish.PointLines();
			if (rocket.getyPos() == window.hoehe() - 55) {
				start = kStart.wurdeGedrueckt();
				rocket.zeichne();
			}
			if (start) {
				Hilfe.warte(250);
				rocket.delete();
				while (!stop) {
					if (rocket.getyPos() != window.hoehe() - 55) {
						stop = kStop.wurdeGedrueckt();
					}
					rocket.bewege();
					finish.drawFinishLine();
					finish.PointLines();
					if (rocket.getyPos() < 0) {
						rocket.deleteFlame();
						finish.delete();
						break;
					}
				}
				num.points(num.checkPos((int)(rocket.getyPos()-(60 * streckung))));
				finish.drawFinishLine();
				finish.PointLines();
				rocket.zeichneFlamme();
				Hilfe.warte(750);
				start = false;
				stop = false;
				rocket.deleteFlame();
				rocket.setyPos(window.hoehe() - 55);
				finish.drawFinishLine();
				finish.PointLines();
				rocket.zeichne();
			}

		}
		System.out.printf("Du hast %d Flüge gebraucht um mehr als %d Punkte zu erreichen!%n",num.getTimes(),maxPoints);
		System.exit(0);

	}

	public static void main(String[] args) {
		SimulationRakete simRak = new SimulationRakete();
		simRak.fly();
	}

}
