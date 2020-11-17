package demo.vo;


import com.google.gson.Gson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class People {
    /**
     * 身份证ID
     */
    private long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空")
    private String sex;

    /**
     * 地址
     */
    @NotEmpty(message = "地址不能为空")
    private String address;

    /**
     * 电话号码
     */
    private String phoNum;

    /**
     * 邮箱
     */
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoNum() {
        return phoNum;
    }

    public void setPhoNum(String phoNum) {
        this.phoNum = phoNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 模拟 Lombok 的 Builder 模式
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String name;
        private int age;
        private String sex;
        private String address;
        private String phoNum;
        private String email;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }
        public Builder address(String address) {
            this.address = address;
            return this;
        }
        public Builder phoNum(String phoNum) {
            this.phoNum = phoNum;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }


        public People build() {
            People people = new People();
            people.setId(id);
            people.setAddress(address);
            people.setAge(age);
            people.setEmail(email);
            people.setSex(sex);
            people.setPhoNum(phoNum);
            people.setName(name);
            return people;
        }
    }

    public static void main(String[] args) {
        People people = People.builder().id(1000L).name("小小").address("深圳").age(18).email("@163.com").sex("男").phoNum("187-0000-8973").build();
        Gson gson = new Gson();
        // {"id":1000,"name":"小小","age":18,"sex":"男","address":"深圳","phoNum":"187-0000-8973","email":"@163.com"}
        System.out.println(gson.toJson(people));
    }
}
