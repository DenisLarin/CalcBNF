package Enums;

public enum Types {
    openbracket("("), closebracket(")"), number, operation;

    String viewSymbol;
    Types(String s) {
        this.viewSymbol = s;
    }
    Types(){

    }

    public String getViewSymbol() {
        return viewSymbol;
    }
}
