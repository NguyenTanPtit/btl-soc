package com.example.bookstore;

import com.example.bookstore.activities.AddProductActivity;

import org.junit.Assert;
import org.junit.Test;

public class TestFormat {
    @Test
    public void test(){
        System.out.println(AddProductActivity.fmt(5.60687));
    }

    @Test
    public void testEqual(){
        Assert.assertEquals("HLIb0w3Czvd4P1R3ig8UxUZbfKb2","HLIb0w3Czvd4P1R3ig8UxUZbfKb2");
    }
}
