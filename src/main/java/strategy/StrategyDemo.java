package strategy;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 普通的if else 实现
 *
 * @author MaxStar
 * @date 2021/8/14
 */
public class StrategyDemo {

    /**
     * 使用枚举获取会员价格(没有复杂逻辑，直接取值)
     *
     * @param memberCode 会员code
     * @param price      价格
     * @return 会员价
     */
    public BigDecimal getPriceByEnum(Integer memberCode, BigDecimal price) {
        return price.multiply(MemberDiscountEnum.of(memberCode).getDiscount());
    }

    /**
     * 采用一个Map存储每种情况(享元模式)
     * key   -> 会员等级
     * value -> 执行的函数逻辑
     */
    Map<Integer, Function<BigDecimal, BigDecimal>> strategyMap = ImmutableMap.<Integer, Function<BigDecimal, BigDecimal>>builder()
            .put(MemberDiscountEnum.LV1.getCode(), originPrice -> printAndReturn(MemberDiscountEnum.LV1))
            .put(MemberDiscountEnum.LV2.getCode(), originPrice -> printAndReturn(MemberDiscountEnum.LV2))
            .put(MemberDiscountEnum.LV3.getCode(), originPrice -> printAndReturn(MemberDiscountEnum.LV3))
            .put(MemberDiscountEnum.LV4.getCode(), originPrice -> printAndReturn2(MemberDiscountEnum.LV4))
            .put(MemberDiscountEnum.LV5.getCode(), originPrice -> printAndReturn2(MemberDiscountEnum.LV5))
            .build();

    /**
     * 打印会员等级和返回折扣(模拟复杂函数)
     *
     * @param memberDiscountEnum 会员等级枚举
     * @return 折扣
     */
    public BigDecimal printAndReturn(MemberDiscountEnum memberDiscountEnum) {
        System.out.printf("member level:%d, discount:%s%n", memberDiscountEnum.getCode(), memberDiscountEnum.getDiscount());
        return memberDiscountEnum.getDiscount();
    }

    /**
     * 打印会员等级和返回折扣(模拟复杂函数)
     *
     * @param memberDiscountEnum 会员等级枚举
     * @return 折扣
     */
    public BigDecimal printAndReturn2(MemberDiscountEnum memberDiscountEnum) {
        System.out.printf("member level:%d ==> discount:%s%n", memberDiscountEnum.getCode(), memberDiscountEnum.getDiscount());
        return memberDiscountEnum.getDiscount();
    }


    /**
     * 使用枚举获取会员价格(没有复杂逻辑，直接取值)
     *
     * @param memberCode 会员code
     * @param price      价格
     * @return 会员价
     */
    public BigDecimal getPriceByStrategy(Integer memberCode, BigDecimal price) {
        return Optional.ofNullable(strategyMap.get(memberCode)).orElse(originPrice -> printAndReturn(MemberDiscountEnum.LV1)).apply(price);
    }

    public static void main(String[] args) {
        StrategyDemo demo = new StrategyDemo();
        BigDecimal originPrice = BigDecimal.valueOf(100);
        // 枚举模式
        System.out.printf("enum: 1 ==> %s%n", demo.getPriceByEnum(1, originPrice));
        System.out.printf("enum: 5 ==> %s%n", demo.getPriceByEnum(5, originPrice));
        System.out.printf("enum: 7 ==> %s%n", demo.getPriceByEnum(7, originPrice));

        // 策略模式
        System.out.printf("enum: 1 ==> %s%n", demo.getPriceByStrategy(1, originPrice));
        System.out.printf("enum: 5 ==> %s%n", demo.getPriceByStrategy(5, originPrice));
        System.out.printf("enum: 7 ==> %s%n", demo.getPriceByStrategy(7, originPrice));
    }
}
