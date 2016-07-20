package Classes.Data;

import com.google.gson.annotations.Expose;

/**
 * Created by MEUrena on 7/19/16.
 * All rights reserved.
 */
public class JsonGame {
    @Expose
    private String username;
    @Expose
    private String gameType;
    @Expose
    private String numbers;
    @Expose
    private double amount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
