package com.littlepay.trip.planner.domain.model;

import java.util.Objects;

public record Tuple<V1, V2>(V1 v1, V2 v2) {

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final Tuple<?, ?> other)) return false;
        final Object a1 = this.v1();
        final Object b1 = other.v1();
        final Object a2 = this.v2();
        final Object b2 = other.v2();
        return (Objects.equals(a1, b1) && Objects.equals(a2, b2)) || (Objects.equals(a1, b2) && Objects.equals(a2, b1));
    }

    public int hashCode() {
        final int PRIME = 59;
        final Object $t1 = this.v1();
        final Object $t2 = this.v2();
        return PRIME + ($t1 == null ? 43 : $t1.hashCode()) + ($t2 == null ? 43 : $t2.hashCode());
    }
}
