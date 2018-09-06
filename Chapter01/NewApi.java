package com.packt.cookbook.ch01_install;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class NewApi {
    public static void main(String... args) {

        characterToString();
        charSequenceCompare();
        stringRepeat();
        stringIsBlank();
        stringLines();
        stringStrip();
        path();
        asMatchPredicate();
    }

    private static void characterToString(){
        var s = Character.toString(50);
        System.out.println(s);  // 2
    }

    private static void charSequenceCompare(){
        var i = CharSequence.compare("a", "b");
        System.out.println(i);   // -1

        i = CharSequence.compare("b", "a");
        System.out.println(i);   // 1

        i = CharSequence.compare("this", "that");
        System.out.println(i);   // 8

        i = CharSequence.compare("that", "this");
        System.out.println(i);   // -8
    }

    private static void stringRepeat(){
        String s1 = "a";
        String s2 = s1.repeat(3);  //aaa
        System.out.println(s2);

        String s3 = "bar".repeat(3);
        System.out.println(s3);   //barbarbar
    }

    private static void stringIsBlank(){
        String s1 = "a";
        System.out.println(s1.isBlank());  //false
        System.out.println(s1.isEmpty());  //false

        String s2 = "";
        System.out.println(s2.isBlank());  //true
        System.out.println(s2.isEmpty());  //true

        String s3 = "  ";
        System.out.println(s3.isBlank());  //true
        System.out.println(s3.isEmpty());  //false
    }

    private static void stringLines(){
        String s = "l1 \nl2 \rl3 \r\nl4 ";
        s.lines().forEach(System.out::print);  //l1 l2 l3 l4
    }

    private static void stringStrip(){
        String s = " a b ";
        System.out.println("'" + s.strip() + "'");         // 'a b'
        System.out.println("'" + s.stripLeading() + "'");  // 'a b '
        System.out.println("'" + s.stripTrailing() + "'"); // ' a b'
    }

    private static void path(){
        Path filePath = Path.of("a", "b", "c.txt");
        System.out.println(filePath);     //prints: a/b/c.txt

        try {
            filePath = Path.of(new URI("file:/a/b/c.txt"));
            System.out.println(filePath);  //prints: /a/b/c.txt
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void asMatchPredicate(){
        Pattern pattern =  Pattern.compile("^a.*z$");
        Predicate<String> predicate = pattern.asMatchPredicate();
        System.out.println(predicate.test("abbbbz"));  // true
        System.out.println(predicate.test("babbbz"));  // false
        System.out.println(predicate.test("abbbbx"));  // false
    }


}
