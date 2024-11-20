package CodeSmashers.AngryBirds.inputHandler;

import CodeSmashers.AngryBirds.HelperClasses.Bird;
import CodeSmashers.AngryBirds.HelperClasses.Pig;
import CodeSmashers.AngryBirds.HelperClasses.Surroundings;
import com.badlogic.gdx.physics.box2d.*;



public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        // Nothing special to do here for health reduction
    }

    @Override
    public void endContact(Contact contact) {
        // Called when the bodies stop colliding (optional)
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Pre-solve step (optional, used for more advanced collision handling)
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Get the impulse magnitude (force * time) from the ContactImpulse
        float impulseMagnitude = calculateImpulseMagnitude(impulse);

        // Check if bird collided with pig
        if (isBird(fixtureA) && isPig(fixtureB)) {
            System.out.println("Bird and pig are in contact");
            reduceHealth(fixtureB, impulseMagnitude);  // Reduce health of pig based on impact
        } else if (isBird(fixtureB) && isPig(fixtureA)) {
            System.out.println("Bird and pig are in contact");
            reduceHealth(fixtureA, impulseMagnitude);  // Reduce health of pig based on impact
        }

//        // Check if bird collided with block
//        if (isBird(fixtureA) && isBlock(fixtureB)) {
//            System.out.println("Bird and block are in contact");
//            reduceHealth(fixtureB, impulseMagnitude);  // Reduce health of block based on impact
//        } else if (isBird(fixtureB) && isBlock(fixtureA)) {
//            System.out.println("Bird and block are in contact");
//            reduceHealth(fixtureA, impulseMagnitude);  // Reduce health of block based on impact
//        }

        // Check if pig collided with block
        if (isPig(fixtureA) && isBlock(fixtureB)) {
//            System.out.println("Pig and block are in contact");
            reduceHealth(fixtureB, impulseMagnitude);  // Reduce health of block based on impact
        } else if (isPig(fixtureB) && isBlock(fixtureA)) {
//            System.out.println("Pig and block are in contact");
            reduceHealth(fixtureA, impulseMagnitude);  // Reduce health of block based on impact
        }

        if (isPig(fixtureA) && isPig(fixtureB)) {
//            System.out.println("Pig and block are in contact");
            reduceHealth(fixtureB, impulseMagnitude);  // Reduce health of block based on impact
            reduceHealth(fixtureA, impulseMagnitude);
        }
    }

    private boolean isBird(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData() instanceof Bird;
    }

    private boolean isPig(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData() instanceof Pig;
    }

    private boolean isBlock(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData() instanceof Surroundings;
    }

    private float calculateImpulseMagnitude(ContactImpulse impulse) {
        float totalImpulse = 0;
        for (int i = 0; i < impulse.getCount(); i++) {
            totalImpulse += impulse.getNormalImpulses()[i];
        }
        return totalImpulse;
    }

    // Reduce health based on the calculated impact magnitude
    private void reduceHealth(Fixture fixture, float impact) {
        Object userData = fixture.getUserData();
        if (userData instanceof Pig && impact > 300) {
            Pig pig = (Pig) userData;
            Body body = pig.getBody();

            float mass = body.getMass(); // Get the pig's mass
            float scalingFactor = 0.8f;  // Adjust this based on your game's balance

            // Calculate damage
            int damage = (int) ((impact / (mass + 1)) * scalingFactor);
            damage = Math.max(damage, 1); // Ensure at least 1 damage is dealt

            // Reduce health
            int health = pig.getHealth();
            pig.setHealth(health - damage);

            System.out.println("Impact = " + impact + ", Mass = " + mass + ", Damage = " + damage + ", New Health = " + pig.getHealth());
        }
    }

}


