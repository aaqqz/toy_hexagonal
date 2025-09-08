package toy.splearn.domain.member;

public enum MemberStatus {
    PENDING, ACTIVE, DEACTIVATED;


    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
