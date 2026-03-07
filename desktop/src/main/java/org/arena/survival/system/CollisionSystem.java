package org.arena.survival.system;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.ArenaGame;
import org.arena.survival.entity.Bullet;
import org.arena.survival.entity.Enemy;
import org.arena.survival.entity.Player;
import org.arena.survival.screen.MenuScreen;

import java.util.Iterator;

public class CollisionSystem {

    public int update(Array<Bullet> bullets, Array<Enemy> enemies, float delta, int score) {
        // обновляем пули и проверяем коллизии
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update(delta);

            // проверка на столкновение с врагами
            for (Iterator<Enemy> enemyIter = enemies.iterator(); enemyIter.hasNext(); ) {
                Enemy enemy = enemyIter.next();
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    bulletIter.remove();

                    // наносим урон врагу
                    boolean dead = enemy.takeDamage(1);

                    if (dead) {
                        enemyIter.remove(); // удаляем врага только если HP <= 0
                        score += 1; // увеличиваем очки
                    }
                    break; // если один bullet убивает одного врага
                }
            }

            // удаление пули за пределами карты
            if (bullet.isOutOfBounds()) {
                bulletIter.remove();
            }
        }
        return score;
    }

    public void checkEnemyBullets(Array<Bullet> enemyBullets, Player player, float delta, ArenaGame game) {

        Iterator<Bullet> bulletIter = enemyBullets.iterator();
        Rectangle playerBounds = new Rectangle(player.getCenterX() - player.getSize() / 2, player.getCenterY() - player.getSize() / 2, player.getSize(), player.getSize());

        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();

            // обновляем движение пули
            bullet.update(delta);

            // проверка на столкновение с игроком
            if (bullet.getBounds().overlaps(playerBounds)) {
                player.takeDamage(1);   // наносим урон игроку
                if (player.getHealth() <= 0) {
                    return;
                }
                bulletIter.remove();     // удаляем пулю
                continue;
            }

            // удаление пули за пределами карты
            if (bullet.isOutOfBounds()) {
                bulletIter.remove();
            }
        }
    }
}
