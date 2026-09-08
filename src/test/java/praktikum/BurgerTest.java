package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BurgerTest {
    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Mock
    private Ingredient mockIngredient3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();

        when(mockBun.getName()).thenReturn("test bun");
        when(mockBun.getPrice()).thenReturn(100f);

        when(mockIngredient1.getPrice()).thenReturn(50f);
        when(mockIngredient1.getName()).thenReturn("ingredient1");
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);

        when(mockIngredient2.getPrice()).thenReturn(75f);
        when(mockIngredient2.getName()).thenReturn("ingredient2");
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);

        when(mockIngredient3.getPrice()).thenReturn(25f);
        when(mockIngredient3.getName()).thenReturn("ingredient3");
        when(mockIngredient3.getType()).thenReturn(IngredientType.SAUCE);
    }

    //setBuns

    @Test
    void testSetBuns() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    void testSetBunsMultipleTimes() {
        Bun anotherBun = mock(Bun.class);
        when(anotherBun.getName()).thenReturn("another bun");
        when(anotherBun.getPrice()).thenReturn(200f);

        burger.setBuns(mockBun);
        burger.setBuns(anotherBun);

        assertEquals(anotherBun, burger.bun);
        assertNotEquals(mockBun, burger.bun);
    }

    //addIngredient

    @Test
    void testAddIngredient() {
        burger.addIngredient(mockIngredient1);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    void testAddMultipleIngredients() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        assertEquals(3, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
        assertEquals(mockIngredient3, burger.ingredients.get(2));
    }

    //removeIngredient

    @Test
    void testRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    void testRemoveLastIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.removeIngredient(2);

        assertEquals(2, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
    }

    @Test
    void testRemoveMiddleIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient3, burger.ingredients.get(1));
    }

    @Test
    void testRemoveIngredientFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            burger.removeIngredient(0);
        });
    }

    //moveIngredient

    @Test
    void testMoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(2, 0);

        assertEquals(mockIngredient3, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
        assertEquals(mockIngredient2, burger.ingredients.get(2));
    }

    @Test
    void testMoveIngredientToSamePosition() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(1, 1);

        assertEquals(mockIngredient1, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
        assertEquals(mockIngredient3, burger.ingredients.get(2));
    }

    @Test
    void testMoveIngredientToLastPosition() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(0, 2);

        assertEquals(mockIngredient2, burger.ingredients.get(0));
        assertEquals(mockIngredient3, burger.ingredients.get(1));
        assertEquals(mockIngredient1, burger.ingredients.get(2));
    }

    @Test
    void testMoveIngredientToFirstPosition() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(2, 0);

        assertEquals(mockIngredient3, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
        assertEquals(mockIngredient2, burger.ingredients.get(2));
    }

    //getPrice

    @ParameterizedTest
    @MethodSource("provideBunAndIngredientsForPrice")
    void testGetPriceWithMocks(float bunPrice, float ingredient1Price,
                               float ingredient2Price, float expectedPrice) {
        when(mockBun.getPrice()).thenReturn(bunPrice);
        when(mockIngredient1.getPrice()).thenReturn(ingredient1Price);
        when(mockIngredient2.getPrice()).thenReturn(ingredient2Price);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    private static Stream<Arguments> provideBunAndIngredientsForPrice() {
        return Stream.of(
                Arguments.of(100f, 50f, 75f, 325f),
                Arguments.of(200f, 100f, 150f, 650f),
                Arguments.of(50f, 25f, 30f, 155f),
                Arguments.of(0f, 50f, 75f, 125f),
                Arguments.of(-100f, 50f, 75f, -75f)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 5})
    void testGetPriceWithDifferentIngredientCount(int ingredientCount) {
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getPrice()).thenReturn(50f);

        burger.setBuns(mockBun);

        for (int i = 0; i < ingredientCount; i++) {
            burger.addIngredient(mockIngredient1);
        }

        float expectedPrice = 200f + 50f * ingredientCount;
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    @Test
    void testGetPriceWithRealObjects() {
        Bun realBun = new Bun("black bun", 100);
        Ingredient realIngredient1 = new Ingredient(IngredientType.SAUCE, "hot sauce", 50);
        Ingredient realIngredient2 = new Ingredient(IngredientType.FILLING, "cutlet", 75);

        burger.setBuns(realBun);
        burger.addIngredient(realIngredient1);
        burger.addIngredient(realIngredient2);

        assertEquals(325, burger.getPrice(), 0.001);
    }

    //getReceipt

    @Test
    void testGetReceiptWithMocks() {
        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100f);
        when(mockIngredient1.getName()).thenReturn("hot sauce");
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getPrice()).thenReturn(100f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"),
                "Чек должен содержать верхнюю часть булочки");

        assertTrue(receipt.contains("= sauce hot sauce ="),
                "Чек должен содержать информацию о соусе");

        assertTrue(receipt.contains("Price:"), "Чек должен содержать строку Price:");
        assertTrue(receipt.contains("300"), "Цена должна быть 300");

        verify(mockBun, atLeast(2)).getName();
        verify(mockIngredient1, atLeast(1)).getName();
        verify(mockIngredient1, atLeast(1)).getType();
    }

    @Test
    void testGetReceiptWithMultipleIngredients() {
        when(mockBun.getName()).thenReturn("white bun");
        when(mockBun.getPrice()).thenReturn(150f);
        when(mockIngredient1.getName()).thenReturn("cutlet");
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient1.getPrice()).thenReturn(100f);
        when(mockIngredient2.getName()).thenReturn("hot sauce");
        when(mockIngredient2.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient2.getPrice()).thenReturn(50f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= filling cutlet ="),
                "Чек должен содержать начинку");
        assertTrue(receipt.contains("= sauce hot sauce ="),
                "Чек должен содержать соус");

        assertTrue(receipt.contains("Price:"), "Чек должен содержать строку Price:");
        assertTrue(receipt.contains("450"), "Цена должна быть 450");
    }

    @Test
    void testGetReceiptWithRealObjects() {
        Bun realBun = new Bun("black bun", 100);
        Ingredient realIngredient = new Ingredient(IngredientType.SAUCE, "hot sauce", 100);

        burger.setBuns(realBun);
        burger.addIngredient(realIngredient);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"),
                "Чек должен содержать булочку");
        assertTrue(receipt.contains("= sauce hot sauce ="),
                "Чек должен содержать соус");

        assertTrue(receipt.contains("Price:"), "Чек должен содержать строку Price:");
        assertTrue(receipt.contains("300"), "Цена должна быть 300");
    }

    @Test
    void testGetReceiptWithoutIngredients() {
        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100f);

        burger.setBuns(mockBun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"),
                "Чек должен содержать булочку");

        assertTrue(receipt.contains("Price:"), "Чек должен содержать строку Price:");
        assertTrue(receipt.contains("200"), "Цена должна быть 200");

        int count = receipt.split("\\(==== black bun ====\\)").length - 1;
        assertEquals(2, count, "Должно быть два вхождения булочки");
    }


    @Test
    void testBurgerConstructor() {
        Burger newBurger = new Burger();
        assertNull(newBurger.bun);
        assertNotNull(newBurger.ingredients);
        assertEquals(0, newBurger.ingredients.size());
    }

    @Test
    void testAddAndRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        assertEquals(1, burger.ingredients.size());

        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    void testMoveAndRemoveIngredient() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.addIngredient(mockIngredient3);

        burger.moveIngredient(2, 0);
        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
        assertEquals(mockIngredient3, burger.ingredients.get(0));
        assertEquals(mockIngredient2, burger.ingredients.get(1));
    }

    @Test
    void testAddNullIngredient() {
        burger.addIngredient(null);
        assertEquals(1, burger.ingredients.size());
        assertNull(burger.ingredients.get(0));
    }

    @Test
    void testGetPriceWithNullBun() {
        burger.setBuns(null);
        burger.addIngredient(mockIngredient1);
        assertThrows(NullPointerException.class, () -> burger.getPrice());
    }

    @Test
    void testGetReceiptWithNullBun() {
        burger.setBuns(null);
        burger.addIngredient(mockIngredient1);
        assertThrows(NullPointerException.class, () -> burger.getReceipt());
    }
}