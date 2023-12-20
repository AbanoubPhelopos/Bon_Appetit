package com.example.myapplication.Database;

import android.media.Rating;

import com.example.myapplication.Classes.Dishes;
import com.example.myapplication.Classes.Payment;
import com.example.myapplication.Classes.Ratings;
import com.example.myapplication.Classes.Restaurants;
import com.example.myapplication.Classes.user;
import com.example.myapplication.Classes.admin;
import com.example.myapplication.R;

import java.util.ArrayList;

public class database {
    public static ArrayList<user> userList = new ArrayList<>();
    public static ArrayList<admin> adminList = new ArrayList<>();
    public static ArrayList<Restaurants> restaurants = new ArrayList<>();

    public static ArrayList<Dishes> dishes = new ArrayList<>();
    public static ArrayList<Ratings> ratings = new ArrayList<>();



    static {
        dishes.add(new Dishes("Foul Sandwich", "balady Bread with foul medames Sandwich", 5, Dishes.cuisines.RUSSIAN, Dishes.categories.BREAKFAST, "arabiata"));
        dishes.add(new Dishes("Foul Box", "foul medames Box", 400, Dishes.cuisines.ITALIAN, Dishes.categories.LUNCH, "arabiata"));
        dishes.add(new Dishes("Koshary Box", "rice with pasta and our special salsa with extra garlic water and spicy sauce", 25, Dishes.cuisines.RUSSIAN, Dishes.categories.BREAKFAST, "EL Tahrir"));

        restaurants.add(new Restaurants("arabiata", "El Rehab Food court", 12345, R.drawable.arabiata));
        restaurants.add(new Restaurants("EL Tahrir", "Nasr City", 12345, R.drawable.koshary_el_tahrir));

    }

    public static ArrayList<Payment> payments = new ArrayList<>();

    public static String addUser(user User) {
        for (user u : userList) {
            if (u.getEmail().equals(User.getEmail())) {
                return "duplicate user";
            }
        }
        userList.add(User);
        return "user added";
    }

    public static user userSearch(String email) {
        user us = new user(300, "a@a.a", "a", "123", "a", "a");
        userList.add(us);
        for (user u : userList) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public static String addAdmin(admin Admin) {
        for (admin a : adminList) {
            if (a.getEmail().equals(Admin.getEmail())) {
                return "duplicate admin";
            }
        }
        for (user u : userList) {
            if (u.getEmail().equals(Admin.getEmail())) {
                return "email isn't available choose another";
            }
        }
        adminList.add(Admin);
        return "admin created";
    }

    public static admin adminSearch(String email) {
        admin new_admin = new admin("arabiata@gmail.com", "admin", "123", "arabiata");
        admin newadmin = new admin("ElTahrir@gmail.com", "admin", "123", "EL Tahrir");
        adminList.add(new_admin);
        adminList.add(newadmin);
        for (admin a : adminList) {
            if (a.getEmail().equals(email)) {
                return a;
            }
        }
        return null;
    }

    public static Restaurants searchRestaurant(String restName) {
        for (Restaurants rest : restaurants) {
            if (rest.getName().equals(restName)) {
                return rest;
            }
        }
        return null;
    }

    public static Restaurants addRestaurant(Restaurants rest) {
        for (Restaurants r : restaurants) {
            if (r == rest) return null;
        }
        restaurants.add(rest);
        return rest;
    }

    public static Dishes searchDish(String dishName) {
        for (Dishes d : dishes) {
            if (d.getName().equals(dishName)) {
                return d;
            }
        }
        return null;
    }

    public static Dishes addDish(Dishes dish) {
        for (Dishes d : dishes) {
            if (d == dish) return null;
        }
        dishes.add(dish);
        return dish;
    }
    public static float assignRatingDish(int dishID){
        float numberOfDishes = 0f;
        float totalRating = 0f;
        for(Ratings r:ratings){
            if(r.getFoodID() == dishID){
                totalRating+=r.getRatingValue();
                numberOfDishes++;
            }
        }
        float rate = totalRating==0?0:totalRating/numberOfDishes;
        for(Dishes d:dishes){
            if(d.getId()==dishID){
                d.setRating(rate);
            }
        }
        return rate;
    }
    public static int AssignResturantRate(String restName){
        int totalNumber = 0;
        int totalRate = 0;
        for(Dishes d:dishes){
            if(d.getRestaurantName().equals(restName)){
                totalNumber++;
                totalRate += d.getRating();
            }
        }
        int rate = totalRate==0?0:totalRate/totalNumber;
        for(Restaurants r:restaurants){
            if(r.getName().equals(restName)){
                r.setRate(rate);
            }
        }
        return rate;
    }
}