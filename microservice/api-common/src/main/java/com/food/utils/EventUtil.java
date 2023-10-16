package com.food.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventUtil {

    public static final String LOG_URL = "http://logs-service/api/logs/foods";
    public static final String FOOD = "food_server";
    public static final String FOOD_LOG = "food_server_log";
    public static final String STOCK = "stock_server";
    public static final String STOCK_LOG = "stock_server_log";

    //TODO KAFKA TOPICS
    public static final String GROUP_ID = "group_id";
    public static final String ORDERS = "orders";
    public static final String ORDERS_PAYMENT = "orders-payment";
    public static final String ORDERS_STOCK = "orders-stock";
    public static final String ORDERS_NOTIFICATION = "orders-notification";


}
