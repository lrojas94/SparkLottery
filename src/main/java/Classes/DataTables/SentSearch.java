package Classes.DataTables;

import com.google.gson.annotations.Expose;

/**
 * Created by MEUrena on 7/1/16.
 * All rights reserved.
 */
public class SentSearch {
    @Expose
    private String search;
    @Expose
    private boolean regex;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }
}
