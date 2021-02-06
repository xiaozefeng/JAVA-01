package io.github.mickey.concurrency.wait.concurrency.concurrencycontainer;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class RouterTable {
    public static class Route {
        String ip;
        String port;
        String iface;

        public Route(String ip, String port, String iface) {
            this.ip = ip;
            this.port = port;
            this.iface = iface;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Route route = (Route) o;
            if (!Objects.equals(ip, route.ip)) return false;
            if (!Objects.equals(port, route.port)) return false;
            return Objects.equals(iface, route.iface);
        }

        @Override
        public int hashCode() {
            int result = ip != null ? ip.hashCode() : 0;
            result = 31 * result + (port != null ? port.hashCode() : 0);
            result = 31 * result + (iface != null ? iface.hashCode() : 0);
            return result;
        }

    }

    private final Map<String, Set<Route>> table
            = new ConcurrentHashMap<>();

    public Set<Route> get(String iface) {
        return table.get(iface);
    }

    public void remove(Route route) {
        Set<Route> routes = table.get(route.iface);
        routes.remove(route);
    }

    public void add(Route route) {
        Set<Route> routes = table.computeIfAbsent(route.iface, r -> new CopyOnWriteArraySet<>());
        routes.add(route);
    }


}


