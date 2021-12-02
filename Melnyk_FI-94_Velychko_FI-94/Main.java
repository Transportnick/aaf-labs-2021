package com.company;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static final String Create = "((C|c)(r|R)(e|E)(a|A)(t|T)(E|e)[ ]*[a-z,A-Z,0-9]*[ ]*[(][a-z,A-Z,0-9, ]*[)]*)";
    private static final String Insert = "[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*[A-Z,a-z,0-9]*[ ]*[(][A-Z,a-z,-?0-9, ]*[)]*[ ]*";
    private static final String Select = "[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*[A-Z, a-z, 0-9, * ]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*(([ ]*)|([ ]*((W|w)(H|h)(E|e)(R|r)(E|e))*[ ]*[A-Z, a-z, 0-9, ]*[ ]*))";
    private static final String Delete = "[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)(([ ]*)|([ ]*(F|f)(R|r)(O|o)(M|m)[ ]*))[A-Z, a-z, 0-9]*[ ]*";
    private static final String Exit = "[ ]*[.](E|e)(X|x)(I|i)(T|t)*[ ]*";
    private static final Pattern patternCreate = Pattern.compile(Create);
    private static final Pattern patternInsert = Pattern.compile(Insert);
    private static final Pattern patternSelect = Pattern.compile(Select);
    private static final Pattern patternDelete = Pattern.compile(Delete);
    private static final Pattern patternExit = Pattern.compile(Exit);

    // Map хранит пары ключ - данные
    // ключ - имя таблицы
    // данные - лист содержащий список полей таблицы
    static Map<String, List<String>> listOfTables = new HashMap<>();
    static Map<String, List<List<Integer>>> listOfTablesData = new HashMap<>();

    public static void main(String[] args) {

        System.out.println("Hello! Enter your command.If you want to finish enter .EXIT");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            // Reading data using readLine
            System.out.print(" > ");
            StringBuilder command = new StringBuilder();
            while (!command.toString().contains(";")) {
                String name = scanner.nextLine();
                command.append(name);
            }
            String saveCommand = command.substring(0, command.toString().indexOf(";"));

            if (patternCreate.matcher(saveCommand).matches()) {
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
                exit = true;
            } else {
                System.out.println("Error, your command is wrong");
            }
        }
    }

    private static void createHandler(String command) {
        System.out.print("Creation of a new table " + " ");
        String cmd = command;
        // 1) get table name from command;
        String[] array = cmd.replaceFirst("( )*[C|c][R|r][E|e][A|a][T|t][E|e]( )*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");
        System.out.println(Arrays.toString(array));
        System.out.println("Table name : " + array[0]);
        String tableName = array[0];
        // if table created with this name then error, break
        if (listOfTables.containsKey(tableName)) {
            System.out.println("Table with this name is already created");
            return;
        }
        // 2) get column names from command;
        LinkedList<String> fields = new LinkedList<>();
        for (int i = 1; i < array.length; i++) {
            fields.add(array[i]);
        }
        // 3) save data about new table with columns in array;
        listOfTables.put(tableName, fields);
        listOfTablesData.put(tableName, new LinkedList<>());
    }

    private static void insertHandler(String command) {
        System.out.print("Insert data into table " + " ");
        String cmd = command;

        String[] array = cmd.replaceFirst("[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");


        System.out.println(Arrays.toString(array));
        String tableName = array[0];
        // 1) get table name from command;
        System.out.println("Table name : " + array[0]);
        // if table name is missing then error;
        if (!listOfTables.containsKey(tableName)) {
            System.out.println("Table with this name doesn't exist");
            return;
        }
        // if data number != table columns number : error;
        if (listOfTables.get(tableName).size() != (array.length - 1)) {
            System.out.println("The amount of data doesn't match the number of columns");
            return;
        }
        // 3) save data into table;
        LinkedList<Integer> fields = new LinkedList<>();
        for (int i = 1; i < array.length; i++) {
            fields.add(Integer.parseInt(array[i]));
        }
        listOfTablesData.get(tableName).add(fields);
    }

    private static void selectHandler(String command) {
        //System.out.print("Select data from table " + " ");
        String cmd = command;
        // 1) get table name from command;
        String[] array1 = cmd.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)*[ ]*[A-Z, a-z, 0-9, *]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");

        String tableName = array1[0];
        // if table name is missing then error;
        if (!listOfTables.containsKey(tableName)) {
            System.out.println("Table with this name doesn't exist");
            return;
        }
        // 2) get column names from command;
        String[] array = cmd.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*", "")
                .replaceAll("[ ]*(F|f)(R|r)(O|o)(M|m)*[A-Z, a-z, 0-9]*[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");

        List<String> allFields = listOfTables.get(tableName);
        boolean[] flags = new boolean[allFields.size()];
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (allFields.contains(array[i])) {
                flags[allFields.indexOf(array[i])] = true;
                counter++;
            }
        }
        if (array[0].equals("*")) {
            Arrays.fill(flags, true);
            counter = allFields.size();
        } else if (counter < array.length) {
            System.out.println("List of fields is incorrect for the given table");
            return;
        }
        // 3) get WHERE condition;
        String[] array2 = cmd.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)*[ ]*[A-Z, a-z, 0-9, * ]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*[ ]*(W|w)(H|h)(E|e)(R|r)(E|e)*[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");
        if (array2[0].equals("")) {
            return;
        } else {
            for (int i = 0; i < array2.length; i++) {
                System.out.println("condition names : " + array2[i]);
            }
        }
        // 4) read data from table according to condition and print;
        for (int i = 0; i < counter; i++)
            System.out.print("================|");
        System.out.println();
        for (int i = 0; i < allFields.size(); i++) {
            if (flags[i]) {
                System.out.printf("%15s |", allFields.get(i));
            }
        }
        System.out.println();
        for (int i = 0; i < counter; i++)
            System.out.print("================|");
        System.out.println();
        List<List<Integer>> allFieldsData = listOfTablesData.get(tableName);
        for (int k = 0; k < allFieldsData.size(); k++) {
            for (int m = 0; m < allFieldsData.get(k).size(); m++) {
                if (flags[m])
                    System.out.printf("%15d |", allFieldsData.get(k).get(m));
            }
            System.out.println();
        }
        for (int i = 0; i < counter; i++)
            System.out.print("================|");
        System.out.println();

    }

    private static void deleteHandler(String command) {
        System.out.print("Delete data from table " + " ");
        String cmd = command;
        // 1) get table name from command;
        String[] array = cmd.replaceFirst("[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)(([ ]*)|([ ]*(F|f)(R|r)(O|o)(M|m)[ ]*))", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ][ ]", " ")
                .split(" ");

        String tableName = array[0];
        System.out.println(Arrays.toString(array));
        System.out.println("Table name : " + array[0]);
        // if table name is missing then error;
        if (!listOfTables.containsKey(tableName)) {
            System.out.println("Table with this name doesn't exist");
            return;
        }
        listOfTables.remove(tableName);
        listOfTablesData.remove(tableName);
        // 2) get WHERE condition;
        // 3) delete data from table according to condition and print;
    }
}