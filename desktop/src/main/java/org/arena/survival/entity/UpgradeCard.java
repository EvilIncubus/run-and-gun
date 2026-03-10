package org.arena.survival.entity;

public class UpgradeCard {
    private UpgradeType type;
    private String title;
    private float weight; // шанс выпадения

    public UpgradeCard(UpgradeType type, String title, float weight) {
        this.type = type;
        this.title = title;
        this.weight = weight;
    }

    public UpgradeType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public float getWeight() {
        return weight;
    }
}
