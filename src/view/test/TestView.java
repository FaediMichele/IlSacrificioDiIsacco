package view.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import view.enumeration.BasicPlayerMenuEnum;
import view.enumeration.PlayerMenuEnum;

/**
 * 
 * Test for view.
 *
 */
@SuppressWarnings("all")
public class TestView {
    /**
     *  .
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    @Test
    public void testLoadEnumFromString() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        final String enumerationClass = "view.enumeration.BasicPlayerMenuEnum";
        final String value = "ISAAC";
        final Enum<? extends PlayerMenuEnum> enumObjet = Enum.valueOf((Class<Enum>) Class.forName(enumerationClass), value);
        assertEquals(BasicPlayerMenuEnum.ISAAC, enumObjet);
    }
}
