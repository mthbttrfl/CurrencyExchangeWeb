package org.example.currencyexchangeweb.entities;

public class Currency {
    private Long id;
    private String code;
    private String name;
    private String sign;

    public Currency(long id, String code, String name, String sign) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sign = sign;
    }

    public Currency() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", code = " + code +
                ", name = " + name +
                ", sign = " + sign;
    }
}
