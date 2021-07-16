package com.stefan.designPattern.builder2;

/**
 * @author stefan
 * @date 2021/7/14 12:44
 */
public class Product {
    private String s1;
    private String s2;
    private String s3;
    private Integer i1;
    private Integer i2;
    private Integer i3;

    private Product(Builder builder) {
        this.s1 = builder.s1;
        this.s2 = builder.s2;
        this.s3 = builder.s3;
        this.i1 = builder.i1;
        this.i2 = builder.i2;
        this.i3 = builder.i3;
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
        private String s1;
        private String s2;
        private String s3;
        private Integer i1;
        private Integer i2;
        private Integer i3;

        public static Builder create(String s1) {
            return new Builder(s1);
        }

        private Builder(String s1) {
            this.s1 = s1;
        }

        public Builder setS1(String s1) {
            this.s1 = s1;
            return this;
        }

        public Builder setS2(String s2) {
            this.s2 = s2;
            return this;
        }

        public Builder setS3(String s3) {
            this.s3 = s3;
            return this;
        }

        public Builder setI1(Integer i1) {
            this.i1 = i1;
            return this;
        }

        public Builder setI2(Integer i2) {
            this.i2 = i2;
            return this;
        }

        public Builder setI3(Integer i3) {
            this.i3 = i3;
            return this;
        }

        public Product build() {
            // 1. s1 必传
            if (s1 == null || s2 == "") {
                throw new IllegalArgumentException("s1必传，不可为空！");
            }
            // 2. s2 s3 非必填，但是具有依赖关系，即s2传了，s3也必须传
            if (s2 != null && s2 != "") {
                if (s3 == null || s3 == "") {
                    throw new IllegalArgumentException("s2与s3具有依赖关系！");
                }
            }
            // 3. i1 不传或者 i1 <= 0 则默认为 1
            if (i1 == null || i1 <= 0) {
                i1 = 1;
            }
            // 4. i2非必传 但是需要校验有效性，不能小于0
            if (i2 != null && i2 < 0) {
                throw new IllegalArgumentException("i2 不能小于0");
            }
            Product product = new Product(this);
            return product;
        }
    }
}
