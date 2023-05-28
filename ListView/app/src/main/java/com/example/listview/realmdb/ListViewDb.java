package com.example.listview.realmdb;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class ListViewDb extends RealmObject  {

    private String item;
    private int id;

    // Construtores, getters e setters


    public ListViewDb() {
        // Construtor vazio necessário para o Realm
    }

    public ListViewDb(String item, int id) {
        this.item = item;
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
