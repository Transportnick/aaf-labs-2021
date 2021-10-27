package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Main {
    private static final String create = "[ ]*(C|c)(r|R)(e|E)(a|A)(t|T)(E|e)[ ]*[a-z,A-Z,0-9]*[ ]*[(][a-z,A-Z,0-9, ]*[)][ ]*";
    private static final String insert = "[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*[A-Z,a-z,0-9]*[ ]*[(][A-Z,a-z,0-9, ]*[)][ ]*";
    private static final String select = "[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*[A-Z, a-z, 0-9]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*";
    private static final String delete = "[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*";
    private static final String exit = "[ ]*[.](E|e)(X|x)(I|i)(T|t)[ ]*";
    private static final Pattern pattern0 = Pattern.compile(create);
    private static final Pattern pattern1 = Pattern.compile(insert);
    private static final Pattern pattern2 = Pattern.compile(select);
    private static final Pattern pattern3 = Pattern.compile(delete);
    private static final Pattern pattern4 = Pattern.compile(exit);


    public static void main(String[] args) {

        System.out.println("Hello! Enter your command.If you want to finish enter .EXIT");

        // Enter data using BufferedReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        try {
            System.out.print(" > ");
            StringBuilder command = new StringBuilder();
            while (command.toString().indexOf(";") == -1) {
                String name = reader.readLine();
                command.append(name);
                String saveCommand = command.substring(0, command.toString().indexOf(";"));

                System.out.println("Current command is : " + saveCommand);
                if (pattern0.matcher(saveCommand).matches()) {
                    createHandler(saveCommand);
                } else if (pattern1.matcher(saveCommand).matches()) {
                    insertHandler(saveCommand);
                } else if (pattern2.matcher(saveCommand).matches()) {
                    selectHandler(saveCommand);
                } else if (pattern3.matcher(saveCommand).matches()) {
                    deleteHandler(saveCommand);
                } else if (pattern4.matcher(saveCommand).matches()) {
                    return;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createHandler(String command) {
        System.out.print("Creation of a new table");
        // 1) get table name from command;
        // if table created with this name then error, break;
        // 2) get column names from command;
        // 3) save data about new table with columns in array;
    }

    private static void insertHandler(String command) {
        System.out.print("Insert data into table");
        // 1) get table name from command;
        // if table name is missing then error;
        // 2) read data from command;
        // if data number != table columns number : error;
        // 3) save data into table;
    }

    private static void selectHandler(String command) {
        System.out.print("Select data from table");
        // 1) get table name from command;
        // 2) get column names from command;
        // 3) get WHERE condition;
        // 4) read data from table according to condition and print;
    }

    private static void deleteHandler(String command) {
        System.out.print("Delete data from table");
        // 1) get table name from command;
        // 2) get WHERE condition;
        // 3) delete data from table according to condition and print;
    }
}

