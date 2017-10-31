package com.sirrusgames.mygame.utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;
import com.sirrusgames.mygame.screens.LoadScreen;

/**
 * Task 1: Loads and stores Gdx assets like textures, bitmapfonts, tile maps, sounds, music and so on.
 * Task 2: Sets Gdx Screens.
 * Task 3: Disposes spesific screen assets or all game assets.
 * Important! Only the com.badlogic.gdx.Game class is used. Not Used com.badlogic.gdx.ApplicationListener
 * @author vefakayaci@gmail.com */
public class GameManager
{
    private Game game;
    private boolean isUsedCustomloadScreen = true;
    private ObjectMap<Class<?>, AssetDescriptor[]> screenMap;
    private static AssetManager assetManager;
    private Screen nextScreen;

    /**
     * Creates a new GameManager also Asset Manager with optionally all default loaders.
     * @param game to add the default Game class (com.badlogic.gdx.Game) */
    public GameManager(Game game)
    {
        this.game = game;
        assetManager = new AssetManager();
        screenMap = new ObjectMap<Class<?>, AssetDescriptor[]>();
    }

    /**
     * It is only called from the Loader screen.
     * This Loader screen processes the return values for progress.
     * @return value in the range 0 to 1
     */
    public float updateProgress()
    {
        assetManager.update();
        if(assetManager.getProgress() >= 1)
        {
            game.setScreen(nextScreen);
        }
        return  assetManager.getProgress();

    }

    /**
     * Adds AssetDescriptor's array value to ObjectMap table for each screen
     * @param screen the type of the com.badlogic.gdx.Screen
     * @param assets the array of com.badlogic.gdx.assets.AssetDescriptor
     */
    public void addScreen(Class<?> screen, AssetDescriptor[] assets)
    {
        screenMap.put(screen, assets);
    }

    /**
     * if isUsedCustomloadScreen is true: Load loader Screen with setScreen method of Gdx Game class
     * if isUsedCustomloadScreen is false: Load @param screen waiting until his assets are loaded
     * @param screen the type of the com.badlogic.gdx.Screen
     */
    public void loadScreen(Screen screen)
    {
        if (isUsedCustomloadScreen)
        {
            nextScreen = screen;
            LoadScreen loadScreen = new LoadScreen(this);
            loadScreen.setAssets(screenMap.get(screen.getClass()));
            game.setScreen(loadScreen);
        }else
        {
            loadAssets(screenMap.get(screen.getClass()));
            assetManager.finishLoading();
            game.setScreen(screen);
        }
    }

    /**
     * Loads @param screen waiting until his assets are loaded
     * @param screen the type of the com.badlogic.gdx.Screen
     */
    public void loadLikeSplashScreen(Screen screen)
    {
        loadAssets(screenMap.get(screen.getClass()));
        assetManager.finishLoading();
        game.setScreen(screen);
    }

    /**
     * default AsssetManager class loads Gdx assets
     * @param assetDescriptors the array of the com.badlogic.gdx.assets.AssetDescriptor
     */
    public void loadAssets(AssetDescriptor[] assetDescriptors)
    {
        for (AssetDescriptor ad : assetDescriptors)
        {
            assetManager.load(ad.fileName, ad.type);
        }
    }

    /**
     * default AsssetManager class loads Gdx assets
     * @param type the type of the asset.
     * @return the asset.
     */
    public static <T> T getAsset(String file, Class<T> type)
    {
        return assetManager.get(file, type);
    }

    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    public Game getGame()
    {
        return game;
    }

    /**
     * Disposes of the assets of the given screen
     * @param screen the type of the com.badlogic.gdx.Screen
     */
    public void disposeScreen(Screen screen)
    {
        for (AssetDescriptor d: screenMap.get(screen.getClass()))
        {
            assetManager.unload(d.fileName);
        }
    }

    /**
     * @param usedCustomloadScreen Will custom load screen be used in the project
     */
    public void setUsedCustomloadScreen(boolean usedCustomloadScreen)
    {
        isUsedCustomloadScreen = usedCustomloadScreen;
    }

    /**
     * (from libGdx wiki)
     * If you want to get rid of all assets at once you can call:
     * manager.clear(); or manager.dispose();
     */
    public void dispose()
    {
        assetManager.dispose();
        //assetManager.clear();
    }
}
