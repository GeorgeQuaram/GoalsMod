package eu.qlow.goals.utils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * lol, this is the ConfigManager of LabyMod 3
 *
 * @author qlow
 */
public class ConfigManager<T> {

    private final static Gson gson;

    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private File file;
    private Class<? extends T> configDefaults;
    private T settings;

    /**
     * Config-Manager that is (de)serializing an object to a json-object
     *
     * @param file           file, that is using to store the object in
     * @param configDefaults object, that is used to serialize/deserialize
     */
    public ConfigManager( File file, Class<? extends T> configDefaults ) {
        this.file = Preconditions.checkNotNull( file );
        this.configDefaults = Preconditions.checkNotNull( configDefaults );

        loadConfig( false );
    }

    /**
     * Call to load config
     *
     * @param reload should the config reload?
     */
    private void loadConfig( boolean reload ) {
        // Checking for parent folder and creating it when it doesn't exist
        if ( file.getParentFile() != null && !file.getParentFile().exists() ) {
            file.getParentFile().mkdir();
        }

        // Deleting config, if last config-load failed
        if ( reload && file.exists() ) {
            file.delete();
        }

        boolean createdNewFile = false;

        if ( !file.exists() ) {
            try {
                file.createNewFile();
                createdNewFile = true;
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }

        try {
            // Initializing settings-object
            this.settings = gson.fromJson( createdNewFile ?
                    gson.toJson( configDefaults.newInstance() ) : // Using default settings if created a new file
                    IOUtils.toString( new FileInputStream( file ) ), configDefaults ); // Reading from file if the file existed before

            if ( !reload && this.settings == null ) {
                loadConfig( true );
            } else if ( settings != null ) {
                save();
            }

        } catch ( Exception ex ) {
            ex.printStackTrace();

            // Reloading config if the first load failed
            if ( !reload )
                loadConfig( true );
        }
    }

    /**
     * Store changes of config in file
     */
    public void save() {
        try {
            // Creating new PrintWriter by file
            PrintWriter w = new PrintWriter( new FileOutputStream( file ) );

            // Printing to file
            w.print( gson.toJson( settings ) );

            // Flushing & closing writer
            w.flush();
            w.close();
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the deserialized JSON object
     *
     * @return object loaded at {@link ConfigManager#loadConfig(boolean)}
     */
    public T getSettings() {
        return settings;
    }
}
