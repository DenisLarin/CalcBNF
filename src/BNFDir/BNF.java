package BNFDir;

import Enums.Types;
import Parser.Element;

import javax.lang.model.element.TypeElement;
import java.util.List;

public class BNF {
    private List<Element> elementList;

    public BNF(List<Element> elementList) {
        this.elementList = elementList;
    }


    public boolean iscorrect() {
        return iscorrect(0,elementList.size()-1);
    }
    private boolean iscorrect(int startIndex, int finishIndex){
        if(startIndex>finishIndex)
            return false;
        System.out.print("вырыжение для проверки: " );
        for (int i = startIndex; i <= finishIndex; i++) {
            System.out.print(elementList.get(i).getValue() + " ");
        }
        System.out.println("\n______");
        //если встречается открвывающаяся скобка
        if (elementList.get(startIndex).getType().equals(Types.openbracket.toString())) {
            int deep = 1;
            int indexOfEnd = 0;//индекс конца выражения в скобке
            //проверка на глубину выражения
            //++startIndex -- что бы передвинуться с открывающейся скобки
            for (int i = ++startIndex; i <= finishIndex; i++) {
                if (elementList.get(i).getType().equals(Types.openbracket.toString()))
                   deep++;
                else if (elementList.get(i).getType().equals(Types.closebracket.toString())) {
                    //закрывающаяся скобка в глубине первого уровня => внутри должно быть выражение
                    if (deep == 1) {
                        //проверка на то что нет еще закрытых скобок
                        if (i != finishIndex) {
                            if (elementList.get(i + 1).getType().equals(Types.closebracket.toString())) {
                                System.out.println("количество открытх скобок меньше чем закрытых");
                                return false;
                            }
                        }
                        //не затрагиваем новую скобку
                        indexOfEnd = i - 1;
                        deep--;
                        break;
                    }
                    // уменьшаем глубину на 1
                    else {
                        deep--;
                    }
                }
            }
            //если глубина осталась то ошибка скобках
            if (deep != 0) {
                System.out.println("колчичество открывающихся скобок больше закрывающихся!");
                return false;
            }
            //вызываем рекурсию выражения внутри скобок
            if (iscorrect(startIndex, indexOfEnd)) {
                //если скобка последняя в списке то +1 так как ранее вычитали 1
                if (indexOfEnd + 1 == finishIndex)
                    return true;
                else {
                    //перескакиваем на две позиции по списку (скобка и на след символ)
                    indexOfEnd += 2;
                    try {
                        //если он дейсвие
                        if (elementList.get(indexOfEnd).getType().equals(Types.operation.toString())) {
                            return iscorrect(++indexOfEnd, finishIndex);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return false;
                    }
                }
            }
        }
        //если одно число или если число + дейсвие + какое-то выражение
        //++startIndex -- что бы передвинуться далее
        else
            return (elementList.get(startIndex).getType().equals(Types.number.toString())
                    && startIndex == finishIndex)
                    ||
                    (elementList.get(startIndex).getType().equals(Types.number.toString())
                    && elementList.get(++startIndex).getType().equals(Types.operation.toString())
                    && iscorrect(++startIndex, finishIndex));
        return false;
    }
}
