package com.mitchelldevries.mywishlist.domain;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Mitchell de Vries.
 */
public class GoalStorage {

    private Realm realm;
    private RealmConfiguration realmConfig;

    public static GoalStorage getInstance(Context context) {
        return new GoalStorage(context);
    }

    public GoalStorage(Context context) {
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
    }

    public List<Goal> findAll() {
        return realm.where(Goal.class).findAll();
    }

    public Goal findOne(int id) {
        return realm.where(Goal.class).equalTo("id", id).findFirst();
    }

    public void save(Goal goal) {
        realm.beginTransaction();
        realm.insertOrUpdate(goal);
        realm.commitTransaction();
    }

    public void deposit(Goal goal, Double current) {
        realm.beginTransaction();
        double old = findOne(goal.getId()).getCurrent();
        goal.setCurrent(old += current);
        realm.commitTransaction();
    }

    public void withdraw(Goal goal, Double current) {
        realm.beginTransaction();
        double old = findOne(goal.getId()).getCurrent();
        goal.setCurrent(old -= current);
        realm.commitTransaction();
    }

    public void delete(Goal goal) {
        realm.beginTransaction();
        goal.deleteFromRealm();
        realm.commitTransaction();
    }

    public int incrementId() {
        return realm.where(Goal.class).max("id") == null ? 0 : realm.where(Goal.class).max("id").intValue() + 1;
    }
}
