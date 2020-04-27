package com.example.simple_http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Htmlview {
    public static String fromStream(InputStream in) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine= System.getProperty("line.separator");
        String line;
        while((line=reader.readLine())!=null){
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}