package praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class PraktikumTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    //main()/
    @Test
    void testMain() {
        Praktikum.main(new String[]{});

        String output = outContent.toString();
        assertNotNull(output);
        assertFalse(output.isEmpty(), "Вывод не должен быть пустым");

        assertTrue(output.contains("(==== black bun ====)"),
                "Вывод должен содержать булочку");
        assertTrue(output.contains("Price:"),
                "Вывод должен содержать цену");
    }

    //создание БД
    @Test
    void testMainUsesDatabase() {
        Praktikum.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("black bun"),
                "Должна использоваться булочка из базы данных");

        String lowerOutput = output.toLowerCase();

        assertTrue(lowerOutput.contains("sour cream") || lowerOutput.contains("sourcream"),
                "Должен использоваться ингредиент 'sour cream' из базы данных");
        assertTrue(lowerOutput.contains("dinosaur"),
                "Должен использоваться ингредиент 'dinosaur' из базы данных");
        assertTrue(lowerOutput.contains("cutlet"),
                "Должен использоваться ингредиент 'cutlet' из базы данных");
        assertTrue(lowerOutput.contains("sausage") == false,
                "Ингредиент 'sausage' должен быть удален");
    }

    @Test
    void testMainCreatesBurger() {
        Praktikum.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("(==== black bun ====)"),
                "Вывод должен содержать верхнюю часть булочки");

        String lowerOutput = output.toLowerCase();

        assertTrue(lowerOutput.contains("sour cream") || lowerOutput.contains("sourcream"),
                "Должен быть ингредиент 'sour cream'");
        assertTrue(lowerOutput.contains("dinosaur"),
                "Должен быть ингредиент 'dinosaur'");
        assertTrue(lowerOutput.contains("cutlet"),
                "Должен быть ингредиент 'cutlet'");
        assertFalse(lowerOutput.contains("sausage"),
                "Ингредиент 'sausage' должен быть удален");
    }

    //финальная цена
    @Test
    void testMainCalculatesCorrectPrice() {
        Praktikum.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("Price:"), "Вывод должен содержать цену");
        assertTrue(output.replace(",", ".").contains("700"),
                "Цена должна быть 700 (булочка 100*2 + 200 + 100 + 200)");
    }

    @Test
    void testMainRunsWithoutErrors() {
        assertDoesNotThrow(() -> Praktikum.main(new String[]{}),
                "Метод main() не должен выбрасывать исключений");
    }

    //форматы в чеке
    @Test
    void testMainOutputFormat() {
        Praktikum.main(new String[]{});

        String output = outContent.toString();

        assertTrue(output.contains("(==== black bun ====)"),
                "Чек должен начинаться с верхней части булочки");

        String lowerOutput = output.toLowerCase();
        assertTrue(lowerOutput.contains("sour cream") || lowerOutput.contains("sourcream"),
                "Должен быть соус 'sour cream'");
        assertTrue(lowerOutput.contains("cutlet"),
                "Должна быть начинка 'cutlet'");
        assertTrue(lowerOutput.contains("dinosaur"),
                "Должна быть начинка 'dinosaur'");

        int bunCount = output.split("\\(==== black bun ====\\)").length - 1;
        assertEquals(2, bunCount, "Булочка должна встречаться дважды (верх и низ)");

        assertTrue(output.replace(",", ".").contains("700"),
                "Цена должна быть 700");
    }

    //операции
    @Test
    void testMainUsesAllBurgerOperations() {
        Praktikum.main(new String[]{});

        String output = outContent.toString();
        String lowerOutput = output.toLowerCase();

        assertTrue(output.contains("black bun"), "Булочка должна быть установлена");

        assertTrue(lowerOutput.contains("sour cream") || lowerOutput.contains("sourcream"),
                "Ингредиенты должны быть добавлены");
        assertTrue(lowerOutput.contains("dinosaur"),
                "Ингредиенты должны быть добавлены");
        assertTrue(lowerOutput.contains("cutlet"),
                "Ингредиенты должны быть добавлены");

        assertFalse(lowerOutput.contains("sausage"),
                "Ингредиент должен быть удален");

        String[] lines = output.split(System.lineSeparator());
        boolean foundSourCream = false;
        boolean foundCutlet = false;
        boolean foundDinosaur = false;
        int sourCreamIndex = -1;
        int cutletIndex = -1;
        int dinosaurIndex = -1;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].toLowerCase();
            if (line.contains("sour cream") || line.contains("sourcream")) {
                foundSourCream = true;
                sourCreamIndex = i;
            }
            if (line.contains("cutlet")) {
                foundCutlet = true;
                cutletIndex = i;
            }
            if (line.contains("dinosaur")) {
                foundDinosaur = true;
                dinosaurIndex = i;
            }
        }

        assertTrue(foundSourCream, "sour cream должен быть найден");
        assertTrue(foundCutlet, "cutlet должен быть найден");
        assertTrue(foundDinosaur, "dinosaur должен быть найден");

        // Проверяем порядок
        if (sourCreamIndex != -1 && cutletIndex != -1 && dinosaurIndex != -1) {
            assertTrue(sourCreamIndex < cutletIndex,
                    "sour cream должен идти до cutlet");
            assertTrue(cutletIndex < dinosaurIndex,
                    "cutlet должен идти до dinosaur");
        }
    }
}