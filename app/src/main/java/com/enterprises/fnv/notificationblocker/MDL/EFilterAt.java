package com.enterprises.fnv.notificationblocker.MDL;

/**
 * Created by fnv on 22/12/2015.
 */
public enum EFilterAt {
    Any(0),
    Title(1),
    Text(2),
    SubText(3);

    private int id;

    EFilterAt (int id){
        this.id = id;
    }
    public int getItemId(){
        return this.id;
    }
    public static EFilterAt getFromId(int id){
        switch (id){
            case 1:
                return Title;
            case 2:
                return Text;
            case 3:
                return SubText;
            default:
                return Any;

        }
    }

    @Override
    public String toString() {
        switch (id){
            case 1:
                return "Título";
            case 2:
                return "Texto";
            case 3:
                return "SubTexto";
            default:
                return "Qualquer";
        }
    }
}
