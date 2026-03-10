package org.arena.survival.system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.entity.UpgradeCard;
import org.arena.survival.entity.UpgradeType;

public class UpgradeSystem {
    private Array<UpgradeCard> allCards = new Array<>();

    public UpgradeSystem() {

        allCards.add(new UpgradeCard(
                UpgradeType.ATTACK_SPEED,
                "Attack speed +20%",
                3f
        ));

        allCards.add(new UpgradeCard(
                UpgradeType.DAMAGE,
                "Damage +1",
                3f
        ));

        allCards.add(new UpgradeCard(
                UpgradeType.DOUBLE_SHOT,
                "Shoot 2 bullets",
                0.5f
        ));

        allCards.add(new UpgradeCard(
                UpgradeType.HOMING_BULLETS,
                "Homing bullets",
                0.5f
        ));

        allCards.add(new UpgradeCard(
                UpgradeType.MOVE_SPEED,
                "Move speed +15%",
                3f
        ));

        allCards.add(new UpgradeCard(
                UpgradeType.ADD_HP,
                "Add +5 HP",
                3f
        ));
    }

    public Array<UpgradeCard> getRandomCards() {

        Array<UpgradeCard> result = new Array<>();

        while (result.size < 3) {
            UpgradeCard card = getWeightedRandom();

            if (!result.contains(card, true)) {
                result.add(card);
            }
        }

        return result;
    }

    private UpgradeCard getWeightedRandom() {

        float total = 0;

        for (UpgradeCard card : allCards) {
            total += card.getWeight();
        }

        float rand = MathUtils.random(total);

        float sum = 0;

        for (UpgradeCard card : allCards) {

            sum += card.getWeight();

            if (rand <= sum) {
                return card;
            }
        }

        return allCards.first();
    }
}
