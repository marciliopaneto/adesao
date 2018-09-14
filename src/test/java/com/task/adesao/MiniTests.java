package com.task.adesao;

import com.task.adesao.constants.Ambiente;
import com.task.adesao.maker.Templates;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by marcus on 14/09/18.
 */
public class MiniTests {

    @Test
    public void TemplatesTest() {
        List<String> l1 = Templates.getHeader(Ambiente.PROD);
        Iterator it = l1.listIterator();
        while (it.hasNext())
            System.out.println(it.next());

        l1 = Templates.getHeader(Ambiente.HEXT);
        it = l1.listIterator();
        while (it.hasNext())
            System.out.println(it.next());
    }
}
