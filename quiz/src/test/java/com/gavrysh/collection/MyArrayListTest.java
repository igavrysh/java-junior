package com.gavrysh.collection;

import org.junit.Ignore;
import org.junit.Test;

public class MyArrayListTest {

    @Test
    public void test_empty_size() {
        if (new MyArrayList<String>().size() != 0) {
            throw new AssertionError();
        }
    }

    @Test
    @Ignore
    public void test_size_after_add() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("A");
        if (list.size() != 1) {
            throw new AssertionError();
        }
    }

    @Test
    public void test_empty_add_remove_same() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("A");
        list.remove("A");
        if (list.size() != 0) {
            throw new AssertionError();
        }
    }

    @Test
    @Ignore
    public void test_empty_add_remove_other() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("A");
        list.remove("B");
        if (list.size() != 1) {
            throw new AssertionError();
        }
    }

    @Test
    @Ignore
    public void test_empty_add_null() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add(null);
        if (list.size() != 1) {
            throw new AssertionError();
        }
    }


}
