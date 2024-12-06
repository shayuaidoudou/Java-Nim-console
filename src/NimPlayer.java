/**
 * @author 鲨鱼爱兜兜
 * @date 2024/12/6 17:53
 */

public class NimPlayer {
    private final String username; // 玩家昵称，唯一且不可修改
    private String familyName;     // 玩家姓氏
    private String givenName;      // 玩家名字
    private int gamesPlayed;       // 玩过的游戏数量
    private int gamesWon;          // 赢过的游戏数量

    public NimPlayer(String username, String familyName, String givenName) {
        this.username = username;
        this.familyName = familyName;
        this.givenName = givenName;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    // 获取用户名
    public String getUsername() {
        return username;
    }

    // 获取姓氏
    public String getFamilyName() {
        return familyName;
    }

    // 设置姓氏
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    // 获取名字
    public String getGivenName() {
        return givenName;
    }

    // 设置名字
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    // 获取玩过的游戏数量
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // 获取赢过的游戏数量
    public int getGamesWon() {
        return gamesWon;
    }

    // 增加游戏数据
    public void recordGame(boolean won) {
        gamesPlayed++;
        if (won) {
            gamesWon++;
        }
    }

    // 重置比赛数据
    public void resetStats() {
        gamesPlayed = 0;
        gamesWon = 0;
    }

    // 计算胜率
    public double getWinRate() {
        if (gamesPlayed == 0) {
            return 0.0;
        }
        return (double) gamesWon / gamesPlayed;
    }

    // 输出玩家信息
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%d games,%d wins",
                username, givenName, familyName, gamesPlayed, gamesWon);
    }
}
