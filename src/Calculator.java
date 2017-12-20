import BNFDir.BNF;
import Parser.Element;
import Parser.ParserApp;
import Tree.TreeApp;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        do {
            String str = new String();
            Scanner scanner = new Scanner(System.in);
            System.out.println("введите выражение или exit для выхода");
            str = scanner.nextLine();
            if (str.equals("exit"))
                break;
            List<Element> elementList = new LinkedList<>(); //список элементов
            if (ParserApp.parser(str, elementList)) {
                System.out.println("Выражение: '" + str + "' записано корректно, БНФ проверка...");

                BNF bnf = new BNF(elementList);
                if (bnf.iscorrect()) {
                    System.out.println("Выражение: '" + str + "' проверено успешно, продолжаем вычисление...");
                    //формирование дерева
                    TreeApp<Element> treeApp = new TreeApp<Element>(elementList, true);
                    //решение
                    System.out.println("\n\n");
                    System.out.println(treeApp.calc());
                    System.out.println("\n\n");
                } else
                    System.out.println("Выражение: '" + str + "' проверено неуспешно, программа будет завершена...");
            } else
                System.out.println("Выражение: '" + str + "' записано некорректно, программа будет завершена...");
        } while (true);
    }
}