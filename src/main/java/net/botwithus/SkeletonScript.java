package net.botwithus;

import jdk.jshell.execution.LoaderDelegate;
import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.game.hud.inventories.Inventory;
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
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.GroundItemQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
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
            case TELEKINETIC -> {}
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
       /* SceneObject AlchemistsTeleport = SceneObjectQuery.newQuery().name("Alchemists Teleport").results().first();
        if(AlchemistsTeleport != null)
        {
            println(" Entering the Alchemists Portal: " + AlchemistsTeleport.interact("Enter"));
            Execution.delay(random.nextLong(1250,1750));
            botState = BotState.ALCHEMISTS;
        }else if((player.getCoordinate().getRegionId() == 13462))
        {
            botState = BotState.ALCHEMISTS;
        }*/
        ///Graveyard Teleport
        SceneObject GraveyardTeleport = SceneObjectQuery.newQuery().name("Graveyard Teleport").results().first();
        if(GraveyardTeleport != null)
        {
            println(" Entering the Graveyard Portal: " + GraveyardTeleport.interact("Enter"));
            Execution.delay(random.nextLong(1250,1750));
            botState = BotState.GRAVEYARD;
        }else if((player.getCoordinate().getRegionId() == 13462))
        {
            botState = BotState.GRAVEYARD;
        }
        /// Telekinetic Teleport



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
            int waitTimeInMs = RandomGenerator.nextInt(minHopIntervalMinutes, maxHopIntervalMinutes);
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
    static int minHopIntervalMinutes = 60;
    static int maxHopIntervalMinutes = 180;


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
