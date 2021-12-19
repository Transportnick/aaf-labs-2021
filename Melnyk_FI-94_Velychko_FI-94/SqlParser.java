import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SqlParser {
    private static final String Create = "((C|c)(r|R)(e|E)(a|A)(t|T)(E|e)[ ]*[a-z,A-Z,0-9]*[ ]*[(][a-z,A-Z,0-9, ]*[)]*)";
    private static final String Insert = "[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*[A-Z,a-z,0-9]*[ ]*[(][A-Z,a-z,-?0-9, ]*[)]*[ ]*";
    private static final String Select = "[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*[A-Z, a-z, 0-9, * ]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*[A-Z, a-z, 0-9]*(([ ]*)|([ ]*((W|w)(H|h)(E|e)(R|r)(E|e))*[ ]*[A-Z, a-z, 0-9,=,!,>,<,+,\\-,*,\\/,&,|,.,(,)]*[ ]*))";
    private static final String Delete = "[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)(([ ]*)|([ ]*(F|f)(R|r)(O|o)(M|m)[ ]*))[A-Z, a-z, 0-9]*(([ ]*)|([ ]*((W|w)(H|h)(E|e)(R|r)(E|e))*[ ]*[A-Z, a-z, 0-9,=,!,>,<,+,\\-,*,\\/,&,|,.,(,)]*[ ]*))";
    private static final Pattern patternCreate = Pattern.compile(Create);
    private static final Pattern patternInsert = Pattern.compile(Insert);
    private static final Pattern patternSelect = Pattern.compile(Select);
    private static final Pattern patternDelete = Pattern.compile(Delete);

    // Map хранит пары ключ - данные
    // ключ - имя таблицы
    // данные - лист содержащий список полей таблицы
    static Map<String, List<String>> listOfTables = new HashMap<>();
    // Map хранит пары ключ - данные
    // ключ - имя таблицы
    // данные - лист содержащий строку с данными
    static Map<String, List<List<Integer>>> listOfTablesData = new HashMap<>();

    public static void commandHandler(String command) {
        System.out.println("-------> Current command is: " + command);

        if (patternCreate.matcher(command).matches()) {
            createHandler(command);
        } else if (patternInsert.matcher(command).matches()) {
            insertHandler(command);
        } else if (patternSelect.matcher(command).matches()) {
            selectHandler(command);
        } else if (patternDelete.matcher(command).matches()) {
            deleteHandler(command);
        } else {
            System.out.println("Error: your command is wrong");
        }
    }

    private static void createHandler(String command) {
        // 1) get table name from command;
        String[] array = command.replaceFirst("( )*[C|c][R|r][E|e][A|a][T|t][E|e]( )*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");
        System.out.println(Arrays.toString(array));
        System.out.println("Table name : " + array[0]);
        String tableName = array[0];
        // if table created with this name then error, break
        if (listOfTables.containsKey(tableName)) {
            System.out.println("Error: Table with this name is already created");
            return;
        }
        if (array.length < 2) {
            System.out.println("Error: Columns not found");
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
        String[] array = command.replaceFirst("[ ]*(((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)[ ]((I|i)(n|N)(T|t)(O|o)))|((I|i)(N|n)(S|s)(E|e)(R|r)(T|t)))[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");


        System.out.println(Arrays.toString(array));
        String tableName = array[0];
        // 1) get table name from command;
        System.out.println("Table name : " + array[0]);
        // if table name is missing then error;
        if (!listOfTables.containsKey(tableName)) {
            System.out.println("Error: Table with this name doesn't exist");
            return;
        }
        // if data number != table columns number : error;
        if (listOfTables.get(tableName).size() != (array.length - 1)) {
            System.out.println("Error: The amount of data doesn't match the number of columns");
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
        // 1) get table name from command;
        String[] array1 = command.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)*[ ]*[A-Z, a-z, 0-9, *]*[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");

        String tableName = array1[0];
        // if table name is missing then error;
        if (!listOfTables.containsKey(tableName)) {
            System.out.println("Error: Table with this name doesn't exist");
            return;
        }
        // 2) get column names from command;
        int indexOfWhere = command.toLowerCase().indexOf("where");
        String cmd;
        if (indexOfWhere > -1)
            cmd = command.substring(0, indexOfWhere);
        else
            cmd = command;
        String[] array = cmd.replaceFirst("[ ]*(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)[ ]*", "")
                .replaceAll("[ ]*(F|f)(R|r)(O|o)(M|m)*[A-Z, a-z, 0-9]*[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");

        // Check that all mentioned fields belong to the table
        List<String> allFields = listOfTables.get(tableName);
        if (array.length == 1 && array[0].equals("*")) {
            array = allFields.toArray(new String[0]);
        } else {
            int counter = 0;
            for (String s : array) {
                if (!allFields.contains(s)) {
                    System.out.println("Error: List of fields is incorrect for the given table - " + s);
                    return;
                }
            }
        }

        // 3) get WHERE condition;
        MathBoolExpressionParser expression = null;
        if (indexOfWhere > -1) {
            cmd = command.substring(indexOfWhere + "where".length());
            expression = new MathBoolExpressionParser(cmd);
        }

        // 4) read data from table according to condition and print;
        for (int i = 0; i < array.length; i++)
            System.out.print("================|");
        System.out.println();

        for (String s : array) {
            System.out.printf("%15s |", s);
        }
        System.out.println();

        for (int i = 0; i < array.length; i++)
            System.out.print("================|");
        System.out.println();

        List<List<Integer>> allFieldsData = listOfTablesData.get(tableName);
        for (int k = 0; k < allFieldsData.size(); k++) {

            if (expression != null)
                for (int i = 0; i < allFields.size(); i++) {
                    expression.addNewVariable(allFields.get(i), allFieldsData.get(k).get(i));
                }

            if (expression == null || (Boolean) expression.calculate()) {
                for (int i = 0; i < array.length; i++) {
                    int index = allFields.indexOf(array[i]);
                    System.out.printf("%15s |", allFieldsData.get(k).get(index));
                }
                System.out.println();
                for (int i = 0; i < array.length; i++)
                    System.out.print("================|");
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void deleteHandler(String command) {
        // 1) get table name from command;
        String[] array = command.replaceFirst("[ ]*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)*[ ]*", "")
                .replaceAll("[ ]*(F|f)(R|r)(O|o)(M|m)[ ]*", "")
                .replaceAll("[(|)|,|;|]", "")
                .replaceAll("[ ]*[ ]", " ")
                .split(" ");

        // if table name is missing then error;
        String tableName = array[0];
        if (!listOfTables.containsKey(tableName)) {
            System.out.println("Error: Table with this name doesn't exist");
            return;
        }

        int indexOfWhere = command.toLowerCase().indexOf("where");
        if (indexOfWhere == -1) {
            int count = listOfTablesData.get(tableName).size();
            listOfTablesData.put(tableName, new LinkedList<>());
            System.out.println(count + " rows have been deleted from the " + tableName + " table");
        } else {
            // get WHERE condition;
            String cmd = command.substring(indexOfWhere + "where".length());
            MathBoolExpressionParser expression = new MathBoolExpressionParser(cmd);

            int counter = 0;
            List<String> allFields = listOfTables.get(tableName);
            List<List<Integer>> allFieldsData = listOfTablesData.get(tableName);

            for (int k = 0; k < allFieldsData.size(); k++) {

                if (expression != null)
                    for (int i = 0; i < allFields.size(); i++) {
                        expression.addNewVariable(allFields.get(i), allFieldsData.get(k).get(i));
                    }

                if ((Boolean) expression.calculate()) {
                    allFieldsData.remove(k);
                    k--;
                    counter++;
                }
            }
            System.out.println(counter + " rows have been deleted from the " + tableName + " table");
        }
    }
}
