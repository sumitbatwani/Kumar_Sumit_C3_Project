import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    @BeforeEach
    public void beforeEach() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(LocalTime.of(11,00));

        assertEquals(true, mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(LocalTime.of(23,00));

        assertEquals(false, mockRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void get_menu_should_display_restaurant_menu() {
        assertEquals("[Sweet corn soup:119\n" +
                ", Vegetable lasagne:269\n" +
                "]", restaurant.getMenu().toString());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<ITEM SELECTION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void select_menu_items_should_display_total_cost_of_selected_items() throws itemNotFoundException {
        ArrayList<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        String totalCost = restaurant.selectMenuItems(selectedItems);
        assertEquals("Your order will cost: ₹388", totalCost);
    }

    @Test
    public void select_menu_items_should_throw_item_not_found_exception_when_selection_item_is_unavailable() {
        ArrayList<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Bread Slice");

        assertThrows(itemNotFoundException.class, () -> restaurant.selectMenuItems(selectedItems));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<ITEM SELECTION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}