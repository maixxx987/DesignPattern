package strategy;

import java.math.BigDecimal;

/**
 * 会员折扣枚举类
 *
 * @author MaxStar
 * @date 2021/8/14
 */
public enum MemberDiscountEnum {

    /**
     * 会员编码及对应折扣
     */
    LV1(1, BigDecimal.valueOf(1)),
    LV2(2, BigDecimal.valueOf(0.9)),
    LV3(3, BigDecimal.valueOf(0.8)),
    LV4(4, BigDecimal.valueOf(0.7)),
    LV5(5, BigDecimal.valueOf(0.6));

    /**
     * 会员编码
     */
    private final Integer code;

    /**
     * 折扣
     */
    private final BigDecimal discount;

    MemberDiscountEnum(Integer code, BigDecimal discount) {
        this.code = code;
        this.discount = discount;
    }

    public Integer getCode() {
        return code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 根据编码获取会员
     *
     * @param code 编码
     * @return 对应的枚举(未找到则返回初级会员)
     */
    public static MemberDiscountEnum of(Integer code) {
        for (MemberDiscountEnum memberDiscountEnum : MemberDiscountEnum.values()) {
            if (memberDiscountEnum.getCode().equals(code)) {
                return memberDiscountEnum;
            }
        }
        return LV1;
    }
}
