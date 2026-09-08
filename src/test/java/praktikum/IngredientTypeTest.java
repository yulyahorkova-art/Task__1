package praktikum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IngredientTypeTest {

    //проверка перечислений - 2
    @Test
    void testEnumValues() {
        IngredientType[] types = IngredientType.values();

        assertEquals(2, types.length);

        assertTrue(containsType(types, IngredientType.SAUCE));
        assertTrue(containsType(types, IngredientType.FILLING));
    }


    private boolean containsType(IngredientType[] types, IngredientType type) {
        for (IngredientType t : types) {
            if (t == type) return true;
        }
        return false;
    }

    //valueOf()
        @Test
    void testEnumValueOf() {
        assertEquals(IngredientType.SAUCE, IngredientType.valueOf("SAUCE"));
        assertEquals(IngredientType.FILLING, IngredientType.valueOf("FILLING"));
    }

    //ordinal()
    @Test
    void testEnumOrdinal() {
        assertEquals(0, IngredientType.SAUCE.ordinal());
        assertEquals(1, IngredientType.FILLING.ordinal());
    }

    //name()
    @Test
    void testEnumName() {
        assertEquals("SAUCE", IngredientType.SAUCE.name());
        assertEquals("FILLING", IngredientType.FILLING.name());
    }

    //несуществующее значение
    @Test
    void testEnumValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            IngredientType.valueOf("INVALID_TYPE");
        });
    }
}