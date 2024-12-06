/**
 * @author 鲨鱼爱兜兜
 * @date 2024/12/6 17:53
 */

import java.util.Scanner;

public class GameRound {
    private int stonesLeft;        // 当前剩余的石子数量
    private final int upperBound; // 每次移动石子的上限
    private final NimPlayer player1; // 玩家1
    private final NimPlayer player2; // 玩家2

    // 构造方法
    public GameRound(int initialStones, int upperBound, NimPlayer player1, NimPlayer player2) {
        this.stonesLeft = initialStones;
        this.upperBound = upperBound;
        this.player1 = player1;
        this.player2 = player2;
    }

    // 开始游戏
    public void play() {
        Scanner scanner = new Scanner(System.in);
        NimPlayer currentPlayer = player1;
        NimPlayer nextPlayer = player2;

        // 输出初始游戏信息
        System.out.println("Initial stone count: " + stonesLeft);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.println("Player 1: " + player1.getGivenName() + " " + player1.getFamilyName());
        System.out.println("Player 2: " + player2.getGivenName() + " " + player2.getFamilyName());

        // 游戏主循环
        while (stonesLeft > 0) {
            // 输出剩余石子数
            System.out.print(stonesLeft + " stones left: ");
            for (int i = 0; i < stonesLeft; i++) {
                System.out.print("*");
            }
            System.out.println();

            // 当前玩家的回合
            System.out.print(currentPlayer.getGivenName() + "'s turn - remove how many? ");
            int stonesToRemove = scanner.nextInt();

            // 检查玩家输入的石子数量是否合法
            if (stonesToRemove < 1 || stonesToRemove > Math.min(upperBound, stonesLeft)) {
                System.out.println("Invalid move. You must remove between 1 and " + Math.min(upperBound, stonesLeft) + " stones.");
                continue; // 重新让当前玩家输入
            }

            // 更新剩余石子数量
            stonesLeft -= stonesToRemove;

            // 检查游戏是否结束
            if (stonesLeft == 0) {
                System.out.println("Game Over");
                System.out.println(currentPlayer.getGivenName() + " " + currentPlayer.getFamilyName() + " wins!");
                currentPlayer.recordGame(true); // 当前玩家胜
                nextPlayer.recordGame(false);  // 下一个玩家败
                return;
            }

            // 胜利判定在轮换玩家之前
            if (stonesLeft == 0) {
                System.out.println("Game Over");
                System.out.println(currentPlayer.getGivenName() + " " + currentPlayer.getFamilyName() + " wins!");
                currentPlayer.recordGame(true);
                nextPlayer.recordGame(false);
                return;
            }

            // 玩家轮换
            NimPlayer temp = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = temp;

        }
    }
}