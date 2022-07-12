package com.meli.interview.back.subscription_api.session;

import com.meli.interview.back.subscription_api.subscription.Subscription;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;

    private ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();

    private List<User> friends = new ArrayList<User>();

    public List<User> getFriends() {
        return friends;
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public void addSubscription(Subscription trip) {
        subscriptions.add(trip);
    }

    public ArrayList<Subscription> subscriptions() {
        //ArrayList<Subscription> subscriptions = new ArrayList<>();
       // subscriptions.stream().forEach(partner->partner.getPartner());
        return subscriptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
