package com.qzlnode.epidemic.miniprogram.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author qzlzzz
 */
public class In {

    private static final String CHARSET_NAME = "utf-8";

    private static final Locale LOCALE = Locale.UK;

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

    private Scanner scanner;

    /**
     *
     */
    public In(){
        scanner = new Scanner(new BufferedInputStream(System.in),CHARSET_NAME);
        scanner.useLocale(LOCALE);
    }

    /**
     *
     * @param url
     */
    public In(URL url){
        if(url == null) throw new IllegalArgumentException("url argument is null");
        try {
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is),CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param name
     */
    public In(String name){
        if(name == null) throw new IllegalArgumentException("argument is null");
        if(name.length() == 0) throw new IllegalArgumentException("argument is the empty string");
        try {
            File file = new File(name);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis),CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }
            URL url = getClass().getResource(name);
            if(url == null){
                url = getClass().getClassLoader().getResource(name);
            }
            if(url == null){
                url = new URL(name);
            }
            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is),CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }catch (IOException ioe){
            throw new IllegalArgumentException("Could not open "+name,ioe);
        }
    }

    public boolean isEmpty(){
        return !scanner.hasNext();
    }

    public boolean hasNextLine(){
        return scanner.hasNextLine();
    }

    public String readAll(){
        if(!scanner.hasNextLine()){
            return "";
        }
        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public String readLine(){
        String line;
        try{
            line = scanner.nextLine();
        }catch (NoSuchElementException e){
            line = null;
        }
        return line;
    }

    public String readString(){
        try{
            return scanner.next();
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("attempts to read a 'String' value from the input stream, "
                    + "but no more tokens are available");
        }
    }
}
