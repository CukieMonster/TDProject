package items;

import java.util.LinkedList;

import static items.ItemParameters.INVENTORY_SIZE;
import static items.ItemParameters.MAX_ACTIVE_ITEMS;

public class Inventory {
    private Item[] items = new Item[INVENTORY_SIZE];
    private int numberOfItems = 0;
    //LinkedList<Item> activeItems = new LinkedList<>();
    Item[] activeItems = new Item[MAX_ACTIVE_ITEMS];

    public boolean addItem(Item item) {
        if (numberOfItems < INVENTORY_SIZE) {
            items[numberOfItems++] = item;
            return true;
        }
        return false;
    }

    public boolean deleteItem(Item item) {
        int index = -1;
        for (int i = 0; i < MAX_ACTIVE_ITEMS; i++) {
            if (activeItems[i] == item) {
                activeItems[i] = null;
            }
        }
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (items[i] == item) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        for (int i = index; i < numberOfItems; i++) {
            if (numberOfItems - i == 1) break;
            items[i] = items[i + 1];
        }
        items[--numberOfItems] = null;
        return true;
    }

    public boolean addActiveItem(Item item) {
        for (int i = 0; i < MAX_ACTIVE_ITEMS; i++) {
            if (activeItems[i] == null) {
                activeItems[i] = item;
                return true;
            }
        }
        return false;
    }

    public boolean removeActiveItem(Item item) {
        for (int i = 0; i < MAX_ACTIVE_ITEMS; i++) {
            if (activeItems[i] == item) {
                activeItems[i] = null;
                return true;
            }
        }
        return false;
    }

    public boolean swapOrder(Item i1, Item i2) {
        return false;
    }
}
