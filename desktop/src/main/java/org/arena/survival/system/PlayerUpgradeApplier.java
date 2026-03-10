package org.arena.survival.system;

import org.arena.survival.entity.Player;
import org.arena.survival.entity.UpgradeCard;

public class PlayerUpgradeApplier {

    public static void apply(Player player, UpgradeCard card) {

        switch (card.getType()) {

            case ATTACK_SPEED:
                player.setShootCooldownPlayer(
                        player.getShootCooldownPlayer() * 0.8f
                );
                break;

            case DAMAGE:
                player.setDamage(player.getDamage() + 1);
                break;

            case MOVE_SPEED:
                player.setSpeed(player.getSpeed() * 1.15f);
                break;

            case DOUBLE_SHOT:
                player.setDoubleShot(true);
                break;

            case HOMING_BULLETS:
                player.setHomingBullets(true);
                break;

            case ADD_HP:
                player.setAddHealth(5);
                break;
        }
    }
}
