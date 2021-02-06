package io.github.mickey.concurrency.wait.concurrency.generate;

import java.util.ArrayList;
import java.util.List;

public class GenerateSQL {

    public static void main(String[] args) {
        String source = "hotel_operation_platform_member\n" +
                "hotel_operation_platform_message\n" +
                "doorway_order\n" +
                "apolloportaldb\n" +
                "hotel_operation_platform\n" +
                "otmgroup_mall\n" +
                "ifm\n" +
                "unionbuy\n" +
                "unionbuy_order\n" +
                "doorway_admin_mobile\n" +
                "doorway_merchant\n" +
                "hotel_operation_platform_auth\n" +
                "doorway\n" +
                "doorway_member\n" +
                "apolloconfigdb\n" +
                "unionbuy_goods\n" +
                "unionbuy_member\n" +
                "hotel_operation_platform_points\n" +
                "doorway_goods\n" +
                "otmgroup_distribution\n" +
                "doorway-admin\n" +
                "hotel_pay_center\n" +
                "db_deposit\n" +
                "api_gate\n" +
                "hotel_operation_platform_coupon\n";
        String[] db_names = source.split("\n");

        String format = "SELECT count(TABLE_NAME) FROM information_schema.TABLES WHERE TABLE_SCHEMA='%s'";
        List<String> result = new ArrayList<>();
        for (String name : db_names) {
            String temp= String.format(format, name);
            temp += " union all ";
            result.add(temp);
        }
        result.forEach(System.out::println);
    }
}
