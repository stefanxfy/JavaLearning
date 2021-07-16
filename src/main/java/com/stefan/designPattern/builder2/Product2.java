package com.stefan.designPattern.builder2;

/**
 * @author stefan
 * @date 2021/7/14 12:44
 */
public class Product2 {
    private String s1;
    private String s2;
    private String s3;
    private Integer i1;
    private Integer i2;
    private Integer i3;

    private Product2() {
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    public Integer getI1() {
        return i1;
    }

    public Integer getI2() {
        return i2;
    }

    public Integer getI3() {
        return i3;
    }

    @Override
    public String toString() {
        return "Product{" +
                "s1='" + s1 + '\'' +
                ", s2='" + s2 + '\'' +
                ", s3='" + s3 + '\'' +
                ", i1=" + i1 +
                ", i2=" + i2 +
                ", i3=" + i3 +
                '}';
    }

    public static class Builder {
        private Product2 product2 = null;

        public static Builder create(String s1) {
            return new Builder(s1);
        }

        private Builder(String s1) {
            product2 = new Product2();
            product2.s1 = s1;
        }

        public Builder setS1(String s1) {
            this.product2.s1 = s1;
            return this;
        }

        public Builder setS2(String s2) {
            this.product2.s2 = s2;
            return this;
        }

        public Builder setS3(String s3) {
            this.product2.s3 = s3;
            return this;
        }

        public Builder setI1(Integer i1) {
            this.product2.i1 = i1;
            return this;
        }

        public Builder setI2(Integer i2) {
            this.product2.i2 = i2;
            return this;
        }

        public Builder setI3(Integer i3) {
            this.product2.i3 = i3;
            return this;
        }

        public Product2 build() {
            // 1. s1 必传
            if (this.product2.s1 == null || this.product2.s2 == "") {
                throw new IllegalArgumentException("s1必传，不可为空！");
            }
            // 2. s2 s3 非必填，但是具有依赖关系，即s2传了，s3也必须传
            if (this.product2.s2 != null && this.product2.s2 != "") {
                if (this.product2.s3 == null || this.product2.s3 == "") {
                    throw new IllegalArgumentException("s2与s3具有依赖关系！");
                }
            }
            // 3. i1 不传或者 i1 <= 0 则默认为 1
            if (this.product2.i1 == null || this.product2.i1 <= 0) {
                this.product2.i1 = 1;
            }
            // 4. i2非必传 但是需要校验有效性，不能小于0
            if (this.product2.i2 != null && this.product2.i2 < 0) {
                throw new IllegalArgumentException("i2 不能小于0");
            }
            return this.product2;
        }
    }
}
