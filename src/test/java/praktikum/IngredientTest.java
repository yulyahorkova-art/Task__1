package praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;


class IngredientTest {
    private Ingredient sauce;
    private Ingredient filling;


    @BeforeEach
    void setUp() {
        sauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100);
        filling = new Ingredient(IngredientType.FILLING, "cutlet", 150);
    }

    //getPrice()
    @Test
    void testGetPrice() {
        assertEquals(100, sauce.getPrice());
        assertEquals(150, filling.getPrice());
    }

    //getName()
    @Test
    void testGetName() {
        assertEquals("hot sauce", sauce.getName());
        assertEquals("cutlet", filling.getName());
    }

    //getType()
    @Test
    void testGetType() {
        assertEquals(IngredientType.SAUCE, sauce.getType());
        assertEquals(IngredientType.FILLING, filling.getType());
    }

    //Ingredient(IngredientType type, String name, float price).
    @Test
    void testIngredientConstructor() {
         Ingredient newIngredient = new Ingredient(IngredientType.SAUCE, "chili sauce", 200);

        assertEquals(IngredientType.SAUCE, newIngredient.getType());
        assertEquals("chili sauce", newIngredient.getName());
        assertEquals(200, newIngredient.getPrice());
    }

    //Пустое имя
     @Test
    void testIngredientWithEmptyName() {
        Ingredient emptyName = new Ingredient(IngredientType.FILLING, "", 50);
        assertEquals("", emptyName.getName());
        assertEquals(IngredientType.FILLING, emptyName.getType());
        assertEquals(50, emptyName.getPrice());
    }
}