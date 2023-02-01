package world.creation.builder;

import world.Direction;
import world.Mobile;
import world.World;
import world.creation.factory.GearFactory;
import world.creation.factory.GearFactoryImpl;
import world.creation.factory.MobileFactory;
import world.creation.factory.MobileFactoryImpl;
import world.gear.Gear;
import world.state.MobileState;
import world.state.StateNoMovable;
import world.state.StateSingle;

public class CreateWorld {

    private WorldBuilder wb;
    private MobileFactory mobileFactory;
    private GearFactory gearFactory;

    public CreateWorld(){
        wb = new WorldConcreteBuilder();
        mobileFactory = new MobileFactoryImpl();
        gearFactory = new GearFactoryImpl();
        create();
    }

    private void create() {

        //Rooms
        wb.buildRoom("0","Home", "This is the outside of the player's room. " +
                        "No gear is found here but you may find an npc..",
                "./resources/rooms/home.png", "./resources/maps/mapHome.png");


        wb.buildRoom("1","Room", "The player's room. Only players can access " +
                        "to it and here they can find different clothes or personal gears " +
                        "like the phone or the jumpsuit.",
                "./resources/rooms/room.png", "./resources/maps/mapRoom.png");

        wb.buildRoom("2","Park", "This is the park of the city, here you can " +
                        "smell the perfume of spring but be careful with the bees!!!! " +
                        "There will be flowers and roses that you can pick up and use as gifts and " +
                        "an icecream machine where you can pay for the Cocacola and Strawberry icecream. " +
                        "Maki spawns here.",
                "./resources/rooms/park.png", "./resources/maps/mapPark.png");

        wb.buildRoom("3","School", "Here is the school, the knowledge provider for everyone " +
                        "except here everyone only wants to flirt with each other, " +
                        "I wonder what they teach them here. " +
                        "You won't be able to find anything else besides very old tables with gum under them, YUK! " +
                        "But the Teacher and Enriqueta will spawn here.",
                "./resources/rooms/school.png", "./resources/maps/mapSchool.png");

        wb.buildRoom("4","Cinema", "The cinema is a very cozy place" +
                        "where you can watch a movie with your new lover if you like" +
                        " dates where you dont even need to speak. " +
                        "Here you will be able to buy both popcorns and cocacola.",
                "./resources/rooms/cinema.png","./resources/maps/mapCinema.png");

        wb.buildRoom("5","Cafe", "This is the cafe, a very romantic place " +
                        "where you can share a snack with your new date. " +
                        "Here you can pay for , coffee, a salad or chocolate to use as a gift. " +
                        "Clementina will spawn here.",
                "./resources/rooms/cafe.png", "./resources/maps/mapCafe.png");

        wb.buildRoom("6","Gym", "The gym, the pinnacle of masculinity, " +
                        "where you are supposed to train but it's normaly used to show of your skills, " +
                        "make sure to get a shower after it, if not you are only going to charm the flies. " +
                        "Here you can build up your beuty and strength using the dunbell, you will also be " +
                        "able to buy energy drinks and bars to keep your energy up.",
                "./resources/rooms/gym.png", "./resources/maps/mapGym.png");

        wb.buildRoom("7","Pool", "The pool, not much to do in the pool but " +
                        "have a swim with your new date in what you can call a big puddle.",
                "./resources/rooms/pool.png", "./resources/maps/mapPool.png");

        wb.buildRoom("8","Shop", "The capitalist center, the Shop. " +
                        "Consuming hours of time into materials has never felt so good, do not worry though, " +
                        "next time you visit they will have another item on sale just for you. " +
                        "Here you will be able to buy clothes and make-up. Things like T-shirts, shirts, " +
                        "lipsticks and even old fashioned hats to upgrade your skills will be at your disposal.",
                "./resources/rooms/shop.png", "./resources/maps/mapShop.png");

        wb.buildRoom("9","Disco", "The disco is the place where you can feel like " +
                        "he king of the dance floor and look like a sweaty pig, you can also use the " +
                        "antique strategy of buying a drink to flirt. " +
                        "Here you will be able to buy Ron-cola, Gintonic, Vodka-Martini or mojito " +
                        "to charm your date and also make her a little dizy.",
                "./resources/rooms/disco.png", "./resources/maps/mapDisco.png");

        wb.buildRoom("10","Beach", "This is the beach, very charming and wet, " +
                        "this is the perfect date if you don't mind a little sand pretty much " +
                        "everywhere but it is worth it because of the pretty sunsets. " +
                        "Here you will be able to buy cocacola and strawberry icecream. " +
                        "Sans will spawn here.",
                "./resources/rooms/beach.png", "./resources/maps/mapBeach.png");


        //Exits
        wb.buildExit("0", Direction.UP, "1");
        wb.buildExit("1", Direction.DOWN, "0");

        wb.buildExit("0", Direction.EAST, "2");
        wb.buildExit("2", Direction.WEST, "0");

        wb.buildExit("2", Direction.NORTH, "3");
        wb.buildExit("3", Direction.SOUTH, "2");

        wb.buildExit("2", Direction.SOUTH, "4");
        wb.buildExit("4", Direction.NORTH, "2");

        wb.buildExit("3", Direction.EAST, "7");
        wb.buildExit("7", Direction.WEST, "3");

        wb.buildExit("2", Direction.EAST, "5");
        wb.buildExit("5", Direction.WEST, "2");

        wb.buildExit("4", Direction.EAST, "6");
        wb.buildExit("6", Direction.WEST, "4");

        wb.buildExit("5", Direction.NORTH, "7");
        wb.buildExit("7", Direction.SOUTH, "5");

        wb.buildExit("5", Direction.SOUTH, "6");
        wb.buildExit("6", Direction.NORTH, "5");

        wb.buildExit("6", Direction.EAST, "9");
        wb.buildExit("9", Direction.WEST, "6");

        wb.buildExit("5", Direction.EAST, "8");
        wb.buildExit("8", Direction.WEST, "5");

        wb.buildExit("8", Direction.SOUTH, "9");
        wb.buildExit("9", Direction.NORTH, "8");

        wb.buildExit("9", Direction.EAST, "10");
        wb.buildExit("10", Direction.WEST, "9");

        //Mobiles
        Mobile mobile;

        mobile = mobileFactory.buildMobile("Garfield","./resources/npc/garfield.png", MobileState.NoSeducible);
        wb.addMobile("2", mobile);
        mobile.addSentence("Miau");
        mobile.addSentence("Sorry, I am a cat");
        mobile.addSentence("If only you were a cat...");

        mobile = mobileFactory.buildMobile("Teacher","./resources/npc/teacher.png", MobileState.NoMovable);
        wb.addMobile("3", mobile);
        mobile.addSentence("Sorry, I'm you teacher");
        mobile.addSentence("This is not posible");
        mobile.addSentence("I don't want to go to jail");

        mobile = mobileFactory.buildMobile("Enriqueta","./resources/npc/enriqueta.png", MobileState.Single);
        wb.addMobile("3", mobile);

        mobile = mobileFactory.buildMobile("Clementina","./resources/npc/clementina.png", MobileState.Single);
        wb.addMobile("5", mobile);

        mobile = mobileFactory.buildMobile("Sergio","./resources/npc/sergio.png", MobileState.Single);
        wb.addMobile("6", mobile);

        mobile = mobileFactory.buildMobile("Sans","./resources/npc/sans.png", MobileState.Single);
        wb.addMobile("10", mobile);

        //Gears
        Gear gear;

        //Room
        gear = gearFactory.buildPersonalGear("Phone", "Your personal mobile phone",
                "./resources/gears/objects/phone.png" , 0);
        wb.addGear("1", gear);

        gear = gearFactory.buildClothes("Jumpsuit", "Green Jumpsuit",
                "./resources/gears/clothing/jumpsuit.png", 0, 10, -5);
        wb.addGear("1", gear);

        //Park
        gear = gearFactory.buildGift("Flower", "A special gift for a special person",
                "./resources/gears/gifts/FlowerPark.png", 0, 10);
        wb.addGear("2", gear);

        gear = gearFactory.buildGift("Roses", "A special gift for a special person",
                "./resources/gears/gifts/roses.png", 0, 20);
        wb.addGear("2", gear);

        gear = gearFactory.buildFood("Strawberry Ice Cream", "Delicious strawberry ice cream",
                "./resources/gears/food/StrawberryIceCream.png", 10, 5);
        wb.addGear("2", gear);

        gear = gearFactory.buildFood("Cola Ice Cream", "Delicious strawberry ice cream",
                "./resources/gears/food/ColaIceCream.png", 10, 5);
        wb.addGear("2", gear);

        //Cinema
        gear = gearFactory.buildFood("Popcorns", "The perfect snak for a romantic film",
                "./resources/gears/food/popcorn.png", 15, -5);
        wb.addGear("4", gear);

        gear = gearFactory.buildFood("Cola", "The perfect drink for a romantic film",
                "./resources/gears/food/cola.png", 15, -5);
        wb.addGear("4", gear);

        //Cafe
        gear = gearFactory.buildGift("Chocolates", "A special gift for a special person",
                "./resources/gears/gifts/chocolate.png", 50, 25);
        wb.addGear("5", gear);

        gear = gearFactory.buildFood("Coffee", "A romantic coffee",
                "./resources/gears/food/coffee.png", 5, 3);
        wb.addGear("5", gear);

        gear = gearFactory.buildFood("Salad", "We are what we eat",
                "./resources/gears/food/salad.png", 5, 10);
        wb.addGear("5", gear);

        gear = gearFactory.buildFood("Apple", "We are what we eat",
                "./resources/gears/food/apple.png", 5, 20);
        wb.addGear("5", gear);

        //Shop
        gear = gearFactory.buildClothes("Hat", "An elegant hat",
                "./resources/gears/clothing/hat.png", 10, 20, 10);
        wb.addGear("8", gear);

        gear = gearFactory.buildClothes("T-Shirt", "A informal t-shirt",
                "./resources/gears/clothing/tShirt.png", 20, 0, 10);
        wb.addGear("8", gear);

        gear = gearFactory.buildClothes("Shirt", "An elegant shirt",
                "./resources/gears/clothing/shirt.png", 50, 10, 30);
        wb.addGear("8", gear);

        gear = gearFactory.buildClothes("Lipstick", "A red lipstick",
                "./resources/gears/clothing/lipstick.png", 20, 10, 10);
        wb.addGear("8", gear);

        //Gym
        gear = gearFactory.buildFood("Energy bar", "Gives you the energy to flirt",
                "./resources/gears/food/energyBar.png", 10, 20);
        wb.addGear("6", gear);

        gear = gearFactory.buildPersonalGear("Dumbbell", "Use it to train",
                "./resources/gears/objects/dumbbell.png" , 0);
        wb.addGear("6", gear);

        //Disco
        gear = gearFactory.buildFood("Ron Cola", "Ron with Coca Cola",
                "./resources/gears/food/ronCola.png", 20, 10);
        wb.addGear("9", gear);

        gear = gearFactory.buildFood("Gin Tonic", "Gin with tonic",
                "./resources/gears/food/ginTonic.png", 20, 10);
        wb.addGear("9", gear);

        gear = gearFactory.buildFood("Vodka Martini", "A glass of vodka",
                "./resources/gears/food/martini.png", 10, 10);
        wb.addGear("9", gear);

        gear = gearFactory.buildFood("Mojito", "A glass of mojito",
                "./resources/gears/food/mojito.png", 10, 10);
        wb.addGear("9", gear);

        //Beach
        gear = gearFactory.buildFood("Strawberry Ice Cream", "Delicious strawberry ice cream",
                "./resources/gears/food/StrawberryIceCream.png", 10, 5);
        wb.addGear("10", gear);

        gear = gearFactory.buildFood("Cola Ice Cream", "Delicious cola ice cream",
                "./resources/gears/food/ColaIceCream.png", 10, 5);
        wb.addGear("10", gear);

    }

    public World getWorld(){
        return wb.getWorld();
    }
}
