package com.manulaiko.blackeye.simulator.account.settings;

import java.util.ArrayList;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.packet.command.SETCommand;
import com.manulaiko.blackeye.net.game.packet.command.SettingsCommand;
import com.manulaiko.blackeye.net.utils.Command;
import com.manulaiko.tabitha.Console;

/**
 * Settings class.
 *
 * God bless copy paste.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Settings implements Cloneable
{
    /**
     * Settings ID.
     *
     * @var ID.
     */
    public int id = 0;

    /**
     * Account's ID.
     *
     * @var `accounts_id`.
     */
    public int accountID = 0;

    /**
     * Whether to display chat or not.
     *
     * @var Whether to display chat or not.
     */
    public boolean display_chat = false;

    /**
     * Whether to display drones or not.
     *
     * @var Whether to display drones or not.
     */
    public boolean display_drones = false;

    /**
     * Whether to display notifications or not.
     *
     * @var Whether to display notifications or not.
     */
    public boolean display_notification = false;

    /**
     * Whether to display player names or not.
     *
     * @var Whether to display player names or not.
     */
    public boolean display_playerNames = false;

    /**
     * Whether to display window backgrounds or not.
     *
     * @var Whether to display window backgrounds or not.
     */
    public boolean display_windowBackground = false;

    /**
     * Whether to play music or not.
     *
     * @var Whether to play music or not.
     */
    public boolean play_music = false;

    /**
     * Whether to play sounds or not.
     *
     * @var Whether to play sounds or not.
     */
    public boolean play_sfx = false;

    /**
     * Attack quality.
     *
     * @var Attack quality.
     */
    public int quality_attack = 0;

    /**
     * Background quality.
     *
     * @var Background quality.
     */
    public int quality_background = 0;

    /**
     * Collectables quality.
     *
     * @var Collectables quality.
     */
    public int quality_collectable = 0;

    /**
     * Customized quality.
     *
     * @var Customized quality.
     */
    public int quality_customized = 0;

    /**
     * Effects quality.
     *
     * @var Effects quality.
     */
    public int quality_effect = 0;

    /**
     * Engines quality.
     *
     * @var Engines quality.
     */
    public int quality_engine = 0;

    /**
     * Explosions quality.
     *
     * @var Explosions quality.
     */
    public int quality_explosion = 0;

    /**
     * Poizones quality.
     *
     * @var Poizones quality.
     */
    public int quality_poizone = 0;

    /**
     * Presseting quality.
     *
     * @var Presseting quality.
     */
    public int quality_presseting = 0;

    /**
     * Ships quality.
     *
     * @var Ships quality.
     */
    public int quality_ship = 0;

    /**
     * Whether windows are draggable or not.
     *
     * @var Whether windows are draggable or not.
     */
    public boolean alwaysDraggableWindows = false;

    /**
     * Whether resources should be automatically refined or not.
     *
     * @var Whether resources should be automatically refined or not.
     */
    public boolean autoRefinement = false;

    /**
     * Whether auto start is enabled or not.
     *
     * @var Whether auto start is enabled or not.
     */
    public boolean autoStart = false;

    /**
     * Whether double click starts attack or not.
     *
     * @var Whether double click starts attack or not.
     */
    public boolean doubleClickAttack = false;

    /**
     * Whether to preload users or not.
     *
     * @var Whether to preload users or not.
     */
    public boolean preloadUserShips = false;

    /**
     * Whether quick slot stops attack or not.
     *
     * @var Whether quick slot stops attack or not.
     */
    public boolean quickSlotStopAttack = false;

    /**
     * Bar status.
     *
     * @var Bar status.
     */
    public String barStatus = "";

    /**
     * Client resolution.
     *
     * @var Client resolution.
     */
    public String clientResolution = "";

    /**
     * Main menu position.
     *
     * @var Main menu position.
     */
    public String mainmenuPosition = "";

    /**
     * Minimap size.
     *
     * @var Minimap size.
     */
    public String minimapSize = "";

    /**
     * Quickbar slots.
     *
     * @var Quickbar slots.
     */
    public String quickbarSlot = "";

    /**
     * Resizable windows.
     *
     * @var Resizable windows.
     */
    public String resizableWindows = "";

    /**
     * Slot menu order.
     *
     * @var Slot menu order.
     */
    public String slotmenu_order = "";

    /**
     * Slot menu position.
     *
     * @var Slot menu position.
     */
    public String slotmenu_position = "";

    /**
     * Window settings.
     *
     * @var Window settings.
     */
    public String windowSettings = "";

    /**
     * SET Object.
     *
     * @var SET Object.
     */
    public SET set = null;

    /**
     * Constructor
     *
     * @param display_chat             Whether to display chat or not.
     * @param display_drones           Whether to display drones or not.
     * @param display_notification     Whether to display notifications or not.
     * @param display_playerNames      Whether to display player names or not.
     * @param display_windowBackground Whether to display window background or not.
     * @param play_music               Whether to play music or not.
     * @param play_sfx                 Whether to play sounds or not.
     * @param quality_attack           Attack quality.
     * @param quality_background       Background quality.
     * @param quality_collectable      Collectables quality.
     * @param quality_customized       Customized quality.
     * @param quality_effect           Effects quality.
     * @param quality_engine           Engines quality.
     * @param quality_explosion        Explosions quality.
     * @param quality_poizone          Poizones quality.
     * @param quality_presseting       Pressetting quality.
     * @param quality_ship             Ships quality.
     * @param alwaysDraggableWindows   Whether windows are draggable or not.
     * @param autoRefinement           Whether resources should be automatically refined or not.
     * @param autoStart                Whether auto start is enabled or not.
     * @param doubleClickAttack        Whether double click starts attack or not.
     * @param preloadUserShips         Whether to preload users or not.
     * @param quickSlotStopAttack      Whether quick slot stops attack or not.
     * @param barStatus                Bar status.
     * @param clientResolution         Client resolution.
     * @param mainmenuPosition         Main menu position.
     * @param minimapSize              Minimap size.
     * @param quickbarSlot             Quickbar slots.
     * @param resizableWindows         Resizable windows.
     * @param slotmenu_order           Slot menu order.
     * @param slotmenu_position        Slot menu position.
     * @param windowSettings           Window settings.
     */
    public Settings(
            int id, int accountID,
            boolean display_chat, boolean display_drones, boolean display_notification, boolean display_playerNames,
            boolean display_windowBackground, boolean play_music, boolean play_sfx, int quality_attack,
            int quality_background, int quality_collectable, int quality_customized, int quality_effect,
            int quality_engine, int quality_explosion, int quality_poizone, int quality_presseting,
            int quality_ship, boolean alwaysDraggableWindows, boolean autoRefinement, boolean autoStart,
            boolean doubleClickAttack, boolean preloadUserShips, boolean quickSlotStopAttack, String barStatus,
            String clientResolution, String mainmenuPosition, String minimapSize, String quickbarSlot,
            String resizableWindows, String slotmenu_order, String slotmenu_position, String windowSettings
    ) {
        this.id        = id;
        this.accountID = accountID;

        this.display_chat             = display_chat;
        this.display_drones           = display_drones;
        this.display_notification     = display_notification;
        this.display_playerNames      = display_playerNames;
        this.display_windowBackground = display_windowBackground;
        this.play_music               = play_music;
        this.play_sfx                 = play_sfx;
        this.quality_attack           = quality_attack;
        this.quality_background       = quality_background;
        this.quality_collectable      = quality_collectable;
        this.quality_customized       = quality_customized;
        this.quality_effect           = quality_effect;
        this.quality_engine           = quality_engine;
        this.quality_explosion        = quality_explosion;
        this.quality_poizone          = quality_poizone;
        this.quality_presseting       = quality_presseting;
        this.quality_ship             = quality_ship;
        this.alwaysDraggableWindows   = alwaysDraggableWindows;
        this.autoRefinement           = autoRefinement;
        this.autoStart                = autoStart;
        this.doubleClickAttack        = doubleClickAttack;
        this.preloadUserShips         = preloadUserShips;
        this.quickSlotStopAttack      = quickSlotStopAttack;
        this.barStatus                = barStatus;
        this.clientResolution         = clientResolution;
        this.mainmenuPosition         = mainmenuPosition;
        this.minimapSize              = minimapSize;
        this.quickbarSlot             = quickbarSlot;
        this.resizableWindows         = resizableWindows;
        this.slotmenu_order           = slotmenu_order;
        this.slotmenu_position        = slotmenu_position;
        this.windowSettings           = windowSettings;
    }

    /**
     * Sets SET object.
     */
    public void setSet(
            boolean boosten, boolean dsplyDamage, boolean dsplyAllLas, boolean dsplyExplo,
            boolean dsplyPlayerName, boolean dsplyFirmIcon, boolean dsplyAlphaBg, boolean ignoreRES,
            boolean ignoreBOX, boolean convertGates, boolean convertOppo, boolean soundOnOff,
            boolean bgmusicOnOff, boolean dsplyStatus, boolean dsplyBubble, boolean selectedLaser,
            boolean selectedRocket, boolean dsplyDigits, boolean dsplyChat, boolean dsplyDrones,
            boolean showStarsystem, boolean ignoreCARGO, boolean ignoreHostileCARGO, boolean autochangeAmmo,
            boolean enableFastBuy
    ) {
        this.set = new SET(
                boosten, dsplyDamage, dsplyAllLas, dsplyExplo, dsplyPlayerName, dsplyFirmIcon, dsplyAlphaBg, ignoreRES,
                ignoreBOX, convertGates, convertOppo, soundOnOff, bgmusicOnOff, dsplyStatus, dsplyBubble, selectedLaser,
                selectedRocket, dsplyDigits, dsplyChat, dsplyDrones, showStarsystem, ignoreCARGO, ignoreHostileCARGO,
                autochangeAmmo, enableFastBuy
        );
    }

    /**
     * Builds and returns settings commands.
     *
     * @return Settings commands.
     */
    public ArrayList<Command> getCommands()
    {
        ArrayList<Command> commands = new ArrayList<>();

        commands.add(this.getHSCommand());
        commands.add(this.set.getSETCommand());
        commands.add(this.getClientResolutionCommand());
        commands.add(this.getMinimapSizeCommand());
        commands.add(this.getResizableWindowsCommand());
        commands.add(this.getDisplayChatCommand());
        commands.add(this.getDisplayDronesCommand());
        commands.add(this.getDisplayPlayerNamesCommand());
        commands.add(this.getDisplayNotificationsCommand());
        commands.add(this.getDisplayWindowBackgroundCommand());
        commands.add(this.getPlayMusicCommand());
        commands.add(this.getPlaySoundsCommand());
        commands.add(this.getBarStatusCommand());
        commands.add(this.getWindowSettingsCommand());
        commands.add(this.getAutoRefinementCommand());
        commands.add(this.getQuickSlotStopAttackCommand());
        commands.add(this.getDoubleclickAttackCommand());
        commands.add(this.getAutoStartCommand());
        commands.add(this.getAlwaysDraggableWindowsCommand());
        commands.add(this.getPreloadUserShipsCommand());
        commands.add(this.getQualityPresettingCommand());
        commands.add(this.getQualityCustomizedCommand());
        commands.add(this.getQualityBackgroundCommand());
        commands.add(this.getQualityPoizoneCommand());
        commands.add(this.getQualityShipCommand());
        commands.add(this.getQualityEngineCommand());
        commands.add(this.getQualityCollectableCommand());
        commands.add(this.getQualityAttackCommand());
        commands.add(this.getQualityEffectCommand());
        commands.add(this.getQualityExplosionCommand());
        commands.add(this.getQuickbarSlotCommand());
        commands.add(this.getSlotmenuPositionCommand());
        commands.add(this.getSlotmenuOrderCommand());
        commands.add(this.getMainmenuPositionCommand());

        return commands;
    }

    /**
     * Builds and returns MainmenuPosition command.
     *
     * @return MainmenuPosition command.
     */
    public SettingsCommand getMainmenuPositionCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("MAINMENU_POSITION");
        p.add(this.mainmenuPosition);

        return p;
    }

    /**
     * Builds and returns SlotmenuOrder command.
     *
     * @return SlotmenuOrder command.
     */
    public SettingsCommand getSlotmenuOrderCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("SLOTMENU_ORDER");
        p.add(this.slotmenu_order);

        return p;
    }

    /**
     * Builds and returns SlotmenuPosition command.
     *
     * @return SlotmenuPosition command.
     */
    public SettingsCommand getSlotmenuPositionCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("SLOTMENU_POSITION");
        p.add(this.slotmenu_position);

        return p;
    }

    /**
     * Builds and returns QuickbarSlot command.
     *
     * @return QuickbarSlot command.
     */
    public SettingsCommand getQuickbarSlotCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUICKBAR_SLOT");
        p.add(this.quickbarSlot);

        return p;
    }

    /**
     * Builds and returns QualityExplosion command.
     *
     * @return QualityExplosion command.
     */
    public SettingsCommand getQualityExplosionCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_EXPLOSION");
        p.add(this.quality_explosion);

        return p;
    }

    /**
     * Builds and returns QualityEffect command.
     *
     * @return QualityEffect command.
     */
    public SettingsCommand getQualityEffectCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_EFFECT");
        p.add(this.quality_effect);

        return p;
    }

    /**
     * Builds and returns QualityAttack command.
     *
     * @return QualityAttack command.
     */
    public SettingsCommand getQualityAttackCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_ATTACK");
        p.add(this.quality_attack);

        return p;
    }

    /**
     * Builds and returns QualityCollectable command.
     *
     * @return QualityCollectable command.
     */
    public SettingsCommand getQualityCollectableCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_COLLECTABLE");
        p.add(this.quality_collectable);

        return p;
    }

    /**
     * Builds and returns QualityEngine command.
     *
     * @return QualityEngine command.
     */
    public SettingsCommand getQualityEngineCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_ENGINE");
        p.add(this.quality_engine);

        return p;
    }

    /**
     * Builds and returns QualityShip command.
     *
     * @return QualityShip command.
     */
    public SettingsCommand getQualityShipCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_SHIP");
        p.add(this.quality_ship);

        return p;
    }

    /**
     * Builds and returns QualityPoizone command.
     *
     * @return QualityPoizone command.
     */
    public SettingsCommand getQualityPoizoneCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_POIZONE");
        p.add(this.quality_poizone);

        return p;
    }

    /**
     * Builds and returns QualityBackground command.
     *
     * @return QualityBackground command.
     */
    public SettingsCommand getQualityBackgroundCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_BACKGROUND");
        p.add(this.quality_background);

        return p;
    }

    /**
     * Builds and returns QualityCustomized command.
     *
     * @return QualityCustomized command.
     */
    public SettingsCommand getQualityCustomizedCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_CUSTOMIZED");
        p.add(this.quality_customized);

        return p;
    }

    /**
     * Builds and returns QualityPresetting command.
     *
     * @return QualityPresetting command.
     */
    public SettingsCommand getQualityPresettingCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUALITY_PRESETTING");
        p.add(this.quality_presseting);

        return p;
    }

    /**
     * Builds and returns PreloadUserShips command.
     *
     * @return PreloadUserShips command.
     */
    public SettingsCommand getPreloadUserShipsCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("PRELOAD_USER_SHIPS");
        p.add(this.preloadUserShips);

        return p;
    }

    /**
     * Builds and returns AlwaysDraggableWindows command.
     *
     * @return AlwaysDraggableWindows command.
     */
    public SettingsCommand getAlwaysDraggableWindowsCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("ALWAYS_DRAGGABLE_WINDOWS");
        p.add(this.alwaysDraggableWindows);

        return p;
    }

    /**
     * Builds and returns DisplayWindowBackground command.
     *
     * @return DisplayWindowBackground command.
     */
    public SettingsCommand getDisplayWindowBackgroundCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("DISPLAY_WINDOW_BACKGROUND");
        p.add(this.display_windowBackground);

        return p;
    }

    /**
     * Builds and returns DisplayDrones command.
     *
     * @return DisplayDrones command.
     */
    public SettingsCommand getDisplayDronesCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("SHOW_DRONES");
        p.add(this.display_drones);

        return p;
    }

    /**
     * Builds and returns AutoStart command.
     *
     * @return AutoStart command.
     */
    public SettingsCommand getAutoStartCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("AUTO_START");
        p.add(this.autoStart);

        return p;
    }

    /**
     * Builds and returns DoubleclickAttack command.
     *
     * @return DoubleclickAttack command.
     */
    public SettingsCommand getDoubleclickAttackCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("DOUBLECLICK_ATTACK");
        p.add(this.doubleClickAttack);

        return p;
    }

    /**
     * Builds and returns QuickSlotStopAttack command.
     *
     * @return QuickSlotStopAttack command.
     */
    public SettingsCommand getQuickSlotStopAttackCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("QUICK_SLOT_STOP_ATTACK");
        p.add(this.quickSlotStopAttack);

        return p;
    }

    /**
     * Builds and returns AutoRefinement command.
     *
     * @return AutoRefinement command.
     */
    public SettingsCommand getAutoRefinementCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("AUTO_REFINEMENT");
        p.add(this.autoRefinement);

        return p;
    }

    /**
     * Builds and returns HS command.
     *
     * @return HS command.
     */
    public SettingsCommand getHSCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("HS");

        return p;
    }

    /**
     * Builds and returns client resolution command.
     *
     * @return ClientResolution command.
     */
    public SettingsCommand getClientResolutionCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("CLIENT_RESOLUTION");
        p.add(this.clientResolution);
        p.add(1);

        return p;
    }

    /**
     * Builds and returns minimap size command.
     *
     * @return MinimapSize command.
     */
    public SettingsCommand getMinimapSizeCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("MINIMAP_SCALE,"+ this.minimapSize);

        return p;
    }

    /**
     * Builds and returns resizable windows command.
     *
     * @return ResizableWindows command.
     */
    public SettingsCommand getResizableWindowsCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("RESIZABLE_WINDOWS,"+ this.resizableWindows);

        return p;
    }

    /**
     * Builds and returns display player names command.
     *
     * @return DisplayPlayerNames command.
     */
    public SettingsCommand getDisplayPlayerNamesCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("DISPLAY_PLAYER_NAMES");
        p.add(this.display_playerNames);

        return p;
    }

    /**
     * Builds and returns display chat command.
     *
     * @return DisplayChat command.
     */
    public SettingsCommand getDisplayChatCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("DISPLAY_CHAT");
        p.add(this.display_chat);

        return p;
    }

    /**
     * Builds and returns display notifications command.
     *
     * @return DisplayNotifications command.
     */
    public SettingsCommand getDisplayNotificationsCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("DISPLAY_NOTIFICATIONS");
        p.add(this.display_notification);

        return p;
    }

    /**
     * Builds and returns play music command.
     *
     * @return PlayMusic command.
     */
    public SettingsCommand getPlayMusicCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("PLAY_MUSIC");
        p.add(this.play_music);

        return p;
    }

    /**
     * Builds and returns play sounds command.
     *
     * @return PlaySounds command.
     */
    public SettingsCommand getPlaySoundsCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("PLAY_SFX");
        p.add(this.play_sfx);

        return p;
    }

    /**
     * Builds and returns bar status command.
     *
     * @return BarStatus command.
     */
    public SettingsCommand getBarStatusCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("BAR_STATUS");
        p.add(this.barStatus);

        return p;
    }

    /**
     * Builds and returns WindowSettings command.
     *
     * @return WindowSettings command.
     */
    public SettingsCommand getWindowSettingsCommand()
    {
        SettingsCommand p = new SettingsCommand();
        p.add("WINDOW_SETTINGS,0");
        p.add(this.windowSettings);

        return p;
    }

    /**
     * SET class.
     *
     * Used for sending the '0|SET|....' packet.
     *
     * @author Manulaiko <manulaiko@gmail.com>
     */
    public class SET implements Cloneable
    {
        public boolean boosten = false;
        public boolean dsplyDamage = false;
        public boolean dsplyAllLas = false;
        public boolean dsplyExplo = false;
        public boolean dsplyPlayerName = false;
        public boolean dsplyFirmIcon = false;
        public boolean dsplyAlphaBg = false;
        public boolean ignoreRES = false;
        public boolean ignoreBOX = false;
        public boolean convertGates = false;
        public boolean convertOppo = false;
        public boolean soundOnOff = false;
        public boolean bgmusicOnOff = false;
        public boolean dsplyStatus = false;
        public boolean dsplyBubble = false;
        public boolean selectedLaser = false;
        public boolean selectedRocket = false;
        public boolean dsplyDigits = false;
        public boolean dsplyChat = false;
        public boolean dsplyDrones = false;
        public boolean showStarsystem = false;
        public boolean ignoreCARGO = false;
        public boolean ignoreHostileCARGO = false;
        public boolean autochangeAmmo = false;
        public boolean enableFastBuy = false;

        /**
         * Constructor
         *
         * @param boosten
         * @param dsplyDamage
         * @param dsplyAllLas
         * @param dsplyExplo
         * @param dsplyPlayerName
         * @param dsplyFirmIcon
         * @param dsplyAlphaBg
         * @param ignoreRES
         * @param ignoreBOX
         * @param convertGates
         * @param convertOppo
         * @param soundOnOff
         * @param bgmusicOnOff
         * @param dsplyStatus
         * @param dsplyBubble
         * @param selectedLaser
         * @param selectedRocket
         * @param dsplyDigits
         * @param dsplyChat
         * @param dsplyDrones
         * @param showStarsystem
         * @param ignoreCARGO
         * @param ignoreHostileCARGO
         * @param autochangeAmmo
         * @param enableFastBuy
         */
        public SET(
                boolean boosten, boolean dsplyDamage, boolean dsplyAllLas, boolean dsplyExplo,
                boolean dsplyPlayerName, boolean dsplyFirmIcon, boolean dsplyAlphaBg, boolean ignoreRES,
                boolean ignoreBOX, boolean convertGates, boolean convertOppo, boolean soundOnOff,
                boolean bgmusicOnOff, boolean dsplyStatus, boolean dsplyBubble, boolean selectedLaser,
                boolean selectedRocket, boolean dsplyDigits, boolean dsplyChat, boolean dsplyDrones,
                boolean showStarsystem, boolean ignoreCARGO, boolean ignoreHostileCARGO, boolean autochangeAmmo,
                boolean enableFastBuy
        ) {
            this.boosten            = boosten;
            this.dsplyDamage        = dsplyDamage;
            this.dsplyAllLas        = dsplyAllLas;
            this.dsplyExplo         = dsplyExplo;
            this.dsplyPlayerName    = dsplyPlayerName;
            this.dsplyFirmIcon      = dsplyFirmIcon;
            this.dsplyAlphaBg       = dsplyAlphaBg;
            this.ignoreRES          = ignoreRES;
            this.ignoreBOX          = ignoreBOX;
            this.convertGates       = convertGates;
            this.convertOppo        = convertOppo;
            this.soundOnOff         = soundOnOff;
            this.bgmusicOnOff       = bgmusicOnOff;
            this.dsplyStatus        = dsplyStatus;
            this.dsplyBubble        = dsplyBubble;
            this.selectedLaser      = selectedLaser;
            this.selectedRocket     = selectedRocket;
            this.dsplyDigits        = dsplyDigits;
            this.dsplyChat          = dsplyChat;
            this.dsplyDrones        = dsplyDrones;
            this.showStarsystem     = showStarsystem;
            this.ignoreCARGO        = ignoreCARGO;
            this.ignoreHostileCARGO = ignoreHostileCARGO;
            this.autochangeAmmo     = autochangeAmmo;
            this.enableFastBuy      = enableFastBuy;
        }

        /**
         * Builds and returns the SET command.
         *
         * @return SET command.
         */
        public SETCommand getSETCommand()
        {
            SETCommand set = (SETCommand) ServerManager.game.packetFactory.getCommandByName("SETCommand");

            set.boosten            = this.boosten;
            set.dsplyDamage        = this.dsplyDamage;
            set.dsplyAllLas        = this.dsplyAllLas;
            set.dsplyExplo         = this.dsplyExplo;
            set.dsplyPlayerName    = this.dsplyPlayerName;
            set.dsplyFirmIcon      = this.dsplyFirmIcon;
            set.dsplyAlphaBg       = this.dsplyAlphaBg;
            set.ignoreRES          = this.ignoreRES;
            set.ignoreBOX          = this.ignoreBOX;
            set.convertGates       = this.convertGates;
            set.convertOppo        = this.convertOppo;
            set.soundOnOff         = this.soundOnOff;
            set.bgmusicOnOff       = this.bgmusicOnOff;
            set.dsplyStatus        = this.dsplyStatus;
            set.dsplyBubble        = this.dsplyBubble;
            set.selectedLaser      = this.selectedLaser;
            set.selectedRocket     = this.selectedRocket;
            set.dsplyDigits        = this.dsplyDigits;
            set.dsplyChat          = this.dsplyChat;
            set.dsplyDrones        = this.dsplyDrones;
            set.showStarsystem     = this.showStarsystem;
            set.ignoreCARGO        = this.ignoreCARGO;
            set.ignoreHostileCARGO = this.ignoreHostileCARGO;
            set.autochangeAmmo     = this.autochangeAmmo;
            set.enableFastBuy      = this.enableFastBuy;
            
            return set;
        }

        /**
         * Clones the object.
         *
         * @return Cloned object.
         */
        public SET clone()
        {
            try {
                SET set = (SET)super.clone();

                return set;
            } catch(CloneNotSupportedException e) {
                Console.println("Couldn't clone account!");
                Console.println(e.getMessage());

                return null;
            }
        }
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Settings clone()
    {
        try {
            Settings set = (Settings)super.clone();

            set.set = this.set.clone();

            return set;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone account!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
