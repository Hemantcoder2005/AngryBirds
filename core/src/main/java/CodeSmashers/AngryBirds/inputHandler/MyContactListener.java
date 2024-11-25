package CodeSmashers.AngryBirds.inputHandler;

import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.Pig;
import CodeSmashers.AngryBirds.HelperClasses.SoundEffects;
import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        // Triggered when two bodies start colliding (optional)
    }

    @Override
    public void endContact(Contact contact) {
        // Triggered when two bodies stop colliding (optional)
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Pre-solve step for advanced collision handling (optional)
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Calculate the magnitude of the collision impulse
        float impulseMagnitude = calculateImpulseMagnitude(impulse);

        // Handle bird collisions
        if (isBird(fixtureA) && isPig(fixtureB)) {
            handleBirdPigCollision(fixtureB, impulseMagnitude);
        } else if (isBird(fixtureB) && isPig(fixtureA)) {
            handleBirdPigCollision(fixtureA, impulseMagnitude);
        } else if (isBird(fixtureA) && isBlock(fixtureB)) {
            handleBirdBlockCollision(fixtureB, impulseMagnitude);
        } else if (isBird(fixtureB) && isBlock(fixtureA)) {
            handleBirdBlockCollision(fixtureA, impulseMagnitude);
        }

        // Handle pig collisions
        if (isPig(fixtureA) && isBlock(fixtureB)) {
            handlePigBlockCollision(fixtureB, impulseMagnitude);
        } else if (isPig(fixtureB) && isBlock(fixtureA)) {
            handlePigBlockCollision(fixtureA, impulseMagnitude);
        } else if (isPig(fixtureA) && isPig(fixtureB)) {
            handlePigPigCollision(fixtureA, fixtureB, impulseMagnitude);
        }
    }

    private boolean isBird(Fixture fixture) {
        return fixture.getUserData() instanceof Bird;
    }

    private boolean isPig(Fixture fixture) {
        return fixture.getUserData() instanceof Pig;
    }

    private boolean isBlock(Fixture fixture) {
        return fixture.getUserData() instanceof Surroundings;
    }

    private float calculateImpulseMagnitude(ContactImpulse impulse) {
        float totalImpulse = 0;
        for (float normalImpulse : impulse.getNormalImpulses()) {
            totalImpulse += normalImpulse;
        }
        return totalImpulse;
    }

    private void handleBirdPigCollision(Fixture pigFixture, float impulseMagnitude) {
        SoundEffects.playBirdCollision();
        SoundEffects.playPigCollide();
        reduceHealth(pigFixture, impulseMagnitude);
    }

    private void handleBirdBlockCollision(Fixture blockFixture, float impulseMagnitude) {
        SoundEffects.playBirdCollision();
        SoundEffects.playWoodCollision();
        reduceHealth(blockFixture, impulseMagnitude);
    }

    private void handlePigBlockCollision(Fixture blockFixture, float impulseMagnitude) {
        reduceHealth(blockFixture, impulseMagnitude);
    }

    private void handlePigPigCollision(Fixture pigFixtureA, Fixture pigFixtureB, float impulseMagnitude) {
        reduceHealth(pigFixtureA, impulseMagnitude);
        reduceHealth(pigFixtureB, impulseMagnitude);
    }

    private void reduceHealth(Fixture fixture, float impact) {
        Object userData = fixture.getUserData();

        if (userData instanceof Pig && impact > 100) {
            Pig pig = (Pig) userData;
            applyDamage(pig, impact, 0.9f);
        } else if (userData instanceof Surroundings && impact > 1000) {
            Surroundings block = (Surroundings) userData;
            applyDamage(block, impact, 0.4f);
        }
    }

    private void applyDamage(Pig pig, float impact, float scalingFactor) {
        Body body = pig.getBody();
        float mass = body.getMass();
        int damage = Math.max((int) ((impact / (mass + 1)) * scalingFactor), 1);
        pig.setHealth(pig.getHealth() - damage);

        System.out.printf("Pig Damage: Impact=%.2f, Mass=%.2f, Damage=%d, New Health=%d%n",
            impact, mass, damage, pig.getHealth());

        if (pig.getHealth() <= 0) {
            SoundEffects.playPigDestroy();
            System.out.println("Pig is destroyed!");
        }
    }

    private void applyDamage(Surroundings block, float impact, float scalingFactor) {
        int damage = Math.max((int) (impact * scalingFactor), 1);
        block.setDurability(block.getDurability() - damage);

        System.out.printf("Block Damage: Impact=%.2f, Damage=%d, New Durability=%d%n",
            impact, damage, block.getDurability());

        // Check if block durability is below a threshold
        if (block.getDurability() <= 0) {
            SoundEffects.playWoodDestroy();
            System.out.println("Block is destroyed!");
        }
    }
}
