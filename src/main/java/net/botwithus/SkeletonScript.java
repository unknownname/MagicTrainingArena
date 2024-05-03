package net.botwithus;

import jdk.jshell.execution.LoaderDelegate;
import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.game.hud.inventories.Inventory;
import net.botwithus.api.game.world.Traverse;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Area;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.actionbar.ActionBar;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.minimenu.actions.SelectableAction;
import net.botwithus.rs3.game.movement.Movement;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.GroundItemQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.item.GroundItem;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.util.RandomGenerator;

import java.util.Random;

public class SkeletonScript extends LoopingScript {

    private BotState botState = BotState.IDLE;
    private boolean someBool = true;
    private Random random = new Random();
    private int ITEM_TO_ENCHANT_ID = 6903;

    static int[] membersWorlds = new int[]{
            1, 2, 4, 5, 6, 9, 10, 12, 14, 15,
            16, 21, 22, 23, 24, 25, 26, 27, 28, 31,
            32, 35, 36, 37, 39, 40, 42, 44, 45, 46,
            47, 49, 50, 51, 53, 54, 56, 58, 59, 60,
            62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 82, 83,
            85, 87, 88, 89, 91, 92, 97, 98, 99, 100,
            102, 103, 104, 105, 106, 116, 117, 118, 119, 121,
            123, 124, 134, 138, 139, 140, 252, 257, 258};

    enum BotState {
        //define your own states here
        IDLE,
        SKILLING,
        BANKING,
        REQUIREDMENT,
        ALCHEMISTS,
        GRAVEYARD,
        TELEKINETIC,
        ENCHANTERS,
        ITEMPICK,
        ENCHANTITEM
        //...
    }

    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
    }



    @Override
    public void onLoop() {
        //Loops every 100ms by default, to change:
        //this.loopDelay = 500;
        LocalPlayer player = Client.getLocalPlayer();
        if (player == null || Client.getGameState() != Client.GameState.LOGGED_IN || botState == BotState.IDLE) {
            //wait some time so we dont immediately start on login.
            Execution.delay(random.nextLong(3000,7000));
            return;
        }
        switch (botState) {
            case IDLE -> {
                //do nothing
                println("We're idle!");
                Execution.delay(random.nextLong(1000,3000));
            }
            case ENCHANTERS ->
            {
                // Execute funtion for enhanters
                Execution.delay(enchanters(player));
            }
            case GRAVEYARD ->
            {
                Execution.delay(grabbones());
            }
            case ALCHEMISTS ->
            {
                Execution.delay(alchemists(player));
            }
            case TELEKINETIC ->
            {
                Execution.delay(rooms(player));
            }
            case REQUIREDMENT ->
            {
                Execution.delay(requiredment(player));
                // Checks for requirements
            }




           /* case SKILLING -> {
                //do some code that handles your skilling
                Execution.delay(handleSkilling(player));
            }
            case BANKING -> {
                //handle your banking logic, etc
            }*/
        }
    }

/*    private long handleSkilling(LocalPlayer player) {
        //for example, if skilling progress interface is open, return a randomized value to keep waiting.
        if (Interfaces.isOpen(1251))
            return random.nextLong(250,1500);
        //if our inventory is full, lets bank.
        if (Backpack.isFull()) {
            println("Going to banking state!");
            botState = BotState.BANKING;
            return random.nextLong(250,1500);
        }
        //click my tree, mine my rock, etc...
        SceneObject tree = SceneObjectQuery.newQuery().name("Tree").option("Chop").results().nearest();
        if (tree != null) {
            println("Interacted tree: " + tree.interact("Chop"));
        }
        return random.nextLong(1500,3000);
    }*/

    public long requiredment(LocalPlayer player)
    {
        // EnchanterTeleport  -- Working Code
       /* SceneObject EnchanterTeleport = SceneObjectQuery.newQuery().name("Enchanters Teleport").results().first();
        if(EnchanterTeleport != null) {
            println(" Entering the Teleport: " + EnchanterTeleport.interact("Enter"));

            Execution.delay(random.nextLong( 1750, 1950));
            botState = BotState.ENCHANTERS;

        } else if((player.getCoordinate().getRegionId() == 13462))
        {
            botState = BotState.ENCHANTERS;
        }*/
        /// Alchemists Teleport
        SceneObject AlchemistsTeleport = SceneObjectQuery.newQuery().name("Alchemists Teleport").results().first();
        if(AlchemistsTeleport != null)
        {
            println(" Entering the Alchemists Portal: " + AlchemistsTeleport.interact("Enter"));
            Execution.delay(random.nextLong(1250,1750));
            botState = BotState.ALCHEMISTS;
        }else if((player.getCoordinate().getRegionId() == 13462))
        {
            botState = BotState.ALCHEMISTS;
        }
        ///Graveyard Teleport
        /*SceneObject GraveyardTeleport = SceneObjectQuery.newQuery().name("Graveyard Teleport").results().first();
        if(GraveyardTeleport != null)
        {
            println(" Entering the Graveyard Portal: " + GraveyardTeleport.interact("Enter"));
            Execution.delay(random.nextLong(1250,1750));
            botState = BotState.GRAVEYARD;
        }else if((player.getCoordinate().getRegionId() == 13462))
        {
            botState = BotState.GRAVEYARD;
        }*/
        /// Telekinetic Teleport
        /*SceneObject TelekineticTeleport = SceneObjectQuery.newQuery().name("Telekinetic Teleport").results().first();
        if(TelekineticTeleport != null)
        {
            println(" Entering the Telekinetic Portal: " + TelekineticTeleport.interact("Enter"));
            Execution.delay(random.nextLong(1250,1750));
            botState = BotState.TELEKINETIC;
        }else if((player.getCoordinate().getRegionId() == 38190))  // Test room1
        {
            botState = BotState.TELEKINETIC;
        }*/


        return random.nextLong(1250, 2250);
    }


    public long enchanters(LocalPlayer player)
    {
            if(player.getCoordinate().getRegionId() == 13462)
            {
                GroundItem item = GroundItemQuery.newQuery().itemId(6903).results().nearest();
                if (item != null && !player.isMoving()) {
                    //println("Select Item: " + item.interact("Take") + item.getName());s
                    println(" Select Enchant Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510735));
                    Execution.delay(random.nextLong(750,1850));
                    println("Use Tele Spell on item: " + item.getName() + ": " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), item.getId(), item.getCoordinate().getX(), item.getCoordinate().getY()));

                    Execution.delay(random.nextLong(1250, 1750));
                }

                if(item == null)
                {
                    itemtoenchant();
                }
            }
            else if( player.getCoordinate().getRegionId() == 13363)
            {
                botState = BotState.REQUIREDMENT;
            }

        return random.nextLong(1250, 3000);
    }

    public void itemtoenchant()
    {
            Item items = InventoryItemQuery.newQuery(93).ids(ITEM_TO_ENCHANT_ID).results().first();

            if(items !=null)
            {
                println(" Select Enchant Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510839));
                Execution.delay(random.nextLong(750,1850));
                println(" Selected to be enchanted item: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, items.getSlot(),96534533));
                Execution.delay(random.nextLong(1100,1750));
            }
            else
            {
                println(" No more item found in Inventory go to hop world");
                WorldHopper();
            }
    }

    private void WorldHopper() {
        if (nextWorldHopTime == 0) {
            int waitTimeInMs = RandomGenerator.nextInt(minHopIntervalmSec, maxHopIntervalmSec);
            nextWorldHopTime = System.currentTimeMillis() + waitTimeInMs;
            println("Next hop scheduled in " + (waitTimeInMs) + " Milli Second.");

            return;
        }

        if (System.currentTimeMillis() >= nextWorldHopTime && !Client.getLocalPlayer().inCombat()) {
            int randomMembersWorldsIndex = RandomGenerator.nextInt(membersWorlds.length);
            HopWorlds(membersWorlds[randomMembersWorldsIndex]);
            println("Hopped to world: " + membersWorlds[randomMembersWorldsIndex]);

            nextWorldHopTime = 0;
        }
    }

    public static long nextWorldHopTime = 0;
    static int minHopIntervalmSec = 60;
    static int maxHopIntervalmSec = 180;


    public void HopWorlds(int world) {
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, 7, 93782016);
        boolean WorldPage = Execution.delayUntil(5000, () -> Interfaces.isOpen(1433));
        Execution.delay(RandomGenerator.nextInt(1500, 2000));

        if (WorldPage) {
            Component HopWorldsMenu = ComponentQuery.newQuery(1433).componentIndex(65).results().first();
            if (HopWorldsMenu != null) {
                MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 93913153);
                println("Hop Worlds Button Clicked.");
                boolean worldSelectOpen = Execution.delayUntil(5000, () -> Interfaces.isOpen(1587));
                Execution.delay(RandomGenerator.nextInt(1500, 2000));

                if (worldSelectOpen) {
                    MiniMenu.interact(ComponentAction.COMPONENT.getType(), 2, world, 104005640);
                    Execution.delay(RandomGenerator.nextInt(10000, 20000));
                }
            } else {
                println("Hop Worlds Button not found.");
            }
        }
    }

    public long alchemists(LocalPlayer player)
    {
        if(player.getCoordinate().getRegionId() == 13462)
        {
            SceneObject cupboard = SceneObjectQuery.newQuery().name("Cupboard").results().random();
            if(cupboard !=null && !Client.getLocalPlayer().isMoving())
            {
                while(!Backpack.isFull()) {
                    println(" Interact with Cupboard: " + cupboard.interact("Search"));
                    Execution.delay(random.nextLong(700, 1000));
                    break;
                }

            }

            if(Backpack.isFull())
                highalech();

        }
        return random.nextLong(1250, 1750);
    }

    public  void highalech()
    {
        Item itemshoes = InventoryItemQuery.newQuery(93).ids(6893).results().first();
        Item itemsh = InventoryItemQuery.newQuery(93).ids(6894).results().first();
        Item itemh = InventoryItemQuery.newQuery(93).ids(6895).results().first();
        Item iteme = InventoryItemQuery.newQuery(93).ids(6896).results().first();
        Item itemsw = InventoryItemQuery.newQuery(93).ids(6897).results().first();
        Component item10 = ComponentQuery.newQuery(194).componentIndex(10).results().first();
        Component item11 = ComponentQuery.newQuery(194).componentIndex(11).results().first();
        Component item12 = ComponentQuery.newQuery(194).componentIndex(12).results().first();
        Component item13 = ComponentQuery.newQuery(194).componentIndex(13).results().first();
        Component item14 = ComponentQuery.newQuery(194).componentIndex(14).results().first();
        if(itemshoes !=null && !Client.getLocalPlayer().isMoving())
        {
            println(" Select Low Alch Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510787));
            Execution.delay(random.nextLong(750,1150));
            println("Select item to Alch: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, itemshoes.getSlot(),96534533));
            Execution.delay(random.nextLong(750,1250));
          /*  if(item10.getText().equals(30) || item10.getText().equals(15) || item10.getText().equals(8))
            {
                println(" Select Low Alch Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510787));
                Execution.delay(random.nextLong(750,1150));
                println("Select item to Alch: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, itemshoes.getSlot(),96534533));
                Execution.delay(random.nextLong(750,1250));
            }*/
        } else if(itemsh !=null && !Client.getLocalPlayer().isMoving())
        {

            /*if(item11.getText() == "30" || item11.getText() == "15" || item11.getText() == "8")
            {*/
                println(" Select Low Alch Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510787));
                Execution.delay(random.nextLong(750,1150));
                println("Select item to Alch: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, itemsh.getSlot(),96534533));
                Execution.delay(random.nextLong(750,1250));

        }else if(itemh !=null && !Client.getLocalPlayer().isMoving())
        {

            /*if(item12.getText() == "30" || item12.getText() == "15" || item12.getText() == "8")
            {*/
                println(" Select Low Alch Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510787));
                Execution.delay(random.nextLong(750,1150));
                println("Select item to Alch: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, itemh.getSlot(),96534533));
                Execution.delay(random.nextLong(750,1250));


        }else if(iteme !=null && !Client.getLocalPlayer().isMoving())
        {

            /*if(item13.getText() == "30" || item13.getText() == "15" || item13.getText() == "8")
            {*/
                println(" Select Low Alch Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510787));
                Execution.delay(random.nextLong(750,1150));
                println("Select item to Alch: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, iteme.getSlot(),96534533));
                Execution.delay(random.nextLong(750,1250));

        }else if(itemsw !=null && !Client.getLocalPlayer().isMoving())
        {

            /*if(item14.getText() == "30" || item14.getText() == "15" || item14.getText() == "8")
            {*/
                println(" Select Low Alch Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510787));
                Execution.delay(random.nextLong(750,1150));
                println("Select item to Alch: " + MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(),0, itemsw.getSlot(),96534533));
                Execution.delay(random.nextLong(750,1250));

        }

        if(!Backpack.contains(6893,6894,6895,6896,6897))
        {
            println(" Getting More Items to Alch");
            botState = BotState.ALCHEMISTS;
        }
    }

    Area graveyardarea = new Area.Rectangular(new Coordinate(3349,9636,1), new Coordinate(3356,9640,1));
    public long grabbones()
    {

        SceneObject bones = SceneObjectQuery.newQuery().name("Bones").inside(graveyardarea).hidden(false).results().first();
        SceneObject bones1 = SceneObjectQuery.newQuery().name("Bones").inside(graveyardarea).hidden(true).results().first();
        if(Client.getLocalPlayer().getAdrenaline() >=600 && Client.getLocalPlayer().getCurrentHealth() <=8000)
        {
            println("Activating Regen ability: " + MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1,-1,93716491));
            Execution.delay(random.nextLong(750,1250));
        }

        if(bones != null && !Backpack.isFull())
        {
            if(bones.isHidden())
            {println("Grabbing Bones: " + bones.interact("Grab"));
                Execution.delay(random.nextLong(750,1250));}
            else
            {
                println("Grabbing Bones Hidden False: " + bones.interact("Grab"));
                Execution.delay(random.nextLong(750,1250));
            }
        }
        else if(Backpack.isFull())
        {
            condepositbones();
        }
        return random.nextLong(750,1250);
    }

    public void condepositbones()
    {
        if(Backpack.contains(6905,6906,69007,6904))
        {
            //println(" Select Bones to Bananas Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0,-1,109510761));
            println(" Action Bar Bones to Bananas Spell: " + ActionBar.useAbility("Bones to Bananas"));
            Execution.delay(random.nextLong(750,1850));

        }
        else if( Backpack.contains(1963) && Backpack.isFull()) {
            SceneObject foodchute = SceneObjectQuery.newQuery().name("Food chute").inside(graveyardarea).results().first();
            if(foodchute != null)
            {
                println("Depositing Banana's: " + foodchute.interact("Deposit"));
            }
        }

    }

    public long rooms(LocalPlayer player)
    {
        if(player.getCoordinate().getRegionId() == 55857)   // Room 1 Wiki
        {
            room1();
        }else if(player.getCoordinate().getRegionId() == 38190)  // room 9
        {
            room8();
        }else if(player.getCoordinate().getRegionId() == 41217)  // room 9
        {
            room9();
        }
        return random.nextLong(1250,1750);
    }

    ///1 Region ID 55857  /// 28230
    ///2
    ///3
    ///4
    ///5
    ///6
    ///7 Region ID 26673
    ///8 Region ID 42801 //38190
    ///9 Region ID 41217
    ///10
    public void room1()
    {
        if(Client.getLocalPlayer().getCoordinate().getRegionId() == 55857) {
            GroundItem golem = GroundItemQuery.newQuery().ids(6888).results().first();
            if(golem.getCoordinate().getX() == 13960 && golem.getCoordinate().getY() == 3158)   //Move 1
            {
                Movement.walkTo(13960, 3148, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13960 && Client.getLocalPlayer().getCoordinate().getY() ==3148);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> (golem.getCoordinate().getX()==13960 && golem.getCoordinate().getY() ==3149));

            }
            if(golem.getCoordinate().getX()==13960 && golem.getCoordinate().getY() ==3149)  // Move 2
            {
                Movement.walkTo(13970,3149,false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13970 && Client.getLocalPlayer().getCoordinate().getY() ==3149);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> golem.getCoordinate().getX()==13964 && golem.getCoordinate().getY() ==3149);
            }
            if(golem.getCoordinate().getX()==13964 && golem.getCoordinate().getY() ==3149)  // Move 3
            {
                Movement.walkTo(13964,3159,false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13964 && Client.getLocalPlayer().getCoordinate().getY() ==3159);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> golem.getCoordinate().getX()==13964 && golem.getCoordinate().getY() ==3155);
            }
            if(golem.getCoordinate().getX()==13964 && golem.getCoordinate().getY() ==3155)  // Move 4
            {
                Movement.walkTo(13959,3155,false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13959 && Client.getLocalPlayer().getCoordinate().getY() ==3155);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> golem.getCoordinate().getX()==13962 && golem.getCoordinate().getY() ==3155);
            }
            if(golem.getCoordinate().getX()==13962 && golem.getCoordinate().getY() ==3155)  // Move 5
            {
                Movement.walkTo(13962,3148,false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13962 && Client.getLocalPlayer().getCoordinate().getY() ==3148);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> golem.getCoordinate().getX()==13962 && golem.getCoordinate().getY() ==3152);
            }
            if(golem.getCoordinate().getX()==13962 && golem.getCoordinate().getY() ==3152)  // move 6
            {
                Movement.walkTo(13970,3152,false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13970 && Client.getLocalPlayer().getCoordinate().getY() ==3152);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> golem.getCoordinate().getX()==13969 && golem.getCoordinate().getY() ==3152);
            }
            if(golem.getCoordinate().getX()==13969 && golem.getCoordinate().getY() ==3152)  // Move 7
            {
                Movement.walkTo(13969,3159,false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 13969 && Client.getLocalPlayer().getCoordinate().getY() ==3159);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> golem.getCoordinate().getX()==13969 && golem.getCoordinate().getY() ==3158);
            }
            Npc golem1 = NpcQuery.newQuery().name("Maze Guardian").results().first();
            if(golem1.getCoordinate().getX()==13969 && golem1.getCoordinate().getY() ==3158)
            {
                println("Golem reached the destination:");
                Execution.delayUntil(10000, () -> Interfaces.isOpen(1186));
                if(Interfaces.isOpen(1186))
                {
                    println("Interact with Interface: " + MiniMenu.interact(ComponentAction.DIALOGUE.getType(), 0,-1,77725704));
                    Execution.delay(random.nextLong(750,1250));
                    if(golem1 !=null)
                    {
                        println("Going to Next Maze: " +golem1.interact("Next-maze"));
                    }
                }

            }

        }
    }
    public void room8()
    {
            GroundItem golem = GroundItemQuery.newQuery().ids(6888).results().first();
            if(golem.getCoordinate().getX() == 9556 && golem.getCoordinate().getY() == 2954)   //Move 1
            {
                Movement.walkTo(9556, 2964, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9556 && Client.getLocalPlayer().getCoordinate().getY() ==2964);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9556 && golem.getCoordinate().getY() == 2963));
            }
            if(golem.getCoordinate().getX() == 9556 && golem.getCoordinate().getY() == 2963)   //Move 2
            {
                Movement.walkTo(9546, 2963, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9546 && Client.getLocalPlayer().getCoordinate().getY() ==2963);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9553 && golem.getCoordinate().getY() == 2963));
            }
            if(golem.getCoordinate().getX() == 9553 && golem.getCoordinate().getY() == 2963)   //Move 3
            {
                Movement.walkTo(9553, 2953, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9553 && Client.getLocalPlayer().getCoordinate().getY() ==2953);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9553 && golem.getCoordinate().getY() == 2955));
            }
            if(golem.getCoordinate().getX() == 9553 && golem.getCoordinate().getY() == 2955)   //Move 4
            {
                Movement.walkTo(9546, 2955, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9546 && Client.getLocalPlayer().getCoordinate().getY() ==2955);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9550 && golem.getCoordinate().getY() == 2955));
            }
            if(golem.getCoordinate().getX() == 9550 && golem.getCoordinate().getY() == 2955)   //Move 5
            {
                Movement.walkTo(9550, 2964, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9550 && Client.getLocalPlayer().getCoordinate().getY() ==2964);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9550 && golem.getCoordinate().getY() == 2963));
            }
            if(golem.getCoordinate().getX() == 9550 && golem.getCoordinate().getY() == 2963)   //Move 6
            {
                Movement.walkTo(9546, 2963, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9546 && Client.getLocalPlayer().getCoordinate().getY() ==2963);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9549 && golem.getCoordinate().getY() == 2963));
            }
            if(golem.getCoordinate().getX() == 9549 && golem.getCoordinate().getY() == 2963)   //Move 7
            {
                Movement.walkTo(9549, 2953, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9549 && Client.getLocalPlayer().getCoordinate().getY() ==2953);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9549 && golem.getCoordinate().getY() == 2954));
            }
            if(golem.getCoordinate().getX() == 9549 && golem.getCoordinate().getY() == 2954)   //Move 8
            {
                Movement.walkTo(9546, 2954, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9546 && Client.getLocalPlayer().getCoordinate().getY() ==2954);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9547 && golem.getCoordinate().getY() == 2954));
            }
            if(golem.getCoordinate().getX() == 9547 && golem.getCoordinate().getY() == 2954)   //Move 9
            {
                Movement.walkTo(9547, 2964, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 9547 && Client.getLocalPlayer().getCoordinate().getY() ==2964);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==9547 && golem.getCoordinate().getY() == 2963));
            }

            Npc golem1 = NpcQuery.newQuery().name("Maze Guardian").results().first();


            /*if((golem1.getCoordinate().getX()==9547 && golem1.getCoordinate().getY() ==2963) && golem1 !=null )
            {
                println("Golem reached the destination:");
                Execution.delayUntil(10000, () -> Interfaces.isOpen(1186));
                if(Interfaces.isOpen(1186))
                {
                    println("Interact with Interface: " + MiniMenu.interact(ComponentAction.DIALOGUE.getType(), 0,-1,77725704));
                    Execution.delay(random.nextLong(750,1250));
                    if(golem1 !=null)
                    {
                        println("Going to Next Maze: " +golem1.interact("Next-maze"));
                    }
                }

            }*/


    }


    public void room9()
    {
        if(Client.getLocalPlayer().getCoordinate().getRegionId() == 41217)
        {
            GroundItem golem = GroundItemQuery.newQuery().ids(6888).results().first();
            if(golem.getCoordinate().getX() == 10317 && golem.getCoordinate().getY() == 78)   //Move 1
            {
                Movement.walkTo(10317, 83, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10317 && Client.getLocalPlayer().getCoordinate().getY() ==83);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10317 && golem.getCoordinate().getY() == 81));
            }
            if(golem.getCoordinate().getX() == 10317 && golem.getCoordinate().getY() == 81)   //Move 2
            {
                Movement.walkTo(10323, 81, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10323 && Client.getLocalPlayer().getCoordinate().getY() == 81);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10318 && golem.getCoordinate().getY() == 81));
            }
            if(golem.getCoordinate().getX() == 10318 && golem.getCoordinate().getY() == 81)   //Move 3
            {
                Movement.walkTo(10318, 72, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10318 && Client.getLocalPlayer().getCoordinate().getY() == 72);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10318 && golem.getCoordinate().getY() == 78));
            }
            if(golem.getCoordinate().getX() == 10318 && golem.getCoordinate().getY() == 78)   //Move 4
            {
                Movement.walkTo(10323, 78, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10328 && Client.getLocalPlayer().getCoordinate().getY() ==78);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10321 && golem.getCoordinate().getY() == 78));
            }
            if(golem.getCoordinate().getX() == 10321 && golem.getCoordinate().getY() == 78)   //Move 5
            {
                Movement.walkTo(10321, 72, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10321 && Client.getLocalPlayer().getCoordinate().getY() == 72);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10321 && golem.getCoordinate().getY() == 76));
            }
            if(golem.getCoordinate().getX() == 10321 && golem.getCoordinate().getY() == 76)   //Move 6   -- Working on This move
            {
                Movement.walkTo(10317, 83, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10317 && Client.getLocalPlayer().getCoordinate().getY() ==83);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10317 && golem.getCoordinate().getY() == 81));
            }
            if(golem.getCoordinate().getX() == 10317 && golem.getCoordinate().getY() == 78)   //Move 1
            {
                Movement.walkTo(10317, 83, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10317 && Client.getLocalPlayer().getCoordinate().getY() ==83);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10317 && golem.getCoordinate().getY() == 81));
            }
            if(golem.getCoordinate().getX() == 10317 && golem.getCoordinate().getY() == 78)   //Move 1
            {
                Movement.walkTo(10317, 83, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10317 && Client.getLocalPlayer().getCoordinate().getY() ==83);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10317 && golem.getCoordinate().getY() == 81));
            }
            if(golem.getCoordinate().getX() == 10317 && golem.getCoordinate().getY() == 78)   //Move 1
            {
                Movement.walkTo(10317, 83, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10317 && Client.getLocalPlayer().getCoordinate().getY() ==83);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10317 && golem.getCoordinate().getY() == 81));
            }
            if(golem.getCoordinate().getX() == 10317 && golem.getCoordinate().getY() == 78)   //Move 1
            {
                Movement.walkTo(10317, 83, false);
                Execution.delayUntil(20000, () -> Client.getLocalPlayer().getCoordinate().getX() == 10317 && Client.getLocalPlayer().getCoordinate().getY() ==83);
                println("Selecting Tele Spell: " + MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, -1, 109510735));
                Execution.delay(random.nextLong(750, 950));
                println("Using Tele on Golem: " + MiniMenu.interact(SelectableAction.SELECT_GROUND_ITEM.getType(), golem.getId(), golem.getCoordinate().getX(), golem.getCoordinate().getY()));
                Execution.delay(random.nextLong(750, 950));
                Execution.delayUntil(20000,() -> ( golem.getCoordinate().getX()==10317 && golem.getCoordinate().getY() == 81));
            }
        }

    }


    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }

    public boolean isSomeBool() {
        return someBool;
    }

    public void setSomeBool(boolean someBool) {
        this.someBool = someBool;
    }
}
