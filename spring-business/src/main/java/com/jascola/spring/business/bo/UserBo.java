package com.jascola.spring.business.bo;

public class UserBo {

    private String name;

    private Long age;

    private PetBo petBo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public PetBo getPetBo() {
        return petBo;
    }

    public void setPetBo(PetBo petBo) {
        this.petBo = petBo;
    }
}
