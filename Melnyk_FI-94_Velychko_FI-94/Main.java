package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello! Enter your command.If you want to finish enter .EXIT");

        // Enter data using BufferedReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        try {
            StringBuilder command = new StringBuilder();
            while (command.toString().indexOf(";") == -1) {
                String name = reader.readLine();
                command.append(name);
            }
           /* String exit = "[ ]*[.](E|e)(X|x)(I|i)(T|t)";
            Pattern pattern4 = Pattern.compile(exit);
            Matcher matcher4 = pattern4.matcher(command);
            System.out.println(command + " : " + matcher4.matches());*/

            while (command.toString().indexOf("T") == -1) {
                String create = "[ ]*(C|c)(r|R)(e|E)(a|A)(t|T)(E|e)[ ]*[a-z,A-Z,0-9]*[ ]*[(][a-z,A-Z,0-9, ]*[)][ ]*[;]";
                String insert = "[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*[A-Z,a-z,0-9]*[ ]*[(][A-Z,a-z,0-9, ]*[)][ ]*[;]";
                String delete = "[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*[;]";
                String select = "[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*[A-Z, a-z, 0-9]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*[;]";

                Pattern pattern0 = Pattern.compile(create);
                Matcher matcher0 = pattern0.matcher(command);
                System.out.println(command + " : " + matcher0.matches());
                Pattern pattern1 = Pattern.compile(insert);
                Matcher matcher1 = pattern1.matcher(command);
                System.out.println(command + " : " + matcher1.matches());
                Pattern pattern2 = Pattern.compile(delete);
                Matcher matcher2 = pattern2.matcher(command);
                System.out.println(command + " : " + matcher2.matches());
                Pattern pattern3 = Pattern.compile(select);
                Matcher matcher3 = pattern3.matcher(command);
                System.out.println(command + " : " + matcher3.matches());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
