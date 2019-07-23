package model.entity;

import model.component.BodyComponent;

/**
 * 
 * Simple enemy entity used for testing.
 *
 */
public class SimpleEnemyMovable extends AbstractEnemyMovable {
    /**
     * Basic constructor.
     */
    public SimpleEnemyMovable() {
        super();
        this.attachComponent(new BodyComponent(this));
    } 
}
