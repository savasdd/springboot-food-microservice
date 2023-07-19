package com.food.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventUtil {

    public static final String LOG_URL = "http://logs-service/api/logs/foods";
    public static final String FOOD = "food_server";
    public static final String FOOD_LOG = "food_server_log";
    public static final String STOCK = "stock_server";
    public static final String GROUP_ID = "group_id";
    public static final String STOCK_LOG = "stock_server_log";

    //topics
    public static final String ORDERS = "orders";
    public static final String PAYMENT_ORDERS = "payment-orders";
    public static final String STOCK_ORDERS = "stock-orders";

    //status
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_ACCEPT = "ACCEPT";
    public static final String STATUS_REJECT = "REJECT";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_ROLLBACK = "ROLLBACK";

}
