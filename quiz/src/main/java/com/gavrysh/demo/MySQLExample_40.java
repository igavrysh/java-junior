package com.gavrysh.demo;

import javax.sound.sampled.spi.AudioFileReader;
import java.nio.charset.spi.CharsetProvider;
import java.sql.Driver;
import java.util.ServiceLoader;

public class MySQLExample_40 {

    public static void main(String[] args) {
        ServiceLoader<Driver> drivers
                = ServiceLoader.load(Driver.class);
        for (Driver driver : drivers) {
            System.out.println(driver);
        }

        System.out.println();

        ServiceLoader<CharsetProvider> charsetProviders
                = ServiceLoader.load(CharsetProvider.class);
        for (CharsetProvider provider : charsetProviders) {
            System.out.println(provider);
        }

        System.out.println();

        ServiceLoader<AudioFileReader> audioFileReaders
                = ServiceLoader.load(AudioFileReader.class);
        for (AudioFileReader reader : audioFileReaders) {
            System.out.println(reader);
        }
    }

}
