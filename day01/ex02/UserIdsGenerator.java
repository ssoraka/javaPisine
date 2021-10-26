public class UserIdsGenerator {
    private Integer lastId;

    private static UserIdsGenerator userIdsGenerator;

    private UserIdsGenerator() {
        lastId = new Integer(0);
    }

    public static UserIdsGenerator getInstance() {
        if (userIdsGenerator == null) {
            userIdsGenerator = new UserIdsGenerator();
        }
        return userIdsGenerator;
    }

    public Integer generateId() {
        return lastId++;
    }
}
