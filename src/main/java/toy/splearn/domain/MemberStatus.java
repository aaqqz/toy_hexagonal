package toy.splearn.domain;

public enum MemberStatus {
    PENDING, ACTIVE, DEACTIVATED;


    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
