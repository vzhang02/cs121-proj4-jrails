package jrails;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class ViewTest {

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());
    }

    @Test
    public void testPTag() {
        Html content = View.t("This is my fancy paragraph content string");
        Html p = View.p(content);
        assertThat(p.toString(), is ("<p>This is my fancy paragraph content string</p>"));
       
    }

    @Test
    public void divTest(){
        Html html = new Html();
        assertThat(View.div(html.t("divTest")).toString(), equalTo("<div>divTest</div>"));
    }

    @Test
    public void brTest(){
        assertThat(View.t("brTest").br().toString(), equalTo("brTest<br/>"));
    }

    @Test
    public void simpleViewTest() {
        Html simple = View.h1(View.t("Hello")).
                            p(View.t("World"));
        assert (simple.toString().equals("<h1>Hello</h1><p>World</p>"));
    }

    @Test
    public void testEmptyHtml() {
        Html emptyHtml = View.empty();
        assertEquals("", emptyHtml.toString());
    }

    @Test
    public void testSingleTag() {
        Html pTag = View.p(new Html("Hello, World!"));
        assertEquals("<p>Hello, World!</p>", pTag.toString());
    }

    @Test
    public void h1() {
        assertThat(View.h1(View.t("foo")), hasToString("<h1>foo</h1>"));
    }
    
}