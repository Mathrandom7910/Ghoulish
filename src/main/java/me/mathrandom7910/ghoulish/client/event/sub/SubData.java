package me.mathrandom7910.ghoulish.client.event.sub;

public record SubData<T, K>(T data, K ci) {

    public SubData(T data) {
        this(data, null);
    }
}
