package doggestdungeon;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

class Controller {

    //Imports pictures
    final ImageIcon treatsIcon = new ImageIcon("nycDogTreats.PNG");
    final ImageIcon nycIcon = new ImageIcon("nycPicture.jpg");
    final ImageIcon introIcon = new ImageIcon("nycPictureIntro.PNG");
    final ImageIcon raceIcon = new ImageIcon("nycRacePicture.PNG");
    final ImageIcon rainIcon = new ImageIcon("nycRainGif.gif");
    final ImageIcon pitbullIcon = new ImageIcon("nycPicturePitbull.PNG");
    final ImageIcon chihuahuaIcon = new ImageIcon("nycPictureChihuahua.PNG");
    final ImageIcon goldenIcon = new ImageIcon("nycPictureGolden.PNG");
    final ImageIcon crossbreedIcon = new ImageIcon("nycPictureCross.PNG");
    final ImageIcon ripIcon = new ImageIcon("nycPictureRIP.PNG");
    final ImageIcon winIcon = new ImageIcon("nycPictureWin.PNG");
    final ImageIcon helpIcon = new ImageIcon("nycPictureHelp.PNG");

    //Creates our object from the Dog class.
    Dog dog = new Dog();

    //Creates a way to end our game loop
    boolean endGame = false;

    public void init() {
    
        //Creates a String
        //"Load Game" and "High Score" is not yet in the game 
        String[] startOptions = new String[]{"New Game", "Load Game", "High Score"};

        //Creates a intro window, but doesnt have any effect on the game yet
        JOptionPane.showOptionDialog(null, "", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, rainIcon, startOptions, startOptions[0]);

        //Creates a list of Strings
        String[] raceOptions = new String[]{"Pitbull", "Chihuahua", "Golden Retriver", "Crossbreed"};

        //Creates an Option window, to choose a dogs starting race.
        //It uses the raceOptions String.
        int chooseDogRace = JOptionPane.showOptionDialog(null, "", "Choose your start race",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, raceIcon, raceOptions, raceOptions[0]);

        //String made of choosen dog race.
        String dogRace = raceOptions[chooseDogRace];
        dog.setDogRace(dogRace);

        //Sets different modifiers, from race choice.
        switch (dogRace) {
            case "Pitbull":
                {
                    dog.setDogHealth(dog.getDogHealth() * 2);
                    //Creates a dialog window, to choose/store a dog name.
                    String setDogName = JOptionPane.showInputDialog(pitbullIcon, "Enter dog name");
                    dog.setDogName(setDogName);
                    dog.setDogIcon(pitbullIcon);
                    break;
                }
            case "Chihuahua":
                {
                    dog.setDogHealth(dog.getDogHealth() * 0.75);
                    //Creates a dialog window, to choose/store a dog name.
                    String setDogName = JOptionPane.showInputDialog(chihuahuaIcon, "Enter dog name");
                    dog.setDogName(setDogName);
                    dog.setDogIcon(chihuahuaIcon);
                    break;
                }
            case "Golden Retriver":
                {
                    dog.setTreatTotal(dog.getTreatTotal() + 5);
                    //Creates a dialog window, to choose/store a dog name.
                    String setDogName = JOptionPane.showInputDialog(goldenIcon, "Enter dog name");
                    dog.setDogName(setDogName);
                    dog.setDogIcon(goldenIcon);
                    break;
                }
            case "Crossbreed":
                {
                    dog.setDogHealth(dog.getDogHealth() * 1.25);
                    //Creates a dialog window, to choose/store a dog name.
                    String setDogName = JOptionPane.showInputDialog(crossbreedIcon, "Enter dog name");
                    dog.setDogName(setDogName);
                    dog.setDogIcon(crossbreedIcon);
                    break;
                }
            default:
                System.out.println("unkown error // Dog");
                break;
        }

        //Creates the world
        createMap();
    }

    public void run() {

        //Creates a list of Strings for action menu
        String[] actionOptions = new String[]{"Help","Fight", "Dog info", "Loot", "Move"};

        //Creates a list of strings - made space for more options
        String[] deathOptions = new String[]{"Game over"};

        //Our game loop
        while (dog.getDogHealth() > 0) {

            //creates a situation where you win by reaching endRoom
            if (dog.getCurrentStreet().getLastRoom() == true) {

                JOptionPane.showMessageDialog(null, dog.getCurrentStreet().getStreetDescription(), "You're home!", 0, dog.getCurrentStreet().getStreetIcon());
                JOptionPane.showMessageDialog(null, "Congratulation! "
                        + "\nYou made the journey home!"
                        + "\nOn the journey home you collected: " + dog.getTreatTotal() + " treats!"
                        + "\nCreated by: Anton & Kasper", "You won!", 0, winIcon);
                break;
            }

            //Creates street trap damage + option if you die from traps
            if (dog.getCurrentStreet().getStreetTrapDamage() != 0) {
                dog.setDogHealth(dog.getDogHealth() - dog.getCurrentStreet().getStreetTrapDamage());
                dog.getCurrentStreet().setStreetTrapDamage(0);
                if (dog.getDogHealth() <= 0) {
                    JOptionPane.showOptionDialog(null, dog.getCurrentStreet().getStreetDeathDescription()
                            + "\nYou died, what now, " + dog.getDogName() + "?", "All out of time!",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, dog.getCurrentStreet().getDeathStreetIcon(), deathOptions, deathOptions[0]);
                    break;
                }
            }

            //Creates a ArrayList<String> with posible destinations
            ArrayList<String> destinationOptions = new ArrayList();
            if (dog.getCurrentStreet().getStreetWest() != null) {
                destinationOptions.add("West");
            }
            if (dog.getCurrentStreet().getStreetNorth() != null) {
                destinationOptions.add("North");
            }
            if (dog.getCurrentStreet().getStreetSouth() != null) {
                destinationOptions.add("South");
            }
            if (dog.getCurrentStreet().getStreetEast() != null) {
                destinationOptions.add("East");
            }

            String[] list = new String[destinationOptions.size()];
            list = destinationOptions.toArray(list);

            //Creates a list of Strings
            String[] moveOptions = list;//new String[]{"North", "East", "South", "West"};

            //Creates a int to hold the player answers from options below.
            int actionChoice;

            if (dog.getCurrentStreet().getVisited() == false) {
            //Creates an Option window, to choose what to do.
                //It uses the actionOptions String.
                actionChoice = JOptionPane.showOptionDialog(null, dog.getCurrentStreet().getStreetDescription()
                        + "\nWhat do you want to do, " + dog.getDogName() + "?", "Time for action!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, dog.getCurrentStreet().getStreetIcon(), actionOptions, actionOptions[0]);
            } else {
                actionChoice = JOptionPane.showOptionDialog(null, dog.getCurrentStreet().getStreetAltDescription()
                        + "\nWhat do you want to do, " + dog.getDogName() + "?", "Time for action!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, dog.getCurrentStreet().getAltStreetIcon(), actionOptions, actionOptions[0]);
            }
            //if "Help" is clicked
            if(actionChoice == 0 && dog.getDogHealth() > 0){
                //Creates a Message with help info
                JOptionPane.showMessageDialog(null, "You choose the help menu,  " + dog.getDogName()
                        + "\n "
                        + "\nThese are the options you can use in the action window"
                        + "\nUse the \"Fight\" button to fight enemies(not in this version)."
                        + "\nUse the \"Dog Info\" button to view your dog-sheet"
                        + "\nUse the \"Loot\" to try and loot treats in the area"
                        + "\nUse the \"Move\" button to move to different streets"
                        + "\n "
                        + "\nThese are the options you can use in the move window"
                        + "\nUse the \"West\" button to move to west"
                        + "\nUse the \"North\" button to move to north."
                        + "\nUse the \"East\" button to move to east."
                        + "\nUse the \"South\" button to move to south."
                        + "\n "
                        + "\nRemember - streets change! Try and visit previously visited streets.", "Help is on it's way!", 0, helpIcon);
            }
            
            //If "Fight" is clicked
            else if (actionChoice == 1 && dog.getDogHealth() > 0) {

                //add combat
            } //If "dog info" is clicked
            else if (actionChoice == 2 && dog.getDogHealth() > 0) {

                //Creates a Message with dog info
                JOptionPane.showMessageDialog(null, "Stats for: " + dog.getDogName()
                        + "\nDog race: " + dog.getDogRace()
                        + "\nDog health: " + dog.getDogHealth()
                        + "\nDog total treats: " + dog.getTreatTotal(), "", 0, dog.getDogIcon());
            } //If "look for loot" is clicked
            else if (actionChoice == 3 && dog.getDogHealth() > 0) {
                if (dog.getCurrentStreet().getStreetTreats() == 0) {
                    JOptionPane.showMessageDialog(null, "You didn't find any loot");
                } else {
                    JOptionPane.showMessageDialog(null, "You found: " + dog.getCurrentStreet().getStreetTreats() + " treats", "Treats!", 0, treatsIcon);
                    dog.setTreatTotal(dog.getTreatTotal() + dog.getCurrentStreet().getStreetTreats());
                    dog.getCurrentStreet().setStreetTreats(0);
                }
            } else if (actionChoice == 4 && dog.getDogHealth() > 0) {

                //Sets currentStreet to visited = true;
                dog.getCurrentStreet().setVisited(true);
                //Creates an Option window, to choose where to go.
                //It uses the moveChoice int.
                int moveChoice = JOptionPane.showOptionDialog(null, "Where do you want to go, " + dog.getDogName() + "?", "Time to move!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, dog.getCurrentStreet().getAltStreetIcon(), moveOptions, moveOptions[0]);

                String whereToMove = moveOptions[moveChoice];

                if (whereToMove.equals("North") && dog.getCurrentStreet().getStreetNorth() != null) {
                    dog.setCurrentStreet(dog.getCurrentStreet().getStreetNorth());
                } else if (whereToMove.equals("East") && dog.getCurrentStreet().getStreetEast() != null) {
                    dog.setCurrentStreet(dog.getCurrentStreet().getStreetEast());
                } else if (whereToMove.equals("South") && dog.getCurrentStreet().getStreetSouth() != null) {
                    dog.setCurrentStreet(dog.getCurrentStreet().getStreetSouth());
                } else if (whereToMove.equals("West") && dog.getCurrentStreet().getStreetWest() != null) {
                    dog.setCurrentStreet(dog.getCurrentStreet().getStreetWest());
                } else {
                    System.out.println("Unkown move error!");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Quit or dead, you're still a loser!", "", 0, ripIcon);
                break;

            }

        }
        if (dog.getDogHealth() > 0 && dog.getCurrentStreet().getLastRoom() == true) {
            System.out.println("You won");
        } else {
            System.out.println("You lost");
        }

    }

    public void createMap() {

        //Creates the streets
        //street1 is startStreet and street26 is the treasureStreet
        Street street1 = new Street();
        Street street2 = new Street();
        Street street3 = new Street();
        Street street4 = new Street();
        Street street5 = new Street();
        Street street6 = new Street();
        Street street7 = new Street();
        Street street8 = new Street();
        Street street9 = new Street();
        Street street10 = new Street();
        Street street11 = new Street();
        Street street12 = new Street();
        Street street13 = new Street();
        Street street14 = new Street();
        Street street15 = new Street();
        Street street16 = new Street();
        Street street17 = new Street();
        Street street18 = new Street();
        Street street19 = new Street();
        Street street20 = new Street();
        Street street21 = new Street();
        Street street22 = new Street();
        Street street23 = new Street();
        Street street24 = new Street();
        Street street25 = new Street();
        Street street26 = new Street();
        Street street27 = new Street();

        //Defines what streets are around this street
        street1.setStreetNorth(null);
        street1.setStreetEast(null);
        street1.setStreetSouth(street2);
        street1.setStreetWest(null);

        //Sets a destription to the street
        street1.setStreetDescription("Where are they? Where have they gone?\n"
                + " \n"
                + "You are tired. Just go back to sleep - they will come for you…\n"
                + " \n"
                + "No! You have to wake up. You have to find them!\n"
                + " \n"
                + "The night is dark and full of terrors but you have to brave them. You must find your family!\n"
                + " \n"
                + "You find yourself standing in a dark alley. There is only one way out. South.");

        //Sets amount of treats in room
        street1.setStreetTreats(2);

        //Sets a icon for the street
        street1.setStreetIcon(new ImageIcon("nycPicture1.PNG"));
        //If a alternate situtaion in the street calls for another picture, its put here
        //Otherwise we reuse the first picture.
        street1.setAltStreetIcon(street1.getStreetIcon());
        street1.setStreetAltDescription("You are back in that dank dark alley where you first awoke.\n"
                + "This is where this horrible night began. You feel a cold shiver down your spine and feel a sudden need to leave."
                + "\nYou better head south once more.");

        street2.setStreetNorth(street1);
        street2.setStreetEast(street11);
        street2.setStreetSouth(street4);
        street2.setStreetWest(street3);
        street2.setStreetDescription("You're standing by a crossroad. "
                + "\nLights are on all around - a sharp contrast from the dark alley from which you emerged.\n"
                + "You take a minute adjusting your sight and suddenly spot a fellow dog chilling on a nearby corner.\n"
                + "Still shaken from last nights horrible experience you approach this black and white street dog looking for direction.\n"
                + "The dog introduces itself:\n"
                + "\"Yo what's up my fellow woofer, you look a bit off, what's your name?\"\n"
                + "\"Hi there " + dog.getDogName() + ", my name is Snoop, Snoop the dog\".\n"
                + "\"I left some treats over there, feel free to loot them. "
                + "\nThe streets look dangerous tonight dawg, you be save now okay?\"\n"
                + "Snoop bounces off towards the east.");
        street2.setStreetTreats(7);
        street2.setAltStreetIcon(new ImageIcon("nycPicture2Alt.PNG"));
        street2.setStreetIcon(new ImageIcon("nycPicture2.PNG"));
        street2.setStreetAltDescription("Your eyes are suddenly blinded a bit by the traffic light in the nearby intersection."
                + "\nYour back in the crossroad where you first met Snoop the dog."
                + "\nYou remember seeing him bounce off towards the east."
                + "\nPerhaps that would be a good place to continue your search?");

        street3.setStreetNorth(null);
        street3.setStreetEast(street2);
        street3.setStreetSouth(null);
        street3.setStreetWest(street9);
        street3.setStreetTreats(7);
        street3.setStreetDescription("You head down a small street with a few broken lamps casting sporadic lights"
                + "\nacross what appears to be a very shady neighborhood."
                + "\nYou hear people inside yelling at each other breaking stuff and making all kinds of frightening sounds."
                + "\nAnd suddenly you hear a soft purr rolling through the air."
                + "\nYou feel the hair on the back of your neck rising. "
                + "\nYou instinctively flashes your razor-sharp teeth. "
                + "\nAnd then the cat is upon you!"
                + "\nIt jumps lightning fast from the shadow, a bit small but its speed seems uncanny in the dark.");
        street3.setStreetIcon(new ImageIcon("nycPicture3.PNG"));
        street3.setStreetAltDescription("You’ve been in this bad neighborhood before.. You remember defeating a small but very fast cat here.");
        street3.setAltStreetIcon(street3.getStreetIcon());

        street4.setStreetNorth(street2);
        street4.setStreetEast(null);
        street4.setStreetSouth(street5);
        street4.setStreetWest(null);
        street4.setStreetDescription("All the windows on this street are dark. Except for one big glass window on one of the\n"
                + "penthouse apartments. Here you see a large group of intoxicated humans doing their strange\n"
                + "mating rituals involving stuff like: dancing, drinking and making out?\n"
                + " \n"
                + "Below the apartment you find a kebab - one of the drunken guests must have dropped it.\n"
                + " \n"
                + "Beside the kebab and the happy loud sounds coming drunken humans nothing else stands out.");
        street4.setStreetIcon(new ImageIcon("nycPicture4.PNG"));
        street4.setStreetTreats(5);
        street4.setAltStreetIcon(street4.getStreetIcon());
        street4.setStreetAltDescription("The street is completely dark now. You remember finding a kebab here and seeing a group of people partying.");

        street5.setStreetNorth(street4);
        street5.setStreetEast(null);
        street5.setStreetSouth(null);
        street5.setStreetWest(street6);
        street5.setStreetDescription("You get to a corner and spot a strange looking bulldog in the distance. "
                + "\nYou get bad vibe from this fellow. "
                + "\nHis mouth is clenched but you feel like he could head for your throat any minute. "
                + "\nBut you have to find your family. You must press on."
                + "\nThe dog barks: Hey what are you looking at punk!"
                + "\nThis is my turf you pathetic " + dog.getDogRace() + " piece of shit,"
                + "\nNow you get to feel how my teeth in your throat feels like, HA!"
                + "\nYou must now fight for your life.");
        street5.setStreetIcon(new ImageIcon("nycPicture5.PNG"));
        street5.setAltStreetIcon(street5.getStreetIcon());
        street5.setStreetAltDescription("You fought an evil looking pitbull here. You can still see his corpse laying in the corner. "
                + "\nHmm where should you go now?");

        street6.setStreetNorth(null);
        street6.setStreetEast(street5);
        street6.setStreetSouth(null);
        street6.setStreetWest(street7);
        street6.setStreetDescription("This street is the most dull street ever!"
                + "\nNo funny smells. Nothing special to see either… "
                + "\nWhat a waste of space…"
                + "\nWell you better get a move on, because there is literally nothing here..");
        street6.setStreetIcon(new ImageIcon("nycPicture6.PNG"));
        street6.setAltStreetIcon(street6.getStreetIcon());
        street6.setStreetAltDescription("Do you remember a street so dull it almost blew your mind? Well you are there again."
                + "\n And guess what? It is still super dull… "
                + "\n "
                + "\nYou better get moving.");

        street7.setStreetNorth(street8);
        street7.setStreetEast(street6);
        street7.setStreetSouth(null);
        street7.setStreetWest(null);

        //This room has "trap damage", added below. 
        street7.setStreetTrapDamage(150);
        street7.setStreetTreats(10);
        street7.setStreetDescription("You get to another corner. You are a bit afraid due overwhelming number of cars driving on the road."
                + "\nWhile looking at the scary cars you fail to notice the bike coming straight at you."
                + "\nYou only manage to partially dodge out of the way and are hit by the annoyed biker."
                + "\nYou take " + street7.getStreetTrapDamage() + " damage");
        street7.setStreetIcon(new ImageIcon("nycPicture7.PNG"));
        street7.setDeathStreetIcon(new ImageIcon("nycPicture7Death.PNG"));
        street7.setStreetDeathDescription("You get to another corner. You are a bit afraid due overwhelming number of cars driving on the road."
                + "\nWhile looking at the scary cars you fail to notice the bike coming straight at you."
                + "\nYou were to hurt to get out of the way..");
        street7.setStreetAltDescription("There are not as many scary cars rounding this corner anymore. And no sign of any stupid bikes."
                + "\nHowever you notice a pile of tasty hot wings next to a trashcan, score!");
        street7.setAltStreetIcon(street7.getStreetIcon());

        street8.setStreetNorth(street9);
        street8.setStreetEast(null);
        street8.setStreetSouth(street7);
        street8.setStreetWest(null);
        street8.setStreetTreats(15);
        street8.setStreetDescription("The street gets wider. Suddenly you feel more at ease with the increased space around you."
                + "\nYou notice this shopping street is the one you visited a couple of weeks past with your family."
                + "\nYou remember being forced to try out silly doggy clothes."
                + "\nAt that time you made a big fuss about it."
                + "\nIf you got a chance to try out silly doggy clothes with your family again you would not make a fuss.. God you miss them.."
                + "\nNext to the shop with the doggy clothes you find a cool dog hat."
                + "\nYou decide to put it on. You feel a new spring in your step as you walk on.");
        street8.setStreetIcon(new ImageIcon("nycPicture8.PNG"));
        street8.setAltStreetIcon(street8.getStreetIcon());
        street8.setStreetAltDescription("This is the large shopping street where you got your swag hat!");

        street9.setStreetNorth(street10);
        street9.setStreetEast(street3);
        street9.setStreetSouth(street8);
        street9.setStreetDescription("You are at a t-shaped road now."
                + "\nWest you see a dark scary alley and to the south a wider brighter shopping street."
                + "\nYou can't quite make out what you see to the north?"
                + "\nPerhaps you should check it out?"
                + "\nBefore you decide which direction to go you are suddenly attacked by a hysterical and psychotic Chihuahua.");
        street9.setStreetWest(null);
        street9.setStreetIcon(new ImageIcon("nycPicture9.PNG"));
        street9.setAltStreetIcon(street9.getStreetIcon());
        street9.setStreetAltDescription("You recognize this as the street with the crazy Chihuahua."
                + "\nEast you see a dark scary alley and to the south a wider brighter shopping street."
                + "\nTo the north you only see darkness now - could there be something there?");

        street10.setStreetNorth(null);
        street10.setStreetEast(null);
        street10.setStreetSouth(street9);
        street10.setStreetWest(null);
        street10.setStreetTreats(3);
        street10.setStreetDescription("You stumble forward not quite sure what you see."
                + "\nThe street is so very dark.. But there is something further ahead."
                + "\nIs that a person? Is that your mommy! You sprint forward not realizing your mistake until it is too late."
                + "\nThe person ahead of you step into light and you see the most scary human face you've ever seen."
                + "\nA mouth with half the teeth missing and the rest pointing in strange directions,"
                + "\nlong and dirty hair and something terribly red on his dirty black clothes. Is that blo.. Blood?"
                + "\nBefore you can turn around he screams, draws a large knife and lunges at you. You must fight for your life.");
        street10.setStreetIcon(new ImageIcon("nycPicture10.PNG"));
        street10.setAltStreetIcon(street10.getStreetIcon());
        street10.setStreetAltDescription("The street is now closed of where the madman corpse was and there are police all around."
                + " You better get away from here.");

        street11.setStreetNorth(null);
        street11.setStreetEast(street12);
        street11.setStreetSouth(null);
        street11.setStreetWest(street2);
        street11.setStreetTreats(1);
        street11.setStreetDescription("You are now on a small street leading from one traffic section to another."
                + "\nYou see a small dog huddled up next to some trash cans."
                + "\nYou walk up to the dog and it turns out to be a small bulldog freezing in the cold night breeze."
                + "\nAs you walk closer you suddenly realize this could be you if you don't manage to find your family soon."
                + "\nThe dog calls out to you"
                + "\n\"Hi there " + dog.getDogRace() + ", could you spare a few treats?\""
                + "\nShould you give him your treats?"
                + "\nYou might need them yourself..");
        street11.setStreetIcon(new ImageIcon("nycPicture11.PNG"));
        
        street11.setAltStreetIcon(street11.getStreetIcon());
        street11.setStreetAltDescription("You are on the small street with the cold pitbull next to the trash cans."
                + "\nSeems like there are intersections both to the west and east from here.");

        street12.setStreetNorth(street13);
        street12.setStreetEast(null);
        street12.setStreetSouth(street15);
        street12.setStreetWest(street11);
        street12.setStreetTreats(12);
        street12.setStreetIcon(new ImageIcon("nycPicture12.PNG"));
        street12.setAltStreetIcon(street12.getStreetIcon());
        street12.setStreetDescription("You get to a new street with 3 exits. There are roads in 3 different directions "
                + "\n- where should you go? Snoop said to head east, but of course that is the only way you can't go…\n"
                + "To the southeast you spot a bridge leading over a river.\n"
                + "There are also  number of corner shops in the area. Perhaps they are worth a visit?\n"
                + "Beside the shops the streets seems empty.");
        street12.setStreetAltDescription("You are once again on the street with the 3 exits with the corner shops. And with the bridge to the southeast."
                + "\nShould you visit the corner shops or move on?");

        street13.setStreetNorth(street14);
        street13.setStreetEast(null);
        street13.setStreetSouth(street12);
        street13.setStreetWest(null);
        street13.setStreetIcon(new ImageIcon("nycPicture13.PNG"));
        street13.setAltStreetIcon(street13.getStreetIcon());
        street13.setStreetDescription("You move along another dark street - how come there is so bad lighting in this city?\n"
                + "You see a couple of shady looking dogs moving along the right sidewalk. "
                + "\nBeside them you are the only other living thing on the street. Should you approach them? They might know where your family is.");
        street13.setStreetAltDescription("You can still see the two dogs in the street. "
                + "\nThe street is still dark and with streets leading both north and south.");

        street14.setStreetNorth(null);
        street14.setStreetEast(null);
        street14.setStreetSouth(street13);
        street14.setStreetWest(null);
        street14.setStreetTreats(1);
        street14.setStreetDescription("You get to a dead end and there appears to be no sign of your family.. Damn it."
                + "+\nWill this night of horrors never end. You feel incredible down."
                + "+\nThere are only a couple of trashcans next to the wall."
                + "\nYou are still a long way from finding home and your family.");
        street14.setStreetAltDescription("How could you end up at this dead end again.."
                + "\nYou should have known going so far north again was wrong."
                + "\nIf only you had a better memory you would not have been in this mess."
                + "\nThere are still a couple of trashcan next to the wall.");
        street14.setStreetIcon(new ImageIcon("nycPicture14.PNG"));
        street14.setAltStreetIcon(street14.getStreetIcon());

        street15.setStreetNorth(street12);
        street15.setStreetEast(street22);
        street15.setStreetSouth(street16);
        street15.setStreetWest(null);
        street15.setStreetIcon(new ImageIcon("nycPicture15.PNG"));
        street15.setStreetDescription("You are in a T-shaped street again."
                + "\nThis time you see the bridge right to the east. "
                + "\nTo the north you see the other t-shaped road and to the south you see a road going further south."
                + "\nOn this particular street you see a couple of humans coming from the bridge. "
                + "\nPerhaps your family live on the either of that river?");
        street15.setStreetAltDescription("You are once again in the T-shaped street with the bridge to east "
                + "\nand with the street stretching further north and south. "
                + "\nYou still see a couple of humans walking around.");
        street15.setAltStreetIcon(street15.getStreetIcon());

        street16.setStreetNorth(street15);
        street16.setStreetEast(null);
        street16.setStreetSouth(street17);
        street16.setStreetWest(null);
        street16.setStreetTreats(20);
        street16.setStreetIcon(new ImageIcon("nycPicture16.JPG"));
        street16.setAltStreetIcon(street16.getStreetIcon());
        street16.setStreetDescription("This street is filled with cats. You can't remember ever seeing so many cats on one street before.\n"
                + "You can still see the river to the east and the road north leading towards the bridge. "
                + "\nTo the south you see another corner seemingly leading west.");
        street16.setStreetAltDescription("This is the street with the horde of cats. You can either move north or south from here.");

        street17.setStreetNorth(street16);
        street17.setStreetEast(street18);
        street17.setStreetSouth(null);
        street17.setStreetWest(null);
        street17.setStreetIcon(new ImageIcon("nycPicture17.PNG"));
        street17.setAltStreetIcon(street17.getStreetIcon());
        street17.setStreetDescription("You spot an open corner office."
                + "\nSeems like the place could be a small animal hospital."
                + "\nYou are pretty banged up. Perhaps it would be a good idea to visit?"
                + "\nYou could also just press on and try to find you family. You can head");
        street17.setStreetAltDescription("You are back at the corner but this time it seems closed."
                + "\nYou can either head north or go towards the east.");

        street18.setStreetNorth(null);
        street18.setStreetEast(street19);
        street18.setStreetSouth(null);
        street18.setStreetWest(street17);
        street18.setStreetTreats(20);
        street18.setStreetIcon(new ImageIcon("nycPicture18.jpg"));
        street18.setAltStreetIcon(street18.getStreetIcon());
        street18.setStreetDescription("You suddenly feel a chill running through you."
                + "\nWhat is this terrible cold feeling. You soon realize why your cold."
                + "\nTo the south you see a scary looking graveyard with strange and spooky sounds coming out from it."
                + "\nYou feel your steps quicken as you want to hurry past this place.\n"
                + "Now the scary sounds intensifies and suddenly a large rugged looking Labrador jumps in front of you."
                + "\nIt looks like it has just risen from the dead.\n"
                + "\"Doggy braaiiins!\"\n"
                + "\"Braaaaiins!\"\n"
                + "You are frightened beyond belief.\n"
                + "However it seems like a fight is unavoidable.");
        street18.setStreetAltDescription("You can still feel the chill from the graveyard here. "
                + "\nThe corpse from the zombie-like Labrador is gone however - spooky.\n"
                + "You can head either west or east.");

        street19.setStreetNorth(street20);
        street19.setStreetEast(street27);
        street19.setStreetSouth(null);
        street19.setStreetWest(street18);
        street19.setStreetTreats(10);
        street19.setStreetIcon(new ImageIcon("nycPicture19.PNG"));
        street19.setAltStreetIcon(street19.getStreetIcon());
        street19.setStreetDescription("You get to yet another T-shaped road."
                + "\nYou feel a  chill coming from the west."
                + "\nAnd to the east you smell something familiar. "
                + "\nWhat is that scent hmm.. strange.. \n"
                + "To the north you see the street  becoming more narrow. \n"
                + "There are a  couple of funny smells on this street. Perhaps you should investigate?");
        street19.setStreetAltDescription("You are at the t-shaped road with the chill from the west and the strange familiar smell to the east."
                + "\nYou also see the street becoming more narrow to the north.");

        street20.setStreetNorth(street21);
        street20.setStreetEast(null);
        street20.setStreetSouth(street19);
        street20.setStreetWest(null);
        street20.setStreetIcon(new ImageIcon("nycPicture20.PNG"));
        street20.setAltStreetIcon(street20.getStreetIcon());
        street20.setStreetDescription("You are on a very narrow street. You feel a bit claustrophobic in this street."
                + "\nYou don't really want to stop and look around."
                + "\nYou feel more at home with more space around you."
                + "\nYou can either head north or south.");
        street20.setStreetAltDescription("Damn it.\n"
                + "You are back at the street that makes you claustrophobic."
                + "\nYou can head either north or south on this street.");

        street21.setStreetNorth(street23);
        street21.setStreetEast(null);
        street21.setStreetSouth(street20);
        street21.setStreetWest(street22);
        street21.setStreetIcon(new ImageIcon("nycPicture21.PNG"));
        street21.setAltStreetIcon(street15.getAltStreetIcon());
        street21.setStreetDescription("You are now on the other side of the bridge. It is now on your west.\n"
                + "You must have managed to circle your way south beneath the river somehow.\n"
                + "The narrow street is behind you ad in front of you the street get wider -  and you see trees on either side of the road. "
                + "\nIt seems familiar somehow.\n"
                + "On this street you also see a cool looking dog wearing shades. Wait isn't that Snoop?\n"
                + "You greet Snoop and thanks him for the help earlier.\n"
                + "\"Don't think about it " + dog.getDogName() + " I got your back dawg\"\n"
                + "Almost before Snoop finishes his sentence we hear a snarl and bark coming from the west.\n"
                + "There we see 3 dogs crossing the bridge heading straight for Snoop.\n"
                + "\"Oh shit.. Damn..\"\n"
                + "You hear Snoop say.\n"
                + "\"Stay back now dawg!\"\n"
                + "As he warns you the first angry looking dog jump towards Snoop - intending to kill.");
        street21.setStreetAltDescription("You are back to the spot where you met Snoop the dog for the second time."
                + "\nYou are overwhelmed with grief as you see the empty spot where you talked to him only a while back."
                + "\nYou simply have to live a good long life. For Snoop!\n"
                + "The bridge is to the west. The narrow street south and to the north you see the wide street with trees to the sides.");

        street22.setStreetNorth(null);
        street22.setStreetEast(street21);
        street22.setStreetSouth(null);
        street22.setStreetWest(street15);
        street22.setStreetIcon(new ImageIcon("nycPicture22.PNG"));
        street22.setStreetTrapDamage(1000);
        street22.setAltStreetIcon(street22.getStreetIcon());
        street22.setDeathStreetIcon(new ImageIcon("nycPicture22Death.PNG"));
        street22.setStreetDeathDescription("You get on the bridge. You remember fondly walking here with your family."
                + "\nThe fresh air is good for you. You suddenly feel more alive than you've done all evening."
                + "\nWhile breathing in the fresh air and enjoying the view of the city."
                + "\nYou fail to notice a group of young street kids as they approach."
                + "\nWhen you notice them it is already too late!"
                + "\nThey grab you and you are no match for their combined strength."
                + "\nYou bark and snarl and whine but to no avail."
                + "\nYou are tossed off the bridge as you fall towards the cold water,"
                + "\nyour last thoughts are off your family and how they will miss you and how much you will miss them…");

        street23.setStreetNorth(street24);
        street23.setStreetEast(null);
        street23.setStreetSouth(street21);
        street23.setStreetWest(null);
        street23.setStreetTreats(4);
        street23.setStreetIcon(new ImageIcon("nycPicture23.jpg"));
        street23.setAltStreetIcon(street23.getStreetIcon());
        street23.setStreetDescription("You find yourself walking on a nice street with beautiful trees to either side.\n"
                + "You feel like you've been here before. But you are not quite sure you have. "
                + "\nYour doggy memory is not the best memory in the animal kingdom.\n"
                + "It is very peaceful here. A sharp contrast from the area you woke up in some hours ago.\n"
                + "You feel at ease with all the green around you.\n"
                + "You can either continue north or head back south.");
        street23.setStreetAltDescription("You back at the nice green street with trees to either side. You can either head north or south from here.");

        street24.setStreetNorth(street25);
        street24.setStreetEast(street26);
        street24.setStreetSouth(street23);
        street24.setStreetWest(null);
        street24.setStreetTreats(1);
        street24.setStreetIcon(new ImageIcon("nycPicture24.PNG"));
        street24.setAltStreetIcon(street24.getStreetIcon());
        street24.setStreetDescription("You are almost there! You can smell home now. It is so close!\n"
                + "You are at another 3 way junction.\n"
                + "Should you head straight ahead north or east. You could of course also go south again."
                + "\nPerhaps you missed something?"
                + "\nThis might be the last street you have to walk on before your home in your family's warm embrace."
                + "\nYou better make this choice count!");
        street24.setStreetAltDescription("You are back at the 3 way junction so very close to home!"
                + "\nIt fees like your family could be right around the corner now. "
                + "\nYou feel your tail waking from side to side - your full of anticipation.");

        street25.setStreetNorth(null);
        street25.setStreetEast(null);
        street25.setStreetSouth(street24);
        street25.setStreetWest(null);
        street25.setStreetTreats(100);
        street25.setStreetIcon(new ImageIcon("nycPicture25.PNG"));
        street25.setAltStreetIcon(new ImageIcon("nycPicture25Alt.PNG"));
        street25.setStreetDescription("You took the wrong direction!\n"
                + "Ahead of you see every loose dogs worst nightmare.\n"
                + "It is the dog catcher and his van filled with homeless and desperate dogs.\n"
                + "You saw him too late, and now you must prepare for the consequences.\n"
                + "You have to fight this most evil man - and in the process you could also be the dog hero"
                + "\nall the dogs in the van deserve this terrible night.");
        street25.setStreetAltDescription("You are back where you freed the dog from the dog catcher. What are you doing!?\n"
                + "You know where your family lives. Just to south and then east. The cold dog told you so. Get going!");

        street26.setLastRoom(true);
        street26.setStreetNorth(null);
        street26.setStreetEast(null);
        street26.setStreetSouth(null);
        street26.setStreetWest(street24);
        street26.setAltStreetIcon(street26.getStreetIcon());
        street26.setStreetIcon(new ImageIcon("nycPicture26.PNG"));
        street26.setStreetDescription("You are home!!!\n"
                + " \n"
                + "You run fast toward the door.\n"
                + " \n"
                + "Too fast!\n"
                + " \n"
                + "You slam headfirst into the door making quite the ruckus.\n"
                + "You hear footsteps approaching. Several pair of feet accompanied by several distinct and wonderful smells.\n"
                + "Then the door open and you are bathed in wonderful warmth and wonderful light.\n"
                + " \n"
                + "\"" + dog.getDogName() + " you are home! However did you find us?\""
                + "\n\"God we missed you!\"\n"
                + " \n"
                + "And god how you have missed them.\n"
                + " \n"
                + "Now you are safe..\n"
                + " \n"
                + "Now you are Home.");

        street27.setStreetNorth(null);
        street27.setStreetEast(null);
        street27.setStreetSouth(null);
        street27.setStreetWest(street19);
        street27.setStreetTreats(30);
        street27.setStreetDescription("You enter a shop with a peculiar sign on the window.\n"
                + "It seems to be a dog in army clothes carrying and AK47.\n"
                + "Inside you see a very swag dog wearing shades on the counter. He gestures you to come closer.\n"
                + "\"Hi there " + dog.getDogRace() + ", looking to arm yourself? The streets are looking tough tonight.\"\n"
                + "\"I got just what you need. These are the swaggest most badass doggy sunglasses on this side of the hemisphere!\"\n"
                + "\"No you don't need to pay. This one is on Bobby. Just spread the word - The 'Bark and Bite' is upon for business.\"\n"
                + "You thank Bobby and wearing your new killer shades you are ready for anything the streets might throw at you.");
        street27.setStreetAltDescription("\"Welcome back to my shop!\"\n"
                + "You see Bobby behind the counter.\n"
                + "\"Do you want to get anything?\"\n"
                + "\"No? Then have a pleasant evening!\"");
        street27.setStreetIcon(new ImageIcon("nycPicture27.PNG"));
        street27.setAltStreetIcon(street27.getStreetIcon());

        //Set the players start position.
        dog.setCurrentStreet(street1);
    }
}
