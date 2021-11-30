package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Main {
    private static final String Create = "((C|c)(r|R)(e|E)(a|A)(t|T)(E|e)[ ]*[a-z,A-Z,0-9]*[ ]*[(][a-z,A-Z,0-9, ]*[)])|((C|c)(r|R)(e|E)(a|A)(t|T)(E|e)[ ]*[a-z,A-Z,0-9]*)";
    private static final String Insert = "[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*[A-Z,a-z,0-9]*[ ]*[(][A-Z,a-z,0-9, ]*[)]*[ ]*";
    private static final String Select = "[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*[A-Z, a-z, 0-9]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*";
    private static final String Delete = "[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*";
    private static final String Exit = "[ ]*[.](E|e)(X|x)(I|i)(T|t)*[ ]*";
    private static final Pattern patternCreate = Pattern.compile(Create);
    private static final Pattern patternInsert = Pattern.compile(Insert);
    private static final Pattern patternSelect = Pattern.compile(Select);
    private static final Pattern patternDelete = Pattern.compile(Delete);
    private static final Pattern patternExit = Pattern.compile(Exit);
    public static ArrayList<tablename> tablenames = new ArrayList<tablename>();
    public static ArrayList<tablevalue> value = new ArrayList<tablevalue>();

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

                if (patternCreate.matcher(saveCommand).matches()){
                    createHandler(saveCommand);
                    System.out.println("Current command is : " + saveCommand);
                } else if (patternInsert.matcher(saveCommand).matches()) {
                    insertHandler(saveCommand);
                    System.out.println("Current command is : " + saveCommand);
                } else if (patternSelect.matcher(saveCommand).matches()) {
                    selectHandler(saveCommand);
                    System.out.println("Current command is : " + saveCommand);
                } else if (patternDelete.matcher(saveCommand).matches()) {
                    deleteHandler(saveCommand);
                    System.out.println("Current command is : " + saveCommand);
                } else if (patternExit.matcher(saveCommand).matches()) {
                    return;
                } else {
                    System.out.println("Error, your command is wrong");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createHandler(String command) {
        System.out.print("Creation of a new table " + " ");
        String cmd = command;

        String[] array = cmd.replaceFirst("( )*[C|c][R|r][E|e][A|a][T|t][E|e]( )*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");
        System.out.println(Arrays.toString(array));
        System.out.println("Table name : " + array[0]);
        tablenames.add(new tablename(array[0]));
        for (int i = 1; i < array.length; i++) {
            tablenames.add(new tablename(array[i]));
            System.out.println("      field : " + array[i]);
            // 1) get table name from command;
            // if table created with this name then error, break;
            // 2) get column names from command;
            // 3) save data about new table with columns in array;
        }
    }
    private static void insertHandler(String command) {
        System.out.print("Insert data into table " + " ");
        String cmd = command;

        String[] array = cmd.replaceFirst("[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");


        System.out.println(Arrays.toString(array));
        System.out.println("Table name : " + array[0]);
        for (int i = 1; i < array.length; i++) {
            System.out.println("      field : " + array[i]);
            int j = Integer.parseInt(array[i]);
            value.add(new tablevalue(j));
            // 1) get table name from command;
            // if table name is missing then error;
            // 2) read data from command;
            // if data number != table columns number : error;
            // 3) save data into table;
        }
    }
    private static void selectHandler(String command) {
        System.out.print("Select data from table " + " ");
        String cmd = command;
        String[] array = cmd.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*", "")
                .replaceAll("[ ]*(F|f)(R|r)(O|o)(M|m)*[A-Z, a-z, 0-9]*[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");
        System.out.println(Arrays.toString(array));
        for (int i = 0; i < array.length ; i++) {
            System.out.println("      condition : " + array[i]);
        }
        String[] array1 = cmd.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)*[ ]*[A-Z, a-z, 0-9]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");
        System.out.println(Arrays.toString(array1));
        for (int i = 0; i < array1.length ; i++) {
            System.out.println("Table names : " + array1[i]);
        }
        //String[] array2 = cmd.replaceFirst();
                //.replaceAll("[(|)|,|;|]", "")
                //.replaceAll("[ ][ ]", " ")
                //.split(" ");

        System.out.println(Arrays.toString(array));
        //System.out.println(Arrays.toString(array2));
        //System.out.println("Table name : " + array2[0]);
            // 1) get table name from command;
            // 2) get column names from command;
            // 3) get WHERE condition;
            // 4) read data from table according to condition and print;

    }
    private static void deleteHandler(String command) {
        System.out.print("Delete data from table " + " ");
        String cmd = command;

        String [] array = cmd.replaceFirst("[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ][ ]", " ")
                .split(" ");


        System.out.println( Arrays.toString(array) );
        System.out.println( "Table name : " + array[0] );
        for(int i=1; i<array.length; i++) {
            System.out.println( "      field : " + array[i] );
            // 1) get table name from command;
            // 2) get WHERE condition;
            // 3) delete data from table according to condition and print;
        }
    }
}