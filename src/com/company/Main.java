package com.company;

import com.company.parser.Parser;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hi");

        Parser parser = new Parser();
        parser.loadFilePath("C:\\Users\\krc\\Documents\\untitled\\src\\com\\company\\grammar\\test01.jukapo");
        parser.parseFile();
        parser.loadFilePath("C:\\Users\\krc\\Documents\\untitled\\src\\com\\company\\grammar\\test02.jukapo");
        parser.parseFile();
        parser.loadFilePath("C:\\Users\\krc\\Documents\\untitled\\src\\com\\company\\grammar\\test03.jukapo");
        parser.parseFile();
        parser.loadFilePath("C:\\Users\\krc\\Documents\\untitled\\src\\com\\company\\grammar\\test04.jukapo");
        parser.parseFile();
        parser.loadFilePath("C:\\Users\\krc\\Documents\\untitled\\src\\com\\company\\grammar\\test05.jukapo");
        parser.parseFile();


    }
}
