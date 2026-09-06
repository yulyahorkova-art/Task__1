package praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


//Проверяем конструктор и все геттеры.

class BunTest {
    private Bun bun;

    @BeforeEach
    void setUp() {
        bun = new Bun("black bun", 100);
    }


    //getName() возвращает правильное имя булочки
    @Test
    void testGetName() {
        assertEquals("black bun", bun.getName());
    }


     //getPrice() возвращает правильную цену булочки
    @Test
    void testGetPrice() {
        assertEquals(100, bun.getPrice());
    }


    //конструктор Bun(String name, float price)
    @Test
    void testBunConstructor() {
        Bun newBun = new Bun("white bun", 200);

        assertEquals("white bun", newBun.getName());
        assertEquals(200, newBun.getPrice());

        assertNotSame(bun, newBun);
    }


     //граничные значения цены булочки + отриц.значения + пустое имя
    @Test
    void testBunWithEdgeValues() {
        // Булочка с нулевой ценой
        Bun zeroPriceBun = new Bun("free bun", 0);
        assertEquals(0, zeroPriceBun.getPrice());

        // Булочка с отрицательной ценой (хотя это странно, но проверим)
        Bun negativePriceBun = new Bun("negative bun", -100);
        assertEquals(-100, negativePriceBun.getPrice());

        // Булочка с пустым именем
        Bun emptyNameBun = new Bun("", 50);
        assertEquals("", emptyNameBun.getName());
    }
}