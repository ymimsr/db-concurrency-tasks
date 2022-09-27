package com.ymimsr.task_6;

public class Main {

    public static void main(String[] args) {
        Company company = new Company(5);
        Founder founder = new Founder(company);
        founder.start();
    }

}
