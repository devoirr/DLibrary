package me.kervand.library.gui;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {

    private final List<T> list;
    private final int itemsPerPage;

    private final int totalPages;

    public Pagination(List<T> list, int itemsPerPage) {
        this.list = list;
        this.itemsPerPage = itemsPerPage;

        totalPages = (int) Math.ceil((list.size() * 1.0) / itemsPerPage);
    }

    // first is 0
    public List<T> getPage(int page) {
        if (page > totalPages) {
            return new ArrayList<>();
        }

        int startIndex = page * itemsPerPage;
        int endIndex = startIndex + itemsPerPage - 1;

        List<T> items = new ArrayList<>();
        if (endIndex >= list.size()) {
            endIndex = list.size() - 1;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            items.add(list.get(i));
        }

        return items;
    }

    public int size() {
        return list.size();
    }

    public boolean hasPage(int page) {
        return page < totalPages;
    }

}
