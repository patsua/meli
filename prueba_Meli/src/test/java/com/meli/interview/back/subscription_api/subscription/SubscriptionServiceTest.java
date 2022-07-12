package com.meli.interview.back.subscription_api.subscription;

import com.meli.interview.back.subscription_api.exception.UserNotLoggedInException;
import com.meli.interview.back.subscription_api.session.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionServiceTest {
    private static final User ANY_USER = null ;
    private static final User LOGGED_IN_USER = new User() ;
    private static final User NOT_LOGGED_IN_USER = null;

    private static final Subscription A_SUBSCRIPTION = new Subscription();
    private static final Subscription A_OTHER_SUBSCRIPTION = new Subscription();

    @Test
    public void should_throws_an_exception_when_user_is_not_logged_in(){

        Assertions.assertThrows(UserNotLoggedInException.class,()->{
            SubscriptionService service= new SubscriptionServiceFor(NOT_LOGGED_IN_USER);
            service.getUserSubscriptionsCost(ANY_USER);


        });

    }
  
    @Test
    public void returns_no_subscriptions_when_use_has_no_friends(){
        SubscriptionService service= new SubscriptionServiceFor(LOGGED_IN_USER);
        User userWithNotFriends = new User();
        Float subscriptionsCost = service.getUserSubscriptionsCost(userWithNotFriends);
        assertFalse(subscriptionsCost.isNaN());

    }
    @Test
    public void no_subscriptions_when_user_is_not_a_friend_of_the_logged_in_user(){

        SubscriptionService service= new SubscriptionServiceFor(LOGGED_IN_USER);
        User userWithFriends = new User();
        userWithFriends.addFriend(new User());
        Float subscriptionsCost = service.getUserSubscriptionsCost(userWithFriends);
        assertFalse(subscriptionsCost.isNaN());

    }
    @Test
    public void return_subscriptions_of_the_user_when_friends_od_the_logged_in_use(){
        SubscriptionService service= new SubscriptionServiceFor(LOGGED_IN_USER);
        User userWithFriends = new User();
        userWithFriends.addFriend(LOGGED_IN_USER);
        userWithFriends.addSubscription(A_SUBSCRIPTION);
        userWithFriends.addSubscription(A_OTHER_SUBSCRIPTION);
        A_SUBSCRIPTION.setPartner("netflix");
        A_OTHER_SUBSCRIPTION.setPartner("disney");

        Float subscriptionsCost = service.getUserSubscriptionsCost(userWithFriends);
        assertEquals(300,subscriptionsCost);
    }

    private class SubscriptionServiceFor extends SubscriptionService{
        private User loggedInUser;

        public SubscriptionServiceFor(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        @Override
        protected ArrayList<Subscription> findSubscriptionByUser(User user) {

            return user.subscriptions();
        }

        @Override
        protected User getLoggedInUser() {
            return  loggedInUser;
        }
    }
}
