package bubllebi;

import robocode.*;

public class bubllebi extends Robot {
    private boolean isMovingForward = true;

    public void run() {
        while (true) {
            if (isMovingForward) {
                ahead(100); // Move para frente 100 pixels
            } else {
                back(100); // Move para trás 100 pixels
            }
            turnGunRight(360); // Gira a arma em círculo
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Calcula o ângulo para mirar na direção do inimigo
        double enemyBearing = e.getBearing();
        double gunTurnAngle = getHeading() + enemyBearing - getGunHeading();

        // Gira a arma na direção do inimigo
        turnGunRight(gunTurnAngle);

        // Verifica se há uma chance razoável de acertar o tiro antes de atirar
        if (Math.abs(gunTurnAngle) <= 10) {
            fire(2); // Atira com potência 2 quando o inimigo está alinhado
        }

        // Esquiva-se de balas inimigas
        if (e.getDistance() < 200) {
            if (isMovingForward) {
                back(50); // Move para trás para evitar balas de perto
                isMovingForward = false;
            } else {
                ahead(50); // Move para frente após se esquivar
                isMovingForward = true;
            }
        }
    }
}
