package bubllebi;

import robocode.*;

public class bubllebi extends Robot {
    private boolean isMovingForward = true;
    private double lastEnemyEnergy = 100; // Inicializa com a energia máxima do inimigo

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
        double enemyBearing = e.getBearing();
        double gunTurnAngle = getHeading() + enemyBearing - getGunHeading();

        // Gira a arma na direção do inimigo
        turnGunRight(gunTurnAngle);

        // Verifica se há uma chance razoável de acertar o tiro antes de atirar
        if (Math.abs(gunTurnAngle) <= 10 && getEnergy() > 20) {
            fire(2); // Atira com potência 2 quando o inimigo está alinhado e você tem energia suficiente
        }

        // Estratégia de esquiva de balas
        double enemyEnergy = e.getEnergy();
        if (lastEnemyEnergy > enemyEnergy) {
            // O inimigo atirou, portanto, ajuste o movimento para evitar tiros
            if (isMovingForward) {
                back(50); // Move para trás para evitar balas
                isMovingForward = false;
            } else {
                ahead(50); // Move para frente após se esquivar
                isMovingForward = true;
            }
        }
        lastEnemyEnergy = enemyEnergy;
    }
}
