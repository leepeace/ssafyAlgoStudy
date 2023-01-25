import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int R, C, K, W;
	static int[][] map;
	static ArrayList<Point> checkList;
	static boolean[][][] walls;
	static int ans;
	static ArrayList<Point>[] machines;

	static class Point {
		int i, j;

		public Point(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}
	}

	public static boolean checkTemperature() {
		for (Point check : checkList) {
			if (map[check.i][check.j] < K)
				return false;
		}
		return true;
	}

	public static int[][] getClone() {
		int[][] clone = new int[R][C];
		for (int i = 0; i < R; i++)
			clone[i] = map[i].clone();
		return clone;
	}
	
	// 오른쪽으로 온풍기가 불때 row, col위치가 벽에 가로막혔는지 확인.
	public static boolean checkRightWalls(int[][] tempMap, int row, int col) {
		
		// 바로 왼쪽에서 바람이 올때
		if(inBoundary(row, col-1) && tempMap[row][col-1] > 0 && !walls[1][row][col-1])
			return true;
		
		// 좌측하단에서 바람이 올때
		if(inBoundary(row+1, col-1) && tempMap[row+1][col-1] > 0
				&& !walls[0][row+1][col-1] && !walls[1][row][col-1])
			return true;
		
		// 좌측 상단에서 바람이 올때
		if(inBoundary(row-1, col-1) && tempMap[row-1][col-1] > 0
				&& !walls[0][row][col-1] && !walls[1][row][col-1])
			return true;
		
		return false;
		
	}
	

	public static void blow() {

		// 온풍기에서 오른쪽으로 바람이 한 번 나온다.
		for (Point right : machines[1]) {
			System.out.println(right.i +" "+right.j+" 에서 오른쪽으로 바람이 분다.");
			for (int j = 1; j <= 5; j++) { // 우측으로 한칸 이동
				int row = right.i - j + 1;
				int col = right.j + j;

				int count = 2 * j - 1; // 1, 3, 5, 7, 9칸에 영향
				int temperature = 6 - j;
				while (count-- > 0 && inBoundary(row, col)) { // 각 열마다 온도 증가
					if(checkRightWalls(map, row, col)) {
						map[row][col] += temperature;

						for (int ti = 1; ti <= R; ti++) {
							for (int tj = 1; tj <= C; tj++) {
								System.out.print(map[ti][tj]+" ");
							}
							System.out.println();
						}
						
						System.out.println();
					}
					row++;
				}
			}
			
		}
		
		

		
		for (Point left : machines[2]) {

		}
		
		for (Point up : machines[3]) {

		}
		
		for (Point down : machines[4]) {

		}
	}

	public static boolean inBoundary(int i, int j) {
		return 0 <= i && 0 <= j && i < R && j < C;
	}

	public static void solve() {
		blow();
		// adjust();
		// decrease();
		// eatChocolate();
		if (checkTemperature())
			return;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[R + 1][C + 1];
		checkList = new ArrayList<>();
		machines = new ArrayList[5];

		for (int i = 1; i < 5; i++)
			machines[i] = new ArrayList();

		for (int i = 1; i <= R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= C; j++) {
				int num = Integer.parseInt(st.nextToken());
				map[i][j] = num;
				if (num == 5) {
					checkList.add(new Point(i, j));
					map[i][j] = 0;
				}
				else if (num > 0) {
					machines[num].add(new Point(i, j));
				}
			}
		}

		W = Integer.parseInt(br.readLine());
		walls = new boolean[2][R + 1][C + 1];
		for (int i = 0; i < W; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			walls[t][x][y] = true;
		}

		ans = 0;
		solve();
		for (int ti = 1; ti <= R; ti++) {
			for (int tj = 1; tj <= C; tj++) {
				System.out.print(map[ti][tj]+" ");
			}
			System.out.println();
		}
		System.out.println(ans);
	}
}