package com.mitchelldevries.mywishlist;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Mitchell de Vries.
 */
public class WishStorage {

    private Realm realm;
    private RealmConfiguration realmConfig;

    public static WishStorage getInstance(Context context) {
        return new WishStorage(context);
    }

    public WishStorage(Context context) {
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
    }

    public List<Wish> findAll() {
        return realm.where(Wish.class).findAll();
    }

    public Wish findOne(int id) {
        return realm.where(Wish.class).equalTo("id", id).findFirst();
    }

    public void save(Wish wish) {
        realm.beginTransaction();
        realm.insertOrUpdate(wish);
        realm.commitTransaction();
    }

    public void deposit(Wish wish, Double current) {
        realm.beginTransaction();
        double old = findOne(wish.getId()).getCurrent();
        wish.setCurrent(old += current);
        realm.commitTransaction();
    }

    public void withdraw(Wish wish, Double current) {
        realm.beginTransaction();
        double old = findOne(wish.getId()).getCurrent();
        wish.setCurrent(old -= current);
        realm.commitTransaction();
    }

    public void delete(Wish wish) {
        realm.beginTransaction();
        wish.deleteFromRealm();
        realm.commitTransaction();
    }

    public int incrementId() {
        return realm.where(Wish.class).max("id") == null ? 0 : realm.where(Wish.class).max("id").intValue() + 1;
    }
}
