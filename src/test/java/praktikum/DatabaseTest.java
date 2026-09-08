package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


class DatabaseTest {
    private Database database;


    @BeforeEach
    void setUp() {
        database = new Database();
    }


     //Тест создания
    @Test
    void testConstructor() {
        assertNotNull(database);

        List<Bun> buns = database.availableBuns();
        List<Ingredient> ingredients = database.availableIngredients();

        assertNotNull(buns, "Список булочек не должен быть null");
        assertNotNull(ingredients, "Список ингредиентов не должен быть null");

        assertEquals(3, buns.size(), "Должно быть 3 булочки");
        assertEquals(6, ingredients.size(), "Должно быть 6 ингредиентов");
    }


    //метод availableBuns()
     @Test
    void testAvailableBuns() {
        List<Bun> buns = database.availableBuns();

        assertNotNull(buns);
        assertEquals(3, buns.size());

        Bun bun1 = buns.get(0);
        assertEquals("black bun", bun1.getName());
        assertEquals(100, bun1.getPrice());

        Bun bun2 = buns.get(1);
        assertEquals("white bun", bun2.getName());
        assertEquals(200, bun2.getPrice());

        Bun bun3 = buns.get(2);
        assertEquals("red bun", bun3.getName());
        assertEquals(300, bun3.getPrice());
    }


   //availableIngredients()
    @Test
    void testAvailableIngredients() {
        // Получаем список ингредиентов
        List<Ingredient> ingredients = database.availableIngredients();

        // Проверяем, что список не null и содержит 6 элементов
        assertNotNull(ingredients);
        assertEquals(6, ingredients.size());

        // Проверяем содержимое ингредиентов
        // Соусы (первые три)
        Ingredient sauce1 = ingredients.get(0);
        assertEquals(IngredientType.SAUCE, sauce1.getType());
        assertEquals("hot sauce", sauce1.getName());
        assertEquals(100, sauce1.getPrice());

        Ingredient sauce2 = ingredients.get(1);
        assertEquals(IngredientType.SAUCE, sauce2.getType());
        assertEquals("sour cream", sauce2.getName());
        assertEquals(200, sauce2.getPrice());

        Ingredient sauce3 = ingredients.get(2);
        assertEquals(IngredientType.SAUCE, sauce3.getType());
        assertEquals("chili sauce", sauce3.getName());
        assertEquals(300, sauce3.getPrice());

        // Начинки (последние три)
        Ingredient filling1 = ingredients.get(3);
        assertEquals(IngredientType.FILLING, filling1.getType());
        assertEquals("cutlet", filling1.getName());
        assertEquals(100, filling1.getPrice());

        Ingredient filling2 = ingredients.get(4);
        assertEquals(IngredientType.FILLING, filling2.getType());
        assertEquals("dinosaur", filling2.getName());
        assertEquals(200, filling2.getPrice());

        Ingredient filling3 = ingredients.get(5);
        assertEquals(IngredientType.FILLING, filling3.getType());
        assertEquals("sausage", filling3.getName());
        assertEquals(300, filling3.getPrice());
    }


    //availableBuns()
    @Test
    void testAvailableBunsReturnsSameList() {
        List<Bun> buns1 = database.availableBuns();
        List<Bun> buns2 = database.availableBuns();

        assertSame(buns1, buns2, "availableBuns() должен возвращать ту же коллекцию");

        Bun newBun = new Bun("test bun", 999);
        buns1.add(newBun);
        assertEquals(4, buns2.size(), "Размер должен измениться в обоих списках");
    }

   //availableIngredients()
    @Test
    void testAvailableIngredientsReturnsSameList() {
        List<Ingredient> ingredients1 = database.availableIngredients();
        List<Ingredient> ingredients2 = database.availableIngredients();

        assertSame(ingredients1, ingredients2,
                "availableIngredients() должен возвращать ту же коллекцию");

        Ingredient newIngredient = new Ingredient(IngredientType.SAUCE, "test sauce", 999);
        ingredients1.add(newIngredient);
        assertEquals(7, ingredients2.size(), "Размер должен измениться в обоих списках");
    }


    //проверка изменений
        @Test
    void testDatabaseDataConsistency() {
        // Получаем данные
        List<Bun> buns = database.availableBuns();
        List<Ingredient> ingredients = database.availableIngredients();

        Database newDatabase = new Database();
        List<Bun> newBuns = newDatabase.availableBuns();
        List<Ingredient> newIngredients = newDatabase.availableIngredients();

        assertEquals(buns.size(), newBuns.size(),
                "Размер списка булочек должен совпадать");
        assertEquals(ingredients.size(), newIngredients.size(),
                "Размер списка ингредиентов должен совпадать");

        assertEquals(buns.get(0).getName(), newBuns.get(0).getName());
        assertEquals(buns.get(0).getPrice(), newBuns.get(0).getPrice());

        assertEquals(ingredients.get(0).getType(), newIngredients.get(0).getType());
        assertEquals(ingredients.get(0).getName(), newIngredients.get(0).getName());
        assertEquals(ingredients.get(0).getPrice(), newIngredients.get(0).getPrice());
    }

    //не null
    @Test
    void testDatabaseListsAreInitialized() {
        // Создаем новую базу данных
        Database newDb = new Database();

        List<Bun> buns = newDb.availableBuns();
        List<Ingredient> ingredients = newDb.availableIngredients();

        assertNotNull(buns, "Список булочек должен быть инициализирован");
        assertNotNull(ingredients, "Список ингредиентов должен быть инициализирован");

        assertFalse(buns.isEmpty(), "Список булочек не должен быть пустым");
        assertFalse(ingredients.isEmpty(), "Список ингредиентов не должен быть пустым");
    }
}