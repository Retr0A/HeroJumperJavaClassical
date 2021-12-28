package org.herojumper.level;

public class Player {

	public double x, y, xd, yd, rot;
	
	public boolean walk = false;
	
	public Player() {
		
	}
	
	public void update(boolean forward, boolean backward, boolean left, boolean right, boolean turnLeft, boolean turnRight) {
		double wSpeed = 0.01;
        double rSpeed = 0.01;

        if (forward) {
            xd++;
            walk = true;
        }
        if (backward) {
        	xd--;
        	walk = true;
        }
        if (left) {
            yd--;
            walk = true;
        }
        if (right) {
            yd++;
            walk = true;
        }
        if (turnLeft) {
            rot += rSpeed;
            walk = true;
        }
        if (turnRight) {
            rot -= rSpeed;
            walk = true;
        }
        
        if (!forward && !backward && !left && !right && !turnLeft && !turnRight) {
        	walk = false;
        }

        double rCos = Math.cos(rot);
        double rSin = Math.sin(rot);

        x += (xd * -rSin + yd * rCos) * wSpeed;
        y += (xd * rCos + yd * rSin) * wSpeed;
        
        xd = 0;
        yd = 0;
	}
}
