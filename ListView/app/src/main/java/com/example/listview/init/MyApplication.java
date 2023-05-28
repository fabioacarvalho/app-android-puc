package com.example.listview.init;

import android.app.Application;

import com.example.listview.realmdb.ListViewDb;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(2) // Aumente o número da versão do esquema
                .migration((RealmMigration) new ListViewDb()) // Se necessário, defina uma classe de migração
                .build();
        Realm.setDefaultConfiguration(configuration);

    }
}
